package com.server;
import java.util.*;

import com.common.Function;
import com.sist.dao.MemberDAO;
import com.sist.dao.MemberDTO;

import java.net.*;
import java.io.*;

public class Server implements Runnable {
	Vector<ClientThread> waitVc = new Vector<ClientThread>();
	Vector<Room>  roomVc=new Vector<Room>();	// 방에 입장해 있는 Client 저장용
	ServerSocket ss = null;	// 서버에서 접속시 처리 (교환  소켓)
	
	public Server(){
		try {
			MemberDAO dao=MemberDAO.newInstance();
			dao.memberAllUpdate();
			ss = new ServerSocket(9469);
			System.out.println("Server Start...");
		    } 
		catch (Exception ex) 
		{
			System.out.println(ex.getMessage());
		}
	}
	
	public void run() {
		//접속 처리
		while(true){
			try {
				// 클라이언트의 정보 => ip, port(Socket)
				Socket s = ss.accept();	// 클라이언트가 접속할때만 호출됨. 접속을 기다리고 있음
				// s => client
				ClientThread ct = new ClientThread(s);	// 전화기를 넘겨줌
				ct.start();	// 통신 시작
			} catch (Exception ex) {}
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 서버 가동
		Server server = new Server();
		new Thread(server).start();
	}
	
	class ClientThread extends Thread {
		String id, name, sex, pos ;
		int avata;
		
		Socket s;
		BufferedReader in;	// 받을 때는 2byte	(Reader)	client 요청값을 읽어온다
		OutputStream out;	// 보낼 때는 byte	(Stream)	client로 결과값을 응답할 때
		
		public ClientThread(Socket s){
			try {
				this.s = s;
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));	// byte --> 2byte로 변환 하여 값을 받는다.
				out = s.getOutputStream();		// 클라이언트가 원하는 값을 보낸다.
				
			} catch (Exception ex) {
				// TODO: handle exception
			}
		}
		
		// 통신 부분
		public void run(){
			while(true){
				try {
					String msg = in.readLine();		// 한줄씩 읽어들임	\n으로 구분한 이유	클라이언트가 보낸 값을 읽었다.
					System.out.println("Client=>" + msg);
					// 100|id|name|sex
					StringTokenizer st = new StringTokenizer(msg, "|");		// 구분해서 잘라넴
					int protocol = Integer.parseInt(st.nextToken());	// 번호 100번 잘라냄
					switch (protocol) {
					  case Function.LOGIN:
					  {
						  String userid=st.nextToken();
	    					String pwd=st.nextToken();
	    					
	    					MemberDAO dao=MemberDAO.newInstance();
	    					String res=dao.isLogin(userid, pwd);
	    					if(res.equals("NOID"))
	    					{
	    						messageTo(Function.NOID+"|"+userid);
	    					}
	    					else if(res.equals("NOPWD"))
	    					{
	    						messageTo(Function.NOPWD+"|");
	    					}
	    					else
	    					{
	    					  MemberDTO d=dao.memberInfoData(userid);
	    					  id=d.getId();
	    					  sex=d.getSex();
	    					  name=d.getName();
	    					  avata=d.getAvata();
	    					  pos="대기실";
	    					  int type=d.getType();
	    					  if(type==0)
	    					  {
	    					    // 대기실에 있는 사람에게 정보 전송
	    					    messageAll(Function.LOGIN+"|"+id+"|"
	    							+name+"|"+sex+"|"+pos);
	    					    // 저장
	    					    waitVc.addElement(this);
	    					    messageTo(Function.MYLOG+"|"+id);
	    					    for(ClientThread client:waitVc)
	    					    {
	    						  messageTo(Function.LOGIN+"|"
	    					            +client.id+"|"
		    							+client.name+"|"
	    					            +client.sex+"|"
		    							+client.pos);
	    					    }
	    					    dao.memberUpdate(id, 1);
	    					  }
	    					  else
	    					  {
	    						 messageTo(Function.MULTIID+"|"); 
	    					  }
	    				
	    					// 개설된 방 정보
	    					  for(Room room:roomVc)
	    					  {
	    						  messageTo(Function.MAKEROOM+"|"
		    							  +room.roomName+"|"+
		    							  room.roomState+"|"+
		    							  room.current+"/"+room.inwon);
	    					  }
	    					}
	    				}
	    				break;
						case Function.WAITCHAT:	// 채팅
						{
							String data = st.nextToken();
							messageAll(Function.WAITCHAT + "|[" + name + "]" + data);
						}
						break;
						
						case Function.MAKEROOM:
						{
	    					Room room=new Room(st.nextToken(),
	    							  st.nextToken(),st.nextToken(),
	    							  Integer.parseInt(st.nextToken()));
	    					
	    					messageAll(Function.MAKEROOM+"|"
	    							  +room.roomName+"|"+
	    							  room.roomState+"|"+
	    							  room.current+"/"+room.inwon);
	    					
	    					room.roomBang=id;
	    					pos=room.roomName;
	    					room.userVc.addElement(this);
	    					roomVc.addElement(room);
	    					messageTo(Function.MYROOMIN+"|"
	   							     +id+"|"+name+"|"
	   							     +sex+"|"+avata+"|"
	   							     +room.roomName+"|"
	   							     +room.roomBang);
	    					messageAll(Function.POSCHANGE+"|"+id+"|"+pos);
	    				}
	    				break;
						case Function.EXIT:
						{
							messageAll(Function.EXIT+"|"+id);
	    					messageTo(Function.MYCHATEND+"|");
	    					for(int i=0;i<waitVc.size();i++)
	    					{
	    						ClientThread client=waitVc.elementAt(i);
	    						if(id.equals(client.id))
	    						{
	    							waitVc.removeElementAt(i);
	    							in.close();
	    							out.close();
	    							break;
	    						}
	    					}
					}
					
				case Function.MYROOMIN:
				{
					/*
					 *   1. 방을 찾기
					 *   2. 위치변경
					 *   3. 현재인원 증가 
					 *   4. 들어가 있는 사람
					 *      = 들어가는 사람의 정보
					 *      = 입장메세지 
					 *   5. 들어가는 사람
					 *      = 대기실=>채팅방으로 변경
					 *      = 들어가 있는 사람들의 정보 
					 *   6. 대기실
					 */
					String rn=st.nextToken();
					for(Room room:roomVc)
					{
						if(rn.equals(room.roomName))
						{
							room.current++;
							pos=room.roomName;
							for(ClientThread client:room.userVc)
							{
								client.messageTo(Function.ROOMCHAT
										+"|[알림 ☞"+name+"님이 입장하셨습니다]");
								client.messageTo(Function.ROOMIN+"|"
		   							     +id+"|"+name+"|"
		   							     +sex+"|"+avata+"|"
		   							     +room.roomBang);
										
							}
							// 들어가는 사람 처리
							messageTo(Function.MYROOMIN+"|"
	   							     +id+"|"+name+"|"
	   							     +sex+"|"+avata+"|"
	   							     +room.roomName+"|"
	   							     +room.roomBang);
							room.userVc.addElement(this);
							for(ClientThread client:room.userVc)
							{
								if(!id.equals(client.id))
								{
									messageTo(Function.ROOMIN+"|"
    		   							     +client.id+"|"
											 +client.name+"|"
    		   							     +client.sex+"|"
											 +client.avata+"|"
    		   							     +room.roomBang);
								}
							}
							messageAll(Function.WAITUPDATE+"|"
									+room.roomName+"|"
									+room.current+"|"
									+room.inwon+"|"
									+id+"|"
									+pos);
						}
					}
				}
				break;

				case Function.ROOMOUT:
				{
					/*
					 *   1. 방을 찾기
					 *   2. 위치변경
					 *   3. 현재인원 증가 
					 *   4. 들어가 있는 사람
					 *      = 들어가는 사람의 정보
					 *      = 입장메세지 
					 *   5. 들어가는 사람
					 *      = 대기실=>채팅방으로 변경
					 *      = 들어가 있는 사람들의 정보 
					 *   6. 대기실
					 */
					String rn=st.nextToken();
					int i=0;
					for(Room room:roomVc)
					{
						if(rn.equals(room.roomName))
						{
							
							room.current--;
							pos="대기실";
							
							for(ClientThread client:room.userVc)
							{
								client.messageTo(Function.ROOMCHAT
										+"|[알림 ☞"+name+"님이 퇴장하셨습니다]");
								client.messageTo(Function.ROOMOUT+"|"+id+"|"+name);
										
							}
							
							if(id.equals(room.roomBang))
							{
								if(room.current!=0)
								{
									room.roomBang=room.userVc.elementAt(1).id;
									String str=room.userVc.elementAt(1).name;
									for(ClientThread client:room.userVc)
									{
										if(!id.equals(client.id))
										{
											client.messageTo(Function.BANGCHANGE+"|"+room.roomBang+"|"+str);
										}
									}
								}
							}
							// 들어가는 사람 처리
							int k=0;
							for(ClientThread client:room.userVc)
							{
								if(id.equals(client.id))
								{
									messageTo(Function.MYROOMOUT+"|");
									room.userVc.removeElementAt(k);
									
								}
								k++;
							}
							messageAll(Function.WAITUPDATE+"|"
									+room.roomName+"|"
									+room.current+"|"
									+room.inwon+"|"
									+id+"|"
									+pos);
							if(room.current<1)
							{
								roomVc.removeElementAt(i);
								break;
							}
						}
						i++;
					}
				}
				break;
				}
				}
					
				 catch (Exception ex) {}
			}
				 
		}
		
			
		
		
		// 개인적으로
	public synchronized void messageTo(String msg)
	{
		try
		{
			out.write((msg+"\n").getBytes());
		}catch(Exception ex)
		{
			for(int i=0;i<waitVc.size();i++)
			{
				ClientThread client=waitVc.elementAt(i);
				if(id.equals(client.id))
				{
					waitVc.removeElementAt(i);
					break;
				}
			}
		}
	}
	// 전체적으로 전송 
	public synchronized void messageAll(String msg)
	{
		for(int i=0;i<waitVc.size();i++)
		{
			ClientThread client=waitVc.elementAt(i);
			try
			{
				client.messageTo(msg);
			}catch(Exception ex)
			{
				waitVc.removeElementAt(i);
			}
		}
	}
}
}

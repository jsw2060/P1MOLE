package com.server;
import java.util.*;

import com.common.Function;

import java.net.*;
import java.io.*;
public class Server implements Runnable {
	Vector<ClientThread> waitVc = new Vector<ClientThread>();
	ServerSocket ss = null;	// 서버에서 접속시 처리 (교환  소켓)
	
	public Server(){
		try {
			ss = new ServerSocket(9469);
			System.out.println("Server Start...");
		} catch (Exception ex) {
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
		String id, name, sex, pos;
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
						case Function.LOGIN: {		// 각각 id, name, sex, pos를 구분해줌
							id = st.nextToken();
							name = st.nextToken();
							sex = st.nextToken();
							pos = "대기실";
							messageAll(Function.LOGIN + "|" + id + "|" + name + "|" + sex + "|" + pos);	// 이미 접속해있던 사람들에게 내가 로그인 한 것을 전송
							waitVc.addElement(this); // 끊어서 저장
							messageTo(Function.MYLOG + "|" + id); // 서버의 응답 중 화면 전환에 대한 내용을 끊어냄
							for(ClientThread client:waitVc){
								messageTo(Function.LOGIN + "|" + client.id + "|" + client.name + "|" + client.sex + "|" + client.pos);	// 내가 이미 접속한 사람들의 정보를 받아옴
							}
							// 방정보 전송
						}
						break;
						case Function.WAITCHAT:	// 채팅
						{
							String data = st.nextToken();
							messageAll(Function.WAITCHAT + "|[" + name + "]" + data);
						}
						break;
					}
					
				} catch (Exception ex) {}
			}
		}
		
		// 개인적으로
		public synchronized void messageTo(String msg){		// 동기화 쓰레드
			try {
				out.write((msg + "\n").getBytes()); // 데이터 넘길 때 씀	out 앞에 this가 숨어서 개인에게만 전송됨
			} catch (Exception ex) {}
		}
		
		// 전체적으로
		public synchronized void messageAll(String msg){		// 동기화 쓰레드
			for(ClientThread client:waitVc){		// 접속자는 모두 waitVc에 있음
				client.messageTo(msg);				// for문을 돌리면 각각을 for문 돌리니까 전체전송됨
			}
		}
	}

}

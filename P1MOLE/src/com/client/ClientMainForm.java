package com.client;
import java.awt.*; // layout
import java.awt.event.*;
import java.net.Socket;

import javax.swing.*; // window
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import com.client.Loading.Move;
import com.common.Function;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientMainForm extends JFrame implements ActionListener, Runnable{
	CardLayout card = new CardLayout();
	
	// 게임창 객체
    MoleGamePlay moleGamePlay=new MoleGamePlay();
    MoleGameView moleGameView=moleGamePlay.moleGameView;
	
	Login login = new Login();
	WaitRoom wr = new WaitRoom();
	Loading loading = new Loading();
	MakeRoom mr = new MakeRoom();
	//GetNewone no=new GetNewone();
	GameRule gr = new GameRule();
	boolean b = false;
	
	// id|대화명|성별
    Socket s;
    BufferedReader in;// 서버에서 값을 읽는다
    OutputStream out; // 서버로 요청값을 보낸다 
	
	public ClientMainForm(){
		setLayout(card);	// BoarderLayout => CardLayout
		
		add("LOG", login);		// 로그인창
		add("LOADING", loading);// 로딩화면
		add("WR", wr);			// 대기실
		add("GAMERULE", gr);	// 정보보기
		add("GAMEROOM",moleGamePlay); // 게임창
		
		// 윈도우창 제목과 크기 지정
		setTitle("잡아라두더지");
		setSize(800, 600);
		setVisible(true);
		setResizable(false);
		
		// 버튼 이벤트 등록
		
		//로그인 창
		//login.getNew.addActionListener(this);		// 회원가입
		login.login.addActionListener(this);		// 로그인
		
		//대기실 창
		wr.tf.addActionListener(this);
		wr.b1.addActionListener(this);
		wr.b2.addActionListener(this);
		wr.b5.addActionListener(this);
		wr.b6.addActionListener(this);
		
		 // 게임 리스너 추가
        moleGamePlay.jButtonStn.addActionListener(this);
        moleGamePlay.jButtonPause.addActionListener(this);
        moleGamePlay.jButtonExit.addActionListener(this);

		
		//게임규칙(정보보기) 창
		gr.b1.addActionListener(this);
		
		//로딩 창
		loading.loadConfirm.addActionListener(this);
		
		// 윈도우 종료버튼 선택시 아무 것도 안함
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
	// 서버와 연결
    public void connection(String id,String name,String sex)
    {
    	try
    	{
    		s=new Socket("localhost", 9469);
    		// s=>server
    		in=new BufferedReader(
					new InputStreamReader(s.getInputStream()));
			out=s.getOutputStream();
			out.write((Function.LOGIN+"|"+id+"|"+name+"|"+sex+"\n").getBytes());
    	}catch(Exception ex){}
    	
    	// 서버로부터 응답값을 받아서 처리
    	new Thread(this).start();// run()
    }
    
	public static void main(String[] args) {
		// 예외처리
		try{
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		}catch(Exception ex) {}
		ClientMainForm cm = new ClientMainForm();
	}
	
	// 패널바꾸기
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == wr.tf){
			String data = wr.tf.getText();
			wr.ta.append(data + "\n");
			wr.tf.setText("");
		}
		/*else if(e.getSource() == login.getNew){
			card.show(getContentPane(), "GetNewOne");
			no.setBounds(275,200,550,330);
			no.setVisible(true);
		}*/
		else if(e.getSource() == login.login){
			String id=login.IDField.getText().trim();
			if(id.length()<1)
			{
				JOptionPane.showMessageDialog(this,
						"ID를 입력하세요");
				login.IDField.requestFocus();
				return;
			}
			String name=login.nf.getText().trim();
			if(name.length()<1)
			{
				JOptionPane.showMessageDialog(this,
						"이름 입력하세요");
				login.nf.requestFocus();
				return;
			}
			String sex="";
			if(login.M.isSelected())
				sex="남자";
			else
				sex="여자";
			connection(id, name, sex);
			//card.show(getContentPane(), "LOADING");
		}
		else if(e.getSource() == loading.loadConfirm && loading.loadFinish == true){
			card.show(getContentPane(), "WR");
		}
		else if(e.getSource()==wr.b1)
		{
			mr.setSize(230, 235);
			mr.setBounds(285, 180, 230, 240);
			mr.setVisible(true);
		}
		else if(e.getSource() == wr.b2){
			card.show(getContentPane(), "GAMEROOM");
		}
		else if(e.getSource() == wr.b6){
			card.show(getContentPane(), "LOG");
			loading.loadFinish = false;
		}
		else if(e.getSource() == wr.b5){
			card.show(getContentPane(), "GAMERULE");
		}
		else if(e.getSource() == gr.b1){
			card.show(getContentPane(), "WR");
		}
		else if(e.getSource() == moleGamePlay.jButtonStn){
			moleGameView.thread=new Thread(moleGameView);
            moleGameView.thread.start();
            moleGameView.timer.start();
		}
		else if(e.getSource() == moleGamePlay.jButtonExit){
			card.show(getContentPane(), "WR");
		}
	}
	public void append(String msg, String color){

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try
			{
				String msg=in.readLine();
				System.out.println("Server=>"+msg);
				StringTokenizer st = new StringTokenizer(msg, "|");
				int protocol=Integer.parseInt(st.nextToken());
				switch(protocol)
				{
				  case Function.LOGIN:
				  {
					  String[] data={
						st.nextToken(),	 
						st.nextToken(),
						st.nextToken(),
						st.nextToken()
					  };
					  wr.model2.addRow(data);
				  }
				  break;
				  case Function.MYLOG:
				  {
					  String id=st.nextToken();
					  setTitle("Loading");
					  loading.new Move().start();	// thread working! 게이지바 차는 것을 시작함
					  card.show(getContentPane(), "LOADING");
				  }
				  break;
				  case Function.WAITCHAT:
				  {
					  wr.ta.append(st.nextToken()+"\n");
					  //wr.bar.setValue(wr.bar.getMaximum());
				  }
				  break;
				}
			}catch(Exception ex){}
		}
	}

}

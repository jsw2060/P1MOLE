package com.client;
import java.awt.*; // layout
import java.awt.event.*;
import javax.swing.*; // window
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import com.client.Loading.Move;

public class ClientMainForm extends JFrame implements ActionListener{
	CardLayout card = new CardLayout();
	Login login = new Login();
	WaitRoom wr = new WaitRoom();
	Loading loading = new Loading();
	MakeRoom mr = new MakeRoom();
	GetNewone no=new GetNewone();
	GameRule gr = new GameRule();
	boolean b = false;
	// id|대화명|성별
	
	public ClientMainForm(){
		setLayout(card);	// BoarderLayout => CardLayout
		
		add("LOG", login);		// 로그인창
		add("LOADING", loading);// 로딩화면
		add("WR", wr);			// 대기실
		add("GAMERULE", gr);	// 정보보기
		
		// 윈도우창 제목과 크기 지정
		setTitle("잡아라두더지");
		setSize(800, 600);
		setVisible(true);
		setResizable(false);
		
		// 버튼 이벤트 등록
		
		//로그인 창
		login.getNew.addActionListener(this);		// 회원가입
		login.login.addActionListener(this);		// 로그인
		
		//대기실 창
		wr.tf.addActionListener(this);
		wr.b1.addActionListener(this);
		wr.b5.addActionListener(this);
		wr.b6.addActionListener(this);
		
		//게임규칙(정보보기) 창
		gr.b1.addActionListener(this);
		
		//로딩 창
		loading.loadConfirm.addActionListener(this);
		
		//종료시, 메모리 회수
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
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
		else if(e.getSource() == login.getNew){
			card.show(getContentPane(), "GetNewOne");
			no.setBounds(275,200,550,330);
			no.setVisible(true);
		}
		else if(e.getSource() == login.login){
			card.show(getContentPane(), "LOADING");
			loading.new Move().start();	// thread working! 게이지바 차는 것을 시작함
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
	}
	public void append(String msg, String color){

	}

}

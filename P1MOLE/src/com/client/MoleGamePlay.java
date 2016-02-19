package com.client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sist on 2016-02-11.
 */
public class MoleGamePlay extends JPanel {
	JPanel myP,yourP;  //게임창에 내 아바타,상대방 아바타 나오는 클래스 변수 추가
	JTextField myTF, yourTF;
    MoleGameView moleGameMyView=new MoleGameView();
    MoleGameView moleGameYourView=new MoleGameView();
    JButton jButtonStn, jButtonPause, jButtonExit;
    NotiBar notiMyBar=moleGameMyView.notiMyBar;
    //NotiBar notiYourBar=moleGameYourView.notiBar;
    
    JTextPane jTextPane;
    JLabel jLabel;
    Image image,cursorImage;
    Cursor cursor;

    public MoleGamePlay(){

        jLabel=new JLabel(new ImageIcon("image/score.jpg"));	//스코어 이미지 위치
        jLabel.setBounds(10,10,200,50);
        
        jTextPane=new JTextPane();							//스코어  위치
        jTextPane.setEditable(false);
        jTextPane.setBounds(140,10,180,50);
        jTextPane.setText("0");
        jTextPane.setFont(new Font("Pompadour",50,50));

        jButtonStn=new JButton(new ImageIcon("image/btn_gameStart.jpg"));
        jButtonStn.setBounds(30,20,191,59);
        jButtonStn.setBorderPainted(false);
        jButtonStn.setContentAreaFilled(false);

        jButtonPause=new JButton(new ImageIcon("image/pause.png"));
        jButtonPause.setBounds(20,95,203,55);
        jButtonPause.setBorderPainted(false);
        jButtonPause.setContentAreaFilled(false);

        jButtonExit=new JButton(new ImageIcon("image/exit.png"));
        jButtonExit.setBounds(30,165,152,59);
        jButtonExit.setBorderPainted(false);
        jButtonExit.setContentAreaFilled(false);

        JLabel jLabel1=new JLabel();
        jLabel1.add(jButtonStn);
        jLabel1.add(jButtonPause);
        jLabel1.add(jButtonExit);
        jLabel1.setBounds(560,330,250,300);

        image=Toolkit.getDefaultToolkit().getImage("image/back.png");
        cursorImage=Toolkit.getDefaultToolkit().getImage("image/starhammericon.png");
        cursor=Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0,0), "null");
        this.setCursor(cursor);
        this.setLayout(null);
        moleGameMyView.setBounds(26,70,400,450);
        notiMyBar.setBounds(15,530,420,30);			//타이머 노티 영역 



        this.add(notiMyBar);
        this.add(jLabel);
        this.add(jTextPane);
        this.add(moleGameMyView);
        this.add(jLabel1);


    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image,0,0,getWidth(),getHeight(),this);
    }
}
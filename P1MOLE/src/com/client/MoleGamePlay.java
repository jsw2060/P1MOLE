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
    JButton jButtonStn, jButtonRdy, jButtonPause, jButtonCancel, jButtonExit;
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
        jTextPane.setBounds(154,10,180,43);
        jTextPane.setText("0");
        jTextPane.setFont(new Font("Pompadour",50,50));

        jLabel=new JLabel(new ImageIcon("image/score.png"));
        jLabel.setBounds(10,10,144,43);

        jButtonStn=new JButton(new ImageIcon("image/start.png"));
        jButtonStn.setBounds(20,20,144,43);
        jButtonStn.setBorderPainted(false);
        jButtonStn.setContentAreaFilled(false);

        jButtonRdy=new JButton(new ImageIcon("image/ready.png"));
        jButtonRdy.setBounds(174,20,144,43);
        jButtonRdy.setBorderPainted(false);
        jButtonRdy.setContentAreaFilled(false);

        jButtonPause=new JButton(new ImageIcon("image/pause.png"));
        jButtonPause.setBounds(20,73,144,43);
        jButtonPause.setBorderPainted(false);
        jButtonPause.setContentAreaFilled(false);

        jButtonCancel=new JButton(new ImageIcon("image/cancel.png"));
        jButtonCancel.setBounds(174,73,144,43);
        jButtonCancel.setBorderPainted(false);
        jButtonCancel.setContentAreaFilled(false);
        
        jButtonExit=new JButton(new ImageIcon("image/exit.png"));
        jButtonExit.setBounds(20,130,152,59);
        jButtonExit.setBorderPainted(false);
        jButtonExit.setContentAreaFilled(false);

        JLabel jLabel1=new JLabel();
        //jLabel1.setLayout(null);
        jLabel1.add(jButtonStn);	jLabel1.add(jButtonRdy);
        jLabel1.add(jButtonPause);	jLabel1.add(jButtonCancel);
        jLabel1.add(jButtonExit);
        jLabel1.setBounds(420,280,800,600);

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
package com.client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sist on 2016-02-11.
 */
public class MoleGamePlay extends JPanel {
    MoleGameView moleGameView=new MoleGameView();
    NotiBar notiBar=moleGameView.notiBar;

    JButton jButtonStn, jButtonPause, jButtonExit;
    JTextPane jTextPane;
    JLabel jLabel;
    Image image,cursorImage;
    Cursor cursor;

    public MoleGamePlay(){
        jTextPane=new JTextPane();
        jTextPane.setEditable(false);
        jTextPane.setBounds(140,10,180,50);
        jTextPane.setText("0");
        jTextPane.setFont(new Font("Pompadour",50,50));

        jLabel=new JLabel(new ImageIcon("image/score.jpg"));
        jLabel.setBounds(10,10,200,50);

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
        moleGameView.setBounds(26,70,400,450);
        notiBar.setBounds(15,530,420,30);



        this.add(notiBar);
        this.add(jLabel);
        this.add(jTextPane);
        this.add(moleGameView);
        this.add(jLabel1);


    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image,0,0,getWidth(),getHeight(),this);
    }
}
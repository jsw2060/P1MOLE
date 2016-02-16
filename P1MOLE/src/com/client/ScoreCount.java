/*package com.client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class ScoreCount extends JFrame implements ActionListener{
   CardLayout card = new CardLayout();
   Image back, roomPanelBack;   // 배경
   ImageIcon hole, fistMole, secdMole, thidMole, fourMole; // 두더지들
   JPanel mapPanel, timePanel, gamePanel;                  // 타이머 조작 버튼용 패널과 게임용 패널
   JProgressBar timeBar;
   JButton startBtn;
   JButton readyBtn;
   JButton cancelBtn;
   boolean readyConfirm;
   boolean startConfirm;
   
   JLabel scoreLabel;
   static int score = 0;
   
   int moleSize = 4;   // 두더지 종류는 4가지 0,1,2,3
   int gridSize = 3;   // 두더지 구멍은 3x3 배열 0,1,2
   int moleType = 0;   // 두더지 종류
   int moleImg = 0;

   JButton[][] bu = new JButton[gridSize][gridSize];   // null   판떼기 위에 버튼 뿌리기 위해 선언
   int[][] panCount = new int[gridSize][gridSize];   // 0   판떼기
   int row=0, col=0;
   
   // 변수 생성 및 초기화
   public ScoreCount() {
      setLayout(card);
      
      back = Toolkit.getDefaultToolkit().getImage("image/back.png");
      roomPanelBack = Toolkit.getDefaultToolkit().getImage("image/gameMap.png");
      fistMole = Toolkit.getDefaultToolkit().getImage("image/fistMole.png"); // 실제로 사용하진 않았음 
      secdMole = Toolkit.getDefaultToolkit().getImage("image/secdMole.png");
      thidMole = Toolkit.getDefaultToolkit().getImage("image/thidMole.png");
      fourMole = Toolkit.getDefaultToolkit().getImage("image/fourMole.png"); // 필요없음
      
      // 버튼 위에 두더지와 구멍 이미지용
      hole = new ImageIcon("image/moleHole.png");
      fistMole = new ImageIcon("image/fistMole.png");
      secdMole = new ImageIcon("image/secdMole.png");
      thidMole = new ImageIcon("image/thidMole.png");
      fourMole = new ImageIcon("image/fourMole.png");
      
      // 타이머용
      timeBar = new JProgressBar();
      startBtn = new JButton("start");
      readyBtn = new JButton("ready");
      cancelBtn = new JButton("cancel");
      readyConfirm = false;            // 준비 확인용 변수
      startConfirm = false;            // 시작 확인용 변수
      
      scoreLabel = new JLabel("Score :" + score);
            
      // 방 전체에 대한 메인 패널
      //roomPanel = new JPanel();
      //roomPanel.setBounds(0, 0, 700, 500);
      //roomPanel.setOpaque(false);
      //setContentPane(roomPanel);
      
      // 판떼기 위치를 새로 지정하기 위해서 레이아웃을 해제함
      setLayout(null);
      
      mapPanel = new JPanel(){
         @Override
         protected void paintComponent(Graphics g) {
            g.drawImage(roomPanelBack, 0, 0, this);
            super.paintComponent(g);
         }
      };
      mapPanel.setLayout(new BorderLayout(10, 10));
      mapPanel.setBounds(40, 40, 400, 500);
      mapPanel.setOpaque(false);
      
      // 게임 맵
      gamePanel = new JPanel();
      gamePanel.setLayout(new GridLayout(gridSize, gridSize, 30, 20));   // 3x3으로 판떼기 분할
      gamePanel.setOpaque(false);

      // 타이머 조작 버튼용 판떼기
      timePanel = new JPanel();
      timePanel.setBounds(470, 400, 110, 200);
      timePanel.setOpaque(false);
      //Border border = BorderFactory.createTitledBorder("Timer!!");
      //timeBar.setBorder(border);
      
      // 판떼기들 추가
      // 게임맵 패널 속 위치 지정
      mapPanel.setLayout(null);
      scoreLabel.setBounds(0, 0, 180, 40);
      scoreLabel.setFont(new Font("맑은고딕", Font.BOLD, 30));
      gamePanel.setBounds(10, 175, 380, 200);
      timeBar.setBounds(10, 430, 380, 25);
      
      // 게임맵 패널
      add(mapPanel);
      mapPanel.add(scoreLabel);
      mapPanel.add(gamePanel);
      mapPanel.add(timeBar);
      
      // 타이머 판떼기 위에 올라가는 버튼들
      timePanel.setLayout(null);
      startBtn.setBounds(5, 5, 100, 50);
      readyBtn.setBounds(5, 60, 100, 50);
      cancelBtn.setBounds(5, 115, 100, 50);
      
      add(timePanel);
      timePanel.add(startBtn);
      timePanel.add(readyBtn);
      timePanel.add(cancelBtn);
      
      //게임 창
      startBtn.addActionListener(this);
      readyBtn.addActionListener(this);
      cancelBtn.addActionListener(this);
      
      //메인 게임 화면 부
      setTitle("잡아라두더지");
      setSize(800, 600);
      setVisible(true);
      setResizable(false);
      
      //종료시, 메모리 회수
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   }
   
      // 타이머 // 테스트 편의상 10초만 줬음 나중에 타이머 다되면 버튼을 false 줘야한다고 생각함
      public void move(){
         EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
               try {
                  
               } catch (Exception e) {
                  // TODO: handle exception
               }
            }
         });
      }
      class move extends Thread {
         public void run() {
            try {
               int num = 60;
               while (true) {
                  //System.out.println(num);
                  timeBar.setValue(num);
                  timeBar.setStringPainted(true);
                  num--;
                  Thread.sleep(100);
                  if (num < 0){
                     Thread.interrupted();
                     break;
                  }
               }
            } catch (Exception e) {}
         }
      }
   
   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      System.out.println("You clicked the button");
      
      // 레디한 상태에서 스타트 버튼 누를 경우
      if (e.getSource() == startBtn && readyConfirm == true) {
         
         //new Move().start();
         startBtn.setEnabled(false);
         readyConfirm = true;
         startConfirm = true;
         cancelBtn.setEnabled(false);
         display();
      }
      // 레디버튼 누를 경우
      else if (e.getSource() == readyBtn) {
         readyBtn.setEnabled(false);
         readyConfirm = true;
         System.out.println("준비 확인이 되었습니다.");
      }
      // 레디취소 버튼 누를 경우
      else if (e.getSource() == cancelBtn) {
         readyConfirm = false;
         readyBtn.setEnabled(true);
         System.out.println("준비 확인이 취소 되었습니다.");
      }
      
      // 두더지 잡을 경우
      for(int i=0; i<3; i++){
         for(int j=0; j<3; j++){
            if   (e.getSource() == bu[i][j] && startConfirm == true){   // 게임시작후, 버튼을 눌렀다
               System.out.println(i+"행"+j+"열"+bu[i][j]+"가 눌렸습니다.");
               if(bu[i][j] == bu[row][col]){   // 버튼을 누른게 두더지들이라면
                  if(panCount[row][col] == 0){// 그 중에서도 일반두더지라면
                     score += 50;
                     System.out.println(score + " 점");
                     secondSet();
                  }
                  else if(panCount[row][col] == 1){
                     score += 100;
                     System.out.println(score + " 점");
                     secondSet();
                  }
                  else if(panCount[row][col] == 2){
                     score += 300;
                     System.out.println(score + " 점");
                     secondSet();
                  }
                  else if(panCount[row][col] == 3){
                     score -= 100;
                     if(score < 0){
                        score = 0;
                        System.out.println(score + " 점");
                        secondSet();
                     }
                     else{
                        System.out.println(score + " 점");
                        secondSet();
                     }
                  }
               }
               else{
                  System.out.println("두더지를 못 잡았습니다");
                  secondSet();
               }
            }
         }
      }
   }
   
   @Override
   public void paint(Graphics g) {
      g.drawImage(back, 0, 0, getWidth(), getHeight(), this);
      super.paint(g);
   }

   public void ArrayInit(){
      // 초기화용도
      for(int i=0; i<gridSize; i++){
         for(int j=0; j<gridSize; j++){
            // 카운팅 배열을 아무 해당 없는 5로 초기화함
            panCount[i][j] = 5;
            // 버튼 배열도 5로 초기화
            //bu[i][j].setIcon(hole);
            bu[i][j] = new JButton();
            bu[i][j].setBorderPainted(false);						// 버튼의 외곽선이 안 보이도록 설정
            bu[i][j].setFocusPainted(false);						// 버튼이 선택되었을 때 외곽선이 안 보이도록 설정
            bu[i][j].setContentAreaFilled(false);					// 버튼의 내용영역 채우기 사용 안함
            gamePanel.add(bu[i][j]);
            bu[i][j].setEnabled(false);   //누를 수 없는 상태로 초기화
            bu[i][j].setFont(new Font("맑은고딕", Font.BOLD, 25));
            bu[i][j].addActionListener(this);
         }
      }
   }
   // 두더지를 맞춰서 초기화 하는 경우
   public void ReArrayInit(){
      for(int i=0; i<gridSize; i++){
         for(int j=0; j<gridSize; j++){
            // 카운팅 배열을 아무 해당 없는 5로 초기화함
            panCount[i][j] = 5;         
         }
      }
   }

   // 랜덤 입력
   public void getRand(){
      Random rand = new Random();
      row = rand.nextInt(gridSize);
      col = rand.nextInt(gridSize);
      moleType = rand.nextInt(moleSize);
      panCount[row][col] = moleType;
   }
   
   // 버튼 출력 메소드
   public void display(){
      
      for(int j=0; j<gridSize; j++){
         for(int k=0; k<gridSize; k++){
            bu[j][k].setEnabled(true);
            bu[j][k].setIcon(hole);
            moleSelect();
         }
      }
   }
   
   // 두더지 종류별 이미지 지정 및 출력
   public void moleSelect(){
      for(int j=0; j<gridSize; j++){
         for(int k=0; k<gridSize; k++){
            if(j == row && k == col){   // 랜덤 출력에서 선택된 버튼이고
               if(moleType == 0){      // 그 두더지 번호에 따라 이미지를 지정해준다
                  bu[j][k].setIcon(fistMole);
               }
               else if(moleType == 1){
                  bu[j][k].setIcon(secdMole);
               }
               else if(moleType == 2){
                  bu[j][k].setIcon(thidMole);
               }
               else if(moleType == 3){
                  bu[j][k].setIcon(fourMole);
               }
            }
         }
      }
   }
   
   // 게임 시작시 최초 초기화 그룹
   public void gameSet() {
      ArrayInit();
      getRand();
   }
   
   // 게임 작동 중 초기화 그룹
   public void secondSet()   {
      ReArrayInit();
      getRand();
      display();
   }

   public static void main(String[] args) {
      // 예외처리
      try{
         UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
      }catch(Exception ex) {}
      
      ScoreCount ScoreCount = new ScoreCount();
      EventQueue.invokeLater(new Runnable(){
         @Override
         public void run() {
            int num = 30;
            try {
            	while(true){
            		ScoreCount.gameSet();
            		System.out.println(num);
            		ScoreCount.timeBar.setValue(num);
            		ScoreCount.timeBar.setStringPainted(true);
            		num--;
            		Thread.sleep(100);
            		if (num < 0){
            			Thread.interrupted();
            			break;
            		}
            	}
            } catch (Exception e) {
               // TODO: handle exception
            }
         }
      });
   }
}*/
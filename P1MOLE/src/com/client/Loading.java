package com.client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
// Loading Page 로딩화면
public class Loading extends JPanel implements MouseListener {
	Image loadImage, backImage, img, img1;		// 추가이미지, 배경화면, 마우스 커서 이미지
	JLabel loadTitle;				// Loading!!! 출력용
	JButton loadConfirm;			// Start Button 스타트 버튼
	JProgressBar percentBar;		// Loading Bar 진행도 나타내는 막대
	boolean loadFinish;				// 로딩 완료됐는지 확인
	
	// 마우스 커서용
	Cursor cursor,cursor1;
	
	// 이미지 불러오고, 각종 컴포넌트 초기화
	public Loading() {
		//loadImage = Toolkit.getDefaultToolkit().getImage("c:\\image\\load.gif");	// 사용x
		backImage = Toolkit.getDefaultToolkit().getImage("image/loadBack.png");		// 배경이미지

		// 마우스용 이미지
		img=Toolkit.getDefaultToolkit().getImage("image/01.png");
		cursor=Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0,0), "null");
				
		img1=Toolkit.getDefaultToolkit().getImage("image/02.png");
		cursor1=Toolkit.getDefaultToolkit().createCustomCursor(img1, new Point(0,0), "null");
		
		percentBar = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
		loadTitle = new JLabel("Loading!!!");
		loadConfirm = new JButton("입장");
		loadFinish = false;
		
		setLayout(null);			// ClientMain의 레이아웃을 해제시키고, 새로 위치를 배치할 수 있도록 함
		loadTitle.setFont(new Font("맑은고딕", Font.BOLD, 24));		// 폰트 지정 맑은고딕, 굵게, 크기 24
		loadConfirm.setBounds(720, 490, 70, 50);
		percentBar.setBounds(10, 550, 775, 30);
		
		add(loadTitle);
		add(loadConfirm);
		add(percentBar);
		
		// 마우스 이벤트 추가
		addMouseListener(this);
	}
	
	@Override	// 그림 출력부
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backImage, 0, 0, getWidth(), getHeight(), this);
		//g.drawImage(loadImage, 10, 0, 775, 500, this);
	}
	
	// Thread 쓰레드
	class Move extends Thread {
		public void run() {
			try {										// 예외처리 num이 0~100으로 증가하면서 게이지바를 그려줌
				int num = 0;
				while (true) {
					System.out.println(num);			// 콘솔창에 게이지가 잘 작동하는지 출력
					percentBar.setValue(num);			// 화면에 게이지 0% -> 100% 출력
					percentBar.setStringPainted(true);	// 게이지 차오르는 것 출력
					num++;
					Thread.sleep(40);					// millisecond 단위  1초는 1000 // 출력 1번당 70만큼 지연을 줌
					if (num > 100){						// 100% 도달시
						Thread.interrupted();			// Thread death 쓰레드 소멸
						loadFinish = true;
						break;
					}
				}
			} catch (Exception e) {}
		}
	}
	
	// 마우스 이벤트
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {	// 마우스가 컴포넌트 위에서 눌렸을 때
		// TODO Auto-generated method stub
		setCursor(cursor1);
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {	// 마우스가 컴포넌트 위에서 눌리지 않았을 때
		// TODO Auto-generated method stub
		setCursor(cursor);
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}



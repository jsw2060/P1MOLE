package com.client;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;

public class Bonus extends JDialog implements ActionListener{
	
	JPanel jPanel;
	JButton jButton;
	JTable jTable;

	
	int m_timer=3;
	Timer timer=new Timer(1000,this);
	
	public Bonus(){
		setLayout(null);
		jButton=new JButton(new ImageIcon(".\\image\\bigMole.jpg"));
		jButton.setBounds(100,10,300,300);
		jButton.setMargin(new Insets(0,0,0,0));
		
		jButton.setContentAreaFilled(false);
		this.add(jButton);
		this.setSize(500,500);
		this.setLocation(250, 250);
		this.setModal(true);
		this.setUndecorated(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(timer==e.getSource()){
			if(0<m_timer){
				System.out.println(m_timer);
				m_timer --;
			}else if(0==m_timer){
				timer.stop();
				m_timer=3;
				setVisible(false);
			}
		}
		
	}

}
























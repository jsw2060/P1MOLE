package com.client;

import javax.swing.*;
import java.awt.*;

public class NotiBar extends JPanel {
	JProgressBar jProgressBar;
	
	public NotiBar(){
		jProgressBar = new JProgressBar(0, 2500);
		jProgressBar.setForeground(Color.green);
		jProgressBar.setBackground(Color.lightGray);
		jProgressBar.setValue(2500);
		jProgressBar.setString("Time");
		jProgressBar.setBorderPainted(false);
		
		this.setLayout(null);
		jProgressBar.setBounds(10, 430, 380, 25);
		this.add(jProgressBar);
		this.setSize(380, 25);
	}
}

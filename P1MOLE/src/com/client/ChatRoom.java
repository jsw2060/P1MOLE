package com.client;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
public class ChatRoom extends JPanel {
	JPanel[] pan = new JPanel[6];
	JTextField[] idtf = new JTextField[6];
	JTextPane jp;
	JTextField tf;
	JComboBox box;
	JTable table;
	DefaultTableModel model;
	JButton b1, b2, b3;
	
	public ChatRoom(){
		for(int i=0; i<pan.length; i++){
			pan[i] = new JPanel();
			pan[i].setBackground(Color.black);
			idtf[i] = new JTextField();
			idtf[i].setEnabled(false);
		}
		
		jp = new JTextPane();
		jp.setEditable(false);
		JScrollPane js1 = new JScrollPane(jp);
		tf = new JTextField();
		box = new JComboBox();
		box.addItem("black");
		box.addItem("blue");
		box.addItem("green");
		box.addItem("pink");
		box.addItem("yellow");
		box.addItem("magenta");
			
		b1 = new JButton("강퇴");
		b2 = new JButton("초대");
		b3 = new JButton("나가기");
			
		String[] col = {"ID", "대화명", "성별"};
		String[][] row = new String[0][3];
		model = new DefaultTableModel(row, col);
		table = new JTable(model);
		JScrollPane js2 = new JScrollPane(table);
			
		setLayout(null);
		for(int i=0; i<pan.length; i++){
			if(i<3){
				pan[i].setBounds(10+(i*190), 15, 180, 150);
				idtf[i].setBounds(10+(i*190), 170, 180, 30);
				add(pan[i]);
				add(idtf[i]);
			}
			else{
				pan[i].setBounds(10+((i-3)*190), 210, 180, 150);
				idtf[i].setBounds(10+((i-3)*190), 365, 180, 30);
				add(pan[i]);
				add(idtf[i]);				
			}
		}
		js1.setBounds(10, 400, 560, 130);
		add(js1);
		tf.setBounds(10, 535, 455, 30);
		add(tf);
		box.setBounds(470, 535, 100, 30);
		add(box);
		js2.setBounds(575, 15, 205, 380);
		add(js2);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(3, 1, 5, 5));
		p.add(b1);
		p.add(b2);
		p.add(b3);
		p.setBounds(575, 400, 205, 165);
		add(p);
	}
}


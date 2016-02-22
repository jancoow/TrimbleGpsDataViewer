package me.janco.tgl.view.frames;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import me.janco.tgl.controller.Main;
import me.janco.tgl.view.menubar.MenuBar;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	public MainFrame() {
		this.setResizable(true);
		this.setBounds(100, 100, 1440, 900);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(false);
		this.setTitle("Trimble GPS Loader");
		this.setIconImage(new ImageIcon(Main.class.getResource("/trimbleicon.png")).getImage());
		this.setJMenuBar(new MenuBar());
		this.setVisible(true);
		this.getContentPane().setBackground(Color.WHITE);
	}

	public void setView(JPanel jpanel) {
		this.setContentPane(jpanel);
		this.revalidate();
	}
	

}

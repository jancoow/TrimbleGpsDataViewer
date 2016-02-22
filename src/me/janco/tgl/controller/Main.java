package me.janco.tgl.controller;

import java.io.File;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import me.janco.tgl.model.TrimbleDataDictonary;
import me.janco.tgl.view.frames.MainFrame;

public class Main {
	private static MainFrame f;

	public static void main(String[] args) {
		// sets the systems look and feels
		if (System.getProperties().getProperty("os.name").equals("Mac OS X")) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Test");
		}
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");
		System.setProperty("apple.awt.fileDialogForDirectories", "true");

		f = new MainFrame();
		f.setView(new DataPanelController().getView());
	}

	public static void setView(JPanel view) {
		f.setView(view);
	}
	private static void setTitleBar(String title){
		f.setTitle(title + " - Trimble GPS Loader");
	}
	public static boolean setData(File file){
		if(file.isDirectory() && file.getName().equals("AgGPS")){
			TrimbleDataDictonary tdd = new TrimbleDataDictonary(file.getAbsolutePath());
			f.setView(new DataPanelController(tdd).getView());
			setTitleBar(file.getPath());
			return true;
		}else{
			return false;
		}
	}
}

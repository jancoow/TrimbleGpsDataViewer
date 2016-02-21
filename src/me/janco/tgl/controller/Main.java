package me.janco.tgl.controller;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import me.janco.tgl.model.TrimbleDataDictonary;
import me.janco.tgl.view.frames.MainFrame;

public class Main {
	static private MainFrame f;

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

		TrimbleDataDictonary tdd = new TrimbleDataDictonary("/home/janco/Documents/tractor gegevens/T7060/AgGPS");
		f = new MainFrame();
		f.setView(new DataPanelController(tdd).getView());

	}

	public static void setView(JPanel view) {
		f.setView(view);
	}
}

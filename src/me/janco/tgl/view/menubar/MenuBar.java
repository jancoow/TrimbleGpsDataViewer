package me.janco.tgl.view.menubar;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import me.janco.tgl.controller.Main;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {

	public MenuBar() {
		initialize();
	}

	private void initialize() {
		setLayout(new FlowLayout(80));
		JMenu fileMenu = new JMenu("Bestand");
		JMenu editMenu = new JMenu("Edit");
		JMenu viewMenu = new JMenu("View");
		JMenu helpMenu = new JMenu("Help");
		this.add(fileMenu);
		this.add(editMenu);
		this.add(viewMenu);
		this.add(helpMenu);

		JMenuItem openAction = new JMenuItem("Open trimble folder");
		JMenuItem exitAction = new JMenuItem("Afsluiten");
		JMenuItem uploadAction = new JMenuItem("Syncroniseren met database");

		// view
		JMenuItem editorView = new JMenuItem("Editor");
		JMenuItem agendaView = new JMenuItem("Agenda");
		JMenuItem simulatorView = new JMenuItem("Simulator");
		JMenuItem tableView = new JMenuItem("Table");

		openAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		exitAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		uploadAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));

		fileMenu.add(openAction);
		openAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jf = new JFileChooser();
				jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(jf.showOpenDialog(MenuBar.this) == JFileChooser.APPROVE_OPTION){
					if(!Main.setData(jf.getSelectedFile())){
						JOptionPane.showMessageDialog(MenuBar.this, "Dit is geen geldige Trimble data folder! Kies de 'AgGPS' folder a.u.b. .");
					};
				}
			}
		});
		fileMenu.add(uploadAction);
		uploadAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});

		fileMenu.addSeparator();

		fileMenu.add(exitAction);
		exitAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		// view
		viewMenu.add(editorView);
		editorView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		viewMenu.add(simulatorView);
		simulatorView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		viewMenu.add(agendaView);
		agendaView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		viewMenu.add(tableView);
		tableView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
	}

}

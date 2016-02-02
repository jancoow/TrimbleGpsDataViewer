package me.janco.tgl.view.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ListPanelPopupMenu extends JPopupMenu{
	private JMenuItem rename ;
	private JMenuItem merging;
	private JMenuItem delete;
	
	public ListPanelPopupMenu(){
		rename = new JMenuItem("Hernoemen");
		merging = new JMenuItem("Samenvoegen");
		delete = new JMenuItem("Verwijderen");
	
		add(rename);
		add(merging);
		add(delete);		
	}
	
	public void setMergingEnabled(boolean enabled){
		merging.setEnabled(enabled);
	}

}

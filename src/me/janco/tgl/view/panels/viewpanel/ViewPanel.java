package me.janco.tgl.view.panels.viewpanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import me.janco.tgl.controller.ViewPanelController;
import me.janco.tgl.view.panels.datapanel.ListPanel;

public class ViewPanel extends JPanel {
	private ViewPanelController vpc;
	public ListPanel swaths, machinery;
	
	public ViewPanel(ViewPanelController vpc){
		this.vpc = vpc;
		setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		setLayout(new BorderLayout());
		
		JPanel listboxes = new JPanel(new GridLayout(2,1));
		listboxes.add(swaths = new ListPanel("AB lijnen"));
		listboxes.add(machinery = new ListPanel("Bewerkingen"));
		
		add(listboxes, BorderLayout.WEST);
		add(new MapViewPanel(), BorderLayout.CENTER);
	}
}

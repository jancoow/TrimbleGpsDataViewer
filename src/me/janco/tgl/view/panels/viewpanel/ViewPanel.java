package me.janco.tgl.view.panels.viewpanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import me.janco.tgl.controller.ViewPanelController;
import me.janco.tgl.view.panels.datapanel.DataPanel;
import me.janco.tgl.view.panels.datapanel.ListPanel;

public class ViewPanel extends JPanel {
	private ViewPanelController vpc;
	public ListPanel swaths, machinery;
	public MapViewPanel mapViewPanel;
	
	public ViewPanel(ViewPanelController vpc){
		this.vpc = vpc;
		setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		setLayout(new BorderLayout());
		
		JPanel left = new JPanel(new GridLayout(2,1));
		left.add(swaths = new ListPanel("AB lijnen"));
		left.add(machinery = new ListPanel("Bewerkingen"));
		
		machinery.list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				vpc.setSelectedMachinery(machinery.list.getSelectedIndex());
			}
		});
		
		swaths.list.addListSelectionListener(new ListSelectionListener() {	
			@Override
			public void valueChanged(ListSelectionEvent e) {
				mapViewPanel.setSelectedSwath(swaths.list.getSelectedValue());				
			}
		});
		swaths.contextmenu.rename.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newname = JOptionPane.showInputDialog("Wijzig naam", vpc.getField().getSwaths().get(swaths.getSelected()).getName());
				if(newname != null){
					vpc.renameSwath(newname, swaths.getSelected());
				}				
			}
		});
		swaths.contextmenu.delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				vpc.deleteSwath(swaths.getSelected());
			}
		});
		
		//Grid for the buttons underneath it
		JPanel bottombar = new JPanel(new GridLayout (1, 6));
		
		add(bottombar, BorderLayout.SOUTH);
		JButton backButton = new JButton("Terug naar overzicht");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				vpc.switchBack();
			}
		});		
		bottombar.setBorder(new EmptyBorder(15, 0, 0, 0));
		bottombar.add(new JLabel(vpc.getField().getName()));
		bottombar.add(new JPanel());
		bottombar.add(new JPanel());
		bottombar.add(new JPanel());
		bottombar.add(new JPanel());
		bottombar.add(backButton);	

		
		add(left, BorderLayout.WEST);
		add(mapViewPanel = new MapViewPanel(vpc.getSwathShp()), BorderLayout.CENTER);
	}
}

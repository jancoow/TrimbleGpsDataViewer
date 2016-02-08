package me.janco.tgl.view.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import me.janco.tgl.controller.DataPanelController;

@SuppressWarnings("serial")
public class DataPanel extends JPanel{
	private ListPanel clientListPanel, farmListPanel, fieldListPanel;
	private DataPanelController controller;
	
	public DataPanel(DataPanelController controller){
		setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		setLayout(new BorderLayout());
		this.controller = controller;
		
		//Grid where all the listviews are placed
		JPanel listviewgrid = new JPanel(new GridLayout (1, 3, 50, 50));
		listviewgrid.add(clientListPanel = new ListPanel("Klanten"));	
		listviewgrid.add(farmListPanel = new ListPanel("Bedrijven"));	
		listviewgrid.add(fieldListPanel = new ListPanel("Velden"));	
		add(listviewgrid);
		
		//Grid for the buttons underneath it
		JPanel bottombar = new JPanel(new GridLayout (1, 6));
		JButton newFieldButton = new JButton("Open veld");
		newFieldButton.setEnabled(false);
		bottombar.setBorder(new EmptyBorder(15, 0, 0, 0));
		bottombar.add(new JPanel());
		bottombar.add(new JPanel());
		bottombar.add(new JPanel());
		bottombar.add(new JPanel());
		bottombar.add(new JPanel());
		bottombar.add(newFieldButton);	
		add(bottombar, BorderLayout.SOUTH);
		
		clientListPanel.list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				controller.setSelectedClient(clientListPanel.list.getSelectedIndex());
			}
		});
		clientListPanel.contextmenu.rename.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newname = JOptionPane.showInputDialog("Wijzig naam", controller.getClients().get(clientListPanel.list.getSelectedIndex()).getName());
				if(newname != null){
					if(!controller.renameClient(newname, clientListPanel.list.getSelectedIndex())){
						JOptionPane.showMessageDialog(DataPanel.this.getRootPane(), "Fout bij het veranderen van de client naam: naam ongeldig");
					}
				}				
			}
		});
		
		farmListPanel.list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				controller.setSelectedFarm(farmListPanel.list.getSelectedIndex());
			}
		});
		farmListPanel.contextmenu.rename.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newname = JOptionPane.showInputDialog("Wijzig naam", controller.getClients().get(clientListPanel.list.getSelectedIndex()).getFarms().get(farmListPanel.list.getSelectedIndex()).getName());
				if(newname != null){
					if(!controller.renameFarm(newname, clientListPanel.list.getSelectedIndex(), farmListPanel.list.getSelectedIndex())){
						JOptionPane.showMessageDialog(DataPanel.this.getRootPane(), "Fout bij het veranderen van de bedrijfs naam: naam ongeldig");
					}
				}				
			}
		});		
		
		fieldListPanel.list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				newFieldButton.setEnabled((fieldListPanel.list.getSelectedIndex() != -1));
				controller.setSelectedField(fieldListPanel.list.getSelectedIndex());
			}
		});
		fieldListPanel.contextmenu.rename.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newname = JOptionPane.showInputDialog("Wijzig naam", controller.getClients().get(clientListPanel.list.getSelectedIndex()).getFarms().get(farmListPanel.list.getSelectedIndex()).getFields().get(fieldListPanel.list.getSelectedIndex()).getName());
				if(newname != null){
					if(!controller.renameField(newname, clientListPanel.list.getSelectedIndex(), farmListPanel.list.getSelectedIndex(), fieldListPanel.list.getSelectedIndex())){
						JOptionPane.showMessageDialog(DataPanel.this.getRootPane(), "Fout bij het veranderen van de veld naam: naam ongeldig");
					}
				}				
			}
		});		
	}
	
	public void setClientListPanelData(String data[]){
		clientListPanel.setData(data);
	}
	public void setFarmListPanelData(String data[]){
		farmListPanel.setData(data);
	}
	public void setFieldListPanelData(String data[]){
		fieldListPanel.setData(data);
	}
	
	
}

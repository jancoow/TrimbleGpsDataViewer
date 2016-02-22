package me.janco.tgl.view.panels.datapanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import me.janco.tgl.controller.DataPanelController;

@SuppressWarnings("serial")
public class DataPanel extends JPanel {
	private ListPanel clientListPanel, farmListPanel, fieldListPanel;

	public DataPanel(DataPanelController controller) {
		setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		setLayout(new BorderLayout());

		// Grid where all the listviews are placed
		JPanel listviewgrid = new JPanel(new GridLayout(1, 3, 50, 50));
		listviewgrid.add(clientListPanel = new ListPanel("Klanten"));
		listviewgrid.add(farmListPanel = new ListPanel("Bedrijven"));
		listviewgrid.add(fieldListPanel = new ListPanel("Velden"));
		add(listviewgrid);

		// Grid for the buttons underneath it
		JPanel bottombar = new JPanel(new GridLayout(1, 6));

		JButton newFieldButton = new JButton("Open veld");
		newFieldButton.setEnabled(false);
		newFieldButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.openField(clientListPanel.getSelected(), farmListPanel.getSelected(),
						fieldListPanel.getSelected());
			}
		});
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
				controller.setSelectedClient(clientListPanel.getSelected());
			}
		});
		clientListPanel.contextmenu.rename.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newname = JOptionPane.showInputDialog("Wijzig naam",
						controller.getClients().get(clientListPanel.getSelected()).getName());
				if (newname != null) {
					if (!controller.renameClient(newname, clientListPanel.getSelected())) {
						JOptionPane.showMessageDialog(DataPanel.this.getRootPane(),
								"Fout bij het veranderen van de client naam: naam ongeldig");
					}
				}
			}
		});
		clientListPanel.contextmenu.merging.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newname = JOptionPane.showInputDialog("Nieuwe naam");
				if (newname != null) {
					controller.mergeClient(clientListPanel.list.getSelectedValuesList(), newname);
				}
			}
		});
		clientListPanel.contextmenu.delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.deleteClient(clientListPanel.getSelected());
			}
		});

		farmListPanel.list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				controller.setSelectedFarm(farmListPanel.getSelected());
			}
		});
		farmListPanel.contextmenu.rename.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newname = JOptionPane.showInputDialog("Wijzig naam", controller.getClients()
						.get(clientListPanel.getSelected()).getFarms().get(farmListPanel.getSelected()).getName());
				if (newname != null) {
					if (!controller.renameFarm(newname, clientListPanel.getSelected(), farmListPanel.getSelected())) {
						JOptionPane.showMessageDialog(DataPanel.this.getRootPane(),
								"Fout bij het veranderen van de bedrijfs naam: naam ongeldig");
					}
				}
			}
		});
		clientListPanel.contextmenu.delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.deleteFarm(clientListPanel.getSelected(), farmListPanel.getSelected());
			}
		});
		
		fieldListPanel.list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				newFieldButton.setEnabled((fieldListPanel.getSelected() != -1));
				controller.setSelectedField(fieldListPanel.getSelected());
			}
		});
		fieldListPanel.contextmenu.rename.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newname = JOptionPane.showInputDialog("Wijzig naam",
						controller.getClients().get(clientListPanel.getSelected()).getFarms()
								.get(farmListPanel.getSelected()).getFields().get(fieldListPanel.getSelected())
								.getName());
				if (newname != null) {
					if (!controller.renameField(newname, clientListPanel.getSelected(), farmListPanel.getSelected(),
							fieldListPanel.getSelected())) {
						JOptionPane.showMessageDialog(DataPanel.this.getRootPane(),
								"Fout bij het veranderen van de veld naam: naam ongeldig");
					}
				}
			}
		});
	}

	public void setClientListPanelData(List<String> data) {
		clientListPanel.setData(data);
	}

	public void setFarmListPanelData(List<String> data) {
		farmListPanel.setData(data);
	}

	public void setFieldListPanelData(List<String> data) {
		fieldListPanel.setData(data);
	}

}

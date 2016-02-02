package me.janco.tgl.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class ListPanel extends JPanel{
	
	public JList<String> list;
	private DefaultListModel<String> listModel;
	
	public ListPanel(String name){
		setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		setLayout(new BorderLayout());
		
		list = new JList<String>(listModel = new DefaultListModel<String>()); 
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);

		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setBorder(new LineBorder(Color.black));
		
		this.add(new JLabel(name), BorderLayout.NORTH);
		this.add(listScroller, BorderLayout.CENTER);
		
		ListPanelPopupMenu menu = new ListPanelPopupMenu();
		
		
		list.addMouseListener(new MouseAdapter() {			
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == e.BUTTON3){
					menu.setMergingEnabled(list.getSelectedIndices().length > 1);
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}
	
	public void setData(String data[]){
		listModel.clear();
		for(String s:data)
			listModel.addElement(s);
	}
}

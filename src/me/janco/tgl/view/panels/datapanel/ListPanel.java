package me.janco.tgl.view.panels.datapanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class ListPanel extends JPanel {

	public ListPanelPopupMenu contextmenu;
	public JList<String> list;
	private DefaultListModel<String> listModel;

	public ListPanel(String name) {
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

		contextmenu = new ListPanelPopupMenu();

		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					contextmenu.setMergingEnabled(list.getSelectedIndices().length > 1);
					contextmenu.setRenamingEnabled(list.getSelectedIndices().length == 1);
					contextmenu.show(list, e.getX(), e.getY());
				}
			}
		});
	}

	public int getSelected() {
		return list.getSelectedIndex();
	}

	public void setData(String data[]) {
		listModel.clear();
		for (String s : data)
			listModel.addElement(s);
	}
}

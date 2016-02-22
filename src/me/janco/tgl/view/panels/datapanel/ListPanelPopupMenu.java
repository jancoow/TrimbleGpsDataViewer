package me.janco.tgl.view.panels.datapanel;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class ListPanelPopupMenu extends JPopupMenu {
	public JMenuItem delete;
	public JMenuItem merging;
	public JMenuItem rename;

	public ListPanelPopupMenu() {
		rename = new JMenuItem("Hernoemen");
		merging = new JMenuItem("Samenvoegen");
		delete = new JMenuItem("Verwijderen");

		add(rename);
		add(merging);
		add(delete);
	}

	public void setDeleteEnabled(boolean enabled) {
		merging.setEnabled(enabled);
	}

	public void setMergingEnabled(boolean enabled) {
		merging.setEnabled(enabled);
	}

	public void setRenamingEnabled(boolean enabled) {
		rename.setEnabled(enabled);
	}
}

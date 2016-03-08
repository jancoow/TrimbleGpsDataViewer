package me.janco.tgl.controller;

import java.io.File;

import javax.swing.JPanel;

import me.janco.tgl.model.data.Field;
import me.janco.tgl.view.panels.viewpanel.ViewPanel;

public class ViewPanelController {
	private DataPanelController dpc;
	private Field field;
	private ViewPanel view;

	public ViewPanelController(Field field, DataPanelController dpc) {
		this.field = field;
		this.dpc = dpc;

		view = new ViewPanel(this);

		setData();
	}

	public void deleteSwath(int selected) {
		try {
			getField().deleteSwath(selected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setData();
	}

	public Field getField() {
		return field;
	}

	public JPanel getView() {
		return view;
	}

	public void renameSwath(String newname, int selected) {
		try {
			getField().renameSwath(newname, selected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setData();
	}

	private void setData() {
		view.swaths.setData(field.getSwathsNames());
		view.machinery.setData(field.getMachineryNames());
		view.mapViewPanel.setMapData(field.getSwaths());
	}

	public void setSelectedMachinery(int index) {
		File m = field.getMachineries().get(index).getCoverageFile();
		view.mapViewPanel.setMachinery(m);
	}

	public void switchBack() {
		view.mapViewPanel.map.dispose();
		Main.setView(dpc.getView());
	}
}

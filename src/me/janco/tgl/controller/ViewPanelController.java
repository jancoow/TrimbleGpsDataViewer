package me.janco.tgl.controller;

import java.io.File;

import javax.swing.JPanel;

import me.janco.tgl.model.data.Field;
import me.janco.tgl.view.panels.viewpanel.ViewPanel;

public class ViewPanelController {
	private ViewPanel view;
	private Field field;
	private DataPanelController dpc;

	public ViewPanelController(Field field, DataPanelController dpc){
    	this.field = field;
    	this.dpc = dpc;

    	view = new ViewPanel(this);

    	view.swaths.setData(field.getSwathsNames());
    	view.machinery.setData(field.getMachineryNames());    	
	}
	
	public JPanel getView(){
		return view;
	}
	
	public void switchBack(){
		Main.setView(dpc.getView());
	}
	
	public File getSwathShp(){
		return field.getSwathsFile();
	}
	
	public Field getField(){
		return field;
	}
	
	public void setSelectedMachinery(int index){
		File m = field.getMachineries().get(index).getCoverageFile();
		view.mapViewPanel.setMachinery(m);
	}

	public void renameSwath(String newname, int selected) {
		getField().renameSwath(newname, selected);
    	view.swaths.setData(field.getSwathsNames());
	}
	
	public void deleteSwath(int selected){
		getField().deleteSwath(selected);
    	view.swaths.setData(field.getSwathsNames());
	}
}

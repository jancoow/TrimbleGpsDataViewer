package me.janco.tgl.controller;

import javax.swing.JPanel;

import me.janco.tgl.model.data.Field;
import me.janco.tgl.view.panels.viewpanel.ViewPanel;

public class ViewPanelController {
	private ViewPanel view;
	private Field field;

	public ViewPanelController(Field field){
    	view = new ViewPanel(this);
    	this.field = field;
    	
    	view.swaths.setData(field.getSwathsNames());
    	view.machinery.setData(field.getMachineryNames());
	}
	
	public JPanel getView(){
		return view;
	}
}

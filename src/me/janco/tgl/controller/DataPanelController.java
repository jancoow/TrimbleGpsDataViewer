package me.janco.tgl.controller;

import java.io.BufferedReader;

import javax.swing.JPanel;

import me.janco.tgl.model.TrimbleDataDictonary;
import me.janco.tgl.view.panels.DataPanel;

public class DataPanelController {
	
	private DataPanel view;
	private TrimbleDataDictonary ttd;
	private int selectedClient, selectedFarm, selectedField;
	
	public DataPanelController(TrimbleDataDictonary ttd){
    	view = new DataPanel(this);
    	this.ttd = ttd;
    	  	
    	view.setClientListPanelData(ttd.getClientNames());
	}
	
	public JPanel getView(){
		return view;
	}
	
	public void setSelectedClient(int i){
		view.setFarmListPanelData(ttd.getClients().get(i).getFarmNames());
		view.setFieldListPanelData(new String[]{""});
		selectedClient = i;
		selectedFarm = -1;
		selectedField = -1;
	}

	public void setSelectedFarm(int i){
		if (i == -1) return;
		view.setFieldListPanelData(ttd.getClients().get(selectedClient).getFarms().get(i).getFieldNames());
		selectedFarm = i;
		selectedField = -1;
	}
	
	public void setSelectedField(int i){
		selectedField = i;
	}
	
}

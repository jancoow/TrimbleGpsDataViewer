package me.janco.tgl.controller;

import java.util.List;

import javax.swing.JPanel;

import me.janco.tgl.model.TrimbleDataDictonary;
import me.janco.tgl.model.data.Client;
import me.janco.tgl.model.data.Field;
import me.janco.tgl.view.panels.datapanel.DataPanel;

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
	
	public List<Client> getClients(){
		return ttd.getClients();
	}
	
	public void setSelectedClient(int i){
		if (i == -1) return;
		view.setFarmListPanelData(ttd.getClients().get(i).getFarmNames());
		view.setFieldListPanelData(new String[]{""});
		selectedClient = i;
	}

	public void setSelectedFarm(int i){
		if (i == -1) return;
		view.setFieldListPanelData(ttd.getClients().get(selectedClient).getFarms().get(i).getFieldNames());
		selectedFarm = i;
	}
	
	public void setSelectedField(int i){
		selectedField = i;
	}
	
	private void refreshLists(){
		view.setClientListPanelData(ttd.getClientNames());
		setSelectedClient(selectedClient);
		setSelectedFarm(selectedFarm);
		setSelectedField(selectedField);
	}
	
	public void openField(int clientid, int farmid, int fieldid){
		Field field = ttd.getClients().get(clientid).getFarms().get(farmid).getFields().get(fieldid);
		Main.setView(new ViewPanelController(field, this).getView());
	}
	
	public boolean renameClient(String newname, int clientid){
		boolean result = getClients().get(clientid).renameClient(newname);
		if (result)
			refreshLists();
		return result;
	}

	public boolean renameFarm(String newname, int clientid, int farmid){
		boolean result = getClients().get(clientid).getFarms().get(farmid).renameFarm(newname);
		if (result)
			refreshLists();
		return result;
	}
	
	public boolean renameField(String newname, int clientid, int farmid, int fieldid){
		boolean result = getClients().get(clientid).getFarms().get(farmid).getFields().get(fieldid).renameField(newname);
		if (result)
			refreshLists();
		return result;
	}	
	
	public boolean deleteClient(int clientid){
		boolean result = ttd.deleteClient(clientid);
		if(result)
			refreshLists();
		return result;
	}
}

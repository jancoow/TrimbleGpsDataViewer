package me.janco.tgl.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import me.janco.tgl.model.TrimbleDataDictonary;
import me.janco.tgl.model.data.Client;
import me.janco.tgl.model.data.Farm;
import me.janco.tgl.model.data.Field;
import me.janco.tgl.view.panels.datapanel.DataPanel;

public class DataPanelController {

	private int selectedClient, selectedFarm, selectedField;
	private TrimbleDataDictonary ttd;
	private DataPanel view;

	public DataPanelController(TrimbleDataDictonary ttd) {
		view = new DataPanel(this);
		this.ttd = ttd;

		view.setClientListPanelData(ttd.getClientNames());
	}
	
	public DataPanelController(){
		view = new DataPanel(this);
	}

	public boolean deleteClient(int clientid) {
		boolean result = ttd.deleteClient(clientid);
		if (result)
			refreshLists();
		return result;
	}
	public boolean deleteFarm(int clientid, int farmid){
		boolean result = ttd.getClients().get(clientid).deleteFarm(farmid);
		if (result)
			refreshLists();
		return result;	
	}
	public boolean deleteField(int clientid, int farmid, int fieldid) {
		boolean result = ttd.getClients().get(clientid).getFarms().get(farmid).deleteField(fieldid);
		if (result)
			refreshLists();
		return result;			
	}
	
	public List<Client> getClients() {
		return ttd.getClients();
	}

	public JPanel getView() {
		return view;
	}

	public void openField(int clientid, int farmid, int fieldid) {
		Field field = ttd.getClients().get(clientid).getFarms().get(farmid).getFields().get(fieldid);
		try {
			field.readSwathsDBF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Main.setView(new ViewPanelController(field, this).getView());
	}

	private void refreshLists() {
		view.setClientListPanelData(ttd.getClientNames());
		setSelectedClient(selectedClient);
		setSelectedFarm(selectedFarm);
		setSelectedField(selectedField);
	}

	public boolean renameClient(String newname, int clientid) {
		boolean result = getClients().get(clientid).renameClient(newname);
		if (result)
			refreshLists();
		return result;
	}

	public boolean renameFarm(String newname, int clientid, int farmid) {
		boolean result = getClients().get(clientid).getFarms().get(farmid).renameFarm(newname);
		if (result)
			refreshLists();
		return result;
	}

	public boolean renameField(String newname, int clientid, int farmid, int fieldid) {
		boolean result = getClients().get(clientid).getFarms().get(farmid).getFields().get(fieldid)
				.renameField(newname);
		if (result)
			refreshLists();
		return result;
	}

	public void setSelectedClient(int i) {
		if (i == -1)
			return;
		view.setFarmListPanelData(ttd.getClients().get(i).getFarmNames());
		view.setFieldListPanelData(new ArrayList<String>());
		selectedClient = i;
	}

	public void setSelectedFarm(int i) {
		if (i == -1)
			return;
		view.setFieldListPanelData(ttd.getClients().get(selectedClient).getFarms().get(i).getFieldNames());
		selectedFarm = i;
	}

	public void setSelectedField(int i) {
		selectedField = i;
	}

	public void mergeClient(List<String> selectedValuesList, String newname) {
		List<Client> selectedClients = new ArrayList<Client>();
		for(String s:selectedValuesList){
			selectedClients.add(ttd.getClientByName(s));
		}
		if(!ttd.mergeClients(selectedClients, newname)){ //Error when mering: probably a duplicated directory name (farm)
			JOptionPane.showMessageDialog(view, "Er is een fout opgetreden bij het samenvoegen. Mogelijk is er een dubbele bedrijfsnaam.", "Fout bij samenvoegen", 0);
		}
		refreshLists();
	}

	public void mergeFarm(int selectedClient, List<String> selectedValuesList, String newname) {
		List<Farm> selectedFarms = new ArrayList<Farm>();
		for(String s:selectedValuesList){
			selectedFarms.add(ttd.getClients().get(selectedClient).getFarmByName(s));
		}
		if(!ttd.getClients().get(selectedClient).mergeFarm(selectedFarms, newname)){ //Error when mering: probably a duplicated directory name (field)
			JOptionPane.showMessageDialog(view, "Er is een fout opgetreden bij het samenvoegen. Mogelijk is er een dubbele veldnaam.", "Fout bij samenvoegen", 0);
		}
		refreshLists();		
	}
}

package me.janco.tgl.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.janco.tgl.model.data.Client;

public class TrimbleDataDictonary {
	private ArrayList<Client> clients;
	private String path;
	
	public TrimbleDataDictonary(String path){
		this.path = path;
		clients = new ArrayList<Client>();
		readClientData();
	}
	
	private void readClientData(){
		//Read each client directory from the root data directory
	    for (File client : new File(path +"/Data/").listFiles()) {
	        if (client.isDirectory()) {
	            clients.add(new Client(client.getName(), client));
	        }
	    }
	}
	
	public ArrayList<Client> getClients(){
		return clients;
	}
	
	public String[] getClientNames(){
    	List<String> data = new ArrayList<String>();
    	for(Client c: getClients()){
    		data.add(c.getName());
    	}
    	return data.toArray(new String[data.size()]);
	}
	
	public boolean deleteClient(int clientid){
		boolean result = clients.get(clientid).delete();
		if (result)
			clients.remove(clientid);
		return result;
	}
}

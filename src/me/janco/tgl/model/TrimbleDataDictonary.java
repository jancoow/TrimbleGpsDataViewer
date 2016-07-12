package me.janco.tgl.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.janco.tgl.model.data.Client;
import me.janco.tgl.model.data.Farm;
import me.janco.tgl.model.data.Field;
import me.janco.tgl.model.data.Swath;

public class TrimbleDataDictonary {
	private ArrayList<Client> clients;
	private String path;

	public TrimbleDataDictonary(String path) {
		this.path = path;
		clients = new ArrayList<Client>();
		readClientData();
		MySqlExport();
	}

	public boolean deleteClient(int clientid) {
		return deleteClient(clients.get(clientid));
	}
	
	private boolean deleteClient(Client client){
		boolean result = client.delete();
		if (result)
			clients.remove(client);
		return result;	
	}
	
	public boolean mergeClients(List<Client> clients, String newname){
		File newlocation = new File(path + "/Data/" + newname);
		boolean success = (newlocation.mkdir());
		if(success){
			for(Client c:clients){
				Iterator<Farm> fi = c.getFarms().iterator();
				boolean fsucess = true;
				while(fi.hasNext()){
					Farm f = fi.next();
					try{
						Files.move(f.getFilepath().toPath(), new File(newlocation.getAbsolutePath() + "/" + f.getName()).toPath());
						fi.remove();
					}catch (IOException e){
						fsucess = false;
					}
				}
				
				//No errors, so all the farms should be moved, so client can be deleted
				if(fsucess){
					deleteClient(c);
				}
			}
			this.clients.add(new Client(newname, newlocation));
		}
		return success;
	}

	public List<String> getClientNames() {
		List<String> data = new ArrayList<String>();
		for (Client c : getClients()) {
			data.add(c.getName());
		}
		return data;
	}
	
	public Client getClientByName(String name){
		for(Client c: getClients()){
			if(c.getName().equals(name))
				return c;
		}
		return null;
	}

	public ArrayList<Client> getClients() {
		return clients;
	}

	private void readClientData() {
		// Read each client directory from the root data directory
		clients.clear();
		for (File client : new File(path + "/Data/").listFiles()) {
			if (client.isDirectory()) {
				clients.add(new Client(client.getName(), client));
			}
		}
	}

	public void MySqlExport(){
		for (Client c:clients){
			System.out.println("INSERT INTO Client(Name) VALUES ('" + c.getName() + "');");
			for(Farm fa: c.getFarms()){
				System.out.println("INSERT INTO Farm(Name, ClientName) VALUES ('" + fa.getName() + "','" + c.getName() + "');");
				for(Field fi: fa.getFields()){
					System.out.println("INSERT INTO Field(Name, Latitude, Longitude, ClientName, FarmName) VALUES ('" + fi.getName() + "','" + fi.getLongitude() + "','" + fi.getLatitude() + "','" + c.getName() + "','" + fa.getName() + "');");
					for(Swath s:fi.getSwaths()){
						System.out.println("INSERT INTO Swath(SwathId, Name, Length, Dist1, Dist2, ClientName, FarmName, FieldName) VALUES ('" + s.getUniqueid() + "', '" + s.getName() + "','" + s.getLength() + "','" + s.getDist1() + "','" + s.getDist2() + "','" + c.getName() + "','" + fa.getName() + "','" + fi.getName() + "');");
						for(int i = 0; i < s.getCoordinates().size(); i++){
							System.out.println("INSERT INTO SwathCoordinate(Latitude, Longitude, Sequence, SwathId, ClientName, FarmName, FieldName) VALUES ('" + s.getCoordinates().get(i)[0] + "','" + s.getCoordinates().get(i)[1] + "', '" + i + "', '" + s.getUniqueid() + "', '" + c.getName() + "','" + fa.getName() + "','" + fi.getName() + "');");
						}
					}
				}
			}
			System.out.println("\n\n");
		}
	}
}

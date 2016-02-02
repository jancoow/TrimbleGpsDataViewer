package me.janco.tgl.model.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Client {
	private ArrayList<Farm> farms;
	private String name;
	private String filepath;
	
	public Client(String name, String filepath){
		this.name = name;
		this.filepath = filepath;
        farms = new ArrayList<Farm>();
		
        //Read each farm directory from the client directory
        for (File farm : new File(filepath).listFiles()) {
	        if (farm.isDirectory()) {
	        	farms.add(new Farm(farm.getName(), farm.getAbsolutePath()));
	        }
        }
	}
	
	public void addFarm(Farm farm){
		farms.add(farm);
	}
	
	public String[] getFarmNames(){
    	List<String> data = new ArrayList<String>();
    	for(Farm c: farms){
    		data.add(c.getName());
    	}
    	return data.toArray(new String[data.size()]);
	}

	public ArrayList<Farm> getFarms() {
		return farms;
	}

	public String getName() {
		return name;
	}

	public String getFilepath() {
		return filepath;
	}	
}

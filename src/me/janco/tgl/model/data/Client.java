package me.janco.tgl.model.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Client {
	private ArrayList<Farm> farms;
	private String name;
	private File file;
	
	public Client(String name, File file){
		this.name = name;
		this.file = file;
        farms = new ArrayList<Farm>();
		
        //Read each farm directory from the client directory
        for (File farm : file.listFiles()) {
	        if (farm.isDirectory()) {
	        	farms.add(new Farm(farm.getName(), farm));
	        }
        }
	}
	
	public boolean renameClient(String newname){
		boolean result = file.renameTo(new File(file.getParent() + "/" + newname));
		if(result){
			file = new File(file.getParent() + "/" + newname);
			this.name = newname;
			for(Farm f:farms){
				f.changeParentDir(file.getAbsolutePath());
			}
		}
		return result;
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
	
	public boolean delete(){
		try {
			FileUtils.deleteDirectory(file);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public ArrayList<Farm> getFarms() {
		return farms;
	}

	public String getName() {
		return name;
	}

	public File getFilepath() {
		return file;
	}	
}

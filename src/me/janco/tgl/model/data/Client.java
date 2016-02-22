package me.janco.tgl.model.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Client {
	private ArrayList<Farm> farms;
	private File file;
	private String name;

	public Client(String name, File file) {
		this.name = name;
		this.file = file;
		farms = new ArrayList<Farm>();

		// Read each farm directory from the client directory
		for (File farm : file.listFiles()) {
			if (farm.isDirectory()) {
				farms.add(new Farm(farm.getName(), farm));
			}
		}
	}

	public void addFarm(Farm farm) {
		farms.add(farm);
	}

	public boolean delete() {
		try {
			FileUtils.deleteDirectory(file);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean deleteFarm(Farm f){
		if(f.delete()){
			this.farms.remove(f);
			return true;
		}
		return false;
	}
	
	public boolean deleteFarm(int farmid){
		return deleteFarm(farms.get(farmid));
	}

	public List<String> getFarmNames() {
		List<String> data = new ArrayList<String>();
		for (Farm c : farms) {
			data.add(c.getName());
		}
		return data;
	}

	public ArrayList<Farm> getFarms() {
		return farms;
	}

	public File getFilepath() {
		return file;
	}

	public String getName() {
		return name;
	}

	public boolean renameClient(String newname) {
		boolean result = file.renameTo(new File(file.getParent() + "/" + newname));
		if (result) {
			file = new File(file.getParent() + "/" + newname);
			this.name = newname;
			for (Farm f : farms) {
				f.changeParentDir(file.getAbsolutePath());
			}
		}
		return result;
	}
}

package me.janco.tgl.model.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
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

	public boolean delete() {
		try {
			FileUtils.deleteDirectory(file);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	private boolean deleteFarm(Farm f){
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
	
	public Farm getFarmByName(String farm){
		for(Farm f: getFarms()){
			if(f.getName().equals(farm))
				return f;
		}
		return null;
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

	public boolean mergeFarm(List<Farm> selectedFarms, String newname) {
		System.out.println("TEST");
		
		File newlocation = new File(file.getAbsolutePath() + "/" + newname);
		boolean success = (newlocation.mkdir());
		if(success){
			for(Farm f:selectedFarms){
				System.out.println(f.getName());
				Iterator<Field> fieldi = f.getFields().iterator();
				boolean fsucess = true;
				while(fieldi.hasNext()){
					Field field = fieldi.next();
					try{
						Files.move(field.getFilepath().toPath(), new File(newlocation.getAbsolutePath() + "/" + field.getName()).toPath());
						fieldi.remove();
					}catch (IOException e){
						fsucess = false;
					}
				}
				
				//No errors, so all the fields should be moved, so farm can be deleted
				if(fsucess){
					deleteFarm(f);
				}
			}
			this.farms.add(new Farm(newname, newlocation));
		}
		return success;
	}
}

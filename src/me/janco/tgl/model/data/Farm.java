package me.janco.tgl.model.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Farm {
	private ArrayList<Field> fields;
	private String name;
	private File file;
	
	public Farm(String name, File file){
		this.name = name;
		this.file = file;
		fields = new ArrayList<Field>();
		
		//Read each field directory from the farm directory
        for (File field : file.listFiles()) {
	        if (field.isDirectory()) {
	        	fields.add(new Field(field.getName(), field));
	        }
        }
	}

	public boolean renameFarm(String newname){
		boolean result = file.renameTo(new File(file.getParent() + "/" + newname));
		if(result)
			this.name = newname;
			changeParentDir(file.getParent());
		return result;
	}	
	
	public void changeParentDir(String newpath){
		file = new File(newpath + "/" + name);
		for(Field f:fields){
			f.changeParentDir(file.getPath());
		}							
	}
	
	public void addField(Field field){
		fields.add(field);
	}

	public String getName() {
		return name;
	}

	public File getFilepath() {
		return file;
	}	
	
	public ArrayList<Field> getFields() {
		return fields;
	}

	public String[] getFieldNames(){
    	List<String> data = new ArrayList<String>();
    	for(Field c: fields){
    		data.add(c.getName());
    	}
    	return data.toArray(new String[data.size()]);
	}
}

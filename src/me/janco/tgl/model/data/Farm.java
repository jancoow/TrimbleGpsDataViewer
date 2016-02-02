package me.janco.tgl.model.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Farm {
	private ArrayList<Field> fields;
	private String name;
	private String filepath;
	
	public Farm(String name, String filepath){
		this.name = name;
		this.filepath = filepath;
		fields = new ArrayList<Field>();
		
		//Read each field directory from the farm directory
        for (File field : new File(filepath).listFiles()) {
	        if (field.isDirectory()) {
	        	fields.add(new Field(field.getName(), field.getAbsolutePath()));
	        }
        }
	}
	
	public void addField(Field field){
		fields.add(field);
	}

	public String getName() {
		return name;
	}

	public String getFilepath() {
		return filepath;
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

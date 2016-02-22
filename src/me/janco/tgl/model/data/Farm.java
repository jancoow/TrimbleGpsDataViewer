package me.janco.tgl.model.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Farm {
	private ArrayList<Field> fields;
	private File file;
	private String name;

	public Farm(String name, File file) {
		this.name = name;
		this.file = file;
		fields = new ArrayList<Field>();

		// Read each field directory from the farm directory
		for (File field : file.listFiles()) {
			if (field.isDirectory()) {
				fields.add(new Field(field.getName(), field));
			}
		}
	}

	public void addField(Field field) {
		fields.add(field);
	}

	public void changeParentDir(String newpath) {
		file = new File(newpath + "/" + name);
		for (Field f : fields) {
			f.changeParentDir(file.getPath());
		}
	}

	public List<String> getFieldNames() {
		List<String> data = new ArrayList<String>();
		for (Field c : fields) {
			data.add(c.getName());
		}
		return data;
	}

	public ArrayList<Field> getFields() {
		return fields;
	}

	public File getFilepath() {
		return file;
	}

	public String getName() {
		return name;
	}

	public boolean renameFarm(String newname) {
		boolean result = file.renameTo(new File(file.getParent() + "/" + newname));
		if (result)
			this.name = newname;
		changeParentDir(file.getParent());
		return result;
	}

	public boolean delete() {
		try {
			FileUtils.deleteDirectory(file);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}

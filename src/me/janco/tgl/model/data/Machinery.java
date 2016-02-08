package me.janco.tgl.model.data;

import java.io.File;

public class Machinery {
	private String name;
	private File file;
	
	public Machinery(String name, File file){
		this.name = name;
		this.file = file;
	}

	public String getName() {
		return name;
	}
	
	public File getCoverageFile(){
		return new File(file.getAbsolutePath() + "/Coverage.shp");
	}
}

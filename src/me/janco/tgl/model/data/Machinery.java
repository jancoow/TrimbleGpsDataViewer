package me.janco.tgl.model.data;

import java.io.File;

public class Machinery {
	private String name;
	
	public Machinery(String name, File file){
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

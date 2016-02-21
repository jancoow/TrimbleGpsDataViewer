package me.janco.tgl.model.data;

import java.io.File;

public class Machinery {
	private File file;
	private String name;

	public Machinery(String name, File file) {
		this.name = name;
		this.file = file;
	}

	public File getCoverageFile() {
		return new File(file.getAbsolutePath() + "/Coverage.shp");
	}

	public String getName() {
		return name;
	}
}

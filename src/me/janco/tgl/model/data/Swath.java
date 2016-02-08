package me.janco.tgl.model.data;

public class Swath {
	private String datetime, version, id , name, uniqueid;
	
	public Swath(String datetime, String version, String id, String name, float length, float dist1, float dist2, String uniqueid){
		this.datetime = datetime;
		this.version = version;
		this.id = id;
		this.name = name;
		this.uniqueid = uniqueid;
	}
	
	public String getName(){
		return name;
	}
}

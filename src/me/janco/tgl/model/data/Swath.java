package me.janco.tgl.model.data;

import net.iryndin.jdbf.core.DbfRecord;

public class Swath {
	private String datetime, version, id , name, uniqueid;
	private DbfRecord dbfr;
	
	public Swath(String datetime, String version, String id, String name, float length, float dist1, float dist2, String uniqueid, DbfRecord dbfr){
		this.datetime = datetime;
		this.version = version;
		this.id = id;
		this.name = name;
		this.uniqueid = uniqueid;
		this.dbfr = dbfr;
	}
	
	public String getName(){
		return name;
	}
}

package me.janco.tgl.model.data;

import java.util.HashMap;
import java.util.Map;

import java.math.BigDecimal;
import java.util.Date;

public class Swath {
	private String time, version, name, uniqueid;
	private Date date;
	private BigDecimal id, length, dist1, dist2;
	
	public Swath(Date date, String time, String version, BigDecimal id, String name, BigDecimal length, BigDecimal dist1, BigDecimal dist2, String uniqueid){
		this.date = date;
		this.time = time;
		this.version = version;
		this.id = id;
		this.name = name;
		this.length = length;
		this.dist1 = dist1;
		this.dist2 = dist2;
		this.uniqueid = uniqueid;
	}
	
	public Map<String, Object> getMap(){
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("Date", date);
		m.put("Time", time);
		m.put("Version", version);
		m.put("Id", id);
		m.put("Name", name);
		m.put("Length", length);
		m.put("Dist1", dist1);
		m.put("Dist2", dist2);
		m.put("UniqueID", uniqueid);
		return m;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String newname){
		this.name = newname;
	}
}

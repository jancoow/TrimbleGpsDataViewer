package me.janco.tgl.model.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.geotools.map.FeatureLayer;

public class Swath {
	private Date date;
	private FeatureLayer featurelayer;
	private double id, length, dist1, dist2;
	private String time, version, name, uniqueid;

	public Swath(Date date, String time, String version, double id, String name, double length, double dist1,
			double dist2, String uniqueid, FeatureLayer featurelayer) {
		this.date = date;
		this.time = time;
		this.version = version;
		this.id = id;
		this.name = name;
		this.length = length;
		this.dist1 = dist1;
		this.dist2 = dist2;
		this.uniqueid = uniqueid;
		this.featurelayer = featurelayer;
	}

	public FeatureLayer getFeaturelayer() {
		return featurelayer;
	}

	public Map<String, Object> getMap() {
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

	public String getName() {
		return name;
	}

	public String getUniqueid() {
		return uniqueid;
	}

	public void setName(String newname) {
		this.name = newname;
	}

}

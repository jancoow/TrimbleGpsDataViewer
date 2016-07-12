package me.janco.tgl.model.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.geotools.map.FeatureLayer;

public class Swath {
	private Date date;
	private FeatureLayer featurelayer;
	private double id, length, dist1, dist2;
	private String time, version, name, uniqueid;
	private ArrayList<float[]> coordinates;

	public Swath(Date date, String time, String version, double id, String name, double length, double dist1,
			double dist2, String uniqueid, FeatureLayer featurelayer, ArrayList<float[]> coordinates) {
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
		this.coordinates = coordinates;
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

	public double getDist2() {
		return dist2;
	}

	public double getLength() {
		return length;
	}

	public double getDist1() {
		return dist1;
	}

	public void setName(String newname) {
		this.name = newname;
	}

	public ArrayList<float[]> getCoordinates() {
		return coordinates;
	}
}

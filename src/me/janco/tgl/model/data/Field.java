package me.janco.tgl.model.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

import diewald_shapeFile.shapeFile.ShapeFile;
import net.iryndin.jdbf.core.DbfRecord;
import net.iryndin.jdbf.reader.DbfReader;

public class Field {
	private String name;
	private String filepath;
	
	private ArrayList<Swath> swaths;
	private float longitude;
	private float latitude;
	
	public Field(String name, String filepath){
		this.name = name;
		this.filepath = filepath;
		swaths = new ArrayList<Swath>();

		
		//read the lat,lon file
        for (File file : new File(filepath).listFiles()) {
        	String[] baseex = file.getName().split("\\.(?=[^\\.]+$)");
	        if (baseex.length == 2 && baseex[1].equals("pos")) {
	        	String latlong[] = baseex[0].split("E");
	        	latitude = Float.parseFloat(latlong[0]);
	        	longitude = Float.parseFloat(latlong[1].substring(0, latlong[1].length()-1));
	        }
        }
        
        //Read all the swaths
		try {
			if(new File(filepath + "/Swaths.dbf").exists())
				readSwathsDBF();
		}catch (Exception e){
			e.printStackTrace();
		}
				
		try {
			new ShapeFile(filepath, "Swaths");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public void readSwathsDBF() throws Exception {
        InputStream dbf = new FileInputStream(filepath + "/Swaths.dbf");
        DbfRecord rec;
        try (DbfReader reader = new DbfReader(dbf)) {
            while ((rec = reader.read()) != null) {
                rec.setStringCharset(Charset.forName("Cp866"));
                swaths.add(new Swath(rec.getString("Date"), rec.getString("Version"), rec.getString("Id"), rec.getString("Name"), Float.parseFloat(rec.getString("Length")), Float.parseFloat(rec.getString("Dist1")), Float.parseFloat(rec.getString("Dist2")), rec.getString("UniqueID")));
            }
        }
    }

	public String getName() {
		return name;
	}

	public float getLongitude() {
		return longitude;
	}

	public float getLatitude() {
		return latitude;
	}
    
    
}

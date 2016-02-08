package me.janco.tgl.model.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import diewald_shapeFile.shapeFile.ShapeFile;
import net.iryndin.jdbf.core.DbfRecord;
import net.iryndin.jdbf.reader.DbfReader;

public class Field {
	private String name;
	private File file;
	
	private ArrayList<Swath> swaths;
	private ArrayList<Machinery> machineries;
	private float longitude;
	private float latitude;
	
	public Field(String name, File file){
		this.name = name;
		this.file = file;
		swaths = new ArrayList<Swath>();
		machineries = new ArrayList<Machinery>();
		
		//read the lat,lon file
        for (File f : file.listFiles()) {
        	String[] baseex = f.getName().split("\\.(?=[^\\.]+$)");
	        if (baseex.length == 2 && baseex[1].equals("pos")) {
	        	try{
	        		String latlong[] = baseex[0].split("E");
	        		latitude = Float.parseFloat(latlong[0]);
	        		longitude = Float.parseFloat(latlong[1].substring(0, latlong[1].length()-1));
	        	}catch(NumberFormatException | IndexOutOfBoundsException e){
	        		System.out.println("Error reading position file");
	        	}
	        }
        }
        
        //Read all the swaths
		try {
			if(new File(file.getAbsolutePath() + "/Swaths.dbf").exists()){
				readSwathsDBF();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		//Read machining
		for (File machining : file.listFiles()) {
			if(machining.isDirectory()){
				machineries.add(new Machinery(machining.getName(), machining));
			}
        }
	}
	
	public boolean renameField(String newname){
		boolean result = file.renameTo(new File(file.getParent() + "/" + newname));
		if(result)
			file = new File(file.getParent() + "/" + newname);
			this.name = newname;
		return result;
	}	
	
	public void changeParentDir(String newpath){
		file = new File(newpath + "/" + name);							
	}
	
	
    private void readSwathsDBF() throws Exception {
        InputStream dbf = new FileInputStream(file.getAbsolutePath() + "/Swaths.dbf");
        DbfRecord rec;
        try (DbfReader reader = new DbfReader(dbf)) {
            while ((rec = reader.read()) != null) {
                rec.setStringCharset(Charset.forName("Cp866"));
                swaths.add(new Swath(rec.getString("Date"), rec.getString("Version"), rec.getString("Id"), rec.getString("Name"), Float.parseFloat(rec.getString("Length")), Float.parseFloat(rec.getString("Dist1")), Float.parseFloat(rec.getString("Dist2")), rec.getString("UniqueID"), rec));
            }
        }
    }
    
    public String[] getSwathsNames(){
    	List<String> data = new ArrayList<String>();
    	for(Swath s: swaths){
    		data.add(s.getName());
    	}
    	return data.toArray(new String[data.size()]);
    }
    
    public String[] getMachineryNames(){
    	List<String> data = new ArrayList<String>();
    	for(Machinery s: machineries){
    		data.add(s.getName());
    	}
    	return data.toArray(new String[data.size()]);
    }
    
    public List<Machinery> getMachineries(){
    	return machineries;
    }
    
    public File getSwathsFile(){
    	return new File(file.getAbsolutePath() + "/Swaths.shp");
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

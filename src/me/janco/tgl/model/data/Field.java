package me.janco.tgl.model.data;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.Transaction;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.map.FeatureLayer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory;

public class Field {
	private File file;
	private float latitude;
	private float longitude;
	private ArrayList<Machinery> machineries;
	private String name;

	private ArrayList<Swath> swaths;

	public Field(String name, File file) {
		this.name = name;
		this.file = file;
		swaths = new ArrayList<Swath>();
		machineries = new ArrayList<Machinery>();

		// read the lat,lon file
		for (File f : file.listFiles()) {
			String[] baseex = f.getName().split("\\.(?=[^\\.]+$)");
			if (baseex.length == 2 && baseex[1].equals("pos")) {
				try {
					String latlong[] = baseex[0].split("E");
					latitude = Float.parseFloat(latlong[0]);
					longitude = Float.parseFloat(latlong[1].substring(0, latlong[1].length() - 1));
				} catch (NumberFormatException | IndexOutOfBoundsException e) {
					System.out.println("Error reading position file");
				}
			}
		}

		// Read all the swaths
		try {
			if (new File(file.getAbsolutePath() + "/Swaths.dbf").exists()) {
				readSwathsDBF();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong while reading swaths");
		}

		// Read machining
		for (File machining : file.listFiles()) {
			if (machining.isDirectory()) {
				machineries.add(new Machinery(machining.getName(), machining));
			}
		}
	}

	public void changeParentDir(String newpath) {
		file = new File(newpath + "/" + name);
	}

	public void deleteSwath(int selected) throws Exception {
		FileDataStore filedatastore = FileDataStoreFinder.getDataStore(getSwathsFile());
		Transaction transaction = new DefaultTransaction("create");
		SimpleFeatureStore featureStore = (SimpleFeatureStore) filedatastore.getFeatureSource();
		featureStore.setTransaction(transaction);

		// Remove the swath
		FilterFactory ff = CommonFactoryFinder.getFilterFactory();
		Filter filter = ff.equal(ff.property("UniqueID"), ff.literal("" + swaths.get(selected).getUniqueid()), true);
		featureStore.removeFeatures(filter);

		// Write it to the file
		transaction.commit();

		// Close everything and reload
		transaction.close();
		filedatastore.dispose();
		readSwathsDBF();
	}

	public List<Machinery> getMachineries() {
		return machineries;
	}

	public List<String> getMachineryNames() {
		List<String> data = new ArrayList<String>();
		for (Machinery s : machineries) {
			data.add(s.getName());
		}
		return data;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Swath> getSwaths() {
		return swaths;
	}

	public File getSwathsFile() {
		return new File(file.getAbsolutePath() + "/Swaths.shp");
	}

	public float getLongitude() {
		return longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public List<String> getSwathsNames() {
		List<String> data = new ArrayList<String>();
		for (Swath s : swaths) {
			data.add(s.getName());
		}
		return data;
	}

	public void readSwathsDBF() throws Exception {
		swaths.clear();
		FileDataStore filedatastore = FileDataStoreFinder.getDataStore(getSwathsFile());
		Style style = SLD.createSimpleStyle(filedatastore.getFeatureSource().getSchema(), Color.red);
		SimpleFeatureIterator it = filedatastore.getFeatureSource().getFeatures().features();
		while (it.hasNext()) {
			SimpleFeature s = it.next();
            DefaultFeatureCollection lineCollection = new DefaultFeatureCollection();
			lineCollection.add(s);

			ArrayList<float[]> coordinates = new ArrayList<float[]>();
			String coordinatesstring = s.getAttribute(0).toString();
			coordinatesstring = coordinatesstring.replace("MULTILINESTRING ((", "");
			coordinatesstring = coordinatesstring.replace("))", "");
			for(String latlong : coordinatesstring.split(", ")){
				String[] latlongsplit = latlong.split(" ");
				coordinates.add(new float[]{Float.parseFloat(latlongsplit[0]), Float.parseFloat(latlongsplit[1])});
			}

			swaths.add(new Swath((Date) s.getAttribute("Date"), (String) s.getAttribute("Time"),
					(String) s.getAttribute("Version"), (double) ((int) s.getAttribute("Id")),
					(String) s.getAttribute("Name"), (double) s.getAttribute("Length"),
					(double) s.getAttribute("Dist1"), (double) s.getAttribute("Dist2"),
					(String) s.getAttribute("UniqueID"),
					new FeatureLayer(lineCollection, style, (String) s.getAttribute("Name")), coordinates));
		}
		it.close();
		filedatastore.dispose();
	}

	public boolean renameField(String newname) {
		boolean result = file.renameTo(new File(file.getParent() + "/" + newname));
		if (result)
			file = new File(file.getParent() + "/" + newname);
		this.name = newname;
		return result;
	}

	public void renameSwath(String newname, int id) throws Exception {
		FileDataStore filedatastore = FileDataStoreFinder.getDataStore(getSwathsFile());
		Transaction transaction = new DefaultTransaction("create");
		SimpleFeatureStore featureStore = (SimpleFeatureStore) filedatastore.getFeatureSource();
		featureStore.setTransaction(transaction);

		// Edit the name
		FilterFactory ff = CommonFactoryFinder.getFilterFactory();
		Filter filter = ff.equal(ff.property("UniqueID"), ff.literal("" + swaths.get(id).getUniqueid()), true);
		featureStore.modifyFeatures("Name", newname, filter);

		// Write to the file
		transaction.commit();

		// Close everything and reload
		transaction.close();
		filedatastore.dispose();
		readSwathsDBF();
	}
	
	public boolean delete() {
		try {
			FileUtils.deleteDirectory(file);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public File getFilepath() {
		return file;
	}
}

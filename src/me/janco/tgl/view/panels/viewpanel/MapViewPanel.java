package me.janco.tgl.view.panels.viewpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.FeatureLayer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapPane;
import org.geotools.swing.action.PanAction;
import org.geotools.swing.action.ZoomInAction;
import org.geotools.swing.action.ZoomOutAction;

public class MapViewPanel extends JPanel {

	private MapContent map;
	private JMapPane mapPane;
	
	public MapViewPanel(File f){
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(0, 15, 0, 0));
		map = new MapContent();
		
		
		try {
			if(f == null || !f.exists())
				throw new IOException("File doesn't exist");
	        SimpleFeatureSource featureSource = FileDataStoreFinder.getDataStore(f).getFeatureSource();
	        Style style = SLD.createSimpleStyle(featureSource.getSchema(), Color.red);    
	        map.addLayer(new FeatureLayer(featureSource, style));
		} catch (IOException e) {
			System.out.println("Couldn't load swaths file");
		}
        
		mapPane = new JMapPane(map);
		this.add(mapPane, BorderLayout.CENTER);
		
		JPanel buttons = new JPanel();
		JButton zoomInButton = new JButton(new ZoomInAction(mapPane));
		JButton zoomOutButton = new JButton(new ZoomOutAction(mapPane));
		JButton panButton = new JButton(new PanAction(mapPane));
		buttons.add(panButton);
		buttons.add(zoomOutButton);
		buttons.add(zoomInButton);
		this.add(buttons, BorderLayout.NORTH);
		
		mapPane.setCursorTool(new org.geotools.swing.tool.PanTool() );
		mapPane.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
			   int clicks = e.getWheelRotation();
			   // -ve means wheel moved up, +ve means down
			   int sign = (clicks < 0 ? -1 : 1);

			   ReferencedEnvelope env = mapPane.getDisplayArea();
			   double width = env.getWidth();
			   double delta = width * 0.1 * sign;

			   env.expandBy(delta);
			   mapPane.setDisplayArea(env);
			   mapPane.repaint();
			}
		});
	}
	

	
	public void setMachinery(File f){
		if(map.layers().size() > 1){
			map.removeLayer(map.layers().get(1));
		}
        SimpleFeatureSource featureSource;
		try {
			if(f == null || !f.exists())
				throw new IOException("File doesn't exist");
			featureSource = FileDataStoreFinder.getDataStore(f).getFeatureSource();
	        Style style = SLD.createSimpleStyle(featureSource.getSchema()); 
	        map.addLayer(new FeatureLayer(featureSource, style));	
		} catch (IOException e) {
			System.out.println("Couldn't load machinery");
		}
		mapPane.repaint(10);
	}
	

}

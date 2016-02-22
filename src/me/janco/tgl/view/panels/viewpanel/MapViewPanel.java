package me.janco.tgl.view.panels.viewpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapPane;
import org.geotools.swing.action.PanAction;
import org.geotools.swing.action.ZoomInAction;
import org.geotools.swing.action.ZoomOutAction;
import org.opengis.feature.simple.SimpleFeature;

import me.janco.tgl.model.data.Swath;

@SuppressWarnings("serial")
public class MapViewPanel extends JPanel {

	public MapContent map;
	public JMapPane mapPane;

	public MapViewPanel() {
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(0, 15, 0, 0));
		map = new MapContent();
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

		mapPane.setCursorTool(new org.geotools.swing.tool.PanTool());
		mapPane.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int clicks = e.getWheelRotation();
				int sign = (clicks < 0 ? -1 : 1);

				ReferencedEnvelope env = mapPane.getDisplayArea();
				double width = env.getWidth();
				double delta = width * 0.1 * sign;

				env.expandBy(delta);
				mapPane.repaint();
			}
		});
	}

	public void setMachinery(File f) {
		if (map.layers().size() > 1) {
			map.removeLayer(map.layers().get(1));
		}
		SimpleFeatureSource featureSource;
		try {
			if (f == null || !f.exists())
				throw new IOException("File doesn't exist");
			featureSource = FileDataStoreFinder.getDataStore(f).getFeatureSource();
			Style style = SLD.createSimpleStyle(featureSource.getSchema());
			map.addLayer(new FeatureLayer(featureSource, style));
		} catch (IOException e) {
			System.out.println("Couldn't load machinery");
		}
		mapPane.repaint();
	}

	public void setMapData(List<Swath> swaths) {
		Iterator<Layer> i = map.layers().iterator();
		while (i.hasNext()) {
			map.removeLayer(i.next());
		}
		for (Swath s : swaths) {
			if (s.getFeaturelayer() != null && map != null)
				map.addLayer(s.getFeaturelayer());
		}
	}

	public void setSelectedSwath(String selectedValue) {
		Iterator<Layer> it = map.layers().iterator();
		while (it.hasNext()) {
			Layer l = it.next();
			if (l.getTitle() != null && l.getTitle().equals(selectedValue)) {
				try {
					Style style = SLD.createLineStyle(Color.green, 1);
					DefaultFeatureCollection lineCollection = new DefaultFeatureCollection();
					SimpleFeature sf;
					sf = (SimpleFeature) l.getFeatureSource().getFeatures().toArray()[0];
					lineCollection.add(sf);
					map.removeLayer(l);
					map.addLayer(new FeatureLayer(lineCollection, style, (String) sf.getAttribute("Name")));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					Style style = SLD.createLineStyle(Color.red, 1);
					DefaultFeatureCollection lineCollection = new DefaultFeatureCollection();
					SimpleFeature sf;
					sf = (SimpleFeature) l.getFeatureSource().getFeatures().toArray()[0];
					lineCollection.add(sf);
					map.removeLayer(l);
					map.addLayer(new FeatureLayer(lineCollection, style, (String) sf.getAttribute("Name")));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		mapPane.repaint();
	}
}

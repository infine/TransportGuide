package com.map.osm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.mapsforge.core.GeoPoint;

public class BusStation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String id;

	public String lat;

	public String lon;

	public String user;

	public String uid;

	public String visible;

	public String version;

	public String changeset;

	public String timestamp;

	public List<Tag> tags = new ArrayList<Tag>();

	public List<BusLine> lineList = new ArrayList<BusLine>();

	public BusStation(String id, String lat, String lon, String user,
			String uid, String visible, String version, String changeset,
			String timestamp) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.user = user;
		this.uid = uid;
		this.visible = visible;
		this.version = version;
		this.changeset = changeset;
		this.timestamp = timestamp;
	}

	public void addTag(Tag tag) {
		tags.add(tag);
	}

	public void addLine(BusLine line) {
		lineList.add(line);
	}

	public GeoPoint getLocation() {
		return new GeoPoint(Double.parseDouble(lat), Double.parseDouble(lon));
	}

	@Override
	public String toString() {
		for (Tag tag : tags) {
			if (tag.getK().equalsIgnoreCase("name"))
				return tag.getV();
		}
		return "no name";
	}

	public String getId() {
		return id;
	}

	public List<BusLine> getLines() {
		return lineList;
	}

}

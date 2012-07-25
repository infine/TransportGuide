package com.map.app;

import java.util.ArrayList;
import java.util.List;

import org.mapsforge.core.GeoPoint;

public class BusStation {
	private String id;
	private String name;
	private double latitude;
	private double longitude;
	private List<BusLines> busLinesList = new ArrayList<BusLines>();

	public BusStation(String BusStaID, String BusStaName, String BusStaCoord) {
		this.setId(BusStaID);
		this.setName(BusStaName);
		String[] s = BusStaCoord.split(",");
		this.latitude = Double.parseDouble(s[1]);
		this.longitude = Double.parseDouble(s[0]);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addLines(BusLines line)
	{
		busLinesList.add(line);
	}
	
	public List<BusLines> getLines()
	{
		return busLinesList;
	}

	@Override
	public String toString() {
		return name;
	}

	public boolean equals(String id1) {
		if (this.id.equals(id1))
			return true;
		else
			return false;
	}

	public GeoPoint busStationLocation() {
		return new GeoPoint(latitude, longitude);
	}

}

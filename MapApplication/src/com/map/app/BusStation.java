package com.map.app;

import org.mapsforge.core.GeoPoint;

public class BusStation {
	private String id;
	private String name;
	private double latitude;
	private double longitude;

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

	@Override
	public String toString() {
		return name;
	}
	
	public GeoPoint busStationLocation()
	{
		return new GeoPoint(latitude,longitude);
	}
	

}

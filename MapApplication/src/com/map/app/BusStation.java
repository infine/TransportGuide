package com.map.app;

public class BusStation {
	private String id;
	private String name;
	private String coord;

	public BusStation(String BusStaID, String BusStaName, String BusStaCoord) {
		this.setId(BusStaID);
		this.setName(BusStaName);
		this.setCoord(BusStaCoord);
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

	public String getCoord() {
		return coord;
	}

	public void setCoord(String coord) {
		this.coord = coord;
	}

	@Override
	public String toString() {
		return name;
	}

}

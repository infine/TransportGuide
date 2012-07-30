package com.map.objects;

import java.util.ArrayList;
import java.util.List;


public class BusLines {
	String name;
	String id;
	List<BusStation> busStationListForLine = new ArrayList<BusStation>();

	public BusLines(String id1, String name1) {
		this.id = id1;
		this.name = name1;
	}
	
	public void addBusStation(BusStation busStation)
	{
		busStationListForLine.add(busStation);
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getID()
	{
		return id;
	}
	
	public List<BusStation> getStations()
	{
		return busStationListForLine;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}

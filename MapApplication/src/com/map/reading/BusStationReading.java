package com.map.reading;

import java.util.HashMap;
import java.util.List;

import org.mapsforge.core.GeoPoint;

import com.map.app.OsmParsing;
import com.map.osm.BusLine;
import com.map.osm.BusStation;
import com.map.osm.Member;

public class BusStationReading {


	private static String id;
	private static List<BusStation> busStationList;
	private static List<BusLine> busLinesList;
	private static HashMap<String, BusStation> busStationMap = new HashMap<String, BusStation>();

	public static void update()
    {
    	busStationList = OsmParsing.getData().getNodes();
    	busLinesList = OsmParsing.getData().getRelations();
    	for(BusLine line : busLinesList)
    		for(Member member : line.getMembers())
    			for(BusStation station : busStationList)
    			{
    				if(member.getReference().equalsIgnoreCase(station.getId())){
    					station.addLine(line);
    					line.addStation(station);
    				}
    			}
    	for(BusStation station : busStationList)
    		busStationMap.put(station.getId(), station);
    }
	public static GeoPoint getNearestStation(GeoPoint a) {
		double min = distance(busStationList.get(0).getLocation(), a);
		GeoPoint nearestCoord = busStationList.get(0).getLocation();
		for (BusStation station : busStationList) {
			double dist = distance(station.getLocation(), a);
			if (dist < min) {
				min = dist;
				nearestCoord = station.getLocation();
				id = station.getId();
			}

		}

		return nearestCoord;
	}

	public static double distance(GeoPoint a, GeoPoint b) {
		double DEGREE_LENGTH = 111.2;
		double latDist = (a.getLatitude() - b.getLatitude()) * DEGREE_LENGTH;
		double lngDist = (a.getLongitude() - b.getLongitude()) * DEGREE_LENGTH;
		double dist = Math.sqrt(latDist * latDist + lngDist * lngDist);
		return dist;
	}

	public static BusStation getNearestID() {
		return busStationMap.get(id);
	}

	public static HashMap<String, BusStation> getHashedValues() {
		return busStationMap;
	}

	public static List<BusStation> getValues() {
		return busStationList;
	}
}
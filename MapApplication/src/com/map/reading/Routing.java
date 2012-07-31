package com.map.reading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mapsforge.core.GeoPoint;

import com.map.app.MapApplicationActivity;
import com.map.objects.BusLines;
import com.map.objects.BusStation;

public class Routing {

	private static List<HashMap<String, String>> busRoutingList = new ArrayList<HashMap<String, String>>();
	private static HashMap<String, String> busRouteMap = new HashMap<String, String>();
	private static String KEY_Station = "busStation";
	private static String KEY_Line = "busLine";
	private static String KEY_Message = "message";
	private static final String TAKE = "Take from the bus station the bus line";
	private static final String LEAVE = "Leave at the bus station the bus line";
	private static final String FOOT = "You walk by foot.";
	private static final String DIST = "Distance is to short to take a bus.";

	public static void route() {
		// Adding menuItems to ListView
		busRoutingList.clear();
		GeoPoint a = MapApplicationActivity.getCurentLocation();
		GeoPoint b = MapApplicationActivity.getDestination();
		GeoPoint a1 = BusStationReading.getNearestStation(a);
		BusStation stationA = BusStationReading.getNearestID();
		System.out.println(a1.getLatitude() + a1.getLongitude());
		GeoPoint b2 = BusStationReading.getNearestStation(b);
		BusStation stationB = BusStationReading.getNearestID();
		System.out.println(b2.getLatitude() + b2.getLongitude());

		if (BusStationReading.distance(stationA.busStationLocation(),
				stationB.busStationLocation()) > 0.2) {
			
			method1(stationA,stationB);
			
			if (busRoutingList.size() == 0) {
				
				method2(stationA,stationB);
				
			}
			
			if (busRoutingList.size() == 0) {
				
				method3(stationA,stationB);
				
			}
		} else {

			add(3, null, null);

		}
	}

	private static void add(int mess, String busStat, String busLine) {
		if (mess == 0) {
			busRouteMap = new HashMap<String, String>();
			busRouteMap.put(KEY_Message, TAKE);
			busRouteMap.put(KEY_Station, busStat);
			busRouteMap.put(KEY_Line, busLine);
			busRoutingList.add(busRouteMap);
		} else if (mess == 1) {
			busRouteMap = new HashMap<String, String>();
			busRouteMap.put(KEY_Message, LEAVE);
			busRouteMap.put(KEY_Station, busStat);
			busRouteMap.put(KEY_Line, busLine);
			busRoutingList.add(busRouteMap);
		} else if (mess == 2) {
			busRouteMap = new HashMap<String, String>();
			busRouteMap
					.put(KEY_Station,
							"------------------------------------------------------------");
			busRoutingList.add(busRouteMap);
		} else {
			busRouteMap = new HashMap<String, String>();
			busRouteMap.put(KEY_Station, FOOT);
			busRouteMap.put(KEY_Line, DIST);
			busRoutingList.add(busRouteMap);
		}

	}
	
	private static void method1(BusStation stationA, BusStation stationB){
		for (BusLines line1 : stationA.getLines())
			for (BusLines line2 : stationB.getLines()) {
				if (line1.equals(line2)) {

					add(0, stationA.getName(), line1.getName());

					add(1, stationB.getName(), line2.getName());

					add(2, null, null);

					break;
				}
			}
	}
	
	private static void method2(BusStation stationA, BusStation stationB)
	{
		for (BusLines line1 : stationA.getLines())
			for (BusLines line2 : stationB.getLines()) {
				for (BusStation station1 : line1.getStations())
					for (BusStation station2 : line2.getStations()) {
						if (station2.equals(station1)) {
							add(0, stationA.getName(), line1.getName());

							add(1, station1.getName(), line1.getName());

							add(0, station1.getName(), line2.getName());

							add(1, stationB.getName(), line2.getName());

							add(2, null, null);

							break;
						}
					}
			}
	}
	
	private static void method3(BusStation stationA, BusStation stationB)
	{
		double min = 10.0;
		BusStation stationDestA = null;
		BusStation stationDestB = null;
		BusLines lineA = null, lineB = null;
		for (BusLines line1 : stationA.getLines())
			for (BusLines line2 : stationB.getLines()) {
				for (BusStation station1 : line1.getStations())
					for (BusStation station2 : line2.getStations()) {
						double dist = BusStationReading.distance(
								station1.busStationLocation(),
								station2.busStationLocation());
						if (dist < min) {
							min = dist;
							stationDestA = station1;
							stationDestB = station2;
							lineA = line1;
							lineB = line2;
						}
					}
			}
		if (lineA != null) {
			if (lineB != null) {

				add(0, stationA.getName(), lineA.getName());

				add(1, stationDestA.getName(), lineA.getName());
				
				add(0, stationDestB.getName(), lineB.getName());

				add(1, stationB.getName(), lineB.getName());
				
				add(2,null,null);
			}
		}
	}

	public static List<HashMap<String, String>> getValues() {
		System.out.println(busRoutingList.size());
		return busRoutingList;
	}
}

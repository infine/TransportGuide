package com.map.reading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mapsforge.core.GeoPoint;

import com.map.app.MapApplicationActivity;
import com.map.osm.BusLine;
import com.map.osm.BusStation;

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

		if (BusStationReading.distance(stationA.getLocation(),
				stationB.getLocation()) > 0.2) {

			busRouteMap.put(KEY_Station, "Take a walk to the station");
			method1(stationA, stationB);
			busRouteMap.put(KEY_Station, "Take a walk to the destination");

			if (busRoutingList.size() == 0) {
				busRouteMap.put(KEY_Station, "Take a walk to the station");
				method2(stationA, stationB);
				busRouteMap.put(KEY_Station, "Take a walk to the destination");
			}

			if (busRoutingList.size() == 0) {
				busRouteMap.put(KEY_Station, "Take a walk to the station");
				method3(stationA, stationB);
				busRouteMap.put(KEY_Station, "Take a walk to the destination");
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

	private static void method1(BusStation stationA, BusStation stationB) {
		for (BusLine line1 : stationA.getLines())
			for (BusLine line2 : stationB.getLines()) {
				if (line1.equals(line2)) {

					add(0, stationA.toString(), line1.toString());

					add(1, stationB.toString(), line2.toString());

					add(2, null, null);

					break;
				}
			}
	}

	private static void method2(BusStation stationA, BusStation stationB) {
		for (BusLine line1 : stationA.getLines())
			for (BusLine line2 : stationB.getLines()) {
				for (BusStation station1 : line1.getStations())
					for (BusStation station2 : line2.getStations()) {
						if (station2.equals(station1)) {
							add(0, stationA.toString(), line1.toString());

							add(1, station1.toString(), line1.toString());

							add(0, station1.toString(), line2.toString());

							add(1, stationB.toString(), line2.toString());

							add(2, null, null);

							break;
						}
					}
			}
	}

	private static void method3(BusStation stationA, BusStation stationB) {
		double min = 10.0;
		BusStation stationDestA = null;
		BusStation stationDestB = null;
		BusLine lineA = null, lineB = null;
		for (BusLine line1 : stationA.getLines())
			for (BusLine line2 : stationB.getLines()) {
				for (BusStation station1 : line1.getStations())
					for (BusStation station2 : line2.getStations()) {
						double dist = BusStationReading.distance(
								station1.getLocation(), station2.getLocation());
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

				add(0, stationA.toString(), lineA.toString());

				add(1, stationDestA.toString(), lineA.toString());

				add(0, stationDestB.toString(), lineB.toString());

				add(1, stationB.toString(), lineB.toString());

				add(2, null, null);
			}
		}
	}

	public static List<HashMap<String, String>> getValues() {
		System.out.println(busRoutingList.size());
		return busRoutingList;
	}
}

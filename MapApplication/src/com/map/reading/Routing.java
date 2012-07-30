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
	private HashMap<String, String> busRouteMap = new HashMap<String, String>();
	private String KEY_Station = "busStation", KEY_Line = "busLine",
			KEY_Message = "message";

	public void Route() {
		// Adding menuItems to ListView
		busRoutingList.clear();
		GeoPoint a = MapApplicationActivity.getCurentLocation();
		GeoPoint b = MapApplicationActivity.getDestination();
		BusStationReading.getNearestStation(a);
		BusStation stationA = BusStationReading.getNearestID();
		BusStationReading.getNearestStation(b);
		BusStation stationB = BusStationReading.getNearestID();

		if (BusStationReading.distance(stationA.busStationLocation(),
				stationB.busStationLocation()) > 0.2) {
			for (BusLines line1 : stationA.getLines())
				for (BusLines line2 : stationB.getLines()) {
					if (line1.equals(line2)) {
						busRouteMap = new HashMap<String, String>();
						busRouteMap.put(KEY_Message,
								"Take from the bus station the bus line");
						busRouteMap.put(KEY_Station, stationA.getName());
						busRouteMap.put(KEY_Line, line1.getName());
						busRoutingList.add(busRouteMap);
						busRouteMap = new HashMap<String, String>();
						busRouteMap.put(KEY_Message,
								"Leave at the bus station the bus line");
						busRouteMap.put(KEY_Station, stationB.getName());
						busRouteMap.put(KEY_Line, line2.getName());
						busRoutingList.add(busRouteMap);
						busRouteMap = new HashMap<String, String>();
						busRouteMap
								.put(KEY_Station,
										"------------------------------------------------------------");
						busRoutingList.add(busRouteMap);
						break;
					}
				}
			if (busRoutingList.size() == 0) {
				for (BusLines line1 : stationA.getLines())
					for (BusLines line2 : stationB.getLines()) {
						for (BusStation station1 : line1.getStations())
							for (BusStation station2 : line2.getStations()) {
								if (station2.equals(station1)) {
									busRouteMap = new HashMap<String, String>();
									busRouteMap
											.put(KEY_Message,
													"Take from the bus station the bus line");
									busRouteMap.put(KEY_Station,
											stationA.getName());
									busRouteMap.put(KEY_Line, line1.getName());
									busRoutingList.add(busRouteMap);
									busRouteMap = new HashMap<String, String>();
									busRouteMap
											.put(KEY_Message,
													"Leave at the bus station the bus line");
									busRouteMap.put(KEY_Station,
											station1.getName());
									busRouteMap.put(KEY_Line, line1.getName());
									busRoutingList.add(busRouteMap);
									busRouteMap = new HashMap<String, String>();
									busRouteMap
											.put(KEY_Message,
													"Take from the bus station the bus line");
									busRouteMap.put(KEY_Station,
											station1.getName());
									busRouteMap.put(KEY_Line, line2.getName());
									busRoutingList.add(busRouteMap);
									busRouteMap = new HashMap<String, String>();
									busRouteMap
											.put(KEY_Message,
													"Leave at the bus station the bus line");
									busRouteMap.put(KEY_Station,
											stationB.getName());
									busRouteMap.put(KEY_Line, line2.getName());
									busRoutingList.add(busRouteMap);
									busRouteMap = new HashMap<String, String>();
									busRouteMap
											.put(KEY_Station,
													"------------------------------------------------------------");
									busRoutingList.add(busRouteMap);
									break;
								}
							}
					}
			}
			double min = 10.0;
			BusStation stationDestA = null;
			BusStation stationDestB = null;
			if (busRoutingList.size() == 0) {
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
						busRouteMap = new HashMap<String, String>();
						busRouteMap.put(KEY_Message,
								"Take from the bus station the bus line");
						busRouteMap.put(KEY_Station, stationA.getName());
						busRouteMap.put(KEY_Line, lineA.getName());
						busRoutingList.add(busRouteMap);
						busRouteMap = new HashMap<String, String>();
						busRouteMap.put(KEY_Message,
								"Leave at the bus station the bus line");
						busRouteMap.put(KEY_Station, stationDestA.getName());
						busRouteMap.put(KEY_Line, lineA.getName());
						busRoutingList.add(busRouteMap);
						busRouteMap = new HashMap<String, String>();
						busRouteMap.put(KEY_Message,
								"Take from the bus station the bus line");
						busRouteMap.put(KEY_Station, stationDestB.getName());
						busRouteMap.put(KEY_Line, lineB.getName());
						busRoutingList.add(busRouteMap);
						busRouteMap = new HashMap<String, String>();
						busRouteMap.put(KEY_Message,
								"Leave at the bus station the bus line");
						busRouteMap.put(KEY_Station, stationB.getName());
						busRouteMap.put(KEY_Line, lineB.getName());
						busRoutingList.add(busRouteMap);
						busRouteMap = new HashMap<String, String>();
						busRouteMap
								.put(KEY_Station,
										"------------------------------------------------------------");
						busRoutingList.add(busRouteMap);
					}
				}
			}
		} else {
			busRouteMap = new HashMap<String, String>();
			busRouteMap.put(KEY_Station, "You walk by foot.");
			busRouteMap.put(KEY_Line, "Distance is to short to take a bus.");
			busRoutingList.add(busRouteMap);

		}
	}
	
	public static List<HashMap<String, String>> getValues()
	{
		return busRoutingList;
	}
}

package com.map.reading;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mapsforge.core.GeoPoint;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.map.objects.BusStation;
import com.map.xmlparse.XMLParser;

public class BusStationReading {

	// All static variables
	private static final String URL = "FILE:///sdcard/Others/map1.xml";
	// XML node keys
	private static final String KEY_ITEM = "node"; // parent node
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_COOR = "coordinates";
	private static final String KEY_HIGH = "highway";
	private static final String KEY_AMEN = "amenity";
	private static final String KEY_RAIL = "railway";
	private final static String BUS = "bus_stop";
	private final static String STATION = "bus_station";
	private final static String TRAM = "tram_stop";
	private static int i;
	private static NodeList nl;
	private static String id;
	private static List<BusStation> busStationList;
	private static HashMap<String, BusStation> busStationMap = new HashMap<String, BusStation>();

	public static void parseBusStations() {
		XMLParser parser = new XMLParser();
		String xml = null;
		try {
			xml = XMLParser.getXmlFromUrl(URL);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // getting XML
		Document doc = parser.getDomElement(xml); // getting DOM element

		nl = doc.getElementsByTagName(KEY_ITEM);
		// looping through all item nodes <item>
		busStationList = new ArrayList<BusStation>();
		for (i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			Element e = (Element) nl.item(i);
			if (parser.getValue(e, KEY_HIGH).equals(BUS)
					|| parser.getValue(e, KEY_AMEN).equals(STATION)
					|| parser.getValue(e, KEY_RAIL).equals(TRAM)) {
				// adding each child node to HashMap key => value
				{
					busStationList.add(new BusStation(parser
							.getValue(e, KEY_ID), parser.getValue(e, KEY_NAME),
							parser.getValue(e, KEY_COOR)));
				}
			}
		}
		for (BusStation station : busStationList) {
			busStationMap.put(station.getId(), station);
		}

	}

	public static GeoPoint getNearestStation(GeoPoint a) {
		double min = distance(busStationList.get(0).busStationLocation(), a);
		GeoPoint nearestCoord = busStationList.get(0).busStationLocation();
		for (BusStation station : busStationList) {
			double dist = distance(station.busStationLocation(), a);
			if (dist < min) {
				min = dist;
				nearestCoord = station.busStationLocation();
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
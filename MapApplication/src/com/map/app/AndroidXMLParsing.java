package com.map.app;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AndroidXMLParsing {

	// All static variables
	static final String URL = "FILE:///sdcard/Others/map1.xml";
	// XML node keys
	static final String KEY_ITEM = "node"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_COOR = "coordinates";
	static final String KEY_HIGH = "highway";
	static final String KEY_AMEN = "amenity";
	static final String KEY_DIST = "dist";
	static int i;
	static NodeList nl;
	static String id;
	static List<BusStation> busStationList = new ArrayList<BusStation>();


	public static void parseBusStations() {
		busStationList.clear();
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
		for (i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			Element e = (Element) nl.item(i);
			if (parser.getValue(e, KEY_HIGH).equals("bus_stop")
					|| parser.getValue(e, KEY_AMEN).equals("bus_station")
					|| parser.getValue(e, KEY_AMEN).equals("tram_stop")) {
				// adding each child node to HashMap key => value
				{busStationList.add( new BusStation(parser.getValue(e, KEY_ID),
						parser.getValue(e, KEY_NAME), parser.getValue(e,
								KEY_COOR)));
				}
			}
		}
	}

	public static String getNearestCoord(double latitudeSpecified,
			double longitudeSpecified) {
		double min = 30;
		String nearestCoord = null;
		for (BusStation station : busStationList) {
			String[] s = station.getCoord().split(",");
			double latitude = Double.parseDouble(s[1]);
			double longitude = Double.parseDouble(s[0]);
			double latDist = (latitudeSpecified - latitude) * 111.2;
			double lngDist = (longitudeSpecified - longitude) * 111.2;
			double dist = Math.sqrt(latDist * latDist + lngDist * lngDist);
			if (dist < min) {
				min = dist;
				nearestCoord = station.getCoord();
				id = station.getId();
			}

		}

		return nearestCoord;
	}

	public static String getNearestID() {
		return id;
	}
	
	public static List<BusStation> getValues(){
		return busStationList;
	}
}
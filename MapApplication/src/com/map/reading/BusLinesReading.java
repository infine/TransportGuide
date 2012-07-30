package com.map.reading;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.map.objects.BusLines;
import com.map.objects.BusStation;
import com.map.xmlparse.XMLParser;

public class BusLinesReading {

	// All static variables
	static final String URL = "FILE:///sdcard/Others/busl.xml";
	// XML node keys
	static final String KEY_ITEM = "relation"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_STATION = "ref";
	static final String KEY_NAME = "name";
	static NodeList nl;
	static List<BusLines> busLinesList = new ArrayList<BusLines>();
	static HashMap<String, BusStation> busStationMap = BusStationReading.getHashedValues();

	public static void parseBusLines() {
		busLinesList.removeAll(busLinesList);
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
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			Element e = (Element) nl.item(i);
			BusLines line = new BusLines(parser.getValue(e, KEY_ID),
					parser.getValue(e, KEY_NAME));

			// adding each child node to HashMap key => value
			List<String> st = parser.getValues(e, KEY_STATION);
			int j = 0;
			int m = st.size();
		    while(j<m){
		    	if(busStationMap.get(st.get(j))!=null)
		    	line.addBusStation(busStationMap.get(st.get(j)));
		    	j++;
		    }
			busLinesList.add(line);
		}

		for (BusLines line : busLinesList) {
			for (BusStation station : line.getStations()) {
				station.addLines(line);
			}
		}

	}

	public static List<BusLines> getValues() {
		return busLinesList;
	}
}
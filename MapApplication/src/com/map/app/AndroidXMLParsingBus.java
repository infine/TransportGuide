package com.map.app;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AndroidXMLParsingBus {

	// All static variables
	static final String URL = "FILE:///sdcard/Others/busl.xml";
	// XML node keys
	static final String KEY_ITEM = "relation"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_STATION = "ref";
	static final String KEY_NAME = "name";
	static NodeList nl;
	static ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

	public static void read(String id) {
		menuItems.removeAll(menuItems);
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
			// adding each child node to HashMap key => value
			while (parser.getValue(e, KEY_STATION) != null) {
				if (id.equals(parser.getValue(e, KEY_STATION))) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
					map.put(KEY_ID, parser.getValue(e, KEY_ID));
					menuItems.add(map);
				}
			}
		}
	}

	public static ArrayList<HashMap<String, String>> getValues() {
		return menuItems;
	}
}
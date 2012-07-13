package com.map.app;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.math.*;

public class AndroidXMLParsingActivity extends ListActivity {

	// All static variables
	static final String URL = "FILE:///sdcard/Others/bus.xml";
	// XML node keys
	static final String KEY_ITEM = "item"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_COST = "lat";
	static final String KEY_DESC = "lon";
	static final String KEY_DIST = "dist";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);

		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
		String xml = null;
		try {
			xml = XMLParser.getXmlFromUrl(URL);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // getting XML
		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(KEY_ITEM);
		// looping through all item nodes <item>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(KEY_ID, parser.getValue(e, KEY_ID));
			map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
			map.put(KEY_COST, parser.getValue(e, KEY_COST));
			map.put(KEY_DESC, parser.getValue(e, KEY_DESC));
			double dist = (Math.sqrt((Math.abs(MapApplicationActivity.getLatitude()
					- Double.parseDouble(parser.getValue(e, KEY_COST)))
					* 111.2
					* Math.abs(MapApplicationActivity.getLatitude()
							- Double.parseDouble(parser.getValue(e, KEY_COST)))
					* 111.2 + Math.abs(MapApplicationActivity.getLongitude()
					- Double.parseDouble(parser.getValue(e, KEY_DESC)))
					* 111.2
					* Math.abs(MapApplicationActivity.getLongitude()
							- Double.parseDouble(parser.getValue(e, KEY_DESC)))
					* 111.2)));
			String distance = Double.toString(dist);
			map.put(KEY_DIST, distance + " km");
			// adding HashList to ArrayList
			menuItems.add(map);
		}

		// Adding menuItems to ListView
		ListAdapter adapter = new SimpleAdapter(this, menuItems,
				R.layout.list_item,
				new String[] { KEY_NAME,  KEY_DIST }, new int[] {
						R.id.name,  R.id.cost });

		setListAdapter(adapter);

	}
}
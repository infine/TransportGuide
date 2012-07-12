package com.map.app;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AndroidXMLParsingActivity extends ListActivity {

	// All static variables
	static final String xml = "/sdcard/Others/bus.xml";
	// XML node keys
	static final String KEY_ITEM = "item"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_Latitude = "lat";
	static final String KEY_Longitude = "lon";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);

		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
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
			map.put(KEY_Latitude, parser.getValue(e, KEY_Latitude));
			map.put(KEY_Longitude, parser.getValue(e, KEY_Longitude));

			// adding HashList to ArrayList
			menuItems.add(map);
		}

		// Adding menuItems to ListView
		ListAdapter adapter = new SimpleAdapter(this, menuItems,
				R.layout.list_item,
				new String[] { KEY_NAME, KEY_Longitude, KEY_Latitude }, new int[] {
						R.id.name, R.id.desciption, R.id.cost });

		setListAdapter(adapter);
		
		// selecting single ListView item
		ListView lv = getListView();

	}
}
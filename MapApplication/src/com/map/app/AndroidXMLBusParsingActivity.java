package com.map.app;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class AndroidXMLBusParsingActivity extends ListActivity {
	// XML node keys
	static final String KEY_ITEM = "relation"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_STATION = "member";
	static final String KEY_TAG = "tag";
	static final String KEY_K = "k";
	static final String KEY_V = "v";
	static final String KEY_NAME = "name";
	ArrayList<HashMap<String, String>> menuItems;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);

		AndroidXMLParsingBus.read(AndroidXMLParsing.getNearestID());
		menuItems = AndroidXMLParsingBus.getValues();

		// Adding menuItems to ListView
		ListAdapter adapter = new SimpleAdapter(this, menuItems,
				R.layout.list_item, new String[] { KEY_NAME, KEY_ID },
				new int[] { R.id.name, R.id.cost });

		setListAdapter(adapter);
	}
}

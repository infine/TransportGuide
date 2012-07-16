package com.map.app;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class AndroidXMLParsingActivity extends ListActivity{
	// All static variables
		static final String URL = "http://api.androidhive.info/pizza/?format=xml";
		// XML node keys
		static final String KEY_ITEM = "item"; // parent node
		static final String KEY_ID = "id";
		static final String KEY_NAME = "name";
		static final String KEY_DIST = "dist";
		ArrayList<HashMap<String, String>> menuItems;
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main1);

			AndroidXMLParsing.read(MapApplicationActivity.getLatitude(),MapApplicationActivity.getLongitude());
			menuItems = AndroidXMLParsing.getValues() ;
           						

			// Adding menuItems to ListView
			ListAdapter adapter = new SimpleAdapter(this, menuItems,
					R.layout.list_item,
					new String[] { KEY_NAME,  KEY_DIST }, new int[] {
							R.id.name,  R.id.cost });

			setListAdapter(adapter);


				}
			
		}
	


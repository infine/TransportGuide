package com.map.app;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.map.osm.FeatureCollection;
import com.map.osm.BusStation;


public class BusStationDisplayActivity extends ListActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
        FeatureCollection data = OsmParsing.getData();   
		// Adding menuItems to ListView
		ArrayAdapter<BusStation> arrayAdapter = new ArrayAdapter<BusStation>(
				this, android.R.layout.simple_list_item_1,
				data.getNodes());
		setListAdapter(arrayAdapter);
	}
}

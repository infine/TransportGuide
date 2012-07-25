package com.map.app;

import java.util.List;

import org.mapsforge.core.GeoPoint;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class AndroidXMLBusParsingActivity extends ListActivity {
	List<BusLines> menuItems;
	BusStation nearestBusStation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);

		GeoPoint a = new GeoPoint(MapApplicationActivity.getLatitude(),MapApplicationActivity.getLongitude());
		AndroidXMLParsing.getNearestCoord(a);
		nearestBusStation = AndroidXMLParsing.getNearestID();
		menuItems = nearestBusStation.getLines();

		// Adding menuItems to ListView
		ArrayAdapter<BusLines> arrayAdapter = new ArrayAdapter<BusLines>(this,
				android.R.layout.simple_list_item_1,
				menuItems);
		setListAdapter(arrayAdapter);
	}
}

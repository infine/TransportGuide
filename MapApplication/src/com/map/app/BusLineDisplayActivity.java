package com.map.app;

import java.util.List;

import org.mapsforge.core.GeoPoint;

import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.map.osm.BusStation;
import com.map.osm.BusLine;
import com.map.reading.BusStationReading;

public class BusLineDisplayActivity extends ListActivity {
	List<BusLine> data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Bundle extras = getIntent().getExtras();
		double lat = extras.getDouble("latitude");
		double lon = extras.getDouble("longitude");
		GeoPoint geoPoint = new GeoPoint(lat, lon);
		BusStationReading.getNearestStation(geoPoint);
		BusStation busStation = BusStationReading.getNearestID();
		// List<BusStation> list = OsmParsing.getData().getNodes();
		data = busStation.getLines();

		// Adding menuItems to ListView
		ArrayAdapter<BusLine> arrayAdapter = new ArrayAdapter<BusLine>(this,
				android.R.layout.simple_list_item_1, data);
		setListAdapter(arrayAdapter);
	}
}

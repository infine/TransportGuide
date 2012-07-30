package com.map.app;

import java.util.List;

import org.mapsforge.core.GeoPoint;

import com.map.objects.BusLines;
import com.map.objects.BusStation;
import com.map.reading.BusStationReading;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class BusLinesDisplayActivity extends ListActivity {
	private List<BusLines> menuItems;
	private BusStation nearestBusStation;
	private ArrayAdapter<BusLines> arrayAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		GeoPoint b;

		if (MapApplicationActivity.getPressedMenu() == 0)
			b = MapApplicationActivity.getCurentLocation();
		else {
			b = MapApplicationActivity.getDestination();
		}

		BusStationReading.getNearestStation(b);
		nearestBusStation = BusStationReading.getNearestID();
		menuItems = nearestBusStation.getLines();

		// Adding menuItems to ListView
		arrayAdapter = new ArrayAdapter<BusLines>(this,
				android.R.layout.simple_list_item_1, menuItems);
		setListAdapter(arrayAdapter);
	}
}

package com.map.app;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import com.map.reading.DownloadTask;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;

public class YourMap extends MapActivity {
	MapView map;
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.context = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// set the zoom level, center point and enable the default zoom controls
		map = (MapView) findViewById(R.id.map);
		map.getController().setZoom(11);
		map.getController().setCenter(
				new GeoPoint(MapApplicationActivity.getCurentLocation()
						.getLatitude(), MapApplicationActivity
						.getCurentLocation().getLongitude()));
		map.setBuiltInZoomControls(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.done:
			Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
					.getDefaultDisplay();
			int width = display.getWidth();
			int height = display.getHeight();
			GeoPoint geopoint1 = map.getProjection().fromPixels(0, 0);
			GeoPoint geopoint2 = map.getProjection().fromPixels(width, height);

			new DownloadTask(context, geopoint1, geopoint2).execute();

			return true;
		case R.id.cancel:
			finish();
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	// return false since no route is being displayed
	@Override
	public boolean isRouteDisplayed() {
		return false;
	}
}
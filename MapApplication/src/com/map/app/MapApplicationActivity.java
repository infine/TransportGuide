package com.map.app;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;
import java.io.File;
import org.mapsforge.android.maps.overlay.ArrayItemizedOverlay;
import org.mapsforge.core.GeoPoint;
import org.mapsforge.android.maps.MapActivity;
import org.mapsforge.android.maps.MapController;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.overlay.OverlayItem;

import com.map.reading.BusLinesReading;
import com.map.reading.BusStationReading;
import com.map.reading.Routing;

import android.content.Context;
import android.content.Intent;

public class MapApplicationActivity extends MapActivity implements
		LocationListener, OnTouchListener {

	private static double lat, lng;
	private String provider;
	private LocationManager locationManager;
	private MapView mapView;
	private OverlayItem locationItem, destinationLocationItem,
			nearestStationLocationItem, nearestStationFromDestItem;
	private ArrayItemizedOverlay displayCurentLocation,
			displayNearestStationLocation,
			displayNearestStationFromDestination, displayDestinationLocation;
	private static GeoPoint projectionOfDestination, curentLocation,
			destinationLocation, nearestStationLocation,
			nearestStationFromDestination;
	private static int pressedMenu;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mapView = new MapView(this);
		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);
		mapView.setMapFile(new File("/sdcard/Others/cluj.map"));
		mapView.setOnTouchListener(this);

		setContentView(mapView);
		BusStationReading.parseBusStations();
		BusLinesReading.parseBusLines();

		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
		// Initialize the location fields
		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			lat = location.getLatitude();
			lng = location.getLongitude();
		}
		// create some points to be shown on top of the map
		curentLocation = new GeoPoint(lat, lng);
		locationItem = new OverlayItem(curentLocation, "Point", "here you are");
		// create the ItemizedOverlay and set the items
		displayCurentLocation = new ArrayItemizedOverlay(getResources()
				.getDrawable(R.drawable.btn_star));
		displayCurentLocation.addItem(locationItem);
		// itemizedOverlay.addItem(item2);

		// add both Overlays to the MapView
		mapView.getOverlays().add(displayCurentLocation);
		mapView.setCenter(curentLocation);
		final MapController mc = mapView.getController();
		mc.setZoom(16);

	}

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.getOverlays().remove(displayNearestStationLocation);
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		lat = location.getLatitude();
		lng = location.getLongitude();
		mapView.getOverlays().remove(displayCurentLocation);
		mapView.getOverlays().remove(displayNearestStationLocation);
		mapView.getOverlays().remove(displayNearestStationFromDestination);
		mapView.getOverlays().remove(displayDestinationLocation);
		GeoPoint geoPoint1 = new GeoPoint(lat, lng);
		OverlayItem item1 = new OverlayItem(geoPoint1, "Point", "here you are");
		// create the ItemizedOverlay and set the items
		displayCurentLocation = new ArrayItemizedOverlay(getResources()
				.getDrawable(R.drawable.btn_star));
		displayCurentLocation.addItem(item1);
		// add both Overlays to the MapView
		mapView.getOverlays().add(displayCurentLocation);

	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider,
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disabled provider " + provider,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.refresh:
			mapView.getOverlays().remove(displayNearestStationLocation);
			locationManager.requestLocationUpdates(provider, 400, 1, this);
			return true;
		case R.id.help:
			Toast.makeText(this, "Aici este meniul Help", Toast.LENGTH_SHORT)
					.show();
			return true;
		case R.id.bus:
			Intent in = new Intent(getApplicationContext(),
					BusStationDisplayActivity.class);
			startActivity(in);
			return true;
		case R.id.near:
			mapView.getOverlays().remove(displayNearestStationLocation);
			nearestStationLocation = BusStationReading
					.getNearestStation(curentLocation);
			nearestStationLocationItem = new OverlayItem(
					nearestStationLocation, "Point", "nearest Station");

			// create the ItemizedOverlay and set the items
			displayNearestStationLocation = new ArrayItemizedOverlay(
					getResources().getDrawable(R.drawable.ic_buss));
			displayNearestStationLocation.addItem(nearestStationLocationItem);
			// add both Overlays to the MapView
			mapView.getOverlays().add(displayNearestStationLocation);
			return true;
		case R.id.neardest:
			mapView.getOverlays().remove(displayNearestStationFromDestination);
			nearestStationFromDestination = BusStationReading
					.getNearestStation(destinationLocation);
			nearestStationFromDestItem = new OverlayItem(
					nearestStationFromDestination, "Point", "nearest Station");
			// create the ItemizedOverlay and set the items
			displayNearestStationFromDestination = new ArrayItemizedOverlay(
					getResources().getDrawable(R.drawable.ic_buss_red));
			displayNearestStationFromDestination
					.addItem(nearestStationFromDestItem);
			// add both Overlays to the MapView
			mapView.getOverlays().add(displayNearestStationFromDestination);
			return true;
		case R.id.bus_l:
			pressedMenu = 0;
			Intent in1 = new Intent(getApplicationContext(),
					BusLinesDisplayActivity.class);
			in1.putExtra("location", new Double[] { lat, lng });
			startActivity(in1);
			return true;
		case R.id.bus_dest_l:
			pressedMenu = 1;
			Intent in2 = new Intent(getApplicationContext(),
					BusLinesDisplayActivity.class);
			in2.putExtra("location",
					new Double[] { projectionOfDestination.getLatitude(),
							projectionOfDestination.getLongitude() });
			startActivity(in2);
			return true;
		case R.id.route:
			if(Routing.getValues().size()!=0){
			Intent in3 = new Intent(getApplicationContext(),
					RouteActivity.class);
			startActivity(in3);
			} else
				Toast.makeText(getApplicationContext(), "To few information to display a route", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.exit:
			finish();
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent event) {

		// ---when user lifts his finger---
		if (event.getAction() == 1) {
			mapView.getOverlays().remove(displayDestinationLocation);
			projectionOfDestination = mapView.getProjection().fromPixels(
					(int) event.getX(), (int) event.getY());
			destinationLocation = new GeoPoint(
					projectionOfDestination.getLatitude(),
					projectionOfDestination.getLongitude());
			destinationLocationItem = new OverlayItem(destinationLocation,
					"Point", "here you are");
			// create the ItemizedOverlay and set the items
			displayDestinationLocation = new ArrayItemizedOverlay(
					getResources().getDrawable(R.drawable.btn_star_red));
			displayDestinationLocation.addItem(destinationLocationItem);
			// itemizedOverlay.addItem(item2);

			// add both Overlays to the MapView
			mapView.getOverlays().add(displayDestinationLocation);
		}

		return false;
	}

	public static GeoPoint getCurentLocation() {
		return curentLocation;
	}

	public static GeoPoint getDestination() {
		return destinationLocation;
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

	}

	public static int getPressedMenu() {
		return pressedMenu;
	}

}

package com.map.app;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import java.io.File;
import org.mapsforge.android.maps.overlay.ArrayItemizedOverlay;
import org.mapsforge.core.GeoPoint;
import org.mapsforge.android.maps.MapActivity;
import org.mapsforge.android.maps.MapController;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.overlay.OverlayItem;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;

public class MapApplicationActivity extends MapActivity implements
		LocationListener {

	static double lat;
	static double lng;
	private String provider;
	private LocationManager locationManager;
	private MapView mapView;
	private OverlayItem item1;
	private ArrayItemizedOverlay itemizedOverlay;
	private ArrayItemizedOverlay itemizedOverlay2;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mapView = new MapView(this);
		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);
		mapView.setMapFile(new File("/sdcard/Others/cluj.map"));
		setContentView(mapView);

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
		GeoPoint geoPoint1 = new GeoPoint(lat, lng);
		item1 = new OverlayItem(geoPoint1, "Point", "here you are");

		// create the paint object for the RouteOverlay and set all parameters
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.BLUE);
		paint.setAlpha(128);
		paint.setStrokeWidth(6);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeJoin(Paint.Join.ROUND);

		// create the ItemizedOverlay and set the items
		itemizedOverlay = new ArrayItemizedOverlay(getResources().getDrawable(
				R.drawable.btn_star));
		itemizedOverlay.addItem(item1);
		// itemizedOverlay.addItem(item2);

		// add both Overlays to the MapView
		mapView.getOverlays().add(itemizedOverlay);
		mapView.setCenter(geoPoint1);
		final MapController mc = mapView.getController();
		mc.setZoom(16);

	}

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.getOverlays().remove(itemizedOverlay2);
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
		mapView.getOverlays().remove(itemizedOverlay);
		GeoPoint geoPoint1 = new GeoPoint(lat, lng);
		OverlayItem item1 = new OverlayItem(geoPoint1, "Point", "here you are");
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.BLUE);
		paint.setAlpha(128);
		paint.setStrokeWidth(6);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeJoin(Paint.Join.ROUND);

		// create the ItemizedOverlay and set the items
		itemizedOverlay = new ArrayItemizedOverlay(getResources().getDrawable(
				R.drawable.btn_star));
		itemizedOverlay.addItem(item1);
		// add both Overlays to the MapView
		mapView.getOverlays().add(itemizedOverlay);

	}

	public static double getLatitude() {
		return lat;
	}

	public static double getLongitude() {
		return lng;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

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
			Location location = locationManager.getLastKnownLocation(provider);
			onLocationChanged(location);
			return true;
		case R.id.help:
			Toast.makeText(this, "Aici este meniul Help", Toast.LENGTH_SHORT)
					.show();
			return true;
		case R.id.bus:
			Intent in = new Intent(getApplicationContext(),
					AndroidXMLParsingActivity.class);
			startActivity(in);
			return true;
		case R.id.near:
			mapView.getOverlays().remove(itemizedOverlay2);
			AndroidXMLParsing.read();
			GeoPoint geoPoint2 = new GeoPoint(
					AndroidXMLParsing.getNearestLat(),
					AndroidXMLParsing.getNearestLon());
			OverlayItem item2 = new OverlayItem(geoPoint2, "Point",
					"nearest Station");
			Paint paint = new Paint();
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(Color.BLUE);
			paint.setAlpha(128);
			paint.setStrokeWidth(6);
			paint.setStrokeCap(Paint.Cap.ROUND);
			paint.setStrokeJoin(Paint.Join.ROUND);

			// create the ItemizedOverlay and set the items
			itemizedOverlay2 = new ArrayItemizedOverlay(getResources()
					.getDrawable(R.drawable.ic_buss));
			itemizedOverlay2.addItem(item2);
			// add both Overlays to the MapView
			mapView.getOverlays().add(itemizedOverlay2);
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

}
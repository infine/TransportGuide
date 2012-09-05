package com.map.reading;

import com.mapquest.android.maps.GeoPoint;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class DownloadTask extends AsyncTask<Void, Void, Void> {
	GeoPoint startPoint, endPoint;
	Context context;
	ProgressDialog dialog;

	public DownloadTask(Context context, GeoPoint point1, GeoPoint point2) {
		this.context = context;
		this.startPoint = point1;
		this.endPoint = point2;
	}

	@Override
	protected void onPreExecute() {
		this.dialog = new ProgressDialog(context);
		this.dialog.setMessage("Downloading Info. Please Wait...");
		this.dialog.setIndeterminate(true);
		this.dialog.setCancelable(false);
		this.dialog.show();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		OsmParsing.read(startPoint, endPoint);
		BusStationReading.update();
		return null;
	}

	@Override
	protected void onPostExecute(Void unused) {
		dialog.dismiss();

	}

}

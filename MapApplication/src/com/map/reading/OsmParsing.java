package com.map.reading;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.map.osm.FeatureCollection;
import com.mapquest.android.maps.GeoPoint;

import android.os.Environment;
import android.util.Log;

public class OsmParsing {
	private static FeatureCollection data;

	public static void read(GeoPoint geoPoint1, GeoPoint geoPoint2) {

		data = _parseXml(geoPoint1, geoPoint2);
	}

	public static void readDeserialized(ObjectInputStream in) {
		data = new FeatureCollection();
		data = ObjectSerializer.deserialize(in);
	}

	public static FeatureCollection getData() {
		return data;
	}

	private static FeatureCollection _parseXml(GeoPoint geoPoint1,
			GeoPoint geoPoint2) {
		FeatureCollection data = null;
		String s = "http://api.openstreetmap.org/api/0.6/map?bbox=";

		if (geoPoint1.getLongitude() < geoPoint2.getLongitude()) {
			s = s.concat(Double.toString(geoPoint1.getLongitude()));
			s = s.concat(",");
			if (geoPoint1.getLatitude() < geoPoint2.getLatitude()) {
				s = s.concat(Double.toString(geoPoint1.getLatitude()));
				s = s.concat(",");
				s = s.concat(Double.toString(geoPoint2.getLongitude()));
				s = s.concat(",");
				s = s.concat(Double.toString(geoPoint2.getLatitude()));
			} else {
				s = s.concat(Double.toString(geoPoint2.getLatitude()));
				s = s.concat(",");
				s = s.concat(Double.toString(geoPoint2.getLongitude()));
				s = s.concat(",");
				s = s.concat(Double.toString(geoPoint1.getLatitude()));
			}
		} else {
			s = s.concat(Double.toString(geoPoint2.getLongitude()));
			s = s.concat(",");
			if (geoPoint1.getLatitude() < geoPoint2.getLatitude()) {
				s = s.concat(Double.toString(geoPoint1.getLatitude()));
				s = s.concat(",");
				s = s.concat(Double.toString(geoPoint1.getLongitude()));
				s = s.concat(",");
				s = s.concat(Double.toString(geoPoint2.getLatitude()));
			} else {
				s = s.concat(Double.toString(geoPoint2.getLatitude()));
				s = s.concat(",");
				s = s.concat(Double.toString(geoPoint1.getLongitude()));
				s = s.concat(",");
				s = s.concat(Double.toString(geoPoint1.getLatitude()));
			}
		}
		// sax stuff
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			URL linkURL = new URL(s);
			String fileName = Environment.getExternalStorageDirectory()
					+ "/Others/map.osm";
			File file = new File(fileName);
			if (!file.exists()) {
				file.mkdirs();
			}
			DownloadFromUrl(linkURL, file);
			XMLReader xr = sp.getXMLReader();

			DataHandler dataHandler = new DataHandler();
			xr.setContentHandler(dataHandler);

			InputStream source = new FileInputStream(file);
			Reader reader = new InputStreamReader(source, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");

			xr.parse(is);

			data = dataHandler.getData();

			ObjectSerializer.serialize(data);

		} catch (ParserConfigurationException pce) {
			Log.e("SAX XML", "sax parse error", pce);
		} catch (SAXException se) {
			Log.e("SAX XML", "sax error", se);
		} catch (IOException ioe) {
			Log.e("SAX XML", "sax parse io error", ioe);
		}

		return data;
	}

	public static void DownloadFromUrl(URL eURL, File fileName) { // this is the
																	// downloader
																	// method
		BufferedReader in = null;
		FileOutputStream fos = null;
		try {
			// Create a URL for the desired page
			in = new BufferedReader(new InputStreamReader(eURL.openStream()));
			fos = new FileOutputStream(fileName);
			// Read all the text returned by the server

			String str;
			while ((str = in.readLine()) != null) {
				fos.write(str.getBytes());
				fos.write("\n".getBytes());
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.d("OsmParsing", e.getMessage());
		} catch (IOException e) {
			Log.d("OsmParsing", e.getMessage());
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

}

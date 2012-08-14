package com.map.reading;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.map.osm.FeatureCollection;

import android.util.Log;

public class OsmParsing {
	private static FeatureCollection data;

	public static void read() {

		data = _parseXml();
	}

	public static void readDeserialized() {
		data = new FeatureCollection();
		data = ObjectSerializer.deserialize();
	}

	public static FeatureCollection getData() {
		return data;
	}

	private static FeatureCollection _parseXml() {
		FeatureCollection data = null;
		
			// sax stuff
			try {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				URL linkURL = new URL(
						"http://api.openstreetmap.org/api/0.6/map?bbox=23.4996000,46.7362000,23.6967000,46.8004000");

				XMLReader xr = sp.getXMLReader();

				DataHandler dataHandler = new DataHandler();
				xr.setContentHandler(dataHandler);

				xr.parse(new InputSource(linkURL.openStream()));

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

}

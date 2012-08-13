package com.map.app;

import java.io.FileInputStream;
import java.io.IOException;
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
    
	public static FeatureCollection getData(){
		return data;
	}

	private static FeatureCollection _parseXml() {
		FeatureCollection data = null;

		// sax stuff
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();

			XMLReader xr = sp.getXMLReader();

			DataHandler dataHandler = new DataHandler();
			xr.setContentHandler(dataHandler);

			xr.parse(new InputSource(new FileInputStream(
					"//sdcard/Others/map.osm")));

			data = dataHandler.getData();

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

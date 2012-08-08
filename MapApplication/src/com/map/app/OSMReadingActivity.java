package com.map.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.map.app.R;
import com.map.osm.FeatureCollection;
import com.map.osm.Node;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class OSMReadingActivity extends Activity {

	private static final String url = "/sdcard/Others/map.osm";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);

		try {

			FeatureCollection xmlData = parseSyncStatusXML(url);
//
//			Reader reader = new StringReader(xmlData);
//			Node osd = serializer.read(Node.class, reader, false);

//			Log.d(OSMReadingActivity.class.getSimpleName(), osd.toString());

		} catch (Exception e) {
			Toast.makeText(this, "Error Occured", Toast.LENGTH_LONG).show();
		}

	}

	public FeatureCollection parseSyncStatusXML(String url)  {
	     Serializer serializer = new Persister();
	       FeatureCollection data = new FeatureCollection();
	     try{
	      String xml= getXmlFromPath(url);
	      data  = serializer.read(FeatureCollection.class, xml);
	     }
	     catch (Exception e) {
	    Log.e("", ".parseSyncStatus# "+e);
	  }
	     
	  return data;
	    }
	
	 public static String getXmlFromPath(String filePath) {
		  String xml = null;
		  try{
		   File xmlFile = new File(filePath);
		   FileInputStream in = new FileInputStream(xmlFile);
		   InputStreamReader isr = new InputStreamReader(in);
		   BufferedReader bufferedReader = new BufferedReader(isr, 64 * 1024);
		   xml = new String();
		   char[] buf = new char[4 * 1024];
		   int n = 0;
		   while (true) {
		       n = bufferedReader.read(buf, 0, buf.length);
		       if (n == -1)
		        break;
		       xml += new String(buf, 0, n);
		   }
		  } catch (Exception e) {
		   Log.e("ParserXML", "File not found!" + e.getMessage());
		  
		  }
		  return xml;
		    }
}
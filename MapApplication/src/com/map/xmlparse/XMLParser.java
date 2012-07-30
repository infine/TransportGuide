package com.map.xmlparse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

public class XMLParser {

	// constructor
	public XMLParser() {

	}

	/**
	 * Getting XML from URL making HTTP request
	 * 
	 * @param url
	 *            string
	 * */
	public static String getXmlFromUrl(String url) throws MalformedURLException {
		String xml = null;
		URL realURL = new URL(url);

		try {
			String filePath = realURL.getFile();
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
			/*
			 * works slower reading line by line String line; while ((line =
			 * bufferedReader.readLine()) != null) { xml += line + '\n'; }
			 */
		} catch (Exception e) {
			Log.e("ParserXML", "File not found!" + e.getMessage());
		}

		return xml;
	}

	/**
	 * Getting XML DOM element
	 * 
	 * @param XML
	 *            string
	 * */
	public Document getDomElement(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);

		} catch (ParserConfigurationException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (SAXException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		}

		return doc;
	}

	/**
	 * Getting node value
	 * 
	 * @param elem
	 *            element
	 */
	public final String getElementValue(Node elem) {
		Node child;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild(); child != null; child = child
						.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	/**
	 * Getting node value
	 * 
	 * @param Element
	 *            node
	 * @param key
	 *            string
	 * */
	public String getValue(Element item, String str) {
		NodeList n = item.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}
	

	public List<String> getValues(Element item, String str) {
		NodeList n = item.getElementsByTagName(str);
		List<String> st = new ArrayList<String>();
		for(int i=0;i<n.getLength();i++)
			st.add(this.getElementValue(n.item(i)));
		return st;
	}
}

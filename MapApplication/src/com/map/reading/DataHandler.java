package com.map.reading;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.map.osm.FeatureCollection;
import com.map.osm.Member;
import com.map.osm.BusStation;
import com.map.osm.BusLine;
import com.map.osm.Tag;

public class DataHandler extends DefaultHandler {

	// booleans that check whether it's in a specific tag or not
	private boolean _isNode, _isRelation;
	private BusStation temp;
	private BusLine tempRelation;
	private Tag tempTag;
	private Tag BUS = new Tag("highway", "bus_stop");
	private Tag TRAM = new Tag("railway", "tram_stop");
	private Tag BUSS = new Tag("amenity", "bus_station");
	private Tag BUSR = new Tag("route", "bus");

	// this holds the data
	private FeatureCollection _data;
	private Member tempMember;

	/**
	 * Returns the data object
	 * 
	 * @return
	 */
	public FeatureCollection getData() {
		return _data;
	}

	/**
	 * This gets called when the xml document is first opened
	 * 
	 * @throws SAXException
	 */
	@Override
	public void startDocument() throws SAXException {
		_data = new FeatureCollection();
	}

	/**
	 * Called when it's finished handling the document
	 * 
	 * @throws SAXException
	 */
	@Override
	public void endDocument() throws SAXException {

	}

	/**
	 * This gets called at the start of an element. Here we're also setting the
	 * booleans to true if it's at that specific tag. (so we know where we are)
	 * 
	 * @param namespaceURI
	 * @param localName
	 * @param qName
	 * @param atts
	 * @throws SAXException
	 */
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException {
		if (localName.equalsIgnoreCase("node")) {
			_isNode = true;

			Log.v("StartElement", localName);

			temp = new BusStation(atts.getValue("id"), atts.getValue("lat"),
					atts.getValue("lon"), atts.getValue("user"),
					atts.getValue("uid"), atts.getValue("visible"),
					atts.getValue("version"), atts.getValue("changeset"),
					atts.getValue("timestamp"));
		}
		if (localName.equalsIgnoreCase("tag")) {
			tempTag = new Tag(atts.getValue("k"), atts.getValue("v"));
			if (_isNode == true) {
				temp.addTag(tempTag);
				if (tempTag.equals(BUS) || tempTag.equals(TRAM)
						|| tempTag.equals(BUSS))
					_data.addNode(temp);
			}
			if(_isRelation == true){
				tempRelation.addTags(tempTag);
				if(tempTag.equals(BUSR)){
					_data.addRelation(tempRelation);
				}
			}
		}
		if(localName.equalsIgnoreCase("member")){
			tempMember = new Member(atts.getValue("type"), atts.getValue("role"), atts.getValue("ref"));
			if(_isRelation && tempMember.getType().equalsIgnoreCase("node")){
				tempRelation.addMembers(tempMember);
			}
		}
		
		if(localName.equalsIgnoreCase("relation"))
		{
			_isRelation = true;
			tempRelation = new BusLine(atts.getValue("id"), atts.getValue("version"), atts.getValue("timestamp"), 
					atts.getValue("uid"), atts.getValue("user"), atts.getValue("changeset"));
		}

	}

	/**
	 * Called at the end of the element. Setting the booleans to false, so we
	 * know that we've just left that tag.
	 * 
	 * @param namespaceURI
	 * @param localName
	 * @param qName
	 * @throws SAXException
	 */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		Log.v("endElement", localName);

		if (localName.equalsIgnoreCase("node")) {
			_isNode = false;
			temp = null;
		}
		if(localName.equalsIgnoreCase("relation")){
			_isRelation = false;
			tempRelation = null;
		}
	}

	/**
	 * Calling when we're within an element. Here we're checking to see if there
	 * is any content in the tags that we're interested in and populating it in
	 * the Config object.
	 * 
	 * @param ch
	 * @param start
	 * @param length
	 */
	@Override
	public void characters(char ch[], int start, int length) {
		String chars = new String(ch, start, length);
		chars = chars.trim();
	}
}

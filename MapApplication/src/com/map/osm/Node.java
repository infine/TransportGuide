package com.map.osm;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

@Element(name = "node")
public class Node {

	@Attribute(required = true)
	public String id;

	@Attribute(required = true)
	public String lat;

	@Attribute(required = true)
	public String lon;

	@Attribute(required = true)
	public String user;

	@Attribute(required = true)
	public String uid;

	@Attribute(required = true)
	public String visible;

	@Attribute(required = true)
	public String version;

	@Attribute(required = true)
	public String changeset;

	@Attribute(required = true)
	public String timestamp;

	@ElementList(required = false, entry = "tag", inline = true)
	public List<Tag> tags;

	@Override
	public String toString() {
		return id;
	}

}

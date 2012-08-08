package com.map.osm;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

@Element(name="relation")
public class Relation {

	@Element(required = true)
	public String id;

	@Element(required = true)
	public String version;

	@Element(required = true)
	public String timestamp;

	@Element(required = true)
	public String uid;

	@Element(required = true)
	public String user;

	@Element(required = true)
	public String changeset;
	
	@ElementList
	public List<Member> members;
	
	@ElementList
	public List<Tag> tags;

}

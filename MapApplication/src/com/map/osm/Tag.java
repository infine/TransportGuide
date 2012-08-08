package com.map.osm;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element(name="tag")
public class Tag {

	@Attribute(required=true)
	public String k;
	
	@Attribute(required=true)
	public String v;
}

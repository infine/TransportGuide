package com.map.osm;

import org.simpleframework.xml.Element;

@Element(name = "nd")
public class ND {

	@Element(required = true)
	public String ref;

}

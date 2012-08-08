package com.map.osm;

import org.simpleframework.xml.Element;

@Element(name = "member")
public class Member {

	@Element(required = true)
	public String type;

	@Element(required = true)
	public String role;

	@Element(required = true)
	public String ref;

}

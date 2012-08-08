package com.map.osm;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class FeatureCollection {

	@ElementList(entry = "node", inline = true)
	public List<Node> nodes;

}
package com.map.osm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FeatureCollection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<BusStation> nodes = new ArrayList<BusStation>();

	public List<BusLine> relations = new ArrayList<BusLine>();

	public FeatureCollection() {

	}

	public void addNode(BusStation nd) {
		nodes.add(nd);
	}

	public void addRelation(BusLine rel) {
		relations.add(rel);
	}

	public List<BusStation> getNodes() {
		return nodes;
	}

	public int CountRelations() {
		return relations.size();
	}

	public int CountNodes() {
		return nodes.size();
	}

	public List<BusLine> getRelations() {
		return relations;
	}

}
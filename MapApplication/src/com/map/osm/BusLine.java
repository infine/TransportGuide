package com.map.osm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BusLine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String id;

	public String version;

	public String timestamp;

	public String uid;

	public String user;

	public String changeset;

	public List<Member> members = new ArrayList<Member>();

	public List<BusStation> busStations = new ArrayList<BusStation>();

	public List<Tag> tags = new ArrayList<Tag>();

	public BusLine(String id, String version, String timestamp, String uid,
			String user, String changeset) {
		this.id = id;
		this.version = version;
		this.timestamp = timestamp;
		this.uid = uid;
		this.user = user;
		this.changeset = changeset;
	}

	public void addMembers(Member mb) {
		members.add(mb);
	}

	public void addTags(Tag tg) {
		tags.add(tg);
	}

	public void addStation(BusStation station) {
		busStations.add(station);
	}

	public List<BusStation> getStations() {
		return busStations;
	}

	public List<Member> getMembers() {
		return members;
	}

	@Override
	public String toString() {
		for (Tag tag : tags) {
			if (tag.getK().equalsIgnoreCase("name"))
				return tag.getV();
		}
		return "no name";
	}

}

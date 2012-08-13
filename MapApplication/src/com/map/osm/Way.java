package com.map.osm;

import java.util.ArrayList;
import java.util.List;

public class Way {

	public String id;

	public String version;

	public String timestamp;

	public String uid;

	public String user;

	public String changeset;

	public List<ND> nodesList = new ArrayList<ND>();

	public List<Tag> tags = new ArrayList<Tag>();

	public Way(String id, String version, String timestamp, String uid,
			String user, String changeset) {
		this.id = id;
		this.version = version;
		this.timestamp = timestamp;
		this.uid = uid;
		this.user = user;
		this.changeset = changeset;
	}

	public void addNodes(ND nd) {
		nodesList.add(nd);
	}

	public void addTags(Tag tag) {
		tags.add(tag);
	}

}

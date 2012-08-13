package com.map.osm;

public class Member {

	public String type;

	public String role;

	public String ref;

	public Member(String type, String role, String ref) {
		this.type = type;
		this.role = role;
		this.ref = ref;
	}

	public String getType() {
		return type;
	}

	public String getReference() {
		return ref;
	}
}

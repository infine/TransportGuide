package com.map.osm;

import java.io.Serializable;

public class Member implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

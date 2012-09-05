package com.map.osm;

import java.io.Serializable;

public class ND implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String ref;

	public ND(String reference) {
		this.ref = reference;
	}

	public String getReference() {
		return ref;
	}

	public void setReference(String reference) {
		this.ref = reference;
	}

}

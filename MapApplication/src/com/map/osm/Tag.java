package com.map.osm;

public class Tag {

	public String k;

	public String v;

	public Tag(String k, String v) {
		this.k = k;
		this.v = v;
	}

	public String getK() {
		return k;
	}

	public String getV() {
		return v;
	}
	
	public String getVwhereK(String k)
	{
		if(this.k.equalsIgnoreCase(k)){
			return v;
		}else return null;
	}
	
	public boolean equals(Tag tag)
	{
		if(this.k.equalsIgnoreCase(tag.getK()) && this.v.equalsIgnoreCase(tag.getV())
				)
			return true;
		else 
			return false;
		
	}
}

package com.virginvoyages.crm.data;

public class ProfileReadRequest {

	private UniqueID UniqueID;

	public UniqueID getUniqueID() {
		return UniqueID;
	}

	public void setUniqueID(UniqueID UniqueID) {
		this.UniqueID = UniqueID;
	}

	@Override
	public String toString() {
		return "ClassPojo [UniqueID = " + UniqueID + "]";
	}
}

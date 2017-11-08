package com.virginvoyages.crm.data;

public class BookingChannel {
	private String Type;

	private String CompanyName;

	public String getType() {
		return Type;
	}

	public void setType(String Type) {
		this.Type = Type;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String CompanyName) {
		this.CompanyName = CompanyName;
	}

	@Override
	public String toString() {
		return "ClassPojo [Type = " + Type + ", CompanyName = " + CompanyName + "]";
	}
}

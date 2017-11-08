package com.virginvoyages.crm.data;

public class OTA_ReadRQ {

	private ReadRequests ReadRequests;

	private POS POS;

	private String xmlns;

	private String Version;

	private String PrimaryLangID;

	public ReadRequests getReadRequests() {
		return ReadRequests;
	}

	public void setReadRequests(ReadRequests ReadRequests) {
		this.ReadRequests = ReadRequests;
	}

	public POS getPOS() {
		return POS;
	}

	public void setPOS(POS POS) {
		this.POS = POS;
	}

	public String getXmlns() {
		return xmlns;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String Version) {
		this.Version = Version;
	}

	public String getPrimaryLangID() {
		return PrimaryLangID;
	}

	public void setPrimaryLangID(String PrimaryLangID) {
		this.PrimaryLangID = PrimaryLangID;
	}

	@Override
	public String toString() {
		return "ClassPojo [ReadRequests = " + ReadRequests + ", POS = " + POS + ", xmlns = " + xmlns + ", Version = "
				+ Version + ", PrimaryLangID = " + PrimaryLangID + "]";
	}

}

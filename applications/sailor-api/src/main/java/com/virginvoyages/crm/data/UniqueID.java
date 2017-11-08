package com.virginvoyages.crm.data;

public class UniqueID {
	private String Type;

	private String ID;

	private String ID_Context;

	public String getType() {
		return Type;
	}

	public void setType(String Type) {
		this.Type = Type;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getID_Context() {
		return ID_Context;
	}

	public void setID_Context(String ID_Context) {
		this.ID_Context = ID_Context;
	}

	@Override
	public String toString() {
		return "ClassPojo [Type = " + Type + ", ID = " + ID + ", ID_Context = " + ID_Context + "]";
	}
}

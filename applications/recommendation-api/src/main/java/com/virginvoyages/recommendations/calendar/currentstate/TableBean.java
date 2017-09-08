package com.virginvoyages.recommendations.calendar.currentstate;

public class TableBean {
	
	String name;
	String city;
	String tribe_name;
	String sub_tribe;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTribe_name() {
		return tribe_name;
	}
	public void setTribe_name(String tribe_name) {
		this.tribe_name = tribe_name;
	}
	public String getSub_tribe() {
		return sub_tribe;
	}
	public void setSub_tribe(String sub_tribe) {
		this.sub_tribe = sub_tribe;
	}
	@Override
	public String toString() {
		return "TableBean [city=" + city + ", name=" + name + ", tribe_name="
				+ tribe_name + ", sub_tribe=" + sub_tribe + "]";
	}
	

	
}

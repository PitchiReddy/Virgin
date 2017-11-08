package com.virginvoyages.crm.data;

public class Source {

	private RequestorID RequestorID;

	private BookingChannel BookingChannel;

	public RequestorID getRequestorID() {
		return RequestorID;
	}

	public void setRequestorID(RequestorID RequestorID) {
		this.RequestorID = RequestorID;
	}

	public BookingChannel getBookingChannel() {
		return BookingChannel;
	}

	public void setBookingChannel(BookingChannel BookingChannel) {
		this.BookingChannel = BookingChannel;
	}

	@Override
	public String toString() {
		return "ClassPojo [RequestorID = " + RequestorID + ", BookingChannel = " + BookingChannel + "]";
	}
}

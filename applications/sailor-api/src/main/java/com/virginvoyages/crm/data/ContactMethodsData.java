package com.virginvoyages.crm.data;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.contact.model.ContactAddress;
import com.virginvoyages.contact.model.ContactEmail;
import com.virginvoyages.contact.model.ContactMethod;
import com.virginvoyages.contact.model.ContactPhone;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class ContactMethodsData {

	@JsonProperty("Id")
	private String id;

	@JsonProperty("Sailor_ID__c")
	private String sailorId;

	@JsonProperty("Type__c")
	private String contactType;

	@JsonProperty("Use_Type__c")
	private String contactSubType;

	@JsonProperty("Primary__c")
	private String primary;

	@JsonProperty("Active_Date_from__c")
	private LocalDate activeDateFrom;

	@JsonProperty("Active_Date_to__c")
	private LocalDate activeDateTo;

	@JsonProperty("Deliverable_Status__c")
	private String deliverableStatus;

	@JsonProperty("Deliverable_Status__c")
	private String undeliverableStatus;

	@JsonProperty("Captured_Date__c")
	private LocalDate capturedDate;

	@JsonProperty("email")
	private String email = null;

	@JsonProperty("addressLine1")
	private String addressLine1 = null;

	@JsonProperty("addressLine2")
	private String addressLine2 = null;

	@JsonProperty("addressLine3")
	private String addressLine3 = null;

	@JsonProperty("addressLine4")
	private String addressLine4 = null;

	@JsonProperty("city")
	private String city = null;

	@JsonProperty("state")
	private String state = null;

	@JsonProperty("province")
	private String province = null;

	@JsonProperty("country")
	private String country = null;

	@JsonProperty("postalCode")
	private String postalCode = null;

	@JsonProperty("countryCode")
	private String countryCode = null;

	@JsonProperty("phoneNumber")
	private String phoneNumber = null;

	@JsonProperty("extenstion")
	private String extenstion = null;

	@JsonProperty("recordTypeId")
	private String recordTypeId = null;

	private void setCommonFields(ContactMethod contactMethod) {
		contactMethod.activeDateFrom(this.activeDateFrom);
		contactMethod.activeDateTo(this.activeDateTo);
		contactMethod.capturedDate(this.capturedDate);
		contactMethod.contactSubType(this.contactSubType);
		contactMethod.deliverableStatus(this.deliverableStatus);
		contactMethod.undeliverableStatus(this.undeliverableStatus);
		contactMethod.primary(this.primary);
				
	}

	public ContactEmail convertToContactEmail() {
		ContactEmail contactEmail = new ContactEmail();
		setCommonFields(contactEmail);
		return contactEmail
				.email(this.email);
	}

	public ContactPhone convertToContactPhone() {
		ContactPhone contactPhone = new ContactPhone();
		setCommonFields(contactPhone);
		return contactPhone
				.phoneNumber(this.phoneNumber)
				.extension(this.extenstion);
	}

	public ContactAddress convertToContactAddress() {
		ContactAddress contactAddress = new ContactAddress();
		setCommonFields(contactAddress);
		return contactAddress
				.addressLine1(this.addressLine1)
				.addressLine2(this.addressLine2)
				.addressLine3(this.addressLine3)
				.addressLine4(this.addressLine4)
				.city(this.city)
				.state(this.state)
				.province(this.province)
				.countryCode(this.countryCode)
				.postalCode(this.postalCode)
				.country(this.country);
		
	}

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.11.09 at 04:47:21 PM IST 
//


package com.virginvoyages.seaware.data;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for List_TravelPurpose_Base.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="List_TravelPurpose_Base">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Bereavment/Emergency"/>
 *     &lt;enumeration value="Business"/>
 *     &lt;enumeration value="BusinessAndPersonal"/>
 *     &lt;enumeration value="Charter/Group"/>
 *     &lt;enumeration value="Conference/Event"/>
 *     &lt;enumeration value="Personal"/>
 *     &lt;enumeration value="Other_"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "List_TravelPurpose_Base")
@XmlEnum
public enum ListTravelPurposeBase {

    @XmlEnumValue("Bereavment/Emergency")
    BEREAVMENT_EMERGENCY("Bereavment/Emergency"),
    @XmlEnumValue("Business")
    BUSINESS("Business"),
    @XmlEnumValue("BusinessAndPersonal")
    BUSINESS_AND_PERSONAL("BusinessAndPersonal"),
    @XmlEnumValue("Charter/Group")
    CHARTER_GROUP("Charter/Group"),
    @XmlEnumValue("Conference/Event")
    CONFERENCE_EVENT("Conference/Event"),
    @XmlEnumValue("Personal")
    PERSONAL("Personal"),

    /**
     * Use: Select this enumeration to exchange a value that is not in the enumerated list by entering the value information in the Code Extension fields.
     * 
     */
    @XmlEnumValue("Other_")
    OTHER("Other_");
    private final String value;

    ListTravelPurposeBase(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ListTravelPurposeBase fromValue(String v) {
        for (ListTravelPurposeBase c: ListTravelPurposeBase.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

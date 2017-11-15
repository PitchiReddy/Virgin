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
 * <p>Java class for CategoryLocationType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CategoryLocationType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Inside"/>
 *     &lt;enumeration value="Outside"/>
 *     &lt;enumeration value="Both"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CategoryLocationType")
@XmlEnum
public enum CategoryLocationType {


    /**
     * No window.
     * 
     */
    @XmlEnumValue("Inside")
    INSIDE("Inside"),

    /**
     * On the outside of the ship generally has a window, porthole or balcony.
     * 
     */
    @XmlEnumValue("Outside")
    OUTSIDE("Outside"),

    /**
     * Used to identify that the category contains both inside and outside cabins.
     * 
     */
    @XmlEnumValue("Both")
    BOTH("Both");
    private final String value;

    CategoryLocationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CategoryLocationType fromValue(String v) {
        for (CategoryLocationType c: CategoryLocationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

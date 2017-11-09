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
 * <p>Java class for List_PowerType_Base.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="List_PowerType_Base">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Diesel"/>
 *     &lt;enumeration value="Electric"/>
 *     &lt;enumeration value="Gas"/>
 *     &lt;enumeration value="Oil"/>
 *     &lt;enumeration value="Solar"/>
 *     &lt;enumeration value="Other_"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "List_PowerType_Base")
@XmlEnum
public enum ListPowerTypeBase {

    @XmlEnumValue("Diesel")
    DIESEL("Diesel"),
    @XmlEnumValue("Electric")
    ELECTRIC("Electric"),
    @XmlEnumValue("Gas")
    GAS("Gas"),
    @XmlEnumValue("Oil")
    OIL("Oil"),
    @XmlEnumValue("Solar")
    SOLAR("Solar"),

    /**
     * Use: Select this enumeration to exchange a value that is not in the enumerated list by entering the value information in the Code Extension fields.
     * 
     */
    @XmlEnumValue("Other_")
    OTHER("Other_");
    private final String value;

    ListPowerTypeBase(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ListPowerTypeBase fromValue(String v) {
        for (ListPowerTypeBase c: ListPowerTypeBase.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

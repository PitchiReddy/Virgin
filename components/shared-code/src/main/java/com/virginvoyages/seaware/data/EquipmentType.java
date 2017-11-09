//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.11.09 at 04:47:21 PM IST 
//


package com.virginvoyages.seaware.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Specifies the aircraft equipment type.
 * 
 * <p>Java class for EquipmentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EquipmentType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.opentravel.org/OTA/2003/05>StringLength0to64">
 *       &lt;attribute name="AirEquipType" type="{http://www.opentravel.org/OTA/2003/05}StringLength3" />
 *       &lt;attribute name="ChangeofGauge" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="AircraftTailNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EquipmentType", propOrder = {
    "value"
})
@XmlSeeAlso({
    EquipmentTypePref.class
})
public class EquipmentType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "AirEquipType")
    protected String airEquipType;
    @XmlAttribute(name = "ChangeofGauge")
    protected Boolean changeofGauge;
    @XmlAttribute(name = "AircraftTailNumber")
    protected String aircraftTailNumber;

    /**
     * Used for Character Strings, length 0 to 64.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the airEquipType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirEquipType() {
        return airEquipType;
    }

    /**
     * Sets the value of the airEquipType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirEquipType(String value) {
        this.airEquipType = value;
    }

    /**
     * Gets the value of the changeofGauge property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isChangeofGauge() {
        return changeofGauge;
    }

    /**
     * Sets the value of the changeofGauge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setChangeofGauge(Boolean value) {
        this.changeofGauge = value;
    }

    /**
     * Gets the value of the aircraftTailNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAircraftTailNumber() {
        return aircraftTailNumber;
    }

    /**
     * Sets the value of the aircraftTailNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAircraftTailNumber(String value) {
        this.aircraftTailNumber = value;
    }

}

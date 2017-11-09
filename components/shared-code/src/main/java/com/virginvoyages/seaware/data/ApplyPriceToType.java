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
import javax.xml.bind.annotation.XmlType;


/**
 * Indicates the specific traveler, itinerary, paid origin/destination or flight segment a priced item applies to.
 * 
 * <p>Java class for ApplyPriceToType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplyPriceToType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="SeatRPH" type="{http://www.opentravel.org/OTA/2003/05}RPH_Type" />
 *       &lt;attribute name="OtherServiceRPH" type="{http://www.opentravel.org/OTA/2003/05}RPH_Type" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplyPriceToType")
public class ApplyPriceToType {

    @XmlAttribute(name = "SeatRPH")
    protected String seatRPH;
    @XmlAttribute(name = "OtherServiceRPH")
    protected String otherServiceRPH;

    /**
     * Gets the value of the seatRPH property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeatRPH() {
        return seatRPH;
    }

    /**
     * Sets the value of the seatRPH property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeatRPH(String value) {
        this.seatRPH = value;
    }

    /**
     * Gets the value of the otherServiceRPH property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherServiceRPH() {
        return otherServiceRPH;
    }

    /**
     * Sets the value of the otherServiceRPH property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherServiceRPH(String value) {
        this.otherServiceRPH = value;
    }

}

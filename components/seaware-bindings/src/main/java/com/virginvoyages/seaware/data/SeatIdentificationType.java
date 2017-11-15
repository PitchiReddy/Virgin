//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.11.09 at 04:47:21 PM IST 
//


package com.virginvoyages.seaware.data;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines a specific seat on the train.
 * 
 * <p>Java class for SeatIdentificationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SeatIdentificationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="CarNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="SeatNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="Deck" type="{http://www.opentravel.org/OTA/2003/05}DeckType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SeatIdentificationType")
public class SeatIdentificationType {

    @XmlAttribute(name = "CarNumber", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger carNumber;
    @XmlAttribute(name = "SeatNumber", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String seatNumber;
    @XmlAttribute(name = "Deck")
    protected DeckType deck;

    /**
     * Gets the value of the carNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCarNumber() {
        return carNumber;
    }

    /**
     * Sets the value of the carNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCarNumber(BigInteger value) {
        this.carNumber = value;
    }

    /**
     * Gets the value of the seatNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeatNumber() {
        return seatNumber;
    }

    /**
     * Sets the value of the seatNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeatNumber(String value) {
        this.seatNumber = value;
    }

    /**
     * Gets the value of the deck property.
     * 
     * @return
     *     possible object is
     *     {@link DeckType }
     *     
     */
    public DeckType getDeck() {
        return deck;
    }

    /**
     * Sets the value of the deck property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeckType }
     *     
     */
    public void setDeck(DeckType value) {
        this.deck = value;
    }

}

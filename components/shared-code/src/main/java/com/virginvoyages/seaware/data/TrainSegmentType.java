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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Defines information about a train segment.
 * 
 * <p>Java class for TrainSegmentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TrainSegmentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DepartureStation" type="{http://www.opentravel.org/OTA/2003/05}StationDetailsType"/>
 *         &lt;element name="ArrivalStation" type="{http://www.opentravel.org/OTA/2003/05}StationDetailsType"/>
 *         &lt;element name="MarketingCompany" type="{http://www.opentravel.org/OTA/2003/05}CompanyNameType"/>
 *         &lt;element name="OperatingCompany" type="{http://www.opentravel.org/OTA/2003/05}CompanyNameType" minOccurs="0"/>
 *         &lt;element name="Equipment" type="{http://www.opentravel.org/OTA/2003/05}ClassCodeType" minOccurs="0"/>
 *         &lt;element name="TrainInfo" type="{http://www.opentravel.org/OTA/2003/05}TrainInfoType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="DepartureDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="ArrivalDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="StopQuantity" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *       &lt;attribute name="JourneyDuration" type="{http://www.w3.org/2001/XMLSchema}duration" />
 *       &lt;attribute name="CrossBorderInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrainSegmentType", propOrder = {
    "departureStation",
    "arrivalStation",
    "marketingCompany",
    "operatingCompany",
    "equipment",
    "trainInfo"
})
@XmlSeeAlso({
    BookedTrainSegmentType.class
})
public class TrainSegmentType {

    @XmlElement(name = "DepartureStation", required = true)
    protected StationDetailsType departureStation;
    @XmlElement(name = "ArrivalStation", required = true)
    protected StationDetailsType arrivalStation;
    @XmlElement(name = "MarketingCompany", required = true)
    protected CompanyNameType marketingCompany;
    @XmlElement(name = "OperatingCompany")
    protected CompanyNameType operatingCompany;
    @XmlElement(name = "Equipment")
    protected ClassCodeType equipment;
    @XmlElement(name = "TrainInfo")
    protected TrainInfoType trainInfo;
    @XmlAttribute(name = "DepartureDateTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar departureDateTime;
    @XmlAttribute(name = "ArrivalDateTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar arrivalDateTime;
    @XmlAttribute(name = "StopQuantity")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger stopQuantity;
    @XmlAttribute(name = "JourneyDuration")
    protected Duration journeyDuration;
    @XmlAttribute(name = "CrossBorderInd")
    protected Boolean crossBorderInd;

    /**
     * Gets the value of the departureStation property.
     * 
     * @return
     *     possible object is
     *     {@link StationDetailsType }
     *     
     */
    public StationDetailsType getDepartureStation() {
        return departureStation;
    }

    /**
     * Sets the value of the departureStation property.
     * 
     * @param value
     *     allowed object is
     *     {@link StationDetailsType }
     *     
     */
    public void setDepartureStation(StationDetailsType value) {
        this.departureStation = value;
    }

    /**
     * Gets the value of the arrivalStation property.
     * 
     * @return
     *     possible object is
     *     {@link StationDetailsType }
     *     
     */
    public StationDetailsType getArrivalStation() {
        return arrivalStation;
    }

    /**
     * Sets the value of the arrivalStation property.
     * 
     * @param value
     *     allowed object is
     *     {@link StationDetailsType }
     *     
     */
    public void setArrivalStation(StationDetailsType value) {
        this.arrivalStation = value;
    }

    /**
     * Gets the value of the marketingCompany property.
     * 
     * @return
     *     possible object is
     *     {@link CompanyNameType }
     *     
     */
    public CompanyNameType getMarketingCompany() {
        return marketingCompany;
    }

    /**
     * Sets the value of the marketingCompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyNameType }
     *     
     */
    public void setMarketingCompany(CompanyNameType value) {
        this.marketingCompany = value;
    }

    /**
     * Gets the value of the operatingCompany property.
     * 
     * @return
     *     possible object is
     *     {@link CompanyNameType }
     *     
     */
    public CompanyNameType getOperatingCompany() {
        return operatingCompany;
    }

    /**
     * Sets the value of the operatingCompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyNameType }
     *     
     */
    public void setOperatingCompany(CompanyNameType value) {
        this.operatingCompany = value;
    }

    /**
     * Gets the value of the equipment property.
     * 
     * @return
     *     possible object is
     *     {@link ClassCodeType }
     *     
     */
    public ClassCodeType getEquipment() {
        return equipment;
    }

    /**
     * Sets the value of the equipment property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassCodeType }
     *     
     */
    public void setEquipment(ClassCodeType value) {
        this.equipment = value;
    }

    /**
     * Gets the value of the trainInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TrainInfoType }
     *     
     */
    public TrainInfoType getTrainInfo() {
        return trainInfo;
    }

    /**
     * Sets the value of the trainInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrainInfoType }
     *     
     */
    public void setTrainInfo(TrainInfoType value) {
        this.trainInfo = value;
    }

    /**
     * Gets the value of the departureDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDepartureDateTime() {
        return departureDateTime;
    }

    /**
     * Sets the value of the departureDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDepartureDateTime(XMLGregorianCalendar value) {
        this.departureDateTime = value;
    }

    /**
     * Gets the value of the arrivalDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getArrivalDateTime() {
        return arrivalDateTime;
    }

    /**
     * Sets the value of the arrivalDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setArrivalDateTime(XMLGregorianCalendar value) {
        this.arrivalDateTime = value;
    }

    /**
     * Gets the value of the stopQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStopQuantity() {
        return stopQuantity;
    }

    /**
     * Sets the value of the stopQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStopQuantity(BigInteger value) {
        this.stopQuantity = value;
    }

    /**
     * Gets the value of the journeyDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getJourneyDuration() {
        return journeyDuration;
    }

    /**
     * Sets the value of the journeyDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setJourneyDuration(Duration value) {
        this.journeyDuration = value;
    }

    /**
     * Gets the value of the crossBorderInd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCrossBorderInd() {
        return crossBorderInd;
    }

    /**
     * Sets the value of the crossBorderInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCrossBorderInd(Boolean value) {
        this.crossBorderInd = value;
    }

}

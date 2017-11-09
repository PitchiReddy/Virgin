//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.11.09 at 04:47:21 PM IST 
//


package com.virginvoyages.seaware.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines information to do a query by train operator or specific train information.
 * 
 * <p>Java class for TrainQueryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TrainQueryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="Train" type="{http://www.opentravel.org/OTA/2003/05}TrainIdentificationType" minOccurs="0"/>
 *         &lt;element name="NetworkCode" type="{http://www.opentravel.org/OTA/2003/05}NetworkCodeType" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrainQueryType", propOrder = {
    "train",
    "networkCode"
})
public class TrainQueryType {

    @XmlElement(name = "Train")
    protected TrainIdentificationType train;
    @XmlElement(name = "NetworkCode")
    protected NetworkCodeType networkCode;

    /**
     * Gets the value of the train property.
     * 
     * @return
     *     possible object is
     *     {@link TrainIdentificationType }
     *     
     */
    public TrainIdentificationType getTrain() {
        return train;
    }

    /**
     * Sets the value of the train property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrainIdentificationType }
     *     
     */
    public void setTrain(TrainIdentificationType value) {
        this.train = value;
    }

    /**
     * Gets the value of the networkCode property.
     * 
     * @return
     *     possible object is
     *     {@link NetworkCodeType }
     *     
     */
    public NetworkCodeType getNetworkCode() {
        return networkCode;
    }

    /**
     * Sets the value of the networkCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link NetworkCodeType }
     *     
     */
    public void setNetworkCode(NetworkCodeType value) {
        this.networkCode = value;
    }

}

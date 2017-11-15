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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Encryptable information, e.g. credit card number.
 * 
 * <p>Java class for EncryptionTokenType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EncryptionTokenType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PlainText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentravel.org/OTA/2003/05}TPA_Extensions" minOccurs="0"/>
 *         &lt;element name="Warning" type="{http://www.opentravel.org/OTA/2003/05}WarningType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="EncryptionKey" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="EncryptionKeyMethod" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="EncryptionMethod" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="EncryptedValue" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Mask">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="[0-9a-zA-Z]{1,32}"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="Token">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="[0-9a-zA-Z]{1,32}"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="TokenProviderID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="AuthenticationMethod">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="SecurityCode"/>
 *             &lt;enumeration value="MagneticStripe"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EncryptionTokenType", propOrder = {
    "plainText",
    "tpaExtensions",
    "warning"
})
public class EncryptionTokenType {

    @XmlElement(name = "PlainText")
    protected String plainText;
    @XmlElement(name = "TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;
    @XmlElement(name = "Warning")
    protected WarningType warning;
    @XmlAttribute(name = "EncryptionKey")
    protected String encryptionKey;
    @XmlAttribute(name = "EncryptionKeyMethod")
    protected String encryptionKeyMethod;
    @XmlAttribute(name = "EncryptionMethod")
    protected String encryptionMethod;
    @XmlAttribute(name = "EncryptedValue")
    protected String encryptedValue;
    @XmlAttribute(name = "Mask")
    protected String mask;
    @XmlAttribute(name = "Token")
    protected String token;
    @XmlAttribute(name = "TokenProviderID")
    protected String tokenProviderID;
    @XmlAttribute(name = "AuthenticationMethod")
    protected String authenticationMethod;

    /**
     * Gets the value of the plainText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlainText() {
        return plainText;
    }

    /**
     * Sets the value of the plainText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlainText(String value) {
        this.plainText = value;
    }

    /**
     * Gets the value of the tpaExtensions property.
     * 
     * @return
     *     possible object is
     *     {@link TPAExtensionsType }
     *     
     */
    public TPAExtensionsType getTPAExtensions() {
        return tpaExtensions;
    }

    /**
     * Sets the value of the tpaExtensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPAExtensionsType }
     *     
     */
    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    /**
     * Gets the value of the warning property.
     * 
     * @return
     *     possible object is
     *     {@link WarningType }
     *     
     */
    public WarningType getWarning() {
        return warning;
    }

    /**
     * Sets the value of the warning property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarningType }
     *     
     */
    public void setWarning(WarningType value) {
        this.warning = value;
    }

    /**
     * Gets the value of the encryptionKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncryptionKey() {
        return encryptionKey;
    }

    /**
     * Sets the value of the encryptionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncryptionKey(String value) {
        this.encryptionKey = value;
    }

    /**
     * Gets the value of the encryptionKeyMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncryptionKeyMethod() {
        return encryptionKeyMethod;
    }

    /**
     * Sets the value of the encryptionKeyMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncryptionKeyMethod(String value) {
        this.encryptionKeyMethod = value;
    }

    /**
     * Gets the value of the encryptionMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncryptionMethod() {
        return encryptionMethod;
    }

    /**
     * Sets the value of the encryptionMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncryptionMethod(String value) {
        this.encryptionMethod = value;
    }

    /**
     * Gets the value of the encryptedValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncryptedValue() {
        return encryptedValue;
    }

    /**
     * Sets the value of the encryptedValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncryptedValue(String value) {
        this.encryptedValue = value;
    }

    /**
     * Gets the value of the mask property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMask() {
        return mask;
    }

    /**
     * Sets the value of the mask property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMask(String value) {
        this.mask = value;
    }

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the tokenProviderID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTokenProviderID() {
        return tokenProviderID;
    }

    /**
     * Sets the value of the tokenProviderID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenProviderID(String value) {
        this.tokenProviderID = value;
    }

    /**
     * Gets the value of the authenticationMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthenticationMethod() {
        return authenticationMethod;
    }

    /**
     * Sets the value of the authenticationMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthenticationMethod(String value) {
        this.authenticationMethod = value;
    }

}

package com.virginvoyages.seaware.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class ClientData {
	
	protected SuccessType success;
    protected WarningsType warnings;
    protected List<UniqueIDType> uniqueID;
    protected ProfilesType profiles;
    protected ErrorsType errors;
    protected String echoToken;
    protected XMLGregorianCalendar timeStamp;
    protected String target;
    protected String targetName;
    protected BigDecimal version;
    protected String transactionIdentifier;
    protected BigInteger sequenceNmbr;
    protected String transactionStatusCode;
    protected Boolean retransmissionIndicator;
    protected String correlationID;
    protected String primaryLangID;
    protected String altLangID;
    
    
    
	public SuccessType getSuccess() {
		return success;
	}
	public void setSuccess(SuccessType success) {
		this.success = success;
	}
	public WarningsType getWarnings() {
		return warnings;
	}
	public void setWarnings(WarningsType warnings) {
		this.warnings = warnings;
	}
	public List<UniqueIDType> getUniqueID() {
		return uniqueID;
	}
	public void setUniqueID(List<UniqueIDType> uniqueID) {
		this.uniqueID = uniqueID;
	}
	public ProfilesType getProfiles() {
		return profiles;
	}
	public void setProfiles(ProfilesType profiles) {
		this.profiles = profiles;
	}
	public ErrorsType getErrors() {
		return errors;
	}
	public void setErrors(ErrorsType errors) {
		this.errors = errors;
	}
	public String getEchoToken() {
		return echoToken;
	}
	public void setEchoToken(String echoToken) {
		this.echoToken = echoToken;
	}
	public XMLGregorianCalendar getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(XMLGregorianCalendar timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public BigDecimal getVersion() {
		return version;
	}
	public void setVersion(BigDecimal version) {
		this.version = version;
	}
	public String getTransactionIdentifier() {
		return transactionIdentifier;
	}
	public void setTransactionIdentifier(String transactionIdentifier) {
		this.transactionIdentifier = transactionIdentifier;
	}
	public BigInteger getSequenceNmbr() {
		return sequenceNmbr;
	}
	public void setSequenceNmbr(BigInteger sequenceNmbr) {
		this.sequenceNmbr = sequenceNmbr;
	}
	public String getTransactionStatusCode() {
		return transactionStatusCode;
	}
	public void setTransactionStatusCode(String transactionStatusCode) {
		this.transactionStatusCode = transactionStatusCode;
	}
	public Boolean getRetransmissionIndicator() {
		return retransmissionIndicator;
	}
	public void setRetransmissionIndicator(Boolean retransmissionIndicator) {
		this.retransmissionIndicator = retransmissionIndicator;
	}
	public String getCorrelationID() {
		return correlationID;
	}
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
	public String getPrimaryLangID() {
		return primaryLangID;
	}
	public void setPrimaryLangID(String primaryLangID) {
		this.primaryLangID = primaryLangID;
	}
	public String getAltLangID() {
		return altLangID;
	}
	public void setAltLangID(String altLangID) {
		this.altLangID = altLangID;
	}
	
	
}

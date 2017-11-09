package com.virginvoyages.crm.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SeawareData {

	 private OTA_ReadRQ OTA_ReadRQ;

	    public OTA_ReadRQ getOTA_ReadRQ ()
	    {
	        return OTA_ReadRQ;
	    }

	    public void setOTA_ReadRQ (OTA_ReadRQ OTA_ReadRQ)
	    {
	        this.OTA_ReadRQ = OTA_ReadRQ;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [OTA_ReadRQ = "+OTA_ReadRQ+"]";
	    }
	   

	   

	    

}

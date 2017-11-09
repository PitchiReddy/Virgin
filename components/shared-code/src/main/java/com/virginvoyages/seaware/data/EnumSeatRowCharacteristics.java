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
 * <p>Java class for Enum_SeatRowCharacteristics.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Enum_SeatRowCharacteristics">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BufferRow"/>
 *     &lt;enumeration value="ExitLeft"/>
 *     &lt;enumeration value="ExitRight"/>
 *     &lt;enumeration value="ExitRow"/>
 *     &lt;enumeration value="ExitRowWithCabinFacilitiesInDesignatedColumn"/>
 *     &lt;enumeration value="ExitRowWithCabinFacilitiesInUndesignatedColumn"/>
 *     &lt;enumeration value="ExtraLegRoom"/>
 *     &lt;enumeration value="IndifferentRow"/>
 *     &lt;enumeration value="LowerdeckRow"/>
 *     &lt;enumeration value="MaindeckRow"/>
 *     &lt;enumeration value="NosmokingRow"/>
 *     &lt;enumeration value="NotOverwingRow"/>
 *     &lt;enumeration value="OverwingRow"/>
 *     &lt;enumeration value="RowDoesNotExist"/>
 *     &lt;enumeration value="RowWithCabinFacilitiesInDesignatedColumn"/>
 *     &lt;enumeration value="RowWithCabinFacilitiesInUndesignatedColumn"/>
 *     &lt;enumeration value="RowWithMovieScreen"/>
 *     &lt;enumeration value="SeatRestrictionsApply"/>
 *     &lt;enumeration value="SmokingRow"/>
 *     &lt;enumeration value="UpperDeckRow"/>
 *     &lt;enumeration value="Preferred_PreferentialRow"/>
 *     &lt;enumeration value="WingStart"/>
 *     &lt;enumeration value="WingEnd"/>
 *     &lt;enumeration value="Other_"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Enum_SeatRowCharacteristics")
@XmlEnum
public enum EnumSeatRowCharacteristics {

    @XmlEnumValue("BufferRow")
    BUFFER_ROW("BufferRow"),
    @XmlEnumValue("ExitLeft")
    EXIT_LEFT("ExitLeft"),
    @XmlEnumValue("ExitRight")
    EXIT_RIGHT("ExitRight"),
    @XmlEnumValue("ExitRow")
    EXIT_ROW("ExitRow"),
    @XmlEnumValue("ExitRowWithCabinFacilitiesInDesignatedColumn")
    EXIT_ROW_WITH_CABIN_FACILITIES_IN_DESIGNATED_COLUMN("ExitRowWithCabinFacilitiesInDesignatedColumn"),
    @XmlEnumValue("ExitRowWithCabinFacilitiesInUndesignatedColumn")
    EXIT_ROW_WITH_CABIN_FACILITIES_IN_UNDESIGNATED_COLUMN("ExitRowWithCabinFacilitiesInUndesignatedColumn"),
    @XmlEnumValue("ExtraLegRoom")
    EXTRA_LEG_ROOM("ExtraLegRoom"),
    @XmlEnumValue("IndifferentRow")
    INDIFFERENT_ROW("IndifferentRow"),
    @XmlEnumValue("LowerdeckRow")
    LOWERDECK_ROW("LowerdeckRow"),
    @XmlEnumValue("MaindeckRow")
    MAINDECK_ROW("MaindeckRow"),
    @XmlEnumValue("NosmokingRow")
    NOSMOKING_ROW("NosmokingRow"),
    @XmlEnumValue("NotOverwingRow")
    NOT_OVERWING_ROW("NotOverwingRow"),
    @XmlEnumValue("OverwingRow")
    OVERWING_ROW("OverwingRow"),
    @XmlEnumValue("RowDoesNotExist")
    ROW_DOES_NOT_EXIST("RowDoesNotExist"),
    @XmlEnumValue("RowWithCabinFacilitiesInDesignatedColumn")
    ROW_WITH_CABIN_FACILITIES_IN_DESIGNATED_COLUMN("RowWithCabinFacilitiesInDesignatedColumn"),
    @XmlEnumValue("RowWithCabinFacilitiesInUndesignatedColumn")
    ROW_WITH_CABIN_FACILITIES_IN_UNDESIGNATED_COLUMN("RowWithCabinFacilitiesInUndesignatedColumn"),
    @XmlEnumValue("RowWithMovieScreen")
    ROW_WITH_MOVIE_SCREEN("RowWithMovieScreen"),
    @XmlEnumValue("SeatRestrictionsApply")
    SEAT_RESTRICTIONS_APPLY("SeatRestrictionsApply"),
    @XmlEnumValue("SmokingRow")
    SMOKING_ROW("SmokingRow"),
    @XmlEnumValue("UpperDeckRow")
    UPPER_DECK_ROW("UpperDeckRow"),
    @XmlEnumValue("Preferred_PreferentialRow")
    PREFERRED_PREFERENTIAL_ROW("Preferred_PreferentialRow"),
    @XmlEnumValue("WingStart")
    WING_START("WingStart"),
    @XmlEnumValue("WingEnd")
    WING_END("WingEnd"),

    /**
     * It is strongly recommended that you submit a comment to have any of your extended list values permanently added to the OpenTravel specification to support maximum trading partner interoperability. http://www.opentraveldevelopersnetwork.com/specificationcomments/2/entercomment.html
     * 
     */
    @XmlEnumValue("Other_")
    OTHER("Other_");
    private final String value;

    EnumSeatRowCharacteristics(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumSeatRowCharacteristics fromValue(String v) {
        for (EnumSeatRowCharacteristics c: EnumSeatRowCharacteristics.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

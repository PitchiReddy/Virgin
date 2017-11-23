package com.virginvoyages.sailor.api;

import com.virginvoyages.booking.model.Booking;
import com.virginvoyages.booking.model.Bookings;
import com.virginvoyages.booking.model.BookingsEmbedded;
import com.virginvoyages.contact.*;
import com.virginvoyages.contact.model.ContactAddress;
import com.virginvoyages.contact.model.ContactEmail;
import com.virginvoyages.contact.model.ContactMethod;
import com.virginvoyages.contact.model.ContactMethods;
import com.virginvoyages.contact.model.ContactMethodsEmbedded;
import com.virginvoyages.contact.model.ContactPhone;
import com.virginvoyages.household.model.Household;
import com.virginvoyages.household.model.Households;
import com.virginvoyages.household.model.HouseholdsEmbedded;
import com.virginvoyages.identification.model.Identification;
import com.virginvoyages.identification.model.Identifications;
import com.virginvoyages.identification.model.IdentificationsEmbedded;
import com.virginvoyages.loyalty.model.Loyalties;
import com.virginvoyages.loyalty.model.LoyaltiesEmbedded;
import com.virginvoyages.loyalty.model.Loyalty;
import com.virginvoyages.model.Page;
import com.virginvoyages.preference.model.Preference;
import com.virginvoyages.preference.model.Preferences;
import com.virginvoyages.preference.model.PreferencesEmbedded;
import com.virginvoyages.sailor.model.Sailor;
import com.virginvoyages.sailor.model.Sailors;
import com.virginvoyages.sailor.model.SailorsEmbedded;
import com.virginvoyages.visa.model.Visa;
import com.virginvoyages.visa.model.Visas;
import com.virginvoyages.visa.model.VisasEmbedded;
import com.virginvoyages.wearable.model.Wearable;
import com.virginvoyages.wearable.model.Wearables;
import com.virginvoyages.wearable.model.WearablesEmbedded;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Mock implementation of SailorAPI to return mock responses to validate deployment
 */
@Component
public class MockSailorAPI {

    private Map<String, Sailor> sailors = new ConcurrentHashMap<>();

    @PostConstruct
    void init() {
        Sailor sailor = createSailor();
        sailor.preferences().add(createPreference());
        sailor.sailingHistory().add(createSailingHistory());
        sailors.put(sailor.id(), sailor);
    }

    public void addSailor(Sailor sailor) {
        sailors.put(sailor.id(), sailor);
    }

    public Sailor getSailorById(String sailorID) {
        return sailors.get(sailorID);
    }

    public void sailorsDelete(Sailor sailor) {
        sailors.remove(sailor.id());
    }
    
    public void deleteSailorById(String sailorID) {
        sailors.remove(sailorID);
    }
    
    public List<Sailor> sailorsFindGet(String loyaltyID, String email, LocalDate dateofBirth, String firstName, String lastName) {
        return sailors.values().stream()
                .filter(e -> e.firstName().equals(firstName))
                .collect(Collectors.toList());
    }

    public Sailor sailorsFindOrCreateGet(String loyaltyID, String email, LocalDate dateofBirth, String firstName, String lastName) {
        String key = sailors.entrySet().stream()
                .filter(e -> e.getValue().firstName().equals(firstName))
                .map(Map.Entry::getKey).findFirst()
                .orElse(null);
        Sailor sailor;
        if (key == null) {
            sailor = new Sailor().id(UUID.randomUUID().toString()).dateofBirth(dateofBirth).firstName(firstName).lastName(lastName);
            addSailor(sailor);
        } else {
            sailor = sailors.get(key);
        }
        return sailor;
    }

    public Sailors sailorsGet(Integer page, Integer size) {
        Sailors sailorsWrapper = new Sailors().page(new Page().size(size)).embedded(new SailorsEmbedded().sailors(sailors.values().stream()
                .collect(Collectors.toList())));
        sailorsWrapper.page().totalElements(sailorsWrapper.embedded().sailors().size()).number(1).totalPages(1);
        return sailorsWrapper;
    }

    public Bookings sailorsSailorIDBookingsGet(String sailorID, Integer page, Integer size) {
        Bookings bookings = new Bookings().page(new Page().size(size)).embedded(new BookingsEmbedded().addBookingsItem(createSailingHistory()));
        return bookings;
    }

    public ContactMethods sailorsSailorIDContactMethodGet(String sailorID, Integer page, Integer size) {
        ContactMethods contactMethods = new ContactMethods().page(new Page().size(size)).embedded(new ContactMethodsEmbedded().addContactMethod(createContactPhone()).addContactMethod(createContactEmail()).addContactMethod(createAddress()));
        return contactMethods;
    }

    public Households sailorsSailorIDHouseholdsGet(String sailorID, Integer page, Integer size) {
        Households households = new Households().page(new Page().size(size)).embedded(new HouseholdsEmbedded().addHouseholdsItem(createHousehold()));
        return households;
    }

    public Identifications sailorsSailorIDIdentificationGet(String sailorID, Integer page, Integer size) {
        Identifications identifications = new Identifications().page(new Page().size(size)).embedded(new IdentificationsEmbedded().addIdentificationsItem(createIdentification()));
        return identifications;
    }

    public Loyalties sailorsSailorIDLoyaltyGet(String sailorID, Integer page, Integer size) {
        Loyalties loyalties = new Loyalties().page(new Page().size(size)).embedded(new LoyaltiesEmbedded().addLoyaltiesItem(createLoyalty()));
        return loyalties;
    }

    public Preferences sailorsSailorIDPreferencesGet(String sailorID, Integer page, Integer size) {
        Preferences preferences = new Preferences().page(new Page().size(size)).embedded(new PreferencesEmbedded().addPreferencesItem(createPreference()));
        return preferences;
    }

    public Bookings sailorsSailorIDSailingHistoryGet(String sailorID, Integer page, Integer size) {
        Bookings bookingsWrapper = new Bookings().page(new Page().size(size)).embedded(new BookingsEmbedded().addBookingsItem(createSailingHistory()));
        return bookingsWrapper;
    }

    public Visas sailorsSailorIDVisasGet(String sailorID, Integer page, Integer size) {
        Visas visas = new Visas().page(new Page().size(size)).embedded(new VisasEmbedded().addVisasItem(createVisa()));
        return visas;
    }

    public Wearables sailorsSailorIDWearablesGet(String sailorID, Integer page, Integer size) {
        Wearables wearables = new Wearables().page(new Page().size(size)).embedded(new WearablesEmbedded().addWearablesItem(createWearable()));
        return wearables;
    }

    public void updateSailor(Sailor sailor) {
        sailors.put(sailor.id(), sailor);
    }

    private Preference createPreference() {
        return new Preference()
                .category("Drinks")
                .subCategory("Soft")
                .option("Coke");
    }

    private Booking createSailingHistory() {
        return new Booking()
                .bookedBySailor("The Sailor")
                .brand("Virgin")
                .sailDate(LocalDate.now())
                .dateBooked(LocalDate.now())
                .reservationNumber("123")
                .currency("USD")
                .numberofAcessibleStaterooms(0)
                .numberofGuests(1)
                .daysonSailing(5)
                .disembarkationDate(LocalDate.now())
                .onboardSpend("100.00")
                .embarkationDate(LocalDate.now())
                .prepaidGratuities("None")
                .vacationProtection("Yes")
                .preCruiseTransfer("Air")
                .postCruiseTransfer("Hotel")
                .flightPurchase("Yes")
                .numberofStateroom(1)
                .ship("Virgin")
                .status("BOOK")
                .sailingPackage("All Inclusive");
    }

    private Sailor createSailor() {
        return new Sailor()
                .id("123")
                .firstName("Sailor")
                .lastName("API")
                .dateofBirth(LocalDate.now())
                .ageGroup("30-40")
                .anniversaryDate(LocalDate.now())
                .averageNTRAmount("100.00")
                .birthCountry("USA")
                .gender("M")
                .seawareClientID("123")
                .prefix("Mr.")
                .middleName("VV")
                .suffix("Jr.")
                .occupation("Crew")
                .citizenshipCountry("USA")
                .preferredLanguage("English")
                .retirementDate(LocalDate.now())
                .martialStatus("S")
                .numberofChildren(0)
                .tribe("Rebel")
                .preferredName("Ship")
                .nickname("Sail")
                .status("Y")
                .vIP("N")
                .averageOBSAmount("50.0")
                .totalLifetimeCruiseNights("3")
                .totalLifetimePerDiem("50.0")
                .totalLifetimeRevenuePerDiem("44")
                .salutation("Hello");
    }

    private Visa createVisa() {
        return new Visa()
                .issueDate(LocalDate.now())
                .number(UUID.randomUUID().toString())
                .visaType("A")
                .issueCountry("USA")
                .expiryDate(LocalDate.now())
                .numberofEntries(10);
    }

    private Wearable createWearable() {
        return new Wearable()
                .wearableID(UUID.randomUUID().toString())
                .wearableName("Band")
                .wearableType("B")
                .state("Open")
                .activatedOn(LocalDate.now())
                .deactiveatedOn(LocalDate.now());
    }


    private ContactAddress createAddress() {
        ContactAddress ca = new ContactAddress()
                .addressLine1("Line 1")
                .addressLine2("Line 2")
                .addressLine3("Line 3")
                .addressLine4("Line 4")
                .city("Plantation")
                .state("FL")
                .province("Plant")
                .countryCode("US")
                .postalCode("90001")
                .country("United States");
        initContactMethod(ca);
        return ca;
    }

    private ContactEmail createContactEmail() {
        ContactEmail ce = new ContactEmail().email("sailor@virgin.com");
        initContactMethod(ce);
        return ce;
    }

    private ContactPhone createContactPhone() {
        ContactPhone cp = new ContactPhone().phoneNumber("999-888-7777").extension("x1243");
        initContactMethod(cp);
        return cp;
    }

    private void initContactMethod(ContactMethod cm) {
        cm.activeDateFrom(LocalDate.now())
                .activeDateTo(LocalDate.now())
                .primary("Yes")
                .contactSubType("Home")
                .deliverableStatus("Delivered")
                .undeliverableStatus("NA")
                .capturedDate(LocalDate.now());
    }

    private Household createHousehold() {
        return new Household()
                .totalLifeTimeCruiseNights(10)
                .householdId("Household-123")
                .status("OK")
                .averageNTRAmount("100")
                .totalOBSAmount("123")
                .averageLifeSpendPerNight("100")
                .averageLifeSpendPerNightLastCruise("250")
                .totalLifeTimeSpend("10000")
                .vVValueScore("10000")
                .primarySailor(4);
    }

    private Identification createIdentification() {
        return new Identification()
                .birthCountryCode("USA")
                .birthDate(LocalDate.now())
                .documentAddress("Line 123")
                .documentType("DL")
                .documentSubType("Official")
                .gender("M")
                .expiryDate(LocalDate.now())
                .issueState("FL")
                .issueDate(LocalDate.now())
                .fatherName("SailorFather")
                .motherMaidenName("SailorMother")
                .issueCity("Plantation")
                .number(UUID.randomUUID().toString())
                .issueCountry("USA")
                .issueAuthority("Feds");
    }

    private Loyalty createLoyalty() {
        return new Loyalty()
                .brand("Virgin")
                .enrollDate(LocalDate.now())
                .enrollSource("Web")
                .individualCredits("3")
                .memberNumber(UUID.randomUUID().toString())
                .recurredCredits("5")
                .program("Virgin")
                .status("ACTIVE")
                .tier("Gold")
                .referredBy("None");
    }
}

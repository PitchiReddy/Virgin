package com.virginvoyages.sailor;

import com.virginvoyages.booking.BookingController;
import com.virginvoyages.contact.ContactMethodController;
import com.virginvoyages.household.HouseholdController;
import com.virginvoyages.identification.IdentificationController;
import com.virginvoyages.loyalty.LoyaltyController;
import com.virginvoyages.visa.VisaController;
import com.virginvoyages.wearable.WearableController;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class SailorResourceAssembler {
    public static Resource<Sailor> createSailorResource(Sailor sailor, EntityLinks entityLinks) {
        int page = 0;
        int size = 10;
        String sailorID = sailor.id();
        Resource<Sailor> resource = new Resource<>(sailor);
        resource.add(entityLinks.linkToSingleResource(Sailor.class, sailorID));
        resource.add(linkTo(methodOn(BookingController.class)
                .findBookingsBySailor(sailorID, null, null, page, size)).withRel(SailorLinks.BOOKINGS));
        resource.add(linkTo(methodOn(HouseholdController.class)
                .findHouseholdsBySailor(sailorID, null, null, page, size)).withRel(SailorLinks.HOUSEHOLDS));
        resource.add(linkTo(methodOn(ContactMethodController.class)
                .findContactMethodsBySailor(sailorID, null, null, page, size)).withRel(SailorLinks.CONTACTMETHODS));
        resource.add(linkTo(methodOn(LoyaltyController.class)
                .findLoyaltiesBySailor(sailorID, null, null, page, size)).withRel(SailorLinks.LOYALTIES));
        resource.add(linkTo(methodOn(VisaController.class)
                .findVisasBySailor(sailorID, null, null, page, size)).withRel(SailorLinks.VISAS));
        resource.add(linkTo(methodOn(IdentificationController.class)
                .findIdentificationBySailor(sailorID, null, null, page, size)).withRel(SailorLinks.IDENTIFICATIONS));
        resource.add(linkTo(methodOn(WearableController.class)
                .findWearablesBySailor(sailorID, null, null, page, size)).withRel(SailorLinks.WEARABLES));
        return resource;
    }

}

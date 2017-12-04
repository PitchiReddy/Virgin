package com.virginvoyages.crossreference.helper;

import org.springframework.stereotype.Component;

import com.virginvoyages.crossreference.data.entities.ReferenceData;
import com.virginvoyages.crossreference.data.entities.ReferenceSourceData;
import com.virginvoyages.crossreference.data.entities.ReferenceTypeData;
import com.virginvoyages.crossreference.model.Reference;
import com.virginvoyages.crossreference.model.ReferenceSource;
import com.virginvoyages.crossreference.model.ReferenceType;

@Component
public class CrossReferenceEntityMapper {

	public ReferenceSource convertToReferenceSourceBusinessEntity(ReferenceSourceData source) {
		return new ReferenceSource()
				.referenceSource(source.referenceSource())
				.referenceSourceID(String.valueOf(source.referenceSourceID()))
				.inActive(source.inActive());

	}

	public ReferenceSourceData convertToReferenceSourceDataEntity(ReferenceSource source) {
		return new ReferenceSourceData()
				.referenceSource(source.referenceSource())
				.referenceSourceID(source.referenceSourceID())
				.inActive(source.inActive());
	}

	public ReferenceType convertToReferenceTypeBusinessEntity(ReferenceTypeData type) {
		return new ReferenceType()
				.referenceType(type.referenceType())
				.referenceSourceID(type.referenceSourceData().referenceSourceID())
				.referenceTypeID(type.referenceTypeID());

	}

	public ReferenceTypeData convertToReferenceTypeDataEntity(ReferenceType type) {
		return new ReferenceTypeData()
				.referenceType(type.referenceType())
				.referenceTypeID(type.referenceTypeID())
				.referenceSourceData(new ReferenceSourceData()
									.referenceSourceID(type.referenceSourceID()));
	}

	public Reference convertToReferenceBusinessEntity(ReferenceData reference) {
		return new Reference()
				.referenceID(String.valueOf(reference.referenceID()))
				.nativeSourceIDValue(String.valueOf(reference.nativeSourceIDValue()))
				.masterID(String.valueOf(reference.masterID()))
				.referenceTypeID(String.valueOf(reference.referenceTypeData().referenceTypeID()));
	}

	public ReferenceData convertToReferenceDataEntity(Reference reference) {
		return new ReferenceData()
				.referenceID(reference.referenceID())
				.nativeSourceIDValue(reference.nativeSourceIDValue())
				.masterID(reference.masterID())
				.referenceTypeData(new ReferenceTypeData()
						.referenceTypeID(reference.referenceTypeID()));

	}

}

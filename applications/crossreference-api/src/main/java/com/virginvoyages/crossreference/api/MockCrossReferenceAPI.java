package com.virginvoyages.crossreference.api;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.virginvoyages.model.crossreference.Reference;
import com.virginvoyages.model.crossreference.References;
import com.virginvoyages.model.crossreference.ReferencesEmbedded;
import com.virginvoyages.model.crossreference.ReferenceSource;
import com.virginvoyages.model.crossreference.ReferenceType;
import com.virginvoyages.model.Page;

/**
 * Mock implementation of CrossReferenceAPI to return mock responses to validate deployment
 */
@Component
public class MockCrossReferenceAPI {
	
	private Map<String, Reference> references = new ConcurrentHashMap<>();
	private Map<String, ReferenceType> referenceTypes = new ConcurrentHashMap<>();
	private Map<String, ReferenceSource> referenceSources = new ConcurrentHashMap<>();
	

	@PostConstruct
    void init() {
		addReferenceType(createReferenceType());
		addReferenceSource(createReferenceSource());
		addReference(createReference());
		       
    }
	
	public void addReference(Reference reference) {
		String key = references.entrySet().stream()
                .filter(e -> e.getValue().referenceID().equals(reference.referenceID()))
                .map(Map.Entry::getKey).findFirst()
                .orElse(null);
		if (key == null) {
			references.put(reference.referenceID(), reference);
        } 
	}
	
	public void updateReference(String referenceID, Reference reference) {
		Reference existingReference = references.get(referenceID);
		if(null != existingReference)
	//		reference.auditData(updateAuditDataForUpdate(existingReference.auditData()));
			references.put(referenceID, reference);
	}
		
	public Reference findReferenceByID(String referenceID) {
		return references.get(referenceID);
	}
	
	public void deleteReferenceByID(String referenceID) {
		references.remove(referenceID);
	}
	
	public References findReferences(Integer page,Integer size) {
		List<Reference> referenceList = references.values().stream().collect(Collectors.toList());
	    References references = new References().page(new Page().size(size)).embedded(new ReferencesEmbedded().references(referenceList));
	    return references;
	}
	
	public List<Reference> findReferencesByMaster(String masterID, String targetSourceID) {
		
		if(StringUtils.isNotEmpty(targetSourceID)) {
			return findReferencesByMasterAndTargetSourceID(masterID, targetSourceID);
		}
		return references
				.values()
				.stream()
                .filter(e -> e.masterID().equals(masterID))
                .collect(Collectors.toList());
               
	}
	
	public List<Reference> findReferencesByMasterAndTargetSourceID(String masterID, String targetSourceID) {
		return references
				.values()
				.stream()
                .filter(e -> e.masterID().equals(masterID))
                .collect(Collectors.toList());
    }
	
	public List<Reference> findReferencesBySource(String nativeSourceID,String sourceID,String typeID,String targetSourceID){
		
		/*if(StringUtils.isNotEmpty(typeID)) {
			return findReferencesSourceAndTypeID(nativeSourceID,sourceID,typeID);
		}*/
		references.put("R1", new Reference()
				.referenceID("R1")
				.masterID("M1")
				.nativeSourceIDValue("NSID1"));
		references.put("R2", new Reference()
				.referenceID("R2")
				.masterID("M1")
				.nativeSourceIDValue("NSID2"));
		return references
				.values()
				.stream()
                .filter(e -> e.nativeSourceIDValue().equals(nativeSourceID))
                .collect(Collectors.toList());
	}
	
	public List<Reference> findReferencesSourceAndTypeID(String nativeSourceID,String sourceID,String typeID){
		return references
				.values()
				.stream()
                .filter(e -> e.nativeSourceIDValue().equals(nativeSourceID))
                             .collect(Collectors.toList());
	}
	
	public void addReferenceType(ReferenceType referenceType) {
		String key = referenceTypes.entrySet().stream()
                .filter(e -> e.getValue().referenceTypeID().equals(referenceType.referenceTypeID()))
                .map(Map.Entry::getKey).findFirst()
                .orElse(null);
		if (key == null) {
			referenceTypes.put(referenceType.referenceTypeID(), referenceType);
        }
	}
	
	public void updateReferenceType(String referenceTypeID, ReferenceType referenceType) {
		ReferenceType existingReferenceType = referenceTypes.get(referenceTypeID);
		if(null != existingReferenceType)
			//referenceType.auditData(updateAuditDataForUpdate(existingReferenceType.auditData()));
			referenceTypes.put(referenceTypeID, referenceType);
	}
	
	public ReferenceType findReferenceTypeByID(String referenceTypeID) {
		return referenceTypes.get(referenceTypeID);
	}
	
	public void deleteReferenceTypeByID(String referenceTypeID) {
		referenceTypes.remove(referenceTypeID);
	}
	
	public List<ReferenceType> findTypes() {
		return referenceTypes.values().stream().collect(Collectors.toList());
	}
		
	public void addReferenceSource(ReferenceSource referenceSource) {
		String key = referenceSources.entrySet().stream()
                .filter(e -> e.getValue().referenceSourceID().equals(referenceSource.referenceSourceID()))
                .map(Map.Entry::getKey).findFirst()
                .orElse(null);
		if (key == null) {
			referenceSources.put(referenceSource.referenceSourceID(), referenceSource);
        } 
	}
	
	public void updateReferenceSource(String referenceSourceID, ReferenceSource referenceSource) {
		ReferenceSource existingReferenceSource = referenceSources.get(referenceSourceID);
		if(null != existingReferenceSource)
			//referenceSource.auditData(updateAuditDataForUpdate(existingReferenceSource.auditData()));
			referenceSources.put(referenceSourceID, referenceSource);
	}
	
	public ReferenceSource findReferenceSourceByID(String referenceSourceID) {
		return referenceSources.get(referenceSourceID);
	}
	
	public void deleteReferenceSourceByID(String referenceSourceID) {
		referenceSources.remove(referenceSourceID);
	}
	
	public List<ReferenceSource> findSources() {
		return referenceSources.values().stream().collect(Collectors.toList());
	}
	
	private ReferenceSource createReferenceSource() {
		return new ReferenceSource()
				.referenceSourceID("RS1")
				.referenceSource("Seaware");
	}
	
	private ReferenceType createReferenceType() {
		return new ReferenceType()
				.referenceTypeID("RT1")
				.referenceType("Reservation");
				
	}
	
	private Reference createReference() {
		return new Reference()
				.referenceID("R1")
				.masterID("M1")
				.nativeSourceIDValue("NSID1");
	}
}

package com.virginvoyages.assembly;

import java.util.List;
import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.sailor.Sailor;

/**
 * {@code Interface} for assembly tasks for Sailor operations
 * @author rpraveen
 *
 */
public interface SailorAssembly {

	/**
	 * Gets sailor object with id = sailorID
	 * @param sailorID
	 * @return
	 */
	public Sailor getSailorById(String sailorID);
	
	/**
	 * Deletes sailor object with id = sailorID
	 * @param sailorID
	 * @return
	 */
	public void deleteSailorById(String sailorID);
	
	/**
	 * Find sailors with fields matching the available values in {@code accountData}
	 * @param accountData - search for sailors is done based on values in accountData
	 * @return 
	 */
	public List<Sailor> findSailors(AccountData accountData);
	
	/**
	 * Create Sailor account with details as available in {@code accountData}
	 * @param accountData - Sailor is created with values present in accountData object
	 * @return
	 */
	public Sailor createSailor(AccountData accountData);
	
}

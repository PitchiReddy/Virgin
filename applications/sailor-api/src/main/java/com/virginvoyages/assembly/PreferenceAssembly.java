package com.virginvoyages.assembly;

import com.virginvoyages.preference.PreferencesEmbedded;

/**
 * {@code Interface} for assembly tasks for Preference operations
 * @author rpraveen
 *
 */
public interface PreferenceAssembly {
    
	/**
	 * Find sailor preferences based on sailorID
	 * @param sailorID
	 * @return
	 */
	public PreferencesEmbedded findSailorPreferences(String sailorID);
}

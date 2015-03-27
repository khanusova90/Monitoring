package cz.hanusova.monitoring.service;

import cz.hanusova.monitoring.model.AddressDTO;

public interface LocationService {

	/**
	 * Finds last known location
	 * 
	 * @return new instance of {@link AddressDTO} filled with information about
	 *         last known location
	 */
	public AddressDTO getLocation();

}

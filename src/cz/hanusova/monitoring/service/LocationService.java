package cz.hanusova.monitoring.service;

import cz.hanusova.monitoring.model.AddressDTO;

public interface LocationService {

	/**
	 * Najde posledni zname umisteni telefonu podle GPS nebo podle site
	 * 
	 * @return Nova instance {@link AddressDTO} naplnena informacemi o poslednim
	 *         znamem umiteni.
	 */
	public AddressDTO getLocation();

}

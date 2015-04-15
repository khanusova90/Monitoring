package cz.hanusova.monitoring.service;

import com.google.android.gms.common.api.GoogleApiClient;

public interface LocationService {

	/**
	 * Najde posledni zname umisteni telefonu podle GPS nebo podle site
	 * 
	 * @return Nova instance {@link AddressDTO} naplnena informacemi o poslednim
	 *         znamem umiteni.
	 */
	// public AddressDTO getLocation();

	/**
	 * Lokalizuje telefon
	 * 
	 * @param googleApiClient
	 *            implementace {@link GoogleApiClient}
	 * @return Adresa, na ktere se telefon aktualne nachazi
	 */
	public String getLocation(GoogleApiClient googleApiClient);

}

package cz.hanusova.monitoring.service;

import com.google.android.gms.common.api.GoogleApiClient;

public interface LocationService {

	/**
	 * Lokalizuje telefon
	 * 
	 * @param googleApiClient
	 *            implementace {@link GoogleApiClient}
	 * @return Adresa, na ktere se telefon aktualne nachazi
	 */
	public String getLocation(GoogleApiClient googleApiClient);

}

package cz.hanusova.monitoring.service.impl;

import java.io.IOException;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import cz.hanusova.monitoring.R;
import cz.hanusova.monitoring.model.AddressDTO;
import cz.hanusova.monitoring.service.LocationService;

public class LocationServiceImpl implements LocationService {
	private LocationManager locManager;
	private Context ctx;

	public LocationServiceImpl(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	public AddressDTO getLocation() {
		locManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
		Location location = locManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			location = locManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}

		return getLocationProperties(location);
	}

	/**
	 * Ulozi informace o umisteni dle nalezene lokality
	 * 
	 * @param location
	 *            Instance tridy {@link Location} ziskana z GPS nebo site
	 * @return Nova instance {@link AddressDTO} naplnena informacemi o umisteni
	 */
	private AddressDTO getLocationProperties(Location location) {
		Geocoder gc = new Geocoder(ctx, Locale.getDefault());
		AddressDTO newAddress = new AddressDTO();
		if (location == null) {
			newAddress.setError(ctx.getString(R.string.cannot_localize));
			return newAddress;
		}
		try {
			Address address = gc.getFromLocation(location.getLatitude(),
					location.getLongitude(), 1).get(0);
			fillAddress(newAddress, address);
		} catch (IOException e) {
			newAddress.setError(ctx.getString(R.string.cannot_localize));
			e.getStackTrace();
		}
		return newAddress;
	}

	/**
	 * Ulozi predane informace o umisteni
	 * 
	 * @param newAddress
	 *            - Prazdna instance tridy {@link AddressDTO}
	 * @param address
	 *            - Instance tridy {@link Address} s informacemi o umisteni
	 * @return Predana instance {@link AddressDTO} naplnena informacemi o
	 *         lokalite
	 */
	private AddressDTO fillAddress(AddressDTO newAddress, Address address) {
		newAddress.setPostalCode(address.getPostalCode());
		newAddress.setCity(address.getLocality());
		newAddress.setSubLocality(address.getSubLocality());
		newAddress.setNumber(address.getSubThoroughfare());
		newAddress.setLatitude(String.valueOf(address.getLatitude()));
		newAddress.setLongitude(String.valueOf(address.getLongitude()));

		return newAddress;
	}

}

package cz.hanusova.monitoring.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import cz.hanusova.monitoring.R;
import cz.hanusova.monitoring.model.Constants;
import cz.hanusova.monitoring.service.LocationService;

public class LocationServiceImpl implements LocationService {
	private LocationManager locManager;
	private LocationListener locListener;
	private Context ctx;

	public LocationServiceImpl(Context ctx) {
		this.ctx = ctx;
	}

	//
	// @Override
	// public AddressDTO getLocation() {
	// locManager = (LocationManager) ctx
	// .getSystemService(Context.LOCATION_SERVICE);
	//
	// requestUpdates();
	//
	// Location location = locManager
	// .getLastKnownLocation(LocationManager.GPS_PROVIDER);
	// if (location == null) {
	// location = locManager
	// .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	// }
	//
	// return getLocationProperties(location);
	// }
	//
	// private void requestUpdates() {
	// locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1,
	// this);
	// locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1,
	// 1, this);
	// }
	//
	// /**
	// * Ulozi informace o umisteni dle nalezene lokality
	// *
	// * @param location
	// * Instance tridy {@link Location} ziskana z GPS nebo site
	// * @return Nova instance {@link AddressDTO} naplnena informacemi o
	// umisteni
	// */
	// @SuppressLint("NewApi")
	// private AddressDTO getLocationProperties(Location location) {
	// Geocoder gc = new Geocoder(ctx, Locale.getDefault());
	// AddressDTO newAddress = new AddressDTO();
	// if (location == null) {
	// newAddress.setError(ctx.getString(R.string.cannot_localize)
	// + "location == null");
	// return newAddress;
	// }
	// try {
	// Address address = gc.getFromLocation(location.getLatitude(),
	// location.getLongitude(), 1).get(0);
	// fillAddress(newAddress, address);
	// } catch (IOException e) {
	// newAddress.setError(ctx.getString(R.string.cannot_localize)
	// + "IOException");
	// // e.getStackTrace();
	// e.printStackTrace();
	// }
	// return newAddress;
	// }
	//
	// /**
	// * Ulozi predane informace o umisteni
	// *
	// * @param newAddress
	// * - Prazdna instance tridy {@link AddressDTO}
	// * @param address
	// * - Instance tridy {@link Address} s informacemi o umisteni
	// * @return Predana instance {@link AddressDTO} naplnena informacemi o
	// * lokalite
	// */
	// private AddressDTO fillAddress(AddressDTO newAddress, Address address) {
	// newAddress.setPostalCode(address.getPostalCode());
	// newAddress.setCity(address.getLocality());
	// newAddress.setSubLocality(address.getSubLocality());
	// newAddress.setNumber(address.getSubThoroughfare());
	// newAddress.setLatitude(String.valueOf(address.getLatitude()));
	// newAddress.setLongitude(String.valueOf(address.getLongitude()));
	//
	// return newAddress;
	// }

	@Override
	public String getLocation(GoogleApiClient googleApiClient) {
		String result;
		Location location = LocationServices.FusedLocationApi
				.getLastLocation(googleApiClient);

		if (location != null) {
			result = getAddress(location);
		} else {
			result = ctx.getString(R.string.address_not_found);
		}
		return result;
	}

	/**
	 * Vyhleda udaje o adrese
	 * 
	 * @param location
	 *            {@link Location} se souradnicemi telefonu
	 * @return Adresa, na ktere se telefon nachazi
	 */
	private String getAddress(Location location) {
		String result;
		double lat = location.getLatitude();
		double lon = location.getLongitude();
		Geocoder gc = new Geocoder(ctx, Locale.getDefault());

		try {
			List<Address> addresses = gc.getFromLocation(lat, lon, 1);
			if (addresses == null || addresses.isEmpty()) {
				result = ctx.getString(R.string.address_not_found)
						+ ctx.getString(R.string.coordinates) + lat + " " + lon;
			} else {
				Address add = addresses.get(0);
				List<String> addressFragments = new ArrayList<String>();
				for (int i = 0; i < add.getMaxAddressLineIndex(); i++) {
					addressFragments.add(add.getAddressLine(i));
				}
				result = TextUtils.join(System.getProperty("line.separator"),
						addressFragments);
			}
			Log.i(Constants.LOG_TAG, result);
		} catch (IOException e) {
			Log.e("Monitoring", "Service is not available!", e);
			result = ctx.getString(R.string.address_not_found)
					+ ctx.getString(R.string.coordinates) + lat + " " + lon;
		}
		return result;
	}
}

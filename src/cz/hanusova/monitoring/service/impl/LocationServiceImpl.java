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
import cz.hanusova.monitoring.service.LocationService;

public class LocationServiceImpl implements LocationService {
	private LocationManager locManager;
	private LocationListener locListener;
	private Context ctx;

	public LocationServiceImpl(Context ctx) {
		this.ctx = ctx;
	}

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

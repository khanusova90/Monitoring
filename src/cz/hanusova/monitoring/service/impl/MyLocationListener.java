package cz.hanusova.monitoring.service.impl;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MyLocationListener implements LocationListener {

	private Location lastLocation;
	private static String info = "No info in listener";

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onLocationChanged(Location location) {
		lastLocation = location;
		updateInfo();
	}

	private void updateInfo() {
		if (lastLocation != null) {
			info = "Lattitude = " + lastLocation.getLatitude()
					+ "; Longitude = " + lastLocation.getLongitude();
		} else {
			info = "cannot get last location";
		}
	}
}

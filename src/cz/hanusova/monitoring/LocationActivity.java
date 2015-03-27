package cz.hanusova.monitoring;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import cz.hanusova.monitoring.service.impl.MyLocationListener;

public class LocationActivity extends ActionBarActivity implements
		ConnectionCallbacks, OnConnectionFailedListener {

	private LocationManager locManager;
	private String info = "No info";

	private final LocationListener locListener = new MyLocationListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("Location activity created");

		super.onCreate(savedInstanceState);
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000,
				100, locListener);
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		info = getString(R.string.connection_failed) + " "
				+ getString(R.string.cannot_localize);
	}

	@Override
	public void onConnected(Bundle arg0) {
		info = "onConnectedMethod";
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		info = getString(R.string.connection_suspended) + " "
				+ getString(R.string.cannot_localize);
	}

	@Override
	protected void onStart() {
		super.onStart();
		System.out.println("Location activity started");

		Toast.makeText(this, info, Toast.LENGTH_LONG).show();
	}
}

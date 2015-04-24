package cz.hanusova.monitoring;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import cz.hanusova.monitoring.service.LocationService;
import cz.hanusova.monitoring.service.impl.Constants;
import cz.hanusova.monitoring.service.impl.LocationServiceImpl;
import cz.hanusova.monitoring.service.impl.Utils;

/**
 * Portions of this class are modifications based on work created and shared by
 * the Android Open Source Project and used according to terms described in the
 * Creative Commons 2.5 Attribution License.
 * 
 * For more information about connecting to Google Api Client see
 * https://developer.android.com/google/auth/api-client.html
 * 
 * @author Katerina Hanusova
 *
 */
public class SosActivity extends ActionBarActivity implements
		OnConnectionFailedListener, ConnectionCallbacks {

	private Ringtone ringtone;
	private String address;

	private LocationService locService;
	private GoogleApiClient googleApiClient;

	private boolean play;
	private boolean sendSms;
	private boolean resolvingError = false;

	/*
	 * Promenne pro reseni chyby s pripojenim ke Google play services
	 */
	private static final int REQUEST_RESOLVE_ERROR = 1001;
	private static final String STATE_RESOLVING_ERROR = "resolving_error";

	/*
	 * Promenne pro odlozeni spusteni alarmu a odeslani SMS
	 */
	public static final String KEY_ALARM_DELAY = "alarm_delay";
	public static final String KEY_SMS_DELAY = "sms_delay";
	private int alarmDelay;
	private int smsDelay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sos_view);
		unlockScreen();
		play = true;
		sendSms = true;
		resolvingError = savedInstanceState != null
				&& savedInstanceState.getBoolean(STATE_RESOLVING_ERROR, false);
		locService = new LocationServiceImpl(getApplicationContext());
		googleApiClient = new GoogleApiClient.Builder(this)
				.addApi(LocationServices.API).addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).build();

		setDelay();
		startAlarm();
		sendSms();
	}

	/**
	 * Odemkne a rozsviti obrazovku
	 */
	private void unlockScreen() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!resolvingError) {
			googleApiClient.connect();
		}
	}

	/**
	 * Metoda na stisknuti tlacitka zastavi stav nouze
	 * 
	 * @param button
	 */
	public void setOk(View button) {
		play = false;
		sendSms = false;
		if (ringtone != null) {
			ringtone.stop();
		}
		super.finish();
	}

	/**
	 * Metoda na stisknuti tlacitka zavola na nouzove cislo a zastavi prehravani
	 * alarmu
	 * 
	 * @param button
	 */
	public void callSos(View button) {
		play = false;
		if (ringtone != null) {
			ringtone.stop();
		}
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + String.valueOf(Constants.SOS_NUMBER)));
		startActivity(intent);
		super.finish();
	}

	/**
	 * Metoda spusti zvuk budiku po nastavene dobe
	 */
	private void startAlarm() {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (play) {
					play = false;
					playSound();
				}
			}
		}, alarmDelay);
	}

	/**
	 * Nastavi dobu, po ktere se spusti alarm a odesle SMS. V pripade, ze nebyly
	 * tyto hodnoty predany, pouzije vychozi nastaveni z {@link Constants}
	 */
	private void setDelay() {
		Intent i = getIntent();
		alarmDelay = i.getIntExtra(KEY_ALARM_DELAY, Constants.DEFAULT_DELAY);
		smsDelay = i.getIntExtra(KEY_SMS_DELAY, Constants.DEFAULT_DELAY);
	}

	/**
	 * Spusti prehravani zvuku nastaveneho na budik
	 */
	private void playSound() {
		setSoundLoud();

		Uri soundUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_ALARM);
		ringtone = RingtoneManager.getRingtone(getApplicationContext(),
				soundUri);
		ringtone.play();
	}

	/**
	 * Nastavi nejvyssi hlasitost vyzvaneni
	 */
	private void setSoundLoud() {
		AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		manager.setStreamVolume(AudioManager.STREAM_RING,
				manager.getStreamMaxVolume(AudioManager.STREAM_RING), 0);
	}

	/**
	 * Po nastavene dobe zasle SMS zpravu s predvolenym textem z
	 * {@link Constants} na cislo ulozene pro pripad nouze
	 */
	private void sendSms() {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (sendSms) {
					String number = Utils
							.getPhoneNumber(getApplicationContext());
					SmsManager manager = SmsManager.getDefault();
					manager.sendTextMessage(number, null, Constants.SMS_MESSAGE
							+ address, null, null);
				}
			}
		}, smsDelay);
	}

	/**
	 * Spusti se, kdyz dojde k uspesnemu pripojeni ke Google play services
	 */
	@Override
	public void onConnected(Bundle bundle) {
		address = locService.getLocation(googleApiClient);
	}

	/**
	 * Spusti se, pokud dojde k preruseni spojeni se sluzbou Google play
	 * services
	 */
	@Override
	public void onConnectionSuspended(int arg0) {
		address = getString(R.string.cannot_localize).concat(
				getString(R.string.connection_suspended));
	}

	/**
	 * Metoda se spusti, pokud dojde k chybe behem pripojovani ke Google play
	 * services
	 */
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// Chyba uz se resi
		if (resolvingError) {
			return;
		}

		// Chyba ma automaticke reseni
		if (result.hasResolution()) {
			try {
				resolvingError = true;
				result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
			} catch (SendIntentException e) {
				googleApiClient.connect();
			}
		} else {
			address = getString(R.string.cannot_localize).concat(
					getString(R.string.connection_failed));
		}
	}

	/**
	 * Spusti se po vyreseni chyby a znovu se pokusi pripojit ke sluzbe
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_RESOLVE_ERROR) {
			resolvingError = false;
			if (resultCode == RESULT_OK) {
				if (!googleApiClient.isConnecting()
						&& !googleApiClient.isConnected()) {
					googleApiClient.connect();
				}
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(STATE_RESOLVING_ERROR, resolvingError);
	}

	@Override
	protected void onStop() {
		googleApiClient.disconnect();
		super.onStop();
	}
}

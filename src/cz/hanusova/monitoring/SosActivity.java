package cz.hanusova.monitoring;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;
import cz.hanusova.monitoring.model.AddressDTO;
import cz.hanusova.monitoring.model.Codelist;
import cz.hanusova.monitoring.service.LocationService;
import cz.hanusova.monitoring.service.NumberService;
import cz.hanusova.monitoring.service.impl.LocationServiceImpl;
import cz.hanusova.monitoring.service.impl.NumberServiceImpl;

public class SosActivity extends ActionBarActivity {

	private Ringtone ringtone;
	private boolean play;
	private NumberService numberService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("SOS activity created");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.sos_view);
		play = true;
		numberService = new NumberServiceImpl(getApplicationContext());

		LocationService locService = new LocationServiceImpl(
				getApplicationContext());
		AddressDTO address = locService.getLocation();

		// TODO: smazat
		Toast.makeText(
				this,
				"Město: " + address.getCity() + " PSC: "
						+ address.getPostalCode() + " Sublocality: "
						+ address.getSubLocality() + " Number: "
						+ address.getNumber(), Toast.LENGTH_LONG).show();

		startAlarm();
	}

	@Override
	protected void onStart() {
		super.onStart();
		System.out.println("SOS activity started");
	}

	/**
	 * Metoda na stisknuti tlacitka zastavi stav nouze
	 * 
	 * @param button
	 */
	public void setOk(View button) {
		play = false;
		// TODO: Vypnout zapinani hudby
		if (ringtone != null) {
			ringtone.stop();
		}
		super.finish();
	}

	/**
	 * Metoda na stisknuti tlacitka zavola na predvolene cislo a zastavi
	 * prehravani alarmu
	 * 
	 * @param button
	 */
	public void callSos(View button) {
		play = false;
		if (ringtone != null) {
			ringtone.stop();
		}
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + numberService.getNumber()));
		startActivity(intent);
		// TODO: zkontrolovat metodu
	}

	private void startAlarm() {
		System.out.println("Starting alarm");

		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (play) {
					play = false;
					playSound();
				}
			}
		}, Codelist.DELAY);
	}

	/**
	 * Metoda spusti prehravani zvuku nastaveneho na budik
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
	 * Metoda nastavi nejvyssi hlasitost vyzvaneni
	 */
	private void setSoundLoud() {
		AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		manager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		manager.setStreamVolume(AudioManager.STREAM_RING,
				manager.getStreamMaxVolume(AudioManager.STREAM_RING), 0);
	}

	@Override
	protected void onStop() {
		System.out.println("Sos activity stopped");
		super.onStop();
	}
}

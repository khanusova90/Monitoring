package cz.hanusova.monitoring;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import cz.hanusova.monitoring.service.impl.Constants;
import cz.hanusova.monitoring.service.impl.Utils;

public class PowerReceiver extends BroadcastReceiver {
	private Context ctx;
	private boolean isConnected;

	/**
	 * Pri nabijeni telefonu se po dobe definovane ve tride {@link Constants}
	 * spusti {@link SosActivity}. Pokud je telefon do teto doby odpojen, nic se
	 * nestane
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		ctx = context;
		isConnected = false;

		if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
			isConnected = false;
		}

		if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
			isConnected = true;
		}

		if (isConnected) {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (isConnected) {
						Utils.startSosActivity(ctx,
								Constants.POWER_ALARM_DELAY,
								Constants.POWER_SMS_DELAY);
					}
				}
			}, Constants.POWER_DELAY);
		}
	}
}

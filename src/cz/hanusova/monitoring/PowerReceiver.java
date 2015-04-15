package cz.hanusova.monitoring;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import cz.hanusova.monitoring.model.Constants;
import cz.hanusova.monitoring.service.impl.Utils;

public class PowerReceiver extends BroadcastReceiver {
	private Context ctx;
	private boolean isConnected;

	/**
	 * Po dobe definovane ve tride {@link Constants} spusti {@link SosActivity}
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		ctx = context;

		if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
			System.out.println("connected");
			isConnected = true;
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (isConnected) {
						System.out.println("still connected");
						Utils.startSosActivity(ctx,
								Constants.POWER_ALARM_DELAY,
								Constants.POWER_SMS_DELAY);
					}
				}
			}, Constants.POWER_DELAY);
		}
		if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
			isConnected = false;
			System.out.println("Disconnected");
		}
	}
}

package cz.hanusova.monitoring;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

	/**
	 * Po zapnuti telefonu spusti {@link MonitoringService}
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context, MonitoringService.class);
		context.startService(i);
	}
}

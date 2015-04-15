package cz.hanusova.monitoring.service.impl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import cz.hanusova.monitoring.R;
import cz.hanusova.monitoring.SosActivity;

public final class Utils {

	/**
	 * Spusti {@link SosActivity} se zadanymi parametry
	 * 
	 * @param ctx
	 *            Aktualni {@link Context}
	 * @param alarmDelay
	 *            Doba od spusteni activity do zapnuti alarmu
	 * @param smsDelay
	 *            Doba od spusteni activity do odeslani SMS na zadane cislo
	 */
	public static void startSosActivity(Context ctx, int alarmDelay,
			int smsDelay) {
		Intent i = new Intent(ctx, SosActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		i.setAction(Intent.ACTION_MAIN);
		i.putExtra(SosActivity.KEY_ALARM_DELAY, alarmDelay);
		i.putExtra(SosActivity.KEY_SMS_DELAY, smsDelay);
		ctx.startActivity(i);
	}

	/**
	 * Najde v pameti telefonni cislo, ktere si uzivatel ulozil
	 * 
	 * @param ctx
	 *            Aktualni {@link Context}
	 * @return <code>String</code> hodnotu telefonniho cisla. Pokud ho v pameti
	 *         nenajde, vrati null
	 */
	public static String getPhoneNumber(Context ctx) {
		SharedPreferences settings = ctx.getSharedPreferences(
				ctx.getString(R.string.app_name), Context.MODE_PRIVATE);
		return settings.getString(ctx.getString(R.string.phone_number), null);
	}

}

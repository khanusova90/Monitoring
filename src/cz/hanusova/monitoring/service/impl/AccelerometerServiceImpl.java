package cz.hanusova.monitoring.service.impl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.telephony.SmsManager;
import cz.hanusova.monitoring.model.Constants;
import cz.hanusova.monitoring.service.AccelerometerService;

public class AccelerometerServiceImpl implements AccelerometerService {
	private Context ctx;

	private double dx;
	private double dy;
	private double dz;
	private double total;

	public AccelerometerServiceImpl(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
			return;
		dx = event.values[0];
		dy = event.values[1];
		dz = event.values[2];
		total = Math.sqrt(dx * dx + dy * dy + dz * dz);

		if (isAccLow() || isAccHigh()) {
			if (isAccLow()) {
				String number = Utils.getPhoneNumber(ctx);
				SmsManager manager = SmsManager.getDefault();
				manager.sendTextMessage(number, null, "Zrychlení < 1,5. dy = "
						+ dx + ", dy = " + dy + ", dz = " + dz + ". Celkove: "
						+ total, null, null);
				System.out.println("AccLow");
			}
			if (isAccHigh()) {
				String number = Utils.getPhoneNumber(ctx);
				SmsManager manager = SmsManager.getDefault();
				manager.sendTextMessage(number, null, "Zrychlení > 17. dy = "
						+ dx + ", dy = " + dy + ", dz = " + dz + ". Celkove: "
						+ total, null, null);
				System.out.println("AccHigh");
			}
			// TODO: smazat
			System.out.println("Rychlost XXXX: " + dx);
			System.out.println("Rychlost YYYY: " + dy);
			System.out.println("Rychlost ZZZZ: " + dz);
			System.out.println("Celkové zrychlení: " + total);

			Utils.startSosActivity(ctx, Constants.FALL_ALARM_DELAY,
					Constants.FALL_SMS_DELAY);
		}
	}

	/**
	 * Zjisti, jestli dochazi k padu - sleduje, jestli se zrychleni celkove i na
	 * vsech osach blizi nule (na zadne z os neni gravitacni zrychleni)
	 * 
	 * @return True, pokud zarizeni pada
	 */
	private boolean isAccLow() {

		return (total < 1.5 && dx < 1.5 && dy < 1.5 && dz < 1.5);
	}

	/**
	 * Zjisti, jestli dochazi k padu. Zjisti, jestli se celkove zrychleni
	 * priblizuje dvojnasobku hodnoty gravitacniho zrychleni a alespon na jedne
	 * z os dochazi k zapornemu zrychleni (zarizeni je otocene)
	 * 
	 * @return True, pokud zarizeni pada
	 */
	private boolean isAccHigh() {
		return (total > 17 && (dx < 0 || dy < 0 || dz < 0));
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

}

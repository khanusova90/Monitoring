package cz.hanusova.monitoring.service.impl;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import cz.hanusova.monitoring.SosActivity;
import cz.hanusova.monitoring.service.AccelerometerService;

public class AccelerometerServiceImpl implements AccelerometerService {
	private Context ctx;

	public AccelerometerServiceImpl(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
			return;
		double dx = event.values[0];
		double dy = event.values[1];
		double dz = event.values[2];
		double total = Math.sqrt(dx * dx + dy * dy + dz * dz);

		if (total < 2 && dx < 2 && dy < 2 && dz < 2) {
			System.out.println("Rychlost XXXX: " + dx);
			System.out.println("Rychlost YYYY: " + dy);
			System.out.println("Rychlost ZZZZ: " + dz);
			System.out.println("Celkové zrychlení: " + total);

			Intent intent = new Intent(ctx.getApplicationContext(),
					SosActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.setAction(Intent.ACTION_MAIN);
			ctx.startActivity(intent);
		}

		if (total < 1 && !(dx < 1 && dy < 1 && dz < 1)) {
			System.out.println("VOLNY PAD ACC!");
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

}

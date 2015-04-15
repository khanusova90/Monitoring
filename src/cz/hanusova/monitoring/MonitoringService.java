package cz.hanusova.monitoring;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import cz.hanusova.monitoring.service.AccelerometerService;
import cz.hanusova.monitoring.service.impl.AccelerometerServiceImpl;

public class MonitoringService extends Service {

	private SensorManager sManager;
	private Sensor accelerometer;
	private AccelerometerService sListener;

	@Override
	public void onCreate() {
		super.onCreate();
		sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sListener = new AccelerometerServiceImpl(getApplicationContext());
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		sManager.registerListener(sListener, accelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		System.out.println("Monitoring service destroyed");

		sManager.unregisterListener(sListener);
		super.onDestroy();
	}
}

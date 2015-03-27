package cz.hanusova.monitoring;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import cz.hanusova.monitoring.service.impl.AccelerometerServiceImpl;

public class MonitoringService extends Service {

	public static boolean MONITOR = false;

	private SensorManager sManager;
	private Sensor accelerometer;
	private SensorEventListener sListener;

	@Override
	public void onCreate() {
		System.out.println("Monitoring service created");

		super.onCreate();
		sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sListener = new AccelerometerServiceImpl(getApplicationContext());
	}

	// Pokud se spusti zde, skonci spolu s aktivitou, ktera ji vytvorila
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	// Zavola se z Activity pomoci startService(intent), musi se sama ukoncit
	// pomoci
	// stopSelf() nebo odjinud pomoci stopService()
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("Monitoring service started");

		MONITOR = true;
		sManager.registerListener(sListener, accelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		System.out.println("Monitoring service destroyed");

		MONITOR = false;
		sManager.unregisterListener(sListener);
		super.onDestroy();
	}
}

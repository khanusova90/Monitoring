package cz.hanusova.monitoring;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import cz.hanusova.monitoring.service.AccelerometerService;

public class SledovaniPozice extends ActionBarActivity implements
		AccelerometerService {

	TextView dx;
	TextView dy;
	TextView dz;
	TextView celkem;

	double actX = 0;
	double actY = 0;
	double actZ = 0;
	double actCelk = 0;

	private SensorManager sManager;
	private Sensor accelerometer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pozice);

		sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sManager.registerListener(this, accelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);

		dx = (TextView) findViewById(R.id.dx);
		dy = (TextView) findViewById(R.id.dy);
		dz = (TextView) findViewById(R.id.dz);
		celkem = (TextView) findViewById(R.id.celkem);

		dx.setText("Zrychleni x: ");
		dy.setText("Zrychleni y: ");
		dz.setText("Zrychleni z: ");
		celkem.setText("Zrychleni celkem");
	}

	private static final float g = SensorManager.GRAVITY_EARTH;

	// private double dy;
	// private Context ctx;
	//
	// public AccelerometerServiceImpl(Context ctx) {
	// this.ctx = ctx;
	// }

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
			return;
		// double dx = event.values[0] - g;
		// double dy = event.values[1] - g;
		// double dz = event.values[2] - g;

		// if (dx > 3 || dy > 3 || dz > 3) {
		// System.out.println("Rychlost XXXX: " + dx);
		// System.out.println("Rychlost YYYY: " + dy);
		// System.out.println("Rychlost ZZZZ: " + dz);

		// Intent intent = new Intent(ctx.getApplicationContext(),
		// SosActivity.class);
		// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// intent.addCategory(Intent.CATEGORY_LAUNCHER);
		// intent.setAction(Intent.ACTION_MAIN);
		// ctx.startActivity(intent);
		// }

		double puvX = actX;
		double puvY = actY;
		double puvZ = actZ;
		double puvCelk = actCelk;

		actX = event.values[0];
		actY = event.values[1];
		actZ = event.values[2];
		actCelk = Math.sqrt(actX * actX + actY * actY + actZ * actZ);

		dx.setText(String.valueOf(actX));
		dy.setText(String.valueOf(actY));
		dz.setText(String.valueOf(actZ));

		celkem.setText(String.valueOf(actCelk));
		//
		// zmenaX.setText(String.valueOf(puvX - actX));
		// zmenaY.setText(String.valueOf(puvY - actY));
		// zmenaZ.setText(String.valueOf(puvZ - actZ));
		// zmenaCelk.setText(String.valueOf(puvCelk - actCelk));

		// if (puvCelk - actCelk > 2 || puvCelk - actCelk < -2) {
		// System.out.println("Změna: " + (puvCelk - actCelk));
		// System.out.println("Celkové zrychlení: " + actCelk);
		// }
		//
		// if (actCelk < 1) {
		// System.out.println("VOLNÝ PÁD!!!!");
		// }
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		System.out.println("accuracy changed. Sensor: " + sensor.getType()
				+ " accuracy: " + accuracy);
	}

}

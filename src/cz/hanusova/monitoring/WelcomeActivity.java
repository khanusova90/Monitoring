package cz.hanusova.monitoring;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cz.hanusova.monitoring.service.impl.Utils;

public class WelcomeActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_view);
		monitor();
		displayNumber();
	}

	/**
	 * Ulozi telefonni cislo do pameti
	 * 
	 * @param button
	 */
	public void saveNumber(View button) {
		String number = getText();

		SharedPreferences settings = getApplicationContext()
				.getSharedPreferences(getString(R.string.app_name),
						Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(getString(R.string.phone_number), number);
		editor.commit();

		displayNumber();
	}

	/**
	 * Ziska telefonni cislo z uzivatelskeho vstupu
	 * 
	 * @return <code>String</code> hodnotu telefonniho cisla
	 */
	private String getText() {
		EditText numberInput = (EditText) findViewById(R.id.edit_number);
		return numberInput.getText().toString();
	}

	/**
	 * Zobrazi telefonni cislo na displeji
	 * 
	 * @param tv
	 *            {@link TextView}, ktere zobrazi cislo
	 */
	private void displayNumber() {
		TextView tv = (TextView) findViewById(R.id.number_view);
		String number = Utils.getPhoneNumber(getApplicationContext());
		if (number != null) {
			String text = getString(R.string.actual_phone, number);
			tv.setText(text);
		}
	}

	/**
	 * Spusti sledovani padu
	 */
	private void monitor() {
		Intent intent = new Intent(this, MonitoringService.class);
		startService(intent);
	}
}

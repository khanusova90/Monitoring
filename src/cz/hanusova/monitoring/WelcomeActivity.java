package cz.hanusova.monitoring;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cz.hanusova.monitoring.service.NumberService;
import cz.hanusova.monitoring.service.impl.NumberServiceImpl;

public class WelcomeActivity extends ActionBarActivity {

	private NumberService numberService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_view);
		numberService = new NumberServiceImpl(getApplicationContext());
		monitor();

		displayNumber();
	}

	/**
	 * Ulozi telefonni cislo do pameti
	 * 
	 * @param number
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
	 *            - {@link TextView}, ktere zobrazi cislo
	 */
	private void displayNumber() {
		TextView tv = (TextView) findViewById(R.id.number_view);
		String number = numberService.getNumber();
		if (number != null) {
			String text = getString(R.string.actual_phone, number);
			tv.setText(text);
		}
	}

	// /**
	// * Vyhleda v pameti telefonni cislo
	// *
	// * @return <code>String</code> hodnotu telefonniho cisla. Pokud v pameti
	// * jeste neni ulozeno, vrati <code>null</code>
	// */
	// private String getNumber() {
	// SharedPreferences settings = getApplicationContext()
	// .getSharedPreferences(getString(R.string.app_name),
	// Context.MODE_PRIVATE);
	// return settings.getString(getString(R.string.phone_number), null);
	// }

	// TODO: smazat
	public void startSos(View button) {
		Intent intent = new Intent(this, SosActivity.class);
		startActivity(intent);
	}

	public void startZrychleni(View button) {
		Intent intent = new Intent(this, SledovaniPozice.class);
		startActivity(intent);
	}

	private void monitor() {
		Intent intent = new Intent(this, MonitoringService.class);
		startService(intent);
	}
}
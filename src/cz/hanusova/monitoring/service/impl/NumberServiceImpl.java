package cz.hanusova.monitoring.service.impl;

import android.content.Context;
import android.content.SharedPreferences;
import cz.hanusova.monitoring.R;
import cz.hanusova.monitoring.service.NumberService;

public class NumberServiceImpl implements NumberService {
	Context ctx;

	public NumberServiceImpl(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	public String getNumber() {
		SharedPreferences settings = ctx.getSharedPreferences(
				ctx.getString(R.string.app_name), Context.MODE_PRIVATE);
		return settings.getString(ctx.getString(R.string.phone_number), null);
	}

}

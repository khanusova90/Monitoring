package cz.hanusova.monitoring.service.impl;

import cz.hanusova.monitoring.SosActivity;

/**
 * Trida slouzici k ulozeni konstant
 */
public final class Constants {

	/**
	 * Vychozi doba v ms od padu telefonu do odeslani SMS a spusteni alarmu pro
	 * pripad, ze dojde k chybe programu a spatne se nastavi tyto hodnoty
	 */
	public static final int DEFAULT_DELAY = 30000;

	/**
	 * Doba od zapnuti {@link SosActivity} po spusteni alarmu behem nabijeni
	 * telefonu
	 */
	public static final int POWER_ALARM_DELAY = 0;

	/**
	 * Doba od zapnuti {@link SosActivity} po odeslani SMS behem nabijeni
	 * telefonu
	 */
	public static final int POWER_SMS_DELAY = 1000 * 60 * 5;

	/**
	 * Doba od padu telefonu po spusteni alarmu
	 */
	public static final int FALL_ALARM_DELAY = 40000;

	/**
	 * Doba od padu telefonu po odeslani SMS
	 */
	public static final int FALL_SMS_DELAY = 1000 * 60 * 2;

	/**
	 * Doba od zapojeni telefonu do spusteni {@link SosActivity}
	 */
	public static final int POWER_DELAY = 1000 * 60 * 60 * 10;

	/**
	 * Tisnova linka
	 */
	public static final int SOS_NUMBER = 112;

	/**
	 * SMS zprava, ktera se zasle v pripade padu na predvolene cislo
	 */
	public static final String SMS_MESSAGE = "Pomoc, neco se mi stalo! ";

	/**
	 * Tag pro zobrazeni logu
	 */
	public static final String LOG_TAG = "Monitoring app";
}

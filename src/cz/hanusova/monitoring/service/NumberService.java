package cz.hanusova.monitoring.service;

public interface NumberService {

	/**
	 * Vyhleda v pameti telefonni cislo
	 * 
	 * @return <code>String</code> hodnotu telefonniho cisla. Pokud v pameti
	 *         jeste neni ulozeno, vrati <code>null</code>
	 */
	public String getNumber();

}

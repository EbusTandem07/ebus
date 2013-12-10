/* BOCountry.java
***********************************************************************************
* 20.03.2007 ** tdi
* - created
*
***********************************************************************************
* Copyright 2007-2010 HTWG Konstanz
* 
* Prof. Dr.-Ing. Juergen Waesch
* Dipl. -Inf. (FH) Thomas Dietrich
* Fakultaet Informatik - Department of Computer Science
* E-Business Technologien 
* 
* Hochschule Konstanz Technik, Wirtschaft und Gestaltung
* University of Applied Sciences
* Brauneggerstrasse 55
* D-78462 Konstanz
* 
* E-Mail: juergen.waesch(at)htwg-konstanz.de
************************************************************************************/
package de.htwg_konstanz.ebus.framework.wholesaler.api.bo;

import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.CountryBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.CurrencyBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Country;


/**
* The business object {@link BOCountry} represents a country entity with it's common attributes
* like name, isoCode or currency.
* 
* The following example code shows how to create and persist a new country entity. 
* <pre id="example">
* // load a existing currency
* {@link BOCurrency} currency = {@link CurrencyBOA}.getInstance().findCurrency(TEST_COUNTRY_CURRENCY_CODE);
*	
* // ensure that the currency exists
* if (currency != null)
* {
*	// create the country
*	{@link BOCountry} country = new BOCountry();
*	country.setIsocode(TEST_COUNTRY_ISOCODE);
*	country.setName(TEST_COUNTRY_NAME);
*	country.setCurrency(currency);
*	{@link CountryBOA}.getInstance().saveOrUpdate(country);
* }
* </pre>
* 
* @author tdi
*/
public class BOCountry
{
	public Country country = null;
	
	
	public BOCountry()
	{
		this.country = new Country();
	}

	public BOCountry(Country country)
	{
		this.country = country;
	}
	
	public BOCurrency getCurrency()
	{
		return new BOCurrency(country.getCurrency());
	}

	public void setCurrency(BOCurrency currency)
	{
		country.setCurrency(currency.currency);
	}

	public String getIsocode()
	{
		return country.getIsocode();
	}

	public void setIsocode(String isocode)
	{
		country.setIsocode(isocode);
	}

	public String getName()
	{
		return country.getName();
	}

	public void setName(String name)
	{
		country.setName(name);
	}
}

/* CountryBOA.java
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
package de.htwg_konstanz.ebus.framework.wholesaler.api.boa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCountry;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCurrency;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Country;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.CountryVOA;


/**
* The {@link CountryBOA} provides common CRUD operations (Create Read Update Delete) for the country entity.<p> 
* 
* The business object {@link BOCountry} represents a country entity with it's common attributes
* like name, isoCode or currency.<p>
* 
* The following example code shows how to <b>create and persist a new country entity</b> using the {@link CountryBOA}. 
* <pre id="example">
* // load a existing currency
* {@link BOCurrency} currency = {@link CurrencyBOA}.getInstance().findCurrency(TEST_COUNTRY_CURRENCY_CODE);
* if (currency != null)
* {	 	
* 	// create the country
*	{@link BOCountry} country = new BOCountry();
*	country.setIsocode(TEST_COUNTRY_ISOCODE);
*	country.setName(TEST_COUNTRY_NAME);
*	country.setCurrency(currency);
*	{@link CountryBOA}.getInstance().saveOrUpdate(country);
* }
</pre>
* 
* The following example code shows how to <b>load an existing country entity</b>, using the {@link CountryBOA}. 
* <pre id="example">
* // load the country
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(TEST_COUNTRY_ISOCODE);
* </pre>
* 
* The following example code shows how to <b>load and update an existing country entity</b>, using the {@link CountryBOA}. 
* <pre id="example">
* // load the country
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(TEST_COUNTRY_ISOCODE);
* if (country != null)
* {	 	
* 	// change the country name 
* 	country.setName(TEST_COUNTRY_UPDATED_NAME);
* 	{@link CountryBOA}.getInstance().saveOrUpdate(country);
* }
* </pre>
* 
* The following example code shows how to <b>delete an existing country entity</b>, using the {@link CountryBOA}. 
* <pre id="example">
* // load the country
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(TEST_COUNTRY_ISOCODE);
* if (country != null)
* {	 	
*	// delete the country
*	{@link CountryBOA}.getInstance().delete(country);
* }
* </pre>
* 
* @author tdi
*/
public class CountryBOA
{
	private static CountryBOA instance = null;


	private CountryBOA()
	{
		super();
	}

	public static CountryBOA getInstance()
	{
		if (instance == null)
		{
			synchronized (CountryBOA.class)
			{
				if (instance == null)
				{
					instance = new CountryBOA();
				}
			}
		}

		return instance;
	}

	public BOCountry findCountry(String isocode)
	{
		CountryVOA voa = new CountryVOA();

		Country country = voa.get(isocode);
		
		if (country == null)
			return null;
		
		return new BOCountry(country);
	}
	
	@SuppressWarnings("unchecked")
   public List<BOCountry> findAll()
	{
		List<BOCountry> countries = new ArrayList<BOCountry>();
		List<Country> tempCountries = CountryVOA.getInstance().findAll();
		for (Iterator iter = tempCountries.iterator(); iter.hasNext();)
		{
			countries.add(new BOCountry((Country)iter.next()));		
		}
		
		return countries;
	}
	
   /**
    * Save or Update a given BO. If the given BO does exist, it will be updated (overwritten) otherwise it will be created.  
    * 
    * @param country an country to save or update
    * @author tdi
    */
	public void saveOrUpdate(BOCountry country)
	{
		CountryVOA.getInstance().saveOrUpdate(country.country);
	}

	public void delete(BOCountry country)
	{
		CountryVOA.getInstance().delete(country.country);
	}
}

/* CurrencyBOA.java
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

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCurrency;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Currency;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.CurrencyVOA;


/**
* The {@link CurrencyBOA} provides common CRUD operations (Create Read Update Delete) for the currency entity.<p> 
* 
* The business object {@link BOCurrency} represents a currency entity with it's common attributes
* like code and description.<p>
* 
* The following example code shows how to <b>create and persist a new currency entity</b> using the {@link CurrencyBOA}. 
* <pre id="example">
* // create the currency
* {@link BOCurrency} currency = new BOCurrency();
* currency.setCode(TEST_CURRENCY_CODE);
* currency.setDescription(TEST_CURRENCY_DESCR);
* {@link CurrencyBOA}.getInstance().saveOrUpdate(currency);
* </pre>
* 
* The following example code shows how to <b>load an existing currency entity</b>, using the {@link CurrencyBOA}. 
* <pre id="example">
* // load the currency
* {@link BOCurrency} currency = {@link CurrencyBOA}.getInstance().findCurrency(TEST_CURRENCY_CODE);
* </pre>
* 
* The following example code shows how to <b>load and update an existing currency entity</b>, using the {@link CurrencyBOA}. 
* <pre id="example">
* // load the currency
* {@link BOCurrency} currency = {@link CurrencyBOA}.getInstance().findCurrency(TEST_CURRENCY_CODE);
* if (currency != null)
* {	 	
* 	// change the currency description
* 	currency.setDescription(TEST_CURRENCY_UPDATED_DESCR);
* 	{@link CurrencyBOA}.getInstance().saveOrUpdate(currency);
* }
* </pre>
* 
* The following example code shows how to <b>delete an existing currency entity</b>, using the {@link CurrencyBOA}. 
* <pre id="example">
* // load the currency
* {@link BOCurrency} currency = {@link CurrencyBOA}.getInstance().findCurrency(TEST_CURRENCY_CODE);
* if (currency != null)
* {	 	
* 	// delete the currency
*	{@link CurrencyBOA}.getInstance().delete(currency);
* }
* </pre>
* 
* @author tdi
*/
public class CurrencyBOA
{
	private static CurrencyBOA instance = null;


	private CurrencyBOA()
	{
		super();
	}

	public static CurrencyBOA getInstance()
	{
		if (instance == null)
		{
			synchronized (CurrencyBOA.class)
			{
				if (instance == null)
				{
					instance = new CurrencyBOA();
				}
			}
		}

		return instance;
	}

	public BOCurrency findCurrency(String code)
	{
		CurrencyVOA voa = new CurrencyVOA();
		Currency currency = voa.get(code);
		
		if (currency == null)
			return null;
		
		return new BOCurrency(currency);
	}
	
	@SuppressWarnings("unchecked")
   public List<BOCurrency> findAll()
	{
		List<BOCurrency> currencies = new ArrayList<BOCurrency>();
		List<Currency> tempCurrencies = CurrencyVOA.getInstance().findAll();
		for (Iterator iter = tempCurrencies.iterator(); iter.hasNext();)
		{
			currencies.add(new BOCurrency((Currency)iter.next()));		
		}
		
		return currencies;
	}
	
   /**
    * Save or Update a given BO. If the given BO does exist, it will be updated (overwritten) otherwise it will be created.  
    * 
    * @param currency an currency to save or update
    * @author tdi
    */
	public void saveOrUpdate(BOCurrency currency)
	{
		CurrencyVOA.getInstance().saveOrUpdate(currency.currency);
	}

	public void delete(BOCurrency currency)
	{
		CurrencyVOA.getInstance().delete(currency.currency);
	}
}

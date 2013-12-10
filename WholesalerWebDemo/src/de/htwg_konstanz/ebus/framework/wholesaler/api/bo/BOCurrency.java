/* BOCurrency.java
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

import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.CurrencyBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Currency;


/**
* The business object {@link BOCurrency} represents a currency entity with it's common attributes
* like code and description.
* 
* The following example code shows how to create and persist a new currency entity. 
* <pre id="example">
* // create the currency
* {@link BOCurrency} currency = new BOCurrency();
* currency.setCode(TEST_CURRENCY_CODE);
* currency.setDescription(TEST_CURRENCY_DESCR);
* {@link CurrencyBOA}.getInstance().saveOrUpdate(currency);
* </pre>
* 
* @author tdi
*/
public class BOCurrency
{
	public Currency currency = null;

	
	public BOCurrency()
	{
		this.currency = new Currency();
	}

	public BOCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public String getCode()
	{
		return currency.getCode();
	}

	public void setCode(String code)
	{
		currency.setCode(code);
	}

	public String getDescription()
	{
		return currency.getDescription();
	}

	public void setDescription(String description)
	{
		currency.setDescription(description);
	}
}

/* BOAddress.java
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

import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.AddressBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.CountryBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Address;

/**
* The business object {@link BOAddress} represents a address entity with it's common attributes
* like street, zipcode, city or country.
* 
* The following example code shows how to create and persist a new address entity. 
* <pre id="example">
* // load a existing country to build the address
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(TEST_ADDRESS_COUNTRY_ISOCODE);
* if(country == null)
* {	 
*	// do something...
*	// e.g. leave the procedure or throw an exception...
* }
*	
* // create the address which must be unique (addresses could not be shared)
* {@link BOAddress} address = new BOAddress();
* address.setStreet(TEST_ADDRESS_STREET);
* address.setZipcode(TEST_ADDRESS_ZIPCODE);
* address.setCity(TEST_ADDRESS_CITY);
* address.setCountry(country);
* {@link AddressBOA}.getInstance().saveOrUpdate(address);
*
* // Important! 
* // The id of the new address object was automatically assigned by the OR-Mapper.
* // So, if you need this id for further use, keep it as a local variable (as shown in the next line). 
* addressId = address.getId();
* </pre>
* 
* @author tdi
*/
public class BOAddress
{
	public Address address = null;
	
	
	public BOAddress()
	{
		this.address = new Address();
	}

	public BOAddress(Address address)
	{
		this.address = address;
	}
	
	public String getCity()
	{
		return address.getCity();
	}

	public void setCity(String city)
	{
		address.setCity(city);
	}

	public BOCountry getCountry()
	{
		return new BOCountry(address.getCountry());
	}

	public void setCountry(BOCountry country)
	{
		address.setCountry(country.country);
	}

	public String getId()
	{
		return address.getId();
	}

	public void setId(String id)
	{
		address.setId(id);
	}

	public String getStreet()
	{
		return address.getStreet();
	}

	public void setStreet(String street)
	{
		address.setStreet(street);
	}

	public String getZipcode()
	{
		return address.getZipcode();
	}

	public void setZipcode(String zipcode)
	{
		address.setZipcode(zipcode);
	}
}

/* BOUserInternal.java
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
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.UserBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.UserInternal;


/**
* The business object {@link BOUserInternal} represents a user internal entity with it's common attributes
* like name, login, address or email.<p>
* 
* The following example code shows how to create and persists an user internal entity. 
* <pre id="example">
* // first of all load a existing country to build the address
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(TEST_USER_INTERNAL_COUNTRY_ISOCODE);
* if(country == null)
* {	 
*	// do something...
*	// e.g. leave the procedure or throw an exception...
* }
* 
* // second, create a address which must be unique for each user (addresses could not be shared)
* {@link BOAddress} address = new BOAddress();
* address.setStreet(TEST_USER_INTERNAL_STREET);
* address.setZipcode(TEST_USER_INTERNAL_ZIPCODE);
* address.setCity(TEST_USER_INTERNAL_CITY);
* address.setCountry(country);
* {@link AddressBOA}.getInstance().saveOrUpdate(address);
* 
* // third, create and save the test customer
* {@link BOUserInternal} userInternal = new BOUserInternal();
* userInternal.setLogin(TEST_USER_INTERNAL_LOGIN);
* userInternal.setFirstname(TEST_USER_INTERNAL_FIRSTNAME);
* userInternal.setLastname(TEST_USER_INTERNAL_LASTNAME);
* userInternal.setAddress(address);
* {@link UserBOA}.getInstance().saveOrUpdate(userInternal);
* 
* // Important! 
* // The userid of the new user internal object was automatically assigned by the OR-Mapper.
* // So, if you need this userid for further use, keep it as a local variable (as shown in the next line). 
* userId = userInternal.getId();
* </pre>
* 
* @author tdi
*/
 public class BOUserInternal extends BOBaseUser
{
	public BOUserInternal()
	{
		this.user = new UserInternal();
	}
	
	public BOUserInternal(UserInternal userInternal)
	{
		this.user = userInternal;
	}
	
	public UserInternal getUserInternal()
	{
		return (UserInternal)user;
	}

	public String getRole()
	{
		return "internal";
	}
}

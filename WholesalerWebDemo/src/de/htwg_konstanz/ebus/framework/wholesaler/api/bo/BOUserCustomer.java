/* BOUserCustomer.java
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
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.CustomerBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.UserBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.UserCustomer;


/**
* The business object {@link BOUserCustomer} represents a user customer entity with it's common attributes
* like name, login, address or email.<p>
* 
* The following example code shows how to create and persists an user customer entity. 
* <pre id="example">
* // first of all load a existing country to build the address
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(TEST_USER_CUSTOMER_COUNTRY_ISOCODE);
* if(country == null)
* {	 
*	// do something...
*	// e.g. leave the procedure or throw an exception...
* }
*
* // second, create a address which must be unique for each user (addresses could not be shared)
* {@link BOAddress} address = new BOAddress();
* address.setStreet(TEST_USER_CUSTOMER_STREET);
* address.setZipcode(TEST_USER_CUSTOMER_ZIPCODE);
* address.setCity(TEST_USER_CUSTOMER_CITY);
* address.setCountry(country);
* {@link AddressBOA}.getInstance().saveOrUpdate(address);
*
* // third, load a existing customer
* {@link BOCustomer} customer = {@link CustomerBOA}.getInstance().getCustomerById(TEST_CUSTOMER_ID);
* if(customer == null)
* {	 
*	// do something...
*	// e.g. leave the procedure or throw an exception...
* }
* 
* // fourth, create and save the test customer
* {@link BOUserCustomer} userCustomer = new BOUserCustomer();
* userCustomer.setLogin(TEST_USER_CUSTOMER_LOGIN);
* userCustomer.setFirstname(TEST_USER_CUSTOMER_FIRSTNAME);
* userCustomer.setLastname(TEST_USER_CUSTOMER_LASTNAME);
* userCustomer.setAddress(address);
* userCustomer.setOrganization(customer);
* {@link UserBOA}.getInstance().saveOrUpdate(userCustomer);
* 
* // Important! 
* // The userid of the new user customer object was automatically assigned by the OR-Mapper.
* // So, if you need this userid for further use, keep it as a local variable (as shown in the next line). 
* userId = userCustomer.getId();
* </pre>
* 
* @author tdi
*/
public class BOUserCustomer extends BOBaseUser
{
	public BOUserCustomer()
	{
		this.user = new UserCustomer();
	}

	public BOUserCustomer(UserCustomer userCustomer)
	{
		this.user = userCustomer;
	}
	
	public UserCustomer getUserCustomer()
	{
		return (UserCustomer)user;
	}
	
	public BOCustomer getOrganization()
	{
		return new BOCustomer(((UserCustomer)user).getCustomer());
	}

	public void setOrganization(BOCustomer organization)
	{
		((UserCustomer)user).setCustomer(organization.customer);
	}
	
	public String getRole()
	{
		return "customer";
	}
}

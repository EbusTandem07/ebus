/* BOUserSupplier.java
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
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.SupplierBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.UserBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.UserSupplier;


/**
* The business object {@link BOUserSupplier} represents a user supplier entity with it's common attributes
* like name, login, address or email.<p>
* 
* The following example code shows how to create and persists an user supplier entity. 
* <pre id="example">
* // first of all load a existing country to build the address
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(TEST_USER_SUPPLIER_COUNTRY_ISOCODE);
* if(country == null)
* {	 
*	// do something...
*	// e.g. leave the procedure or throw an exception...
* }
* 
* // second, create a address which must be unique for each user (addresses could not be shared)
* {@link BOAddress} address = new BOAddress();
* address.setStreet(TEST_USER_SUPPLIER_STREET);
* address.setZipcode(TEST_USER_SUPPLIER_ZIPCODE);
* address.setCity(TEST_USER_SUPPLIER_CITY);
* address.setCountry(country);
* {@link AddressBOA}.getInstance().saveOrUpdate(address);
* 
* // third, load a existing supplier
* {@link BOSupplier} supplier = {@link SupplierBOA}.getInstance().getSupplierById(TEST_SUPPLIER_ID);
* if(supplier == null)
* {	 
*	// do something...
*	// e.g. leave the procedure or throw an exception...
* }
* 
* // fourth, create and save the test customer
* {@link BOUserSupplier} userSupplier = new BOUserSupplier();
* userSupplier.setLogin(TEST_USER_SUPPLIER_LOGIN);
* userSupplier.setFirstname(TEST_USER_SUPPLIER_FIRSTNAME);
* userSupplier.setLastname(TEST_USER_SUPPLIER_LASTNAME);
* userSupplier.setAddress(address);
* userSupplier.setOrganization(supplier);
* {@link UserBOA}.getInstance().saveOrUpdate(userSupplier);
* 
* // Important! 
* // The userid of the new user supplier object was automatically assigned by the OR-Mapper.
* // So, if you need this userid for further use, keep it as a local variable (as shown in the next line). 
* userId = userSupplier.getId();
* </pre>
* 
* @author tdi
*/
public class BOUserSupplier extends BOBaseUser
{
	public BOUserSupplier()
	{
		this.user = new UserSupplier();
	}

	public BOUserSupplier(UserSupplier userSupplier)
	{
		this.user = userSupplier;
	}
	
	public UserSupplier getUserSupplier()
	{
		return (UserSupplier)user;
	}
	
	public BOSupplier getOrganization()
	{
		BOSupplier supplier = new BOSupplier();
		supplier.setCompanyname(((UserSupplier)user).getSupplier().getCompanyname());
		supplier.setFirstname(((UserSupplier)user).getSupplier().getFirstname());
		supplier.setLastname(((UserSupplier)user).getSupplier().getLastname());
		supplier.setAddress(new BOAddress(((UserSupplier)user).getSupplier().getAddress()));
		
		return supplier;
	}

	public void setOrganization(BOSupplier organization)
	{
		((UserSupplier)user).setSupplier(organization.supplier);
	}

	public String getRole()
	{
		return "supplier";
	}
}

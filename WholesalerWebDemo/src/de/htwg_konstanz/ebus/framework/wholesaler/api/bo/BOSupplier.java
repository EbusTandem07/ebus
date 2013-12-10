/* BOSupplier.java
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
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Supplier;


/**
* The business object {@link BOSupplier} represents a supplier entity with it's common attributes
* like supplierNumber, name, address or company.<p>
* 
* The following example code shows how to create and persists an supplier entity. 
* <pre id="example">
* // load a existing country to build the supplier address
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(TEST_SUPPLIER_COUNTRY_ISOCODE);
* if(country == null)
* {	 
*	// do something...
*	// e.g. leave the procedure or throw an exception...
* }
* 
* // create a address which must be unique for each supplier (addresses could not be shared)
* {@link BOAddress} address = new BOAddress();
* address.setStreet(TEST_SUPPLIER_STREET);
* address.setZipcode(TEST_SUPPLIER_ZIPCODE);
* address.setCity(TEST_SUPPLIER_CITY);
* address.setCountry(country);
* {@link AddressBOA}.getInstance().saveOrUpdate(address);
* 
* // create the supplier
* {@link BOSupplier} supplier = new BOSupplier();
* supplier.setAddress(address);
* supplier.setFirstname(TEST_SUPPLIER_FIRST_NAME);
* supplier.setLastname(TEST_SUPPLIER_LAST_NAME);
* supplier.setCompanyname(TEST_SUPPLIER_COMPANY_NAME);
* {@link SupplierBOA}.getInstance().saveOrUpdate(supplier);
* 	
* // Important! 
* // The supplierNumber of the new supplier object was automatically assigned by the OR-Mapper.
* // So, if you need this supplierNumber for further use, keep it as a local variable (as shown in the next line). 
* supplierNumber = supplier.getSupplierNumber();
* </pre>
* 
* @author tdi
*/
public class BOSupplier implements IBOOrganization
{
	public Supplier supplier = null;
	
	
	public BOSupplier()
	{
		this.supplier = new Supplier();
		supplier.setSuppliernumber(String.valueOf(System.currentTimeMillis()));
	}

	public BOSupplier(Supplier supplier)
	{
		this.supplier = supplier;
	}

	public String getSupplierNumber()
	{
		return supplier.getSuppliernumber();
	}
	
	public void setSupplierNumber(String supplierNumber)
	{
		supplier.setSuppliernumber(supplierNumber);
	}
	
	public BOAddress getAddress()
	{
		return new BOAddress(supplier.getAddress());
	}

	public void setAddress(BOAddress address)
	{
		supplier.setAddress(address.address);	
	}

	public String getCompanyname()
	{
		return supplier.getCompanyname();
	}

	public void setCompanyname(String companyname)
	{
		supplier.setCompanyname(companyname);
	}

	public String getFirstname()
	{
		return supplier.getFirstname();
	}

	public void setFirstname(String firstname)
	{
		supplier.setFirstname(firstname);
	}

	public String getLastname()
	{
		return supplier.getLastname();
	}

	public void setLastname(String lastname)
	{
		supplier.setLastname(lastname);
	}

	public String getRemark()
	{
		return supplier.getRemark();
	}

	public void setRemark(String remark)
	{
		supplier.setRemark(remark);
	}

	public void setWsUserName(String wsUserName)
   {
	   supplier.setWsUserName(wsUserName);
   }

   public String getWsUserName()
   {
      return supplier.getWsUserName();
   }
   
   public void setWsPassword(String wsPassword)
   {
      supplier.setWsPassword(wsPassword);
   }

   public String getWsPassword()
   {
      return supplier.getWsPassword();
   }
   
   public String getWsCatalogEndpoint() 
   {
      return supplier.getWsCatalogEndpoint();
   }

   public void setWsCatalogEndpoint(String wsCatalogEndpoint) 
   {
      supplier.setWsCatalogEndpoint(wsCatalogEndpoint);
   }

   public String getWsOrderEndpoint() 
   {
      return supplier.getWsOrderEndpoint();
   }

   public void setWsOrderEndpoint(String wsOrderEndpoint) 
   {
      supplier.setWsOrderEndpoint(wsOrderEndpoint);
   }
}

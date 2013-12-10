/* SupplierBOA.java
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

import org.hibernate.Criteria;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOAddress;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCountry;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOSupplier;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Supplier;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.SupplierVOA;


/**
* The {@link SupplierBOA} provides common CRUD operations (Create Read Update Delete) for the supplier entity.<p> 
* 
* The business object {@link BOSupplier} represents a supplier entity with it's common attributes
* like supplierNumber, name, address or company.<p>
* 
* The following example code shows how to <b>create and persist a new supplier entity</b> using the {@link SupplierBOA}. 
* <pre id="example">
* // load a existing country to build the supplier address
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(TEST_SUPPLIER_COUNTRY_ISOCODE);
* if(country == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
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
* // important! store the supplier number for further use
* supplierNumber = supplier.getSupplierNumber();
* </pre>
* 
* The following example code shows how to <b>load an existing supplier entity</b>, using the SupplierBOA. 
* <pre id="example">
* // load the supplier
* {@link BOSupplier} supplier = {@link SupplierBOA}.getInstance().getSupplierById(supplierNumber);
* if(supplier == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
* </pre>
* 
* The following example code shows how to <b>load and update an existing supplier entity</b>, using the SupplierBOA. 
* <pre id="example">
* // load the supplier
* {@link BOSupplier} supplier = {@link SupplierBOA}.getInstance().getSupplierById(supplierNumber);
* if(supplier == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
*
* // change the company name 
* supplier.setCompanyname(TEST_SUPPLIER_UPDATED_COMPANY_NAME);
* {@link SupplierBOA}.getInstance().saveOrUpdate(supplier);
* </pre>
* 
* The following example code shows how to <b>delete an existing supplier entity</b>, using the SupplierBOA. 
* <pre id="example">
* // load the supplier
* {@link BOSupplier} supplier = {@link SupplierBOA}.getInstance().getSupplierById(supplierNumber);
* if(supplier == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
* 
* // delete the supplier
* {@link SupplierBOA}.getInstance().delete(supplier);
* </pre>
* 
* @author tdi
*/
public class SupplierBOA
{
	private static SupplierBOA instance = null;


	private SupplierBOA()
	{
		super();
	}

	public static SupplierBOA getInstance()
	{
		if (instance == null)
		{
			synchronized (SupplierBOA.class)
			{
				if (instance == null)
				{
					instance = new SupplierBOA();
				}
			}
		}

		return instance;
	}

	public BOSupplier findSupplierById(String supplierNumber)
	{
	   Supplier supplier = SupplierVOA.getInstance().get(supplierNumber);
		
		if (supplier == null)
			return null;
		
		return new BOSupplier(supplier);
	}
	
	@SuppressWarnings("unchecked")
   public List<BOSupplier> findAll()
	{
		List<BOSupplier> suppliers = new ArrayList<BOSupplier>();
		List<Supplier> tempSuppliers = SupplierVOA.getInstance().findAll();
		for (Iterator iter = tempSuppliers.iterator(); iter.hasNext();)
		{
			suppliers.add(new BOSupplier((Supplier)iter.next()));		
		}
		
		return suppliers;
	}

	@SuppressWarnings("unchecked")
   public List<BOSupplier> findByCompanyName(String companyName)
	{
      List<BOSupplier> suppliers = new ArrayList<BOSupplier>();
      Criteria crit = SupplierVOA.getInstance().findFilteredLike("Companyname", companyName);
      List<Supplier> tempSuppliers = (List<Supplier>)crit.list();
      
      for (Iterator iter = tempSuppliers.iterator(); iter.hasNext();)
      {
         suppliers.add(new BOSupplier((Supplier)iter.next()));    
      }
      
      return suppliers;
	}
	
	public void saveOrUpdate(BOSupplier supplier)
	{
		SupplierVOA.getInstance().saveOrUpdate(supplier.supplier);
	}

	public void delete(BOSupplier supplier)
	{
		SupplierVOA.getInstance().delete(supplier.supplier);
	}
}

/* CustomerBOA.java
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
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOAddress;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCountry;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCustomer;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.IBOOrganization;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Customer;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.CustomerVOA;


/**
* The {@link CustomerBOA} provides common CRUD operations (Create Read Update Delete) for the customer entity.<p> 
* 
* The business object {@link BOCustomer} represents a customer entity with it's common attributes
* like customerId, name, address or companyName. The customer implements the {@link IBOOrganization} interface.<p>
* 
* The following example code shows how to <b>create and persist a new customer entity</b> using the {@link CustomerBOA}. 
* <pre id="example">
* // first of all load a existing country to build the address
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(TEST_CUST_COUNTRY_ISOCODE);
* if(country == null)
* {	 
*	// do something...
*	// e.g. leave the procedure or throw an exception...
* }
*		
* // second, create a address which must be unique for each customer (addresses could not be shared)
* {@link BOAddress} address = new BOAddress();
* address.setStreet(TEST_CUST_STREET);
* address.setZipcode(TEST_CUST_ZIPCODE);
* address.setCity(TEST_CUST_CITY);
* address.setCountry(country);
* {@link AddressBOA}.getInstance().saveOrUpdate(address);
*
* // third, create the customer
* {@link BOCustomer} customer = new BOCustomer();
* customer.setCustomerId(TEST_CUST_ID);
* customer.setFirstname(TEST_CUST_FIRSTNAME);
* customer.setLastname(TEST_CUST_LASTNAME);
* customer.setCompanyname(TEST_CUST_COMPANYNAME);
* customer.setAddress(address);
* {@link CustomerBOA}.getInstance().saveOrUpdate(customer);	
* </pre>
* 
* The following example code shows how to <b>load an existing customer entity</b>, using the {@link CustomerBOA}. 
* <pre id="example">
* // load an existing customer
* {@link BOCustomer} customer = {@link CustomerBOA}.getInstance().findCustomerById(TEST_CUSTOMER_ID);
* </pre>
* 
* The following example code shows how to <b>load and update an existing customer entity</b>, using the {@link CustomerBOA}. 
* <pre id="example">
* // load an existing customer
* {@link BOCustomer} customer = {@link CustomerBOA}.getInstance().findCustomerById(TEST_CUSTOMER_ID);
* if (customer != null)
* {
* 	// set different name values and update
*	customer.setFirstname(TEST_CUST_UPDATED_FIRSTNAME);
*	customer.setLastname(TEST_CUST_UPDATED_LASTNAME);
*	{@link CustomerBOA}.getInstance().saveOrUpdate(customer);	
* }
* </pre>
* 
* The following example code shows how to <b>delete an existing customer entity</b>, using the {@link CustomerBOA}. 
* <pre id="example">
* // load an existing customer
* {@link BOCustomer} customer = {@link CustomerBOA}.getInstance().findCustomerById(TEST_CUSTOMER_ID);
* if (customer != null)
* {
* 	// delete the customer
*	{@link CustomerBOA}.getInstance().delete(customer);	
* }
* </pre>
* 
* @author tdi
*/
public class CustomerBOA
{
	private static CustomerBOA instance = null;


	private CustomerBOA()
	{
		super();
	}

	public static CustomerBOA getInstance()
	{
		if (instance == null)
		{
			synchronized (CustomerBOA.class)
			{
				if (instance == null)
				{
					instance = new CustomerBOA();
				}
			}
		}

		return instance;
	}

	public BOCustomer findCustomerById(String customerId)
	{
		CustomerVOA voa = new CustomerVOA();
		Customer customer = voa.get(customerId);
		
		if (customer == null)
			return null;
		
		return new BOCustomer(customer);
	}
	
	public BOCustomer findCustomerByWsUserName(String wsUserName)
   {
      Criteria crit = null;
      Session session = null;

      CustomerVOA voa = new CustomerVOA();
      session = voa.getSession();
      
      crit = session.createCriteria(Customer.class);
      crit.add(Expression.eq("wsUserName", wsUserName));

      Customer customer = (Customer)crit.uniqueResult();
      if(customer != null)
         return new BOCustomer(customer);
   
      return null;
   }

	@SuppressWarnings("unchecked")
   public List<BOCustomer> findAll()
	{
		List<BOCustomer> customers = new ArrayList<BOCustomer>();
		List<Customer> tempCustomers = CustomerVOA.getInstance().findAll();
		for (Iterator iter = tempCustomers.iterator(); iter.hasNext();)
		{
			customers.add(new BOCustomer((Customer)iter.next()));		
		}
		
		return customers;
	}
	
   /**
    * Save or Update a given BO. If the given BO does exist, it will be updated (overwritten) otherwise it will be created.  
    * 
    * @param customer an customer to save or update
    * @author tdi
    */
	public void saveOrUpdate(BOCustomer customer)
	{
		CustomerVOA.getInstance().saveOrUpdate(customer.customer);
	}

	public void delete(BOCustomer customer)
	{
		CustomerVOA.getInstance().delete(customer.customer);
	}
}

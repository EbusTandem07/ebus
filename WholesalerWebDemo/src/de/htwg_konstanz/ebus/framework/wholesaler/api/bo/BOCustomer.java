/* BOCustomer.java
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

import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.CustomerBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Customer;


/**
* The business object {@link BOCustomer} represents a customer entity with it's common attributes
* like customerId, name, address or companyName. The customer implements the {@link IBOOrganization} interface.<p>
* 
* The following example code shows how to load an existing customer. 
* <pre id="example">
* // load an existing customer
* {@link BOCustomer} customer = {@link CustomerBOA}.getInstance().findCustomerById(TEST_CUSTOMER_ID);
* </pre>
* 
* @author tdi
*/
public class BOCustomer implements IBOOrganization
{
	public Customer customer = null;

	
	public BOCustomer()
	{
		this.customer = new Customer();
	}

	public BOCustomer(Customer customer)
	{
		this.customer = customer;
	}

	public String getCustomerId()
	{
		return customer.getCustomerid();
	}
	
	public void setCustomerId(String customerId)
	{
		this.customer.setCustomerid(customerId);
	}
	
	public BOAddress getAddress()
	{
		return new BOAddress(customer.getAddress());
	}

	public void setAddress(BOAddress address)
	{
		customer.setAddress(address.address);
	}

	public String getCompanyname()
	{
		return customer.getCompanyname();
	}

	public void setCompanyname(String companyname)
	{
		customer.setCompanyname(companyname);
	}

	public String getFirstname()
	{
		return customer.getFirstname();
	}

	public void setFirstname(String firstname)
	{
		customer.setFirstname(firstname);
	}

	public String getLastname()
	{
		return customer.getLastname();
	}

	public void setLastname(String lastname)
	{
		customer.setLastname(lastname);
	}

	public String getRemark()
	{
		return customer.getRemark();
	}

	public void setRemark(String remark)
	{
		customer.setRemark(remark);
	}

   public void setWsUserName(String wsUserName)
   {
      customer.setWsUserName(wsUserName);
   }

   public String getWsUserName()
   {
      return customer.getWsUserName();
   }
   
   public void setWsPassword(String wsPassword)
   {
      customer.setWsPassword(wsPassword);
   }

   public String getWsPassword()
   {
      return customer.getWsPassword();
   }
   
   public void setWsDeliveryEndpoint(String wsDeliveryEndpoint)
   {
      customer.setWsDeliveryEndpoint(wsDeliveryEndpoint);
   }
   
   public String getWsDeliveryEndpoint()
   {
      return customer.getWsDeliveryEndpoint();
   }
   
   public void setWsInvoiceEndpoint(String wsInvoiceEndpoint)
   {
      customer.setWsInvoiceEndpoint(wsInvoiceEndpoint);
   }
   
   public String getWsInvoiceEndpoint()
   {
      return customer.getWsInvoiceEndpoint();
   }
}

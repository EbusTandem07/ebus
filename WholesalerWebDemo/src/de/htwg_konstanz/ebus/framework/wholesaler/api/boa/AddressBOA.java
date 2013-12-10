/* AddressBOA.java
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

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOAddress;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCountry;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Address;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.AddressVOA;

/**
* The {@link AddressBOA} provides common CRUD operations (Create Read Update Delete) for the address entity.<p> 
* 
* The business object {@link BOAddress} represents a address entity with it's common attributes
* like street, zipcode, city or country.<p>
* 
* The following example code shows how to <b>create and persist a new address entity</b> using the {@link AddressBOA}. 
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
* The following example code shows how to <b>load an existing address entity</b>, using the {@link AddressBOA}. 
* <pre id="example">
* // load the address
* {@link BOAddress} address = {@link AddressBOA}.getInstance().findAddress(addressId);		
* if (address != null)
* {	 
* 	// do something with the loaded address object...
* }
* </pre>
*	
* The following example code shows how to <b>load and update an existing address entity</b>, using the {@link AddressBOA}. 
* <pre id="example">
* // load the address
* {@link BOAddress} address = {@link AddressBOA}.getInstance().findAddress(addressId);		
* if (address != null)
* {	 	
*	// update the city
*	address.setCity(TEST_ADDRESS_UPDATED_CITY);
*	{@link AddressBOA}.getInstance().saveOrUpdate(address);
* }
* </pre>
*	
* The following example code shows how to <b>delete an existing address entity</b>, using the {@link AddressBOA}. 
* <pre id="example">
* // load the address
* {@link BOAddress} address = {@link AddressBOA}.getInstance().findAddress(addressId);		
* if (address != null)
* {	 	
* 	// delete the address
*	{@link AddressBOA}.getInstance().delete(address);
* }
* </pre>
* 
* @author tdi
*/
public class AddressBOA
{
	private static AddressBOA instance = null;


	private AddressBOA()
	{
		super();
	}

	public static AddressBOA getInstance()
	{
		if (instance == null)
		{
			synchronized (AddressBOA.class)
			{
				if (instance == null)
				{
					instance = new AddressBOA();
				}
			}
		}

		return instance;
	}

	public BOAddress findAddress(String addressId)
	{
		Address address = AddressVOA.getInstance().get(addressId);
		
		if (address == null)
			return null;
		
		return new BOAddress(address);
	}
	
   /**
    * Returns all available addresses. If no record could be found, the method will return an empty list  
    * <pre>
    * <code>
    *    List<BOAddress> boAddressList = AddressBOA.getInstance().findAll()
    * </code>
    * </pre>
    * 
    * @return all available addresses
    * @author tdi
    */
	@SuppressWarnings("unchecked")
   public List<BOAddress> findAll()
	{
		List<BOAddress> addressList = new ArrayList<BOAddress>();
		List<Address> tempAddressList = AddressVOA.getInstance().findAll();
		for (Iterator iter = tempAddressList.iterator(); iter.hasNext();)
		{
			addressList.add(new BOAddress((Address)iter.next()));		
		}
		
		return addressList;
	}
	
   /**
    * Save or Update a given BO. If the given BO does exist, it will be updated (overwritten) otherwise it will be created.  
    * 
    * @param address an address to save or update
    * @author tdi
    */
	public void saveOrUpdate(BOAddress address)
	{
		// ensure that the primary key is set to a unique value
		if ((address.address.getId() == null) || (address.address.getId().length() == 0))
			address.address.setId(String.valueOf(System.currentTimeMillis()));
		
		AddressVOA.getInstance().saveOrUpdate(address.address);
	}

	public void delete(BOAddress address)
	{
		AddressVOA.getInstance().delete(address.address);
	}
}

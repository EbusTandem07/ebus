/* UserBOA.java
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
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOSupplier;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOUserCustomer;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOUserInternal;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOUserSupplier;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.IBOUser;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.UserCustomer;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.UserInternal;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.UserSupplier;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.util.Constants;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.UserCustomerVOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.UserInternalVOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.UserSupplierVOA;

/**
* The {@link UserBOA} provides common CRUD operations (Create Read Update Delete) for the user customer, supplier and internal entity.<p> 
* 
* The business object {@link BOUserCustomer} represents a user customer entity with it's common attributes
* like name, login, address or email.<p>
*
* The business object {@link BOUserInternal} represents a user internal entity with it's common attributes
* like name, login, address or email.<p>
* 
* The business object {@link BOUserSupplier} represents a user supplier entity with it's common attributes
* like name, login, address or email.<p>
* 
* The following example code shows how to <b>create and persist a new user customer entity</b> using the {@link UserBOA}. 
* <pre id="example">
* // first of all load a existing country to build the address
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(TEST_USER_CUSTOMER_COUNTRY_ISOCODE);
* if(country == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
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
* {@link BOCustomer} customer = {@link CustomerBOA}.getInstance().findCustomerById(TEST_CUSTOMER_ID);
* if(customer == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
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
* // important! store the userid for further use
* userId = userCustomer.getId();
* </pre>
* 
* The following example code shows how to <b>load an existing user customer entity</b>, using the UserBOA. 
* <pre id="example">
* // load the created user
* {@link BOUserCustomer} userCustomer = {@link UserBOA}.getInstance().findUserCustomerById(userId);
* if(userCustomer == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
* </pre>
* 
* The following example code shows how to <b>load and update an existing user customer entity</b>, using the UserBOA. 
* <pre id="example">
* // load the existing user
* {@link BOUserCustomer} userCustomer = {@link UserBOA}.getInstance().findUserCustomerById(userId);
* if(userCustomer == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
* 
* // set different name values and update
* userCustomer.setFirstname(TEST_USER_CUSTOMER_UPDATED_FIRSTNAME);
* userCustomer.setLastname(TEST_USER_CUSTOMER_UPDATE_LASTNAME);
* {@link UserBOA}.getInstance().saveOrUpdateUserCustomer(userCustomer);
* </pre>
* 
* The following example code shows how to <b>delete an existing user customer entity</b>, using the UserBOA. 
* <pre id="example">
* // load the existing user
* {@link BOUserCustomer} userCustomer = {@link UserBOA}.getInstance().findUserCustomerById(userId);
* if(userCustomer == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
* 
* // delete the user
* {@link UserBOA}.getInstance().delete(userCustomer);
* </pre>
* 
* @author tdi
*/
public class UserBOA
{
	private static UserBOA instance = null;

	private UserBOA()
	{
		super();
	}

	public static UserBOA getInstance()
	{
		if (instance == null)
		{
			synchronized (UserBOA.class)
			{
				if (instance == null)
				{
					instance = new UserBOA();
				}
			}
		}

		return instance;
	}

   /**
   * <br>
   * <b>short description:</b><br>
   * this method returns ...
   * <p>
   * <b>usage:</b><br>
   * use this method to ...
   * <p>
   * <b>special comment:</b><br>
   * a special comment ...
   * <p>
   * <b>example:</b><br>
   * <code>a sample code</code>
   * <p>
   *  
   * @param
   * @return
   */
	public List<IBOUser> findAll()
	{
		List<IBOUser> users = new ArrayList<IBOUser>();
		users.addAll(findAllUserInternal());
		users.addAll(findAllUserCustomer());
		users.addAll(findAllUserSupplier());

		return users;
	}

	/**
	* <br>
	* <b>short description:</b><br>
	* this method returns all internal users (users with role internal).
	* <p>
	* <b>example:</b><br>
	* <code><pre>
	*	List<BOUserInternal> userList = findAllUserInternal();
	*	for (Iterator<BOUserInternal> iter = userList.iterator(); iter.hasNext();)
	*	{
	*		BOUserInternal user = iter.next();
	*		// do something with the user object...
	*	}
	* </pre></code>
	* <p>
	*  
	* @return a list of all internal users (users with role internal)
	*/
	public List<BOUserInternal> findAllUserInternal()
	{
		List<BOUserInternal> users = new ArrayList<BOUserInternal>();

		UserInternalVOA voa = new UserInternalVOA();
		List<UserInternal> tempUsers = voa.findAll();
		for (Iterator<UserInternal> iter = tempUsers.iterator(); iter.hasNext();)
		{
			users.add(new BOUserInternal(iter.next()));
		}

		return users;
	}

	/**
	* <br>
	* <b>short description:</b><br>
	* this method returns all customer users (users with role customer).
	* <p>
	* <b>example:</b><br>
	* <code><pre>
	*	List<BOUserCustomer> userList = findAllUserCustomer();
	*	for (Iterator<BOUserCustomer> iter = userList.iterator(); iter.hasNext();)
	*	{
	*		BOUserCustomer user = iter.next();
	*		// do something with the user object...
	*	}
	* </pre></code>
	* <p>
	*  
	* @return a list of all customer users (users with role customer)
	*/
	public List<BOUserCustomer> findAllUserCustomer()
	{
		List<BOUserCustomer> users = new ArrayList<BOUserCustomer>();
		List<UserCustomer> tempUsers = UserCustomerVOA.getInstance().findAll();
		for (Iterator<UserCustomer> iter = tempUsers.iterator(); iter.hasNext();)
		{
			users.add(new BOUserCustomer(iter.next()));
		}

		return users;
	}

	/**
	* <br>
	* <b>short description:</b><br>
	* this method returns all supplier users (users with role supplier).
	* <p>
	* <b>example:</b><br>
	* <code><pre>
	*	List<BOUserSupplier> userList = findAllUserSupplier();
	*	for (Iterator<BOUserSupplier> iter = userList.iterator(); iter.hasNext();)
	*	{
	*		BOUserSupplier user = iter.next();
	*		// do something with the user object...
	*	}
	* </pre></code>
	* <p>
	*  
	* @return a list of all supplier users (users with role supplier)
	*/
	public List<BOUserSupplier> findAllUserSupplier()
	{
		List<BOUserSupplier> users = new ArrayList<BOUserSupplier>();
		List<UserSupplier> tempUsers = UserSupplierVOA.getInstance().findAll();
		for (Iterator<UserSupplier> iter = tempUsers.iterator(); iter.hasNext();)
		{
			users.add(new BOUserSupplier(iter.next()));
		}

		return users;
	}

	/**
	* <br>
	* <b>short description:</b><br>
	* this method returns a single internal user object identified by the given parameter id.
	* <p>
	*
	* @param id the unique id of the user object
	* @return a internal user object identified by the given parameter id
	*/
	public BOUserInternal findUserInternalById(Integer id)
	{
		UserInternal userInternal = UserInternalVOA.getInstance().get(id);
		
		if (userInternal != null)
		{
			return new BOUserInternal(userInternal);
		} 
		
		return null;
	}

	/**
	* <br>
	* <b>short description:</b><br>
	* this method returns a single supplier user object identified by the given parameter id.
	* <p>
	*
	* @param id the unique id of the user object
	* @return a supplier user object identified by the given parameter id
	*/
	public BOUserSupplier findUserSupplierById(Integer id)
	{
		UserSupplier userSupplier = UserSupplierVOA.getInstance().get(id);
		userSupplier.getSupplier();
		
		
		if (userSupplier != null)
		{
			return new BOUserSupplier(userSupplier);
		} 
		
		return null;
	}

   /**
   * <br>
   * <b>short description:</b><br>
   * this method returns a single supplier user object identified by the given parameter id.
   * <p>
   *
   * @param id the unique id of the user object
   * @return a supplier user object identified by the given parameter id
   */
   public BOUserSupplier findUserSupplierBySupplier(BOSupplier boSupplier)
   {
      Criteria crit = null;
      Session session = null;

      UserSupplierVOA userSupplierVoa = new UserSupplierVOA();
      session = userSupplierVoa.getSession();
      
      crit = session.createCriteria(UserSupplier.class);
      crit.add(Expression.eq("supplier", boSupplier.supplier));

      UserSupplier userSupplier = (UserSupplier)crit.uniqueResult();
      if(userSupplier != null)
         return new BOUserSupplier(userSupplier);
      
      return null;
   }

	/**
   * <br>
   * <b>short description:</b><br>
   * this method returns a single supplier user object identified by the given parameter id.
   * <p>
   *
   * @param id the unique id of the user object
   * @return a supplier user object identified by the given parameter id
   */
   public BOUserSupplier findUserSupplierBySupplierNumber(String supplierNumber)
   {
      BOSupplier boSupplier = SupplierBOA.getInstance().findSupplierById(supplierNumber);

      return findUserSupplierBySupplier(boSupplier);
   }

	/**
	* <br>
	* <b>short description:</b><br>
	* this method returns a single customer user object identified by the given parameter id.
	* <p>
	*
	* @param id the unique id of the user object
	* @return a customer user object identified by the given parameter id
	*/
	public BOUserCustomer findUserCustomerById(Integer id)
	{
		UserCustomer userCustomer = UserCustomerVOA.getInstance().get(id);
		
		if (userCustomer != null)
		{
			return new BOUserCustomer(userCustomer);
		} 
		
		return null;
	}

	public BOUserCustomer findUserCustomerByWsUserName(String wsUserName)
   {
      Criteria crit = null;
      Session session = null;

      UserCustomerVOA userCustomerVoa = new UserCustomerVOA();
      session = userCustomerVoa.getSession();
      
      crit = session.createCriteria(UserCustomer.class);
      crit.add(Expression.eq("wsUserName", wsUserName));

      UserCustomer userCustomer = (UserCustomer)crit.uniqueResult();
      if(userCustomer != null)
         return new BOUserCustomer(userCustomer);
      
      return null;
   }

	/**
	* <br>
	* <b>short description:</b><br>
	* this method returns a single user object identified by the given parameter login and role.
	* <p>
	*
	* @param login the unique login of the user
	* @param role the role of the user (see Constants class for valid roles)
	* @return a user object identified by the given parameter login and role
	*/
	public IBOUser findUser(String login, int role)	
	{
		IBOUser user = null;
		Criteria crit = null;
		Session session = null;
		
		switch(role)
		{
			case Constants.USER_INTERNAL:  
				UserInternalVOA userInternalVoa = new UserInternalVOA();
				session = userInternalVoa.getSession();
			   crit = session.createCriteria(UserInternal.class);
			   crit.add(Expression.eq("Login", login));
			   UserInternal userInternal = (UserInternal)crit.uniqueResult();
			   if (userInternal != null)
			   	user = new BOUserInternal(userInternal);
			   break;
			   
			case Constants.USER_CUSTOMER:
				UserCustomerVOA userCustomerVoa = new UserCustomerVOA();
				session = userCustomerVoa.getSession();
			   crit = session.createCriteria(UserCustomer.class);
			   crit.add(Expression.eq("Login", login));
			   UserCustomer userCustomer = (UserCustomer)crit.uniqueResult();
			   if (userCustomer != null)
			   	user = new BOUserCustomer(userCustomer);
				break;

			case Constants.USER_SUPPLIER:
				UserSupplierVOA userSupplierVoa = new UserSupplierVOA();
				session = userSupplierVoa.getSession();
			   crit = session.createCriteria(UserSupplier.class);
			   crit.add(Expression.eq("Login", login));
			   UserSupplier userSupplier = (UserSupplier)crit.uniqueResult();
			   if (userSupplier != null)
			   	user = new BOUserSupplier(userSupplier);
				break;
		}

		return user;
	}

	/**
	* <br>
	* <b>short description:</b><br>
	* this method persists the given user object. If the user object does not exists
	* in the database it will be created. Otherwise it will be updated.
	* <p>
	*
	* @param user the user object (any role)
	*/
	public void saveOrUpdate(IBOUser user)
	{
		if (user instanceof BOUserCustomer)
		{
			saveOrUpdateUserCustomer((BOUserCustomer)user);
		}
		if (user instanceof BOUserSupplier)
		{
			saveOrUpdateUserSupplier((BOUserSupplier)user);
		}
		if (user instanceof BOUserInternal)
		{
			saveOrUpdateUserInternal((BOUserInternal)user);
		}
	}

	/**
	* <br>
	* <b>short description:</b><br>
	* this method persists the given internal user object. If the internal user object does 
	* not exists in the database it will be created. Otherwise it will be updated.
	* <p>
	*
	* @param user the internal user object
	*/
	public void saveOrUpdateUserInternal(BOUserInternal userInternal)
	{
		UserInternalVOA.getInstance().saveOrUpdate(userInternal.getUserInternal());
	}

	/**
	* <br>
	* <b>short description:</b><br>
	* this method persists the given supplier user object. If the supplier user object does 
	* not exists in the database it will be created. Otherwise it will be updated.
	* <p>
	*
	* @param user the supplier user object
	*/
	public void saveOrUpdateUserSupplier(BOUserSupplier userSupplier)
	{
		UserSupplierVOA.getInstance().saveOrUpdate(userSupplier.getUserSupplier());
	}

	/**
	* <br>
	* <b>short description:</b><br>
	* this method persists the given customer user object. If the customer user object does 
	* not exists in the database it will be created. Otherwise it will be updated.
	* <p>
	*
	* @param user the customer user object
	*/
	public void saveOrUpdateUserCustomer(BOUserCustomer userCustomer)
	{
		UserCustomerVOA.getInstance().saveOrUpdate(userCustomer.getUserCustomer());
	}

	/**
	* <br>
	* <b>short description:</b><br>
	* this method removes an existing user object from the database.
	* <p>
	*
	* @param user the user object (any role)
	*/
	public void delete(IBOUser user)
	{
		if (user instanceof BOUserCustomer)
		{
			deleteUserCustomer((BOUserCustomer)user);
		}
		if (user instanceof BOUserSupplier)
		{
			deleteUserSupplier((BOUserSupplier)user);
		}
		if (user instanceof BOUserInternal)
		{
			deleteUserInternal((BOUserInternal)user);
		}		
	}

	/**
	* <br>
	* <b>short description:</b><br>
	* this method removes an existing internal user object from the database.
	* <p>
	*
	* @param user the internal user object
	*/
	public void deleteUserInternal(BOUserInternal user)
	{
		UserInternalVOA.getInstance().delete(user.getId());
	}
	
	/**
	* <br>
	* <b>short description:</b><br>
	* this method removes an existing customer user object from the database.
	* <p>
	*
	* @param user the customer user object
	*/
	public void deleteUserCustomer(BOUserCustomer user)
	{
		UserCustomerVOA.getInstance().delete(user.getId());
	}
	
	/**
	* <br>
	* <b>short description:</b><br>
	* this method removes an existing supplier user object from the database.
	* <p>
	*
	* @param user the supplier user object
	*/
	public void deleteUserSupplier(BOUserSupplier user)
	{
		UserSupplierVOA.getInstance().delete(user.getId());
	}
}

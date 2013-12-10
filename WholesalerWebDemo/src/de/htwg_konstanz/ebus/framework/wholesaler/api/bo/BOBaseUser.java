/* BOBaseUser.java
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

import de.htwg_konstanz.ebus.framework.wholesaler.vo.IUser;


/**
* The business object {@link BOBaseUser} represents the base class for all user objects.
* The object provides common user attributes like name, login, address or email.<p>
* 
* The base class is defined as an abstract class, so you can't instantiate them.
* Use the derived classes "{@link BOUserInternal}", "{@link BOUserSupplier}" and "{@link BOUserCustomer}" instead.<br>
* 
* @author tdi
*/
public abstract class BOBaseUser implements IBOUser
{
	protected IUser user;

	public BOAddress getAddress()
	{
		return new BOAddress(user.getAddress());
	}

	public void setAddress(BOAddress address)
	{
		user.setAddress(address.address);
	}

	public String getEmail()
	{
		return user.getEmail();
	}

	public void setEmail(String email)
	{
		user.setEmail(email);
	}

	public String getFaxnumber()
	{
		return user.getFaxnumber();
	}

	public void setFaxnumber(String faxnumber)
	{
		user.setFaxnumber(faxnumber);
	}

	public String getFirstname()
	{
		return user.getFirstname();
	}

	public void setFirstname(String firstname)
	{
		user.setFirstname(firstname);
	}

	public Integer getId()
	{
		return user.getId();
	}

	public void setId(Integer id)
	{
		user.setId(id);
	}

	public String getLastname()
	{
		return user.getLastname();
	}

	public void setLastname(String lastname)
	{
		user.setLastname(lastname);
	}

	public String getLogin()
	{
		return user.getLogin();
	}

	public void setLogin(String login)
	{
		user.setLogin(login);
	}

	public String getMobilenumber()
	{
		return user.getMobilenumber();
	}

	public void setMobilenumber(String mobilenumber)
	{
		user.setMobilenumber(mobilenumber);
	}

	public String getPasswd()
	{
		return user.getPasswd();
	}

	public void setPasswd(String passwd)
	{
		user.setPasswd(passwd);
	}

	public String getPhonenumber()
	{
		return user.getPhonenumber();
	}

	public void setPhonenumber(String phonenumber)
	{
		user.setPhonenumber(phonenumber);
	}

	public String getRemark()
	{
		return user.getRemark();
	}

	public void setRemark(String remark)
	{
		user.setRemark(remark);
	}

	public String getTitle()
	{
		return user.getTitle();
	}

	public void setTitle(String title)
	{
		user.setTitle(title);
	}

	public abstract String getRole();
}

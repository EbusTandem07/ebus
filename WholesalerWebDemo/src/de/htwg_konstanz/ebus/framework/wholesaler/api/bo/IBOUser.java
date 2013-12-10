/* IBOUser.java
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


/**
* The interface {@link IBOUser} represents the common attributes for all user entities.<p>
* 
* To simplify the implementation and handling of the different user types (internal, 
* customer and supplier) the framework provides a base class called {@link BOBaseUser}.
* This base class implements the {@link IBOUser} Interface and is defined as abstract, 
* so you can't instantiate them. To work with user entities use the derived classes 
* "{@link BOUserInternal}", "{@link BOUserSupplier}" and "{@link BOUserCustomer}" instead.<p>
* 
* @author tdi
*/
public interface IBOUser
{
	public BOAddress getAddress();
	public void setAddress(BOAddress address);
	public String getEmail();
	public void setEmail(String email);
	public String getFaxnumber();
	public void setFaxnumber(String faxnumber);
	public String getFirstname();
	public void setFirstname(String firstname);
	public Integer getId();
	public void setId(Integer id);
	public String getLastname();
	public void setLastname(String lastname);
	public String getLogin();
	public void setLogin(String login);
	public String getMobilenumber();
	public void setMobilenumber(String mobilenumber);
	public String getPasswd();
	public void setPasswd(String passwd);
	public String getPhonenumber();
	public void setPhonenumber(String phonenumber);
	public String getRemark();
	public void setRemark(String remark);
	public String getTitle();
	public void setTitle(String title);
	public abstract String getRole();
}

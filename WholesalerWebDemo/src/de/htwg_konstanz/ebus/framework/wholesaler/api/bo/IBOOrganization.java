/* IBOOrganization.java
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
* The interface {@link IBOOrganization} represents the common attributes of an organization entity.<p>
* 
* @author tdi
*/
public interface IBOOrganization
{
	public BOAddress getAddress();
	public void setAddress(BOAddress address);
	public String getCompanyname();
	public void setCompanyname(String companyname);
	public String getFirstname();
	public void setFirstname(String firstname);
	public String getLastname();
	public void setLastname(String lastname);
	public String getRemark();
	public void setRemark(String remark);
}

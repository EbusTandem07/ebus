/* IBOPrice.java
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

import java.math.BigDecimal;

/**
* The interface {@link IBOPrice} represents the common attributes for all price entities.<p>
* 
* @author tdi
*/
public interface IBOPrice
{
	public BigDecimal getAmount();
	public void setAmount(BigDecimal amount);
	public BigDecimal getTaxrate();
	public void setTaxrate(BigDecimal taxrate);
	public String getPricetype();
	public void setPricetype(String pricetype);
	public BOCountry getCountry();
	public void setCountry(BOCountry country);
	public Integer getLowerboundScaledprice();
	public void setLowerboundScaledprice(Integer lowerboundScaledprice);
}

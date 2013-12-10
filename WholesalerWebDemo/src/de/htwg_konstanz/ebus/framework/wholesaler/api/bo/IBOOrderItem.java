/* IBOOrderItem.java
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
* The interface {@link IBOOrderItem} represents the common attributes of the order items 
* purchase and customer.<p>
* 
* @author tdi
*/
public interface IBOOrderItem
{
	public String getOrderNumber();
	public void setOrderNumber(String orderNumber);
	public BigDecimal getUnitNetPrice();
	public void setUnitNetPrice(BigDecimal unitNetPrice);
	public BigDecimal getItemNetPrice();
	public void setItemNetPrice(BigDecimal itemNetPrice);
	public String getProductDescription();
	public void setProductDescription(String productDescription);
	public BOAddress getDeliveryAddress();
	public void setDeliveryAddress(BOAddress delivAddress);
	public Integer getOrderAmount();
	public void setOrderAmount(Integer orderAmount);
	public String getOrderUnit();
	public void setOrderUnit(String orderUnit);
	public BigDecimal getTaxRate();
	public void setTaxRate(BigDecimal taxRate);
   public Boolean getRejected();
   public void setRejected(Boolean rejected);
}

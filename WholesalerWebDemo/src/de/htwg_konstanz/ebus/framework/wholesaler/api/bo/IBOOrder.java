/* IBOOrder.java
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
import java.util.Date;

/**
* The interface {@link IBOOrder} represents the common attributes of the order purchase 
* and customer.<p>
* 
* @author tdi
*/
public interface IBOOrder
{
	public String getOrderNumber();
	public void setOrderNumber(String orderNumber);
	public Date getOrderDate();
	public void setOrderDate(Date orderDate);
	public BOAddress getDeliveryAddress();
	public void setDeliveryAddress(BOAddress delivAddress);
	public BOAddress getInvoiceAddress();
	public void setInvoiceAddress(BOAddress invoiceAddress);
	public BigDecimal getPriceTotalNet();
	public void setPriceTotalNet(BigDecimal priceTotalNet);
	public BigDecimal getPriceTotalGross();
	public void setPriceTotalGross(BigDecimal priceTotalGross);
	public BigDecimal getTaxAmount();
	public void setTaxAmount(BigDecimal taxAmount);
	public BigDecimal getTaxTotal();
	public void setTaxTotal(BigDecimal taxTotal);
	public Integer getTotalLineItems();
	public void setTotalLineItems(Integer totalLineItems);
	public Boolean getRejected();
	public void setRejected(Boolean rejected);
}

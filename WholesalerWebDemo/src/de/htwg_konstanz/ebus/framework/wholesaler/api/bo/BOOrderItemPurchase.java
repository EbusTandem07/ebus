/* BOOrderItemPurchase.java
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
import java.math.RoundingMode;

import de.htwg_konstanz.ebus.framework.wholesaler.test.TestBOOrderPurchaseCRUD;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.OrderitemPurchase;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.OrderitemPurchasePK;


/**
 * The business object {@link BOOrderItemPurchase} represents a order item entity with it's common attributes
 * like orderNumber, amount, orderUnit or price.<p>
 * 
 * For code example please have a look at the {@link BOOrderPurchase} object or the unit test case {@link TestBOOrderPurchaseCRUD}.  
 * 
 * @author tdi
 */
 public class BOOrderItemPurchase implements IBOOrderItem
{
	public OrderitemPurchase orderItemPurchase = null;
	
	
	public BOOrderItemPurchase()
	{
		orderItemPurchase = new OrderitemPurchase();
	}

	public BOOrderItemPurchase(OrderitemPurchase orderItemPurchase)
	{
		this.orderItemPurchase = orderItemPurchase;
	}

	public BOCurrency getCurrency()
	{
	   return getOrderPurchase().getCurrency();
	}

   public String getCurrencyCode()
   {
      return getCurrency().getCode();
   }

	public BOAddress getDeliveryAddress()
	{
		return new BOAddress(orderItemPurchase.getDeliveryaddress());
	}

	public BigDecimal getItemNetPrice()
	{
		return orderItemPurchase.getItemprice();
	}

	public Integer getOrderAmount()
	{
		return orderItemPurchase.getOrderamount();
	}

	public String getOrderNumber()
	{
		return orderItemPurchase.getOrderref().getOrderid();
	}

	public String getOrderUnit()
	{
		return orderItemPurchase.getOrderunit();
	}

	public String getProductDescription()
	{
		return orderItemPurchase.getProductdescription();
	}

	public BigDecimal getTaxRate()
	{
		return orderItemPurchase.getTaxrate();
	}

	public BigDecimal getUnitNetPrice()
	{
		return orderItemPurchase.getUnitprice();
	}

	public BigDecimal getItemGrossPrice()
   {
      return orderItemPurchase.getItemprice().multiply(orderItemPurchase.getTaxrate().divide(new BigDecimal(100)).add(new BigDecimal(1))).setScale(2, RoundingMode.HALF_UP );
   }

	public void setDeliveryAddress(BOAddress delivAddress)
	{
		orderItemPurchase.setDeliveryaddress(delivAddress.address);
	}

	public void setItemNetPrice(BigDecimal itemNetPrice)
	{
		orderItemPurchase.setItemprice(itemNetPrice);
	}

	public void setOrderAmount(Integer orderAmount)
	{
		orderItemPurchase.setOrderamount(orderAmount);
	}

	public void setOrderNumber(String orderNumber)
	{
		orderItemPurchase.getOrderref().setOrderid(orderNumber);
	}

	public void setOrderUnit(String orderUnit)
	{
		orderItemPurchase.setOrderunit(orderUnit);
	}

	public void setProductDescription(String productDescription)
	{
		orderItemPurchase.setProductdescription(productDescription);
	}

	public void setTaxRate(BigDecimal taxRate)
	{
		orderItemPurchase.setTaxrate(taxRate);
	}

	public void setUnitNetPrice(BigDecimal unitNetPrice)
	{
		orderItemPurchase.setUnitprice(unitNetPrice);
	}
	
	public String getOrderNumberSupplier()
	{		
		return orderItemPurchase.getOrdernumberSupplier();
	}

	public void setOrderNumberSupplier(String orderNumberSupplier)
	{
		orderItemPurchase.setOrdernumberSupplier(orderNumberSupplier);
	}
	
	public void setOrderItemNumber(int orderItemNumber)
	{
		setOrderItemNumber(new Integer(orderItemNumber));
	}
		
	public void setOrderItemNumber(Integer orderItemNumber)
	{
		if (orderItemPurchase.getId() == null)
			orderItemPurchase.setId(new OrderitemPurchasePK());
			
		orderItemPurchase.getId().setItemnumber(orderItemNumber);
	}	

	public Integer getOrderItemNumber()
	{
		if (orderItemPurchase.getId() != null)
			return orderItemPurchase.getId().getItemnumber();
		
		return null;
	}
	
	public void setOrderPurchase(BOOrderPurchase orderPurchase)
	{
		if (orderItemPurchase.getId() == null)
			orderItemPurchase.setId(new OrderitemPurchasePK());
			
		orderItemPurchase.getId().setOrderref(orderPurchase.orderPurchase);
	}

	public BOOrderPurchase getOrderPurchase()
	{
		if ((orderItemPurchase.getId() != null) && (orderItemPurchase.getId().getOrderref() != null))
			return new BOOrderPurchase(orderItemPurchase.getId().getOrderref());
		
		return null;
	}

   public Boolean getRejected()
   {
      return orderItemPurchase.isRejected();
   }

   public void setRejected(Boolean rejected)
   {
      orderItemPurchase.setRejected(rejected);
   }
}

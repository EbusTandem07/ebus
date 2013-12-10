/* BOOrderItemCustomer.java
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
import de.htwg_konstanz.ebus.framework.wholesaler.test.TestBOOrderCustomerCRUD;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.OrderitemCustomer;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.OrderitemCustomerPK;


/**
 * The business object {@link BOOrderItemCustomer} represents an order item entity with it's common attributes
 * like orderNumber, amount, orderUnit or price.<p>
 * 
 * For code example please have a look at the {@link BOOrderCustomer} object or the unit test case {@link TestBOOrderCustomerCRUD}.  
 * 
 * @author tdi
 */
 public class BOOrderItemCustomer implements IBOOrderItem
{
	public OrderitemCustomer orderItemCustomer = null;
	
	
	public BOOrderItemCustomer()
	{
		orderItemCustomer = new OrderitemCustomer();
	}

	public BOOrderItemCustomer(OrderitemCustomer orderItemCustomer)
	{
		this.orderItemCustomer = orderItemCustomer;
	}

	public BOAddress getDeliveryAddress()
	{
		return new BOAddress(orderItemCustomer.getDeliveryaddress());
	}

	public BigDecimal getItemNetPrice()
	{
		return orderItemCustomer.getItemprice();
	}

	public Integer getOrderAmount()
	{
		return orderItemCustomer.getOrderamount();
	}

	public String getOrderNumber()
	{
		return orderItemCustomer.getOrderref().getOrderid();
	}

	public String getOrderUnit()
	{
		return orderItemCustomer.getOrderunit();
	}

	public String getProductDescription()
	{
		return orderItemCustomer.getProductdescription();
	}

	public BigDecimal getTaxRate()
	{
		return orderItemCustomer.getTaxrate();
	}

	public BigDecimal getUnitNetPrice()
	{
		return orderItemCustomer.getUnitprice();
	}

	public void setDeliveryAddress(BOAddress delivAddress)
	{
		orderItemCustomer.setDeliveryaddress(delivAddress.address);
	}

	public void setItemNetPrice(BigDecimal itemNetPrice)
	{
		orderItemCustomer.setItemprice(itemNetPrice);
	}

	public void setOrderAmount(Integer orderAmount)
	{
		orderItemCustomer.setOrderamount(orderAmount);
	}

	public void setOrderNumber(String orderNumber)
	{
		orderItemCustomer.getOrderref().setOrderid(orderNumber);
	}

	public void setOrderUnit(String orderUnit)
	{
		orderItemCustomer.setOrderunit(orderUnit);
	}

	public void setProductDescription(String productDescription)
	{
		orderItemCustomer.setProductdescription(productDescription);
	}

	public void setTaxRate(BigDecimal taxRate)
	{
		orderItemCustomer.setTaxrate(taxRate);
	}

	public void setUnitNetPrice(BigDecimal unitNetPrice)
	{
		orderItemCustomer.setUnitprice(unitNetPrice);
	}

	public String getOrderNumberCustomer()
	{		
		return orderItemCustomer.getOrdernumberCustomer();
	}

	public void setOrderNumberCustomer(String orderNumberCustomer)
	{
		orderItemCustomer.setOrdernumberCustomer(orderNumberCustomer);
	}
	
	public void setOrderItemNumber(int orderItemNumber)
	{
		setOrderItemNumber(new Integer(orderItemNumber));
	}
		
	public void setOrderItemNumber(Integer orderItemNumber)
	{
		if (orderItemCustomer.getId() == null)
			orderItemCustomer.setId(new OrderitemCustomerPK());
			
		orderItemCustomer.getId().setItemnumber(orderItemNumber);
	}	

	public Integer getOrderItemNumber()
	{
		if (orderItemCustomer.getId() != null)
			return orderItemCustomer.getId().getItemnumber();
		
		return null;
	}
	
	public void setOrderCustomer(BOOrderCustomer orderCustomer)
	{
		if (orderItemCustomer.getId() == null)
			orderItemCustomer.setId(new OrderitemCustomerPK());
			
		orderItemCustomer.getId().setOrderref(orderCustomer.orderCustomer);
	}

	public BOOrderCustomer getOrderCustomer()
	{
		if ((orderItemCustomer.getId() != null) && (orderItemCustomer.getId().getOrderref() != null))
			return new BOOrderCustomer(orderItemCustomer.getId().getOrderref());
		
		return null;
	}

	public BOCurrency getCurrency()
   {
      return getOrderCustomer().getCurrency();
   }

   public String getCurrencyCode()
   {
      return getCurrency().getCode();
   }
	
	public BigDecimal getItemGrossPrice()
   {
	   return orderItemCustomer.getItemprice().add(orderItemCustomer.getItemprice().multiply(orderItemCustomer.getTaxrate()));
   }
	  
   public String getRemark()
   {
      return orderItemCustomer.getRemark();
   }

   public void setRemark(String remark)
   {
      orderItemCustomer.setRemark(remark);
   }

   public String getTransport()
   {
      return orderItemCustomer.getTransport();
   }

   public void setTransport(String transport)
   {
      orderItemCustomer.setTransport(transport);
   }
   
   public String getSpecialtreatment()
   {
      return orderItemCustomer.getSpecialtreatment();
   }

   public void setSpecialtreatment(String specialtreatment)
   {
      orderItemCustomer.setSpecialtreatment(specialtreatment);
   }
   
   public Integer getPartialshipment()
   {
      return orderItemCustomer.getPartialshipment();
   }

   public void setPartialshipment(Integer partialshipment)
   {
      orderItemCustomer.setPartialshipment(partialshipment);
   }
   
   public String getInternationalrestrictions()
   {
      return orderItemCustomer.getInternationalrestrictions();
   }

   public void setInternationalrestrictions(String internationalrestrictions)
   {
      orderItemCustomer.setInternationalrestrictions(internationalrestrictions);
   }

   public Boolean getRejected()
   {
      return orderItemCustomer.isRejected();
   }

   public void setRejected(Boolean rejected)
   {
      orderItemCustomer.setRejected(rejected);
   }
   
   public String getPricetype()
   {
      return orderItemCustomer.getPricetype();
   }
   
   public void setPricetype(String pricetype)
   {
      orderItemCustomer.setPricetype(pricetype);
   }
}

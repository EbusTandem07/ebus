/* BOOrderCustomer.java
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

import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.AddressBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.CountryBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.CustomerBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.OrderBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.OrderItemBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.OrderCustomer;


/**
* The business object {@link BOOrderCustomer} represents an order entity of a customer with it's common attributes
* like orderNumber, orderDate, invoiceAddress or price.<p>
* 
* The following example code shows how to create and persist a new order with one order item. 
* <pre id="example">
* // load a existing country to build the delivery address
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(TEST_USER_CUSTOMER_COUNTRY_ISOCODE);
* if(country == null)
* {	 
*	// do something...
*	// e.g. leave the procedure or throw an exception...
* }
* 
* // create a delivery address which must be unique for each user (addresses could not be shared)
* {@link BOAddress} delivAddress = new BOAddress();
* delivAddress.setStreet(TEST_USER_CUSTOMER_STREET);
* delivAddress.setZipcode(TEST_USER_CUSTOMER_ZIPCODE);
* delivAddress.setCity(TEST_USER_CUSTOMER_CITY);
* delivAddress.setCountry(country);
* {@link AddressBOA}.getInstance().saveOrUpdate(delivAddress);
* 
* // load an existing customer
* {@link BOCustomer} customer = {@link CustomerBOA}.getInstance().getCustomerById(TEST_CUSTOMER_ID);
* if(customer == null)
* {	 
*	// do something...
*	// e.g. leave the procedure or throw an exception...
* }
* 
* {@link BOOrderCustomer} orderCustomer = new BOOrderCustomer();
* orderCustomer.setDeliveryAddress(delivAddress);
* orderCustomer.setCustomer(customer);
* orderCustomer.setOrderNumberCustomer(TEST_ORDER_CUSTOMER_ORDER_NUMBER_CUSTOMER);
* orderCustomer.setOrderDate(TEST_ORDER_CUSTOMER_ORDER_DATE);
* orderCustomer.setOrderNumber(TEST_ORDER_CUSTOMER_ORDER_NUMBER);
* orderCustomer.setPriceTotalNet(TEST_ORDER_CUSTOMER_ITEM_NET_PRICE);
* orderCustomer.setPriceTotalGross(TEST_ORDER_CUSTOMER_ITEM_GROSS_PRICE);
* orderCustomer.setTaxAmount(TEST_ORDER_CUSTOMER_ITEM_TAX_AMOUNT);
* orderCustomer.setTotalLineItems(new Integer(0));
* {@link OrderBOA}.getInstance().saveOrUpdate(orderCustomer);
* 
* {@link BOOrderItemCustomer} orderItem = new BOOrderItemCustomer();
* orderItem.setOrderNumberCustomer(TEST_ORDER_CUSTOMER_ITEM_ORDER_NUMBER);
* orderItem.setItemNetPrice(TEST_ORDER_CUSTOMER_ITEM_NET_PRICE);
* orderItem.setOrderAmount(TEST_ORDER_CUSTOMER_ITEM_AMOUNT);
* orderItem.setOrderUnit(TEST_ORDER_CUSTOMER_ITEM_UNIT);
* orderItem.setTaxRate(TEST_ORDER_CUSTOMER_ITEM_TAX_RATE);
* orderItem.setProductDescription(TEST_ORDER_CUSTOMER_ITEM_DESCR);	
* orderItem.setOrderItemNumber(TEST_ORDER_CUSTOMER_ITEM_AMOUNT);
* orderItem.setOrderCustomer(orderCustomer);
* {@link OrderItemBOA}.getInstance().saveOrUpdateOrderItemCustomer(orderItem);
* 
* // the line item count has changed -> update the order customer
* orderCustomer.setTotalLineItems(new Integer(1));
* {@link OrderBOA}.getInstance().saveOrUpdate(orderCustomer);
* </pre>
* 
* @author tdi
*/
public class BOOrderCustomer implements IBOOrder
{
	public OrderCustomer orderCustomer = null;
	
	
	public BOOrderCustomer()
	{		
		orderCustomer = new OrderCustomer();
	}

	public BOOrderCustomer(OrderCustomer orderCustomer)
	{		
		this.orderCustomer = orderCustomer;
	}

	public String getOrderNumber()
	{
		return orderCustomer.getOrderid();
	}

	public void setOrderNumber(String orderNumber)
	{
		orderCustomer.setOrderid(orderNumber);
	}

	public Date getOrderDate()
	{
		return orderCustomer.getOrderdate();
	}

	public void setOrderDate(Date orderDate)
	{
		orderCustomer.setOrderdate(orderDate);
	}

	public BOAddress getDeliveryAddress()
	{
		return new BOAddress(orderCustomer.getDeliveryaddress());
	}

	public void setDeliveryAddress(BOAddress delivAddress)
	{
		orderCustomer.setDeliveryaddress(delivAddress.address);
	}

	public BOAddress getInvoiceAddress()
	{
		return new BOAddress(orderCustomer.getInvoiceaddress());
	}

	public void setInvoiceAddress(BOAddress invoiceAddress)
	{
		orderCustomer.setInvoiceaddress(invoiceAddress.address);
	}

//	public List<BOOrderItemCustomer> getOrderItems()
//	{
//		List<BOOrderItemCustomer> orderItems = new ArrayList<BOOrderItemCustomer>();
//		Set<OrderitemCustomer> tempOrderItems = orderCustomer.getOrderItems();
//		if (tempOrderItems != null)
//		{
//			for (OrderitemCustomer item : tempOrderItems)
//			{
//				orderItems.add(new BOOrderItemCustomer(item));
//			}
//		}
//		
//		return orderItems;
//	}
//
//	public void setOrderItems(List<BOOrderItemCustomer> orderItems)
//	{
//		orderCustomer.getOrderItems().clear();
//
//		for (BOOrderItemCustomer item : orderItems)
//		{
//			orderCustomer.getOrderItems().add(item.orderItemCustomer);
//		}
//	}

	public BigDecimal getPriceTotalNet()
	{
		return orderCustomer.getPricetotalNet();
	}

	public void setPriceTotalNet(BigDecimal priceTotalNet)
	{
		orderCustomer.setPricetotalNet(priceTotalNet);
	}

	public BigDecimal getPriceTotalGross()
	{
		return orderCustomer.getPricetotalGross();
	}

	public void setPriceTotalGross(BigDecimal priceTotalGross)
	{
		orderCustomer.setPricetotalGross(priceTotalGross);
	}

	public BigDecimal getTaxAmount()
	{
		return orderCustomer.getTaxamount();
	}

	public void setTaxAmount(BigDecimal taxAmount)
	{
		orderCustomer.setTaxamount(taxAmount);
	}

	public BigDecimal getTaxTotal()
	{
		return orderCustomer.getTaxtotal();
	}

	public void setTaxTotal(BigDecimal taxTotal)
	{
		orderCustomer.setTaxtotal(taxTotal);
	}

	public void setCurrency(BOCurrency boCurrency)
	{
	   orderCustomer.setCurrency(boCurrency.currency);
	}
	
	public BOCurrency getCurrency()
   {
      return new BOCurrency(orderCustomer.getCurrency());
   }

	public String getCurrencyCode()
   {
      return getCurrency().getCode();
   }

	public BOCustomer getCustomer()
	{
		return new BOCustomer(orderCustomer.getCustomer());
	}
	
	public void setCustomer(BOCustomer customer)
	{
		orderCustomer.setCustomer(customer.customer);
	}

	public Integer getTotalLineItems()
	{
		return orderCustomer.getTotallineitems();
	}

	public void setTotalLineItems(Integer totalLineItems)
	{
		orderCustomer.setTotallineitems(totalLineItems);		
	}

	public String getOrderNumberCustomer()
	{
		return orderCustomer.getOrderidCustomer();
	}
	
	public void setOrderNumberCustomer(String orderNumberCustomer)
	{
		orderCustomer.setOrderidCustomer(orderNumberCustomer);
	}
	
	public byte[] getXmlFileRequest()
	{
	   return orderCustomer.getXmlFileRequest();
	}
	
	public void setXmlFileRequest(byte[] xmlFile)
	{
	   orderCustomer.setXmlFileRequest(xmlFile);
	}
   
   public byte[] getXmlFileResponse()
   {
      return orderCustomer.getXmlFileResponse();
   }
   
   public void setXmlFileResponse(byte[] xmlFile)
   {
      orderCustomer.setXmlFileResponse(xmlFile);
   }
   
   public void setOrdertype(String ordertype)
   {
      orderCustomer.setOrdertype(ordertype);
   }
      
   public String getOrdertype()
   {
      return orderCustomer.getOrdertype();
   }
   
   public String getRemark()
   {
      return orderCustomer.getRemark();
   }

   public void setRemark(String remark)
   {
      orderCustomer.setRemark(remark);
   }

   public String getTransport()
   {
      return orderCustomer.getTransport();
   }

   public void setTransport(String transport)
   {
      orderCustomer.setTransport(transport);
   }
   
   public String getSpecialtreatment()
   {
      return orderCustomer.getSpecialtreatment();
   }

   public void setSpecialtreatment(String specialtreatment)
   {
      orderCustomer.setSpecialtreatment(specialtreatment);
   }
   
   public Integer getPartialshipment()
   {
      return orderCustomer.getPartialshipment();
   }

   public void setPartialshipment(Integer partialshipment)
   {
      orderCustomer.setPartialshipment(partialshipment);
   }
   
   public String getInternationalrestrictions()
   {
      return orderCustomer.getInternationalrestrictions();
   }

   public void setInternationalrestrictions(String internationalrestrictions)
   {
      orderCustomer.setInternationalrestrictions(internationalrestrictions);
   }

   public Boolean getRejected()
   {
      return orderCustomer.isRejected();
   }

   public void setRejected(Boolean rejected)
   {
      orderCustomer.setRejected(rejected);
   }
   
   public Boolean getSplitted()
   {
      return orderCustomer.isSplitted();
   }
   
   public void setSplitted(Boolean splitted)
   {
      orderCustomer.setSplitted(splitted);
   }
}

/* BOOrderPurchase.java
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
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.OrderBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.OrderItemBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.SupplierBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.OrderPurchase;

/**
* The business object {@link BOOrderPurchase} represents a order purchase entity with it's common attributes
* like orderNumber, orderDate, invoiceAddress or price.<p>
* 
* The following example code shows how to create and persist a new order with one order item. 
* <pre id="example">
* // load a existing country to build the delivery address
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(TEST_USER_PURCHASE_COUNTRY_ISOCODE);
* if(country == null)
* {	 
*	// do something...
*	// e.g. leave the procedure or throw an exception...
* }
* 
* // create a delivery address which must be unique for each user (addresses could not be shared)
* {@link BOAddress} delivAddress = new BOAddress();
* delivAddress.setStreet(TEST_USER_PURCHASE_STREET);
* delivAddress.setZipcode(TEST_USER_PURCHASE_ZIPCODE);
* delivAddress.setCity(TEST_USER_PURCHASE_CITY);
* delivAddress.setCountry(country);
* {@link AddressBOA}.getInstance().saveOrUpdate(delivAddress);
* 
* // load a existing supplier
* {@link BOSupplier} supplier = {@link SupplierBOA}.getInstance().getSupplierById(TEST_SUPPLIER_ID);
* assertNotNull(supplier);
* 
* {@link BOOrderPurchase} orderPurchase = new BOOrderPurchase();
* orderPurchase.setDeliveryAddress(delivAddress);
* orderPurchase.setSupplier(supplier);
* orderPurchase.setOrderNumberSupplier(TEST_ORDER_PURCHASE_ORDER_NUMBER_SUPPLIER);
* orderPurchase.setOrderDate(TEST_ORDER_PURCHASE_ORDER_DATE);
* orderPurchase.setOrderNumber(TEST_ORDER_PURCHASE_ORDER_NUMBER);
* orderPurchase.setPriceTotalNet(TEST_ORDER_PURCHASE_ITEM_NET_PRICE);
* orderPurchase.setPriceTotalGross(TEST_ORDER_PURCHASE_ITEM_GROSS_PRICE);
* orderPurchase.setTaxAmount(TEST_ORDER_PURCHASE_ITEM_TAX_AMOUNT);
* orderPurchase.setTotalLineItems(new Integer(0));
* {@link OrderBOA}.getInstance().saveOrUpdate(orderPurchase);
* 
* {@link BOOrderItemPurchase} orderItem = new BOOrderItemPurchase();
* orderItem.setOrderNumberSupplier(TEST_ORDER_PURCHASE_ITEM_ORDER_NUMBER);
* orderItem.setItemNetPrice(TEST_ORDER_PURCHASE_ITEM_NET_PRICE);
* orderItem.setOrderAmount(TEST_ORDER_PURCHASE_ITEM_AMOUNT);
* orderItem.setOrderUnit(TEST_ORDER_PURCHASE_ITEM_UNIT);
* orderItem.setTaxRate(TEST_ORDER_PURCHASE_ITEM_TAX_RATE);
* orderItem.setProductDescription(TEST_ORDER_PURCHASE_ITEM_DESCR);	
* orderItem.setOrderItemNumber(TEST_ORDER_PURCHASE_ITEM_AMOUNT);
* orderItem.setOrderPurchase(orderPurchase);
* {@link OrderItemBOA}.getInstance().saveOrUpdateOrderItemPurchase(orderItem);
* 
* // the line item count has changed -> update the order purchase
* orderPurchase.setTotalLineItems(new Integer(1));
* {@link OrderBOA}.getInstance().saveOrUpdate(orderPurchase);
* </pre>
* 
* @author tdi
*/
public class BOOrderPurchase implements IBOOrder
{
	public OrderPurchase orderPurchase = null;
	
	
	public BOOrderPurchase()
	{
		orderPurchase = new OrderPurchase();
	}

	public BOOrderPurchase(OrderPurchase orderPurchase)
	{
		this.orderPurchase = orderPurchase;
	}

	public String getOrderNumber()
	{
		return orderPurchase.getOrderid();
	}

	public void setOrderNumber(String orderNumber)
	{
		orderPurchase.setOrderid(orderNumber);
	}

	public Date getOrderDate()
	{
		return orderPurchase.getOrderdate();
	}

	public void setOrderDate(Date orderDate)
	{
		orderPurchase.setOrderdate(orderDate);
	}

	public BOAddress getDeliveryAddress()
	{
		return new BOAddress(orderPurchase.getDeliveryaddress());
	}

	public void setDeliveryAddress(BOAddress delivAddress)
	{
		orderPurchase.setDeliveryaddress(delivAddress.address);
	}

	public BOAddress getInvoiceAddress()
	{
		return new BOAddress(orderPurchase.getInvoiceaddress());
	}

	public void setInvoiceAddress(BOAddress invoiceAddress)
	{
		orderPurchase.setInvoiceaddress(invoiceAddress.address);
	}

//	public List<BOOrderItemPurchase> getOrderItems()
//	{
//		List<BOOrderItemPurchase> orderItems = new ArrayList<BOOrderItemPurchase>();
//		Set<OrderitemPurchase> tempOrderItems = orderPurchase.getOrderItems();
//		for (OrderitemPurchase item : tempOrderItems)
//		{
//			orderItems.add(new BOOrderItemPurchase(item));
//		}
//		
//		return orderItems;
//	}
//
//	public void setOrderItems(List<BOOrderItemPurchase> orderItems)
//	{
//		orderPurchase.getOrderItems().clear();
//
//		for (BOOrderItemPurchase item : orderItems)
//		{
//			orderPurchase.getOrderItems().add(item.orderItemPurchase);
//		}
//	}

	public BigDecimal getPriceTotalNet()
	{
		return orderPurchase.getPricetotalNet();
	}

	public void setPriceTotalNet(BigDecimal priceTotalNet)
	{
		orderPurchase.setPricetotalNet(priceTotalNet);
	}

	public BigDecimal getPriceTotalGross()
	{
		return orderPurchase.getPricetotalGross();
	}

	public void setPriceTotalGross(BigDecimal priceTotalGross)
	{
		orderPurchase.setPricetotalGross(priceTotalGross);
	}

	public BigDecimal getTaxAmount()
	{
		return orderPurchase.getTaxamount();
	}

	public void setTaxAmount(BigDecimal taxAmount)
	{
		orderPurchase.setTaxamount(taxAmount);
	}

	public BigDecimal getTaxTotal()
	{
		return orderPurchase.getTaxtotal();
	}

	public void setTaxTotal(BigDecimal taxTotal)
	{
		orderPurchase.setTaxtotal(taxTotal);
	}
	
	public BOSupplier getSupplier()
	{
		return new BOSupplier(orderPurchase.getSupplier());
	}
	
	public void setSupplier(BOSupplier supplier)
	{
		orderPurchase.setSupplier(supplier.supplier);
	}

	public Integer getTotalLineItems()
	{
		return orderPurchase.getTotallineitems();
	}

	public void setTotalLineItems(Integer totalLineItems)
	{
		orderPurchase.setTotallineitems(totalLineItems);
	}
	
	public String getOrderNumberSupplier()
	{
		return orderPurchase.getOrderidSupplier();		
	}
	
	public void setOrderNumberSupplier(String orderNumberSupplier)
	{
		orderPurchase.setOrderidSupplier(orderNumberSupplier);
	}

	public byte[] getXmlFileRequest()
   {
      return orderPurchase.getXmlFileRequest();
   }
   
   public void setXmlFileRequest(byte[] xmlFile)
   {
      orderPurchase.setXmlFileRequest(xmlFile);
   }
   
   public byte[] getXmlFileResponse()
   {
      return orderPurchase.getXmlFileResponse();
   }
   
   public void setXmlFileResponse(byte[] xmlFile)
   {
      orderPurchase.setXmlFileResponse(xmlFile);
   }

   public Boolean getRejected()
   {
   return orderPurchase.isRejected();
   }

   public void setRejected(Boolean rejected)
   {
      orderPurchase.setRejected(rejected);
   }
   
   public BOCurrency getCurrency()
   {
      return new BOCurrency(orderPurchase.getCurrency());
   }

   public void setCurrency(BOCurrency boCurrency)
   {
      orderPurchase.setCurrency(boCurrency.currency);
   }
   
   public String getCurrencyCode()
   {
      return getCurrency().getCode();
   }
}

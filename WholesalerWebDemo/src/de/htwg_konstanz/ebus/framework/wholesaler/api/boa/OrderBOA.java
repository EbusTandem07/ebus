/* OrderBOA.java
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
package de.htwg_konstanz.ebus.framework.wholesaler.api.boa;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOAddress;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCountry;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCustomer;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOOrderCustomer;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOOrderItemCustomer;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOOrderPurchase;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOSupplier;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.IBOOrder;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.OrderCustomer;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.OrderPurchase;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.OrderCustomerVOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.OrderPurchaseVOA;

/**
* The {@link OrderBOA} provides common CRUD operations (Create Read Update Delete) for the order customer and the order purchase entity.<p> 
* 
* The business object {@link BOOrderCustomer} represents an order entity of a customer with it's common attributes
* like orderNumber, orderDate, invoiceAddress or price.<p>
* 
* The business object {@link BOOrderPurchase} represents a order purchase entity with it's common attributes
* like orderNumber, orderDate, invoiceAddress or price.<p>
*
* The following example code shows how to <b>create and persist a new order customer entity</b> using the {@link OrderBOA}. 
* <pre id="example">
* // first of all load a existing country to build the delivery address
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(TEST_USER_CUSTOMER_COUNTRY_ISOCODE);
* if(country == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
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
* // load a existing customer
* {@link BOCustomer} customer = {@link CustomerBOA}.getInstance().findCustomerById(TEST_CUSTOMER_ID);
* if(customer == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
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
* The following example code shows how to <b>load an existing order customer entity and print out some order details</b>, using the OrderBOA. 
* <pre id="example">
* {@link BOOrderCustomer} orderCustomer = {@link OrderBOA}.getInstance().findOrderCustomer(TEST_ORDER_CUSTOMER_ORDER_NUMBER);    
* if(orderCustomer == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
* 
* {@link BOCustomer} customer = orderCustomer.getCustomer();
* if(customer == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
*
* System.out.println(customer.getLastname());
* System.out.println("Item count: " + {@link OrderItemBOA}.getInstance().findAllOrderItemCustomer(orderCustomer).size());
* System.out.println("Order Number (first item): " + {@link OrderItemBOA}.getInstance().findAllOrderItemCustomer(orderCustomer).get(0).getOrderNumberCustomer());
* </pre>
* 
* The following example code shows how to <b>load and update an existing order customer entity</b>, using the OrderBOA. 
* <pre id="example">
* // load the order customer
* {@link BOOrderCustomer} orderCustomer = {@link OrderBOA}.getInstance().findOrderCustomer(TEST_ORDER_CUSTOMER_ORDER_NUMBER);    
* if(orderCustomer == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
*
* // load all line items
* List<{@link BOOrderItemCustomer}> items = {@link OrderItemBOA}.getInstance().findAllOrderItemCustomer(orderCustomer);
* if (items != null && !items.isEmpty())
* {
*     // get the first line item
*     {@link BOOrderItemCustomer} orderItem = items.get(0);
*
*     // update the first line item
*     orderItem.setOrderNumberCustomer(TEST_ORDER_CUSTOMER_UPDATED_ITEM_ORDER_NUMBER);
*     {@link OrderItemBOA}.getInstance().saveOrUpdateOrderItemCustomer(orderItem);
* }
* </pre>
* 
* The following example code shows how to <b>delete an existing order customer entity</b>, using the OrderBOA. 
* <pre id="example">
* // load the order customer and delete it
* {@link BOOrderCustomer} orderCustomer = {@link OrderBOA}.getInstance().findOrderCustomer(TEST_ORDER_CUSTOMER_ORDER_NUMBER);    
* if(orderCustomer == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
*
* // delete the order customer 
* // the line items will be deleted automatically (by the on delete cascade clause)
* {@link OrderBOA}.getInstance().delete(orderCustomer);
* </pre>
* 
* @author tdi
*/
public class OrderBOA
{
	private static OrderBOA instance = null;


	private OrderBOA()
	{
		super();
	}

	public static OrderBOA getInstance()
	{
		if (instance == null)
		{
			synchronized (OrderBOA.class)
			{
				if (instance == null)
				{
					instance = new OrderBOA();
				}
			}
		}

		return instance;
	}

	public BOOrderCustomer findOrderCustomer(String orderNumber)
	{
		OrderCustomer orderCustomer = OrderCustomerVOA.getInstance().get(orderNumber);
		if (orderCustomer != null)
			return new BOOrderCustomer(orderCustomer);
		
		return null;
	}
	
	public BOOrderPurchase findOrderPurchase(String orderNumber)
	{
		OrderPurchase orderPurchase = OrderPurchaseVOA.getInstance().get(orderNumber); 
		if (orderPurchase != null)
			return new BOOrderPurchase(orderPurchase);
		
		return null;
	}

	public List<IBOOrder> findAll()
	{
		List<IBOOrder> orderList = new ArrayList<IBOOrder>();

		orderList.addAll(findAllCustomerOrders());
		orderList.addAll(findAllPurchaseOrders());
		
		return orderList;
	}
	
	public List<BOOrderCustomer> findAllCustomerOrders()
	{
		List<BOOrderCustomer> orderList = new ArrayList<BOOrderCustomer>();		
		List<OrderCustomer> tempOrderList = OrderCustomerVOA.getInstance().findAll();
		for (OrderCustomer order : tempOrderList)
		{
			orderList.add(new BOOrderCustomer(order));
		}
		
		return orderList;
	}

	public List<BOOrderPurchase> findAllPurchaseOrders()
	{
		List<BOOrderPurchase> orderList = new ArrayList<BOOrderPurchase>();		
		List<OrderPurchase> tempOrderList = OrderPurchaseVOA.getInstance().findAll();
		for (OrderPurchase order : tempOrderList)
		{
			orderList.add(new BOOrderPurchase(order));
		}
		
		return orderList;
	}
	
	@SuppressWarnings("unchecked")
   public List<BOOrderCustomer> findAllCustomerOrders(BOCustomer customer)
	{
		List<BOOrderCustomer> orderList = new ArrayList<BOOrderCustomer>();		
		
		OrderCustomerVOA voa = new OrderCustomerVOA();
		Session session = voa.getSession();
	   Criteria crit = session.createCriteria(OrderCustomer.class);
	   crit.add(Expression.eq("Customer", customer.customer));
	   ArrayList<OrderCustomer> tempOrderList = (ArrayList<OrderCustomer>)crit.list();

	   for (OrderCustomer order : tempOrderList)
		{
			orderList.add(new BOOrderCustomer(order));
		}

	   return orderList;
	}

	@SuppressWarnings("unchecked")
   public List<BOOrderPurchase> findAllPurchaseOrders(BOSupplier supplier)
	{
		List<BOOrderPurchase> orderList = new ArrayList<BOOrderPurchase>();		
		
		OrderPurchaseVOA voa = new OrderPurchaseVOA();
		Session session = voa.getSession();
	   Criteria crit = session.createCriteria(OrderPurchase.class);
	   crit.add(Expression.eq("Supplier", supplier.supplier));
	   ArrayList<OrderPurchase> tempOrderList = (ArrayList<OrderPurchase>)crit.list();

	   for (OrderPurchase order : tempOrderList)
		{
			orderList.add(new BOOrderPurchase(order));
		}

	   return orderList;
	}

   /**
    * Save or Update a given BO. If the given BO does exist, it will be updated (overwritten) otherwise it will be created.  
    * 
    * @param order an order to save or update
    * @author tdi
    */
	public void saveOrUpdate(IBOOrder order)
	{
		if (order instanceof BOOrderCustomer)
		{
			saveOrUpdateOrderCustomer((BOOrderCustomer)order);			
		}
		
		if (order instanceof BOOrderPurchase)
		{
			saveOrUpdateOrderPurchase((BOOrderPurchase)order);			
		}
	}

	public void saveOrUpdateOrderCustomer(BOOrderCustomer orderCustomer)
	{
		OrderCustomerVOA.getInstance().saveOrUpdate(orderCustomer.orderCustomer);
	}

	public void saveOrUpdateOrderPurchase(BOOrderPurchase orderPurchase)
	{
		OrderPurchaseVOA.getInstance().saveOrUpdate(orderPurchase.orderPurchase);
	}
	
	public void delete(IBOOrder order)
	{
		if (order instanceof BOOrderCustomer)
		{
			deleteOrderCustomer((BOOrderCustomer)order);			
		}
		
		if (order instanceof BOOrderPurchase)
		{
			deleteOrderPurchase((BOOrderPurchase)order);			
		}
	}

	public void deleteOrderCustomer(BOOrderCustomer orderCustomer)
	{
		OrderCustomerVOA.getInstance().delete(orderCustomer.orderCustomer);
	}

	public void deleteOrderPurchase(BOOrderPurchase orderPurchase)
	{
		OrderPurchaseVOA.getInstance().delete(orderPurchase.orderPurchase);
	}
}

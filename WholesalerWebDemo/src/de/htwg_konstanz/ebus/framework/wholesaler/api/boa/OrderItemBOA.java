/* OrderItemBOA.java
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

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOOrderCustomer;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOOrderItemCustomer;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOOrderItemPurchase;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOOrderPurchase;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.OrderitemCustomer;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.OrderitemCustomerPK;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.OrderitemPurchase;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.OrderitemPurchasePK;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.OrderitemCustomerVOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.OrderitemPurchaseVOA;

/**
* The {@link OrderItemBOA} provides common CRUD operations (Create Read Update Delete) for the order item (customer and purchase) entity.<p> 
* 
* The business object {@link BOOrderItemCustomer} represents an order item entity with it's common attributes
* like orderNumber, amount, orderUnit or price.<p>
*
* The business object {@link BOOrderItemPurchase} represents a order item entity with it's common attributes
* like orderNumber, amount, orderUnit or price.<p>
* 
* The following example code shows how to create and persist a new order item customer entity using the {@link OrderItemBOA}. 
* <pre id="example">
* // load an existing order customer
* {@link BOOrderCustomer} orderCustomer = {@link OrderBOA}.getInstance().findOrderCustomer(TEST_ORDER_CUSTOMER_ORDER_NUMBER);    
* if(orderCustomer == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
*
* {@link BOOrderItemCustomer} orderItem = new BOOrderItemCustomer();
* orderItem.setOrderNumberCustomer(TEST_ORDER_CUSTOMER_ITEM_ORDER_NUMBER);
* orderItem.setItemNetPrice(TEST_ORDER_CUSTOMER_ITEM_NET_PRICE);
* orderItem.setOrderAmount(TEST_ORDER_CUSTOMER_ITEM_AMOUNT);
* orderItem.setOrderUnit(TEST_ORDER_CUSTOMER_ITEM_UNIT);
* orderItem.setTaxRate(TEST_ORDER_CUSTOMER_ITEM_TAX_RATE);
* orderItem.setProductDescription(TEST_ORDER_CUSTOMER_ITEM_DESCR);  
* orderItem.setOrderItemNumber(TEST_ORDER_CUSTOMER_ITEM_AMOUNT);
* 
* // important! set the order customer to create a relation between the order item and the corresponding order 
* orderItem.setOrderCustomer(orderCustomer);
* {@link OrderItemBOA}.getInstance().saveOrUpdateOrderItemCustomer(orderItem);
*
* // the line item count has changed -> update the order customer
* // load all line items to determine the size
* List<{@link BOOrderItemCustomer}> items = {@link OrderItemBOA}.getInstance().findAllOrderItemCustomer(orderCustomer);
* if (items != null)
* {
*     orderCustomer.setTotalLineItems(Integer.valueOf(items.size()));
*     {@link OrderBOA}.getInstance().saveOrUpdate(orderCustomer);
* }
* </pre>
* 
* @author tdi
*/
public class OrderItemBOA
{
	private static OrderItemBOA instance = null;

	
	private OrderItemBOA()
	{
		super();
	}

	public static OrderItemBOA getInstance()
	{
		if (instance == null)
		{
			synchronized (OrderItemBOA.class)
			{
				if (instance == null)
				{
					instance = new OrderItemBOA();
				}
			}
		}

		return instance;
	}

	public BOOrderItemCustomer findOrderItemCustomer(BOOrderCustomer orderCustomer, int itemNumber)
	{
		return findOrderItemCustomer(orderCustomer, new Integer(itemNumber));
	}

	public BOOrderItemCustomer findOrderItemCustomer(BOOrderCustomer orderCustomer, Integer itemNumber)
	{
		OrderitemCustomerPK orderItemKey = new OrderitemCustomerPK();
		orderItemKey.setOrderref(orderCustomer.orderCustomer);
		orderItemKey.setItemnumber(itemNumber);
		OrderitemCustomer orderItem = OrderitemCustomerVOA.getInstance().get(orderItemKey);
		
		if (orderItem != null)
			return new BOOrderItemCustomer(orderItem);
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
   public List<BOOrderItemCustomer> findAllOrderItemCustomer(BOOrderCustomer orderCustomer)
	{
		List<BOOrderItemCustomer> orderItemList = new ArrayList<BOOrderItemCustomer>();		
		
		OrderitemCustomerVOA voa = new OrderitemCustomerVOA();
		Session session = voa.getSession();
	   Criteria crit = session.createCriteria(OrderitemCustomer.class);
		crit.add(Expression.eq("Orderref", orderCustomer.orderCustomer));
	   ArrayList<OrderitemCustomer> tempOrderItemList = (ArrayList<OrderitemCustomer>)crit.list();

	   for (OrderitemCustomer orderItem : tempOrderItemList)
		{
	   	orderItemList.add(new BOOrderItemCustomer(orderItem));
		}
		
	   return orderItemList;
	}

   /**
    * Save or Update a given BO. If the given BO does exist, it will be updated (overwritten) otherwise it will be created.  
    * 
    * @param orderItem an orderItem to save or update
    * @author tdi
    */
	public void saveOrUpdate(BOOrderItemCustomer orderItem)
	{
		saveOrUpdateOrderItemCustomer(orderItem);
	}

	public void saveOrUpdateOrderItemCustomer(BOOrderItemCustomer orderItem)
	{
		OrderitemCustomerVOA.getInstance().saveOrUpdate(orderItem.orderItemCustomer);
	}
	
	public void delete(BOOrderItemCustomer orderItem)
	{
		deleteOrderItemCustomer(orderItem);
	}
	
	public void deleteOrderItemCustomer(BOOrderItemCustomer orderItem)
	{
		OrderitemCustomerVOA.getInstance().delete(orderItem.orderItemCustomer);
	}

	public BOOrderItemPurchase findOrderItemPurchase(BOOrderPurchase orderPurchase, int itemNumber)
	{
		return findOrderItemPurchase(orderPurchase, new Integer(itemNumber));
	}

	public BOOrderItemPurchase findOrderItemPurchase(BOOrderPurchase orderPurchase, Integer itemNumber)
	{
		OrderitemPurchasePK orderItemKey = new OrderitemPurchasePK();
		orderItemKey.setOrderref(orderPurchase.orderPurchase);
		orderItemKey.setItemnumber(itemNumber);
		OrderitemPurchase orderItem = OrderitemPurchaseVOA.getInstance().get(orderItemKey);
		
		if (orderItem != null)
			return new BOOrderItemPurchase(orderItem);
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
   public List<BOOrderItemPurchase> findAllOrderItemPurchase(BOOrderPurchase orderPurchase)
	{
		List<BOOrderItemPurchase> orderItemList = new ArrayList<BOOrderItemPurchase>();		
		
		OrderitemPurchaseVOA voa = new OrderitemPurchaseVOA();
		Session session = voa.getSession();
	   Criteria crit = session.createCriteria(OrderitemPurchase.class);
		crit.add(Expression.eq("Orderref", orderPurchase.orderPurchase));
	   ArrayList<OrderitemPurchase> tempOrderItemList = (ArrayList<OrderitemPurchase>)crit.list();

	   for (OrderitemPurchase orderItem : tempOrderItemList)
		{
	   	orderItemList.add(new BOOrderItemPurchase(orderItem));
		}
		
	   return orderItemList;
	}

	public void saveOrUpdate(BOOrderItemPurchase orderItem)
	{
		saveOrUpdateOrderItemPurchase(orderItem);
	}
	
	public void saveOrUpdateOrderItemPurchase(BOOrderItemPurchase orderItem)
	{
		OrderitemPurchaseVOA.getInstance().saveOrUpdate(orderItem.orderItemPurchase);
	}
	
	public void delete(BOOrderItemPurchase orderItem)
	{
		deleteOrderItemPurchase(orderItem);
	}

	public void deleteOrderItemPurchase(BOOrderItemPurchase orderItem)
	{
		OrderitemPurchaseVOA.getInstance().delete(orderItem.orderItemPurchase);
	}
}

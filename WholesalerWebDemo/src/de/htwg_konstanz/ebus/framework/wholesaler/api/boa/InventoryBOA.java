/* InventoryBOA.java
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
import java.util.Iterator;
import java.util.List;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOInventory;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOProduct;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Inventory;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.InventoryVOA;

/**
* The {@link InventoryBOA} provides common CRUD operations (Create Read Update Delete) for the inventory entity.<p> 
* 
* The business object {@link BOInventory} represents an inventory entity with it's common attributes
* like inventory, numberToReorder or thresholdReorder.<p>
* 
* The following example code shows how to <b>load an existing inventory entity</b>, using the {@link InventoryBOA}. 
* <pre id="example">
* // load an existing product
* {@link BOProduct} product = {@link ProductBOA}.getInstance().findByMaterialNumber(materialnumber);    
* if(product == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
* 
* // load the corresponding inventory to the given product  
* {@link BOInventory} inventory =  {@link InventoryBOA}.getInstance().findByProduct(product);
* if(inventory == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
* 
* // print out inventory value
* System.out.println("Inventory Value: " + inventory.getInventory());      
* </pre>
*  
* The following example code shows how to <b>load and update an existing inventory entity</b>, using the {@link InventoryBOA}. 
* <pre id="example">
* // load an existing product
* {@link BOProduct} product = {@link ProductBOA}.getInstance().findByMaterialNumber(materialnumber);    
* if(product == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
* 
* // load the corresponding inventory to the given product  
* {@link BOInventory} inventory =  {@link InventoryBOA}.getInstance().findByProduct(product);
* if(inventory == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
* 
* // set a different inventory
* inventory.setInventory(TEST_UPDATED_WAREHOUSE_INVENTORY);
* {@link InventoryBOA}.getInstance().saveOrUpdate(inventory);
* </pre>
*  
* @author tdi
*/
public class InventoryBOA
{
	private static InventoryBOA instance = null;


	private InventoryBOA()
	{
		super();
	}

	public static InventoryBOA getInstance()
	{
		if (instance == null)
		{
			synchronized (InventoryBOA.class)
			{
				if (instance == null)
				{
					instance = new InventoryBOA();
				}
			}
		}

		return instance;
	}

   public boolean checkInventoryAmount(Integer materialNumber, Integer amount)
   {
      if(materialNumber == null || amount == null)
         return false;

      BOInventory boInventory = findByMaterialNumber(materialNumber);           
      if(boInventory == null)
         return false;
      
      if(boInventory.getInventoryNumber().compareTo(amount) >= 0)
         return true;
      
      return false;
   }

   public boolean checkInventoryAmount(int materialNumber, Integer amount)
   {
      return checkInventoryAmount(Integer.valueOf(materialNumber), amount);  
   }
   
   public boolean checkInventoryAmount(BOProduct product, Integer amount)
   {
      if(product != null && checkInventoryAmount(product.getMaterialNumber(), amount))
         return true;
         
      return false;
   }

   public BOInventory findByMaterialNumber(int materialNumber)
   {
      return findByMaterialNumber(Integer.valueOf(materialNumber));
   }
   
   public BOInventory findByMaterialNumber(Integer materialNumber)
   {
      Inventory inventory = InventoryVOA.getInstance().get(materialNumber);
      
      if (inventory == null)
         return null;
      
      return new BOInventory(inventory);
   }
	
   public BOInventory findByProduct(BOProduct product)
   {
      return findByMaterialNumber(product.getMaterialNumber());
   }

   @SuppressWarnings("unchecked")
   public List<BOInventory> findAll()
	{
		List<BOInventory> inventoryList = new ArrayList<BOInventory>();
		List<Inventory> tempInventoryList = InventoryVOA.getInstance().findAll();
		for (Iterator iter = tempInventoryList.iterator(); iter.hasNext();)
		{
         inventoryList.add(new BOInventory((Inventory)iter.next()));		
		}
		
		return inventoryList;
	}
	
   /**
    * Save or Update a given BO. If the given BO does exist, it will be updated (overwritten) otherwise it will be created.  
    * 
    * @param inventory an inventory to save or update
    * @author tdi
    */
	public void saveOrUpdate(BOInventory inventory)
	{
		InventoryVOA.getInstance().saveOrUpdate(inventory.inventory);
	}

	public void delete(BOInventory inventory)
	{
		InventoryVOA.getInstance().delete(inventory.inventory);
	}
}

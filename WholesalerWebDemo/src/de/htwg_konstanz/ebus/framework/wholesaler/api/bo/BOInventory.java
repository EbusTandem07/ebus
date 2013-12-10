/* BOInventory.java
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

import de.htwg_konstanz.ebus.framework.wholesaler.vo.Inventory;


/**
* The business object {@link BOInventory} represents an Inventory entity with it's common attributes
* like inventory, numberToReorder or thresholdReorder.
* 
* @author tdi
*/
public class BOInventory
{
   public Inventory inventory = null;
   
   
   public BOInventory()
   {
      this.inventory = new Inventory();
   }

   public BOInventory(Integer inventory)
   {
      this.inventory = new Inventory();
      this.inventory.setInventory(inventory);
   }

   public BOInventory(Inventory inventory)
   {
      this.inventory = inventory;
   }

   public Integer getProduct()
   {
      return inventory.getProduct();
   }

   public void setProduct(Integer id)
   {
      inventory.setProduct(id);
   }

   public Integer getInventoryNumber()
   {
      return inventory.getInventory();
   }

   public void setInventory(Integer inventory)
   {
      this.inventory.setInventory(inventory);
   }

   public Integer getNonPhysicalDelivery()
   {
      return inventory.getNonphysicaldelivery();
   }

   public void setNonPhysicalDelivery(Integer nonPhysicalDelivery)
   {
      inventory.setNonphysicaldelivery(nonPhysicalDelivery);
   }

   public Integer getNumberToReorder()
   {
      return inventory.getNumberToReorder();
   }

   public void setNumberToReorder(Integer numberToReorder)
   {
      inventory.setNumberToReorder(numberToReorder);
   }

   public String getRelativePathDigitalProduct()
   {
      return inventory.getRelativepathDigitalproduct();
   }

   public void setRelativePathDigitalProduct(String relativePathDigitalProduct)
   {
      inventory.setRelativepathDigitalproduct(relativePathDigitalProduct);
   }

   public Integer getThresholdReorder()
   {
      return inventory.getThresholdReorder();
   }

   public void setThresholdReorder(Integer thresholdReorder)
   {
      inventory.setThresholdReorder(thresholdReorder);
   }
}

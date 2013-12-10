/* PriceBOA.java
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

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCountry;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOProduct;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOPurchasePrice;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOSalesPrice;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.IBOPrice;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Purchaseprice;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.PurchasepricePK;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Salesprice;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.SalespricePK;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.util.Constants;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.PurchasepriceVOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.SalespriceVOA;

/**
* The {@link PriceBOA} provides common CRUD operations (Create Read Update Delete) for the sales price and purchase price entities. 
* 
* The business object {@link BOSalesPrice} represents a sales price entity with it's common attributes
* like amount, priceType, taxRate or country.<p>
*
* The business object {@link BOPurchasePrice} represents a purchase price entity with it's common attributes
* like amount, priceType, taxRate or country.<p>
* 
* The following example code shows how to <b>create and persist a new sales price entity</b> using the {@link PriceBOA}. 
* <pre id="example">
* // load an existing product
* {@link BOProduct} product = {@link ProductBOA}.getInstance().findByMaterialNumber(TEST_PRODUCT_MATERIAL_NUMBER);
* if(product == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
*
* // load an existing country
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(Constants.DEFAULT_COUNTRY_ISO_CODE);
* if(country == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
*
* // create a sales price
* {@link BOSalesPrice} salesPrice = new BOSalesPrice(TEST_PRODUCT_SALES_PRICE_AMOUNT, 
*                                      TEST_PRODUCT_SALES_PRICE_TAXRATE, 
*                                      TEST_PRODUCT_SALES_PRICE_TYPE);
* salesPrice.setProduct(product);
* salesPrice.setCountry(country);
* salesPrice.setLowerboundScaledprice(Constants.DEFAULT_LOWERBOUND_SCALED_PRICE);
* {@link PriceBOA}.getInstance().saveOrUpdateSalesPrice(salesPrice);
* </pre>
* 
* The following example code shows how to <b>load an existing sales price entity and print out some details</b>, using the CountryBOA. 
* <pre id="example">
* // load an existing product
* {@link BOProduct} product = {@link ProductBOA}.getInstance().findByMaterialNumber(TEST_PRODUCT_MATERIAL_NUMBER);
* if(product == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
*
* // load an existing country
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(Constants.DEFAULT_COUNTRY_ISO_CODE);
* if(country == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
*
* // load the sales price
* BOSalesPrice salesPrice = PriceBOA.getInstance().findSalesPrice(product, country, Constants.DEFAULT_LOWERBOUND_SCALED_PRICE);
* if(salesPrice == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
* 
* // print out some price details
* System.out.println("Sales Price Amount: " + salesPrice.getAmount());      
* System.out.println("Sales Price Taxrate: " + salesPrice.getTaxrate());      
* System.out.println("Sales Price Pricetype: " + salesPrice.getPricetype());      
* </pre>
* 
* The following example code shows how to load and <b>update an existing sales price entity</b>, using the PriceBOA. 
* <pre id="example">
* // load an existing product
* {@link BOProduct} product = {@link ProductBOA}.getInstance().findByMaterialNumber(TEST_PRODUCT_MATERIAL_NUMBER);
* if(product == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
*
* // load an existing country
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(Constants.DEFAULT_COUNTRY_ISO_CODE);
* if(country == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
*
* // load the sales price
* BOSalesPrice salesPrice = PriceBOA.getInstance().findSalesPrice(product, country, Constants.DEFAULT_LOWERBOUND_SCALED_PRICE);
* if(salesPrice == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
* 
* // change the amount 
* salesPrice.setAmount(TEST_UPDATED_PRODUCT_SALES_PRICE_AMOUNT);
* PriceBOA.getInstance().saveOrUpdateSalesPrice(salesPrice);
* </pre>
* 
* The following example code shows how to <b>delete an existing sales price entity</b>, using the PriceBOA. 
* <pre id="example">
* // load an existing product
* {@link BOProduct} product = {@link ProductBOA}.getInstance().findByMaterialNumber(TEST_PRODUCT_MATERIAL_NUMBER);
* if(product == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
*
* // load an existing country
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(Constants.DEFAULT_COUNTRY_ISO_CODE);
* if(country == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
*
* // load the sales price
* BOSalesPrice salesPrice = PriceBOA.getInstance().findSalesPrice(product, country, Constants.DEFAULT_LOWERBOUND_SCALED_PRICE);
* if(salesPrice == null)
* {    
*  // do something...
*  // e.g. leave the procedure or throw an exception...
* }
* 
* // delete the sales price
* PriceBOA.getInstance().deleteSalesPrice(salesPrice);
* </pre>
* 
* @author tdi
*/
public class PriceBOA
{
	private static PriceBOA instance = null;

	
	private PriceBOA()
	{
		super();
	}

	public static PriceBOA getInstance()
	{
		if (instance == null)
		{
			synchronized (PriceBOA.class)
			{
				if (instance == null)
				{
					instance = new PriceBOA();
				}
			}
		}

		return instance;
	}

	public List<IBOPrice> findAll()
	{
		ArrayList<IBOPrice> priceList = new ArrayList<IBOPrice>();
		
		List<Salesprice> tempSalespriceList = SalespriceVOA.getInstance().findAll();
		for (Salesprice salesprice : tempSalespriceList)
		{
			priceList.add(new BOSalesPrice(salesprice));
		}
		
		List<Purchaseprice> tempPurchasepriceList = PurchasepriceVOA.getInstance().findAll();
		for (Purchaseprice purchaseprice : tempPurchasepriceList)
		{
			priceList.add(new BOPurchasePrice(purchaseprice));
		}
		
		return priceList;
	}
	
	public BOSalesPrice findSalesPrice(BOProduct product, BOCountry country, Integer lowerboundScaledprice)
	{
		SalespricePK salespriceKey = new SalespricePK();
		salespriceKey.setCountry(country.country);
		salespriceKey.setProduct(product.product);
		salespriceKey.setLowerboundScaledprice(Constants.DEFAULT_LOWERBOUND_SCALED_PRICE);

		Salesprice salesprice = SalespriceVOA.getInstance().get(salespriceKey);
				
		if (salesprice != null)
		{
			return new BOSalesPrice(salesprice);
		} 
		
		return null;
	}

   @SuppressWarnings("unchecked")
   public List<BOSalesPrice> findSalesPrices(BOProduct product)
   {
      List<BOSalesPrice> salesPriceList = new ArrayList<BOSalesPrice>();
      
      Criteria crit = SalespriceVOA.getInstance().findFiltered("Product", product.product);
      List<Salesprice> priceList = crit.list();
      
      for(Salesprice salesprice : priceList)
      {
         salesPriceList.add(new BOSalesPrice(salesprice));
      } 

      return salesPriceList;
   }

   public BOPurchasePrice findPurchasePrice(BOProduct product, BOCountry country, Integer lowerboundScaledprice)
	{
		PurchasepricePK purchasepriceKey = new PurchasepricePK();
		purchasepriceKey.setCountry(country.country);
		purchasepriceKey.setProduct(product.product);
		purchasepriceKey.setLowerboundScaledprice(Constants.DEFAULT_LOWERBOUND_SCALED_PRICE);

		Purchaseprice purchaseprice = PurchasepriceVOA.getInstance().get(purchasepriceKey);
				
		if (purchaseprice != null)
		{
			return new BOPurchasePrice(purchaseprice);
		} 
		
		return null;
	}

   @SuppressWarnings("unchecked")
   public List<BOPurchasePrice> findPurchasePrices(BOProduct product)
   {
      List<BOPurchasePrice> purchasePriceList = new ArrayList<BOPurchasePrice>();
      
      Criteria crit = PurchasepriceVOA.getInstance().findFiltered("Product", product.product);
      List<Purchaseprice> priceList = crit.list();
      
      for(Purchaseprice puchaseprice : priceList)
      {
         purchasePriceList.add(new BOPurchasePrice(puchaseprice));
      } 

      return purchasePriceList;
   }

	public void saveOrUpdate(BOSalesPrice salesprice)
	{
		saveOrUpdateSalesPrice(salesprice);
	}

	public void saveOrUpdateSalesPrice(BOSalesPrice salesprice)
	{
		SalespricePK salespriceKey = new SalespricePK();
		salespriceKey.setCountry(salesprice.getCountry().country);
		salespriceKey.setProduct(salesprice.getProduct().product);
		salespriceKey.setLowerboundScaledprice(salesprice.getLowerboundScaledprice());

		salesprice.salesprice.setId(salespriceKey);
		SalespriceVOA.getInstance().saveOrUpdate(salesprice.salesprice);
	}

   /**
    * Save or Update a given BO. If the given BO does exist, it will be updated (overwritten) otherwise it will be created.  
    * 
    * @param purchaseprice an purchaseprice to save or update
    * @author tdi
    */
	public void saveOrUpdate(BOPurchasePrice purchaseprice)
	{
		saveOrUpdatePurchasePrice(purchaseprice);
	}

   /**
    * Save or Update a given BO. If the given BO does exist, it will be updated (overwritten) otherwise it will be created.  
    * 
    * @param purchaseprice an purchaseprice to save or update
    * @author tdi
    */
	public void saveOrUpdatePurchasePrice(BOPurchasePrice purchaseprice)
	{
		PurchasepricePK purchasepriceKey = new PurchasepricePK();
		purchasepriceKey.setCountry(purchaseprice.getCountry().country);
		purchasepriceKey.setProduct(purchaseprice.getProduct().product);
		purchasepriceKey.setLowerboundScaledprice(purchaseprice.getLowerboundScaledprice());

		purchaseprice.purchaseprice.setId(purchasepriceKey);
		PurchasepriceVOA.getInstance().saveOrUpdate(purchaseprice.purchaseprice);
	}

	public void delete(BOSalesPrice salesprice)
	{
		deleteSalesPrice(salesprice);
	}

	public void deleteSalesPrice(BOSalesPrice salesprice)
	{
		SalespriceVOA.getInstance().delete(salesprice.salesprice);
	}
	
	public void delete(BOPurchasePrice purchaseprice)
	{
		deletePurchasePrice(purchaseprice);
	}

	public void deletePurchasePrice(BOPurchasePrice purchaseprice)
	{
		PurchasepriceVOA.getInstance().delete(purchaseprice.purchaseprice);
	}
}

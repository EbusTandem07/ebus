/* BOPurchasePrice.java
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

import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.CountryBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.PriceBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.ProductBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Purchaseprice;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.PurchasepricePK;


/**
* The business object {@link BOPurchasePrice} represents a purchase price entity with it's common attributes
* like amount, priceType, taxRate or country.<p>
* 
* The following example code shows how to create and persists an purchase price entity. 
* <pre id="example">
* // a price object has a relation to a product. 
* // so, first of all load the product.
* {@link BOProduct} product = {@link ProductBOA}.getInstance().findByMaterialNumber(TEST_PRODUCT_MATERIAL_NUMBER);
* if(product == null)
* {	 
*	// do something...
*	// e.g. leave the procedure or throw an exception...
* }
* 
* // load the country
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(Constants.DEFAULT_COUNTRY_ISO_CODE);
* if(country == null)
* {	 
*	// do something...
*	// e.g. leave the procedure or throw an exception...
* }
* 
* // create a purchase price
* {@link BOPurchasePrice} purchasePrice = new BOPurchasePrice(TEST_PRODUCT_PURCHASE_PRICE_AMOUNT, 
*                                                       TEST_PRODUCT_PURCHASE_PRICE_TAXRATE, 
*                                                       TEST_PRODUCT_PURCHASE_PRICE_TYPE);
* purchasePrice.setProduct(product);
* purchasePrice.setCountry(country);
* purchasePrice.setLowerboundScaledprice(Constants.DEFAULT_LOWERBOUND_SCALED_PRICE);
* {@link PriceBOA}.getInstance().saveOrUpdatePurchasePrice(purchasePrice);
* </pre>
* 
* @author tdi
*/
public class BOPurchasePrice implements IBOPrice
{
	public Purchaseprice purchaseprice = null;
	
	
	public BOPurchasePrice()
	{
		this.purchaseprice = new Purchaseprice();
		purchaseprice.setId(new PurchasepricePK());
	}

	public BOPurchasePrice(BigDecimal amount, BigDecimal taxrate, String pricetype)
	{
		this.purchaseprice = new Purchaseprice();
		purchaseprice.setId(new PurchasepricePK());
		purchaseprice.setAmount(amount);
		purchaseprice.setTaxrate(taxrate);
		purchaseprice.setPricetype(pricetype);
	}

	public BOPurchasePrice(Purchaseprice purchaseprice)
	{
		this.purchaseprice = purchaseprice;		
	}

	public BigDecimal getAmount()
	{
		return purchaseprice.getAmount();
	}
	
	public void setAmount(BigDecimal amount)
	{
		purchaseprice.setAmount(amount);
	}
	
	public BigDecimal getTaxrate()
	{
		return purchaseprice.getTaxrate();
	}
	
	public void setTaxrate(BigDecimal taxrate)
	{
		purchaseprice.setTaxrate(taxrate);
	}
	
	public String getPricetype()
	{
		return purchaseprice.getPricetype();
	}
	
	public void setPricetype(String pricetype)
	{
		purchaseprice.setPricetype(pricetype);
	}
	
	public BOProduct getProduct()
	{
		return new BOProduct(purchaseprice.getProduct());
	}
	
	public void setProduct(BOProduct product)
	{
		purchaseprice.setProduct(product.product);
	}
	
	public BOCountry getCountry()
	{
		return new BOCountry(purchaseprice.getCountry());
	}
	
	public void setCountry(BOCountry country)
	{
		purchaseprice.setCountry(country.country);
	}
	
	public Integer getLowerboundScaledprice()
	{
		return purchaseprice.getLowerboundScaledprice();
	}
	
	public void setLowerboundScaledprice(Integer lowerboundScaledprice)
	{
		purchaseprice.setLowerboundScaledprice(lowerboundScaledprice);
	}

	public BigDecimal getAmountGross()
	{
	   return purchaseprice.getAmount().add(purchaseprice.getAmount().multiply(purchaseprice.getTaxrate()));
	}
}

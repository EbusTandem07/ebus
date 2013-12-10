/* BOSalesPrice.java
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
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Salesprice;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.SalespricePK;


/**
* The business object {@link BOSalesPrice} represents a sales price entity with it's common attributes
* like amount, priceType, taxRate or country.<p>
* 
* The following example code shows how to create and persists an sales price entity. 
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
* // create a sales price
* {@link BOSalesPrice} salesPrice = new BOSalesPrice(TEST_PRODUCT_SALES_PRICE_AMOUNT, 
*                                               TEST_PRODUCT_SALES_PRICE_TAXRATE, 
*                                               TEST_PRODUCT_SALES_PRICE_TYPE);
* salesPrice.setProduct(product);
* salesPrice.setCountry(country);
* salesPrice.setLowerboundScaledprice(Constants.DEFAULT_LOWERBOUND_SCALED_PRICE);
* {@link PriceBOA}.getInstance().saveOrUpdateSalesPrice(salesPrice);
* </pre>
* 
* @author tdi
*/
public class BOSalesPrice implements IBOPrice 
{
	public Salesprice salesprice = null;
	
	
	public BOSalesPrice()
	{
		this.salesprice = new Salesprice();
		salesprice.setId(new SalespricePK());
	}
	
	public BOSalesPrice(BigDecimal amount, BigDecimal taxrate, String pricetype)
	{
		this.salesprice = new Salesprice();
		salesprice.setId(new SalespricePK());
		salesprice.setAmount(amount);
		salesprice.setTaxrate(taxrate);
		salesprice.setPricetype(pricetype);
	}

	public BOSalesPrice(Salesprice salesprice)
	{
		this.salesprice = salesprice;		
	}

	public BigDecimal getAmount()
	{
		return salesprice.getAmount();
	}
	
	public void setAmount(BigDecimal amount)
	{
		salesprice.setAmount(amount);
	}
	
	public BigDecimal getTaxrate()
	{
		return salesprice.getTaxrate();
	}
	
	public void setTaxrate(BigDecimal taxrate)
	{
		salesprice.setTaxrate(taxrate);
	}
	
	public String getPricetype()
	{
		return salesprice.getPricetype();
	}
	
	public void setPricetype(String pricetype)
	{
		salesprice.setPricetype(pricetype);
	}
	
	public BOProduct getProduct()
	{
		return new BOProduct(salesprice.getProduct());
	}
	
	public void setProduct(BOProduct product)
	{
		salesprice.setProduct(product.product);
	}
	
	public BOCountry getCountry()
	{
		return new BOCountry(salesprice.getCountry());
	}
	
	public void setCountry(BOCountry country)
	{
		salesprice.setCountry(country.country);
	}
	
	public Integer getLowerboundScaledprice()
	{
		return salesprice.getLowerboundScaledprice();
	}
	
	public void setLowerboundScaledprice(Integer lowerboundScaledprice)
	{
		salesprice.setLowerboundScaledprice(lowerboundScaledprice);
	}
	
	public BigDecimal getAmountGross()
	{
	   return salesprice.getAmount().add(salesprice.getAmount().multiply(salesprice.getTaxrate()));
	}
}

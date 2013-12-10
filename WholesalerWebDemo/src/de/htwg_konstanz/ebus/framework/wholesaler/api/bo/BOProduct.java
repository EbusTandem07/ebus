/* BOProduct.java
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

import java.util.List;

import org.hibernate.Session;

import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.CountryBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.InventoryBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.PriceBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.ProductBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.SupplierBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.ActiveconfigInternal;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Country;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Inventory;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Product;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.util.Constants;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.ActiveconfigInternalVOA;


/**
* The business object {@link BOProduct} represents a product entity with it's common attributes
* like materialNumber, description, price, type or category.<p>
* 
* The following example code shows how to create and persists a product. 
* <pre id="example">
* // load a existing supplier
* {@link BOSupplier} supplier = {@link SupplierBOA}.getInstance().getSupplierById("1");
* 
* // create the product
* {@link BOProduct} product = new BOProduct();
* product.setShortDescription("i am the product short description");
* product.setOrderNumberSupplier("PROS001");
* product.setOrderNumberCustomer("PROC001");
* product.setSupplier(supplier);
* 
* // save the product
* {@link ProductBOA}.getInstance().saveOrUpdate(product);
* 
* // important! store the materialnumber for further use
* materialnumber = product.getMaterialNumber();
*
* // create a salesprice and assign it to the new product 		
* // 1. load a country
* {@link BOCountry} country = {@link CountryBOA}.getInstance().findCountry(Constants.DEFAULT_COUNTRY_ISO_CODE);
*		
* // 2. create a sales price
* {@link BOSalesPrice} salesPrice = new BOSalesPrice(new BigDecimal(18.7), new Integer(16), "test_price_type");
* salesPrice.setProduct(product);
* salesPrice.setCountry(country);
* salesPrice.setLowerboundScaledprice(Constants.DEFAULT_LOWERBOUND_SCALED_PRICE);
*		
* // 3. save the new sales price associated to the new product
* {@link PriceBOA}.getInstance().saveOrUpdateSalesPrice(salesPrice);
* </pre>
* 
* @author tdi
*/
public class BOProduct
{
	public Product product = null;
	public Inventory inventory = null;
	
//	private String id;
//	private String orderNumberSupplier;
//	private String orderNumberCustomer;
//	private String shortDescription;
//	private String longDescription;
//	private BOSalesPrice supplierPrice; 
//	private BOCountry supplierPriceCountry;
//	private BOSalesPrice customerPrice;
//	private BOCountry customerPriceCountry;
//	private BOCategory category;
//	private BOSupplier supplier;
//	private String manufacturer;
//	private String producttype;
//	private String eanNumber;
//	private List<BOProduct> accessory;
//	private String thumbnail;
//	private String image;
//	private List<BODocument> describingDocuments;
//	private BODocument productDocument;
//	private String supplierOrderUnit;
//	private String supplierContentUnit;
//	private String cutomerOrderUnit;
//	private String cutomerContentUnit;
//	private BigDecimal supplierNumberContentUnitPerOrderUnit;
//	private BigDecimal customerNumberContentUnitPerOrderUnit;
//	private Integer numberOfOrderUnitInStock;
//	private Integer threshold; // Schwellwert für Nachbestellung
//	private Integer reorderAmount; // Menge für Nachbestellung

	
	public BOProduct()
	{
		this.product = new Product();
		this.inventory = new Inventory(0);
      this.inventory.setProduct(product.getMaterialnumber());
	}

	public BOProduct(Product product)
	{
		this.product = product;
		
		BOInventory inventory = InventoryBOA.getInstance().findByProduct(this);
		if(inventory==null)
		   this.inventory = new Inventory(0);
		else
	      this.inventory = inventory.inventory;
		
		this.inventory.setProduct(product.getMaterialnumber());
	}
	
	public Integer getMaterialNumber()
	{
		return product.getMaterialnumber();
	}

	public void setMaterialNumber(Integer materialNumber)
	{
		product.setMaterialnumber(materialNumber);
	}
	
	public BOInventory getInventory()
	{
	   return new BOInventory(this.inventory);
	}
	
	public void setInventory(BOInventory inventory)
	{
	   this.inventory = inventory.inventory;
      this.inventory.setProduct(product.getMaterialnumber());
	}
	
   public void setInventoryAmount(Integer inventory)
   {
      this.inventory.setInventory(inventory);
   }
   
   public Integer getInventoryAmount()
   {
      return this.inventory.getInventory();
   }

//	public List<BOProduct> getAccessory()
//	{
//		return accessory;
//	}
//
//	public void setAccessory(List<BOProduct> accessory)
//	{
//		this.accessory = accessory;
//	}

	public BOCategory getCategory()
	{	
		if(product.getProductcategory() == null)
			return null;
		
		return new BOCategory(product.getProductcategory());
	}

	public void setCategory(BOCategory category)
	{
		product.setProductcategory(category.productCategory);
	}

//	public BigDecimal getCustomerNumberContentUnitPerOrderUnit()
//	{
//		return customerNumberContentUnitPerOrderUnit;
//	}
//
//	public void setCustomerNumberContentUnitPerOrderUnit(
//			BigDecimal customerNumberContentUnitPerOrderUnit)
//	{
//		this.customerNumberContentUnitPerOrderUnit = customerNumberContentUnitPerOrderUnit;
//	}
//
	public BOSalesPrice getDefaultSalesPrice()
	{
		ActiveconfigInternalVOA voa = new ActiveconfigInternalVOA();
		Session session = voa.getSession();
		ActiveconfigInternal activeConfigInternal = voa.load(1, session);
		Country country = activeConfigInternal.getConfigInternal().getWholesaleraddress().getCountry();
		BOCountry defaultCountry = new BOCountry(country);
		
		return getSalesPrice(defaultCountry);	
	}

	public BOSalesPrice getSalesPrice(BOCountry country)
	{
		return getSalesPrice(country, Constants.DEFAULT_LOWERBOUND_SCALED_PRICE);
	}

	public BOSalesPrice getSalesPrice(BOCountry country, Integer lowerboundScaledprice)
	{
		return PriceBOA.getInstance().findSalesPrice(this, country, lowerboundScaledprice);
	}

   public List<BOSalesPrice> getSalesPrices()
   {
      return PriceBOA.getInstance().findSalesPrices(this);
   }
	
	public BOPurchasePrice getDefaultPurchasePrice()
	{
		ActiveconfigInternalVOA voa = new ActiveconfigInternalVOA();
		Session session = voa.getSession();
		ActiveconfigInternal activeConfigInternal = voa.load(1, session);
		Country country = activeConfigInternal.getConfigInternal().getWholesaleraddress().getCountry();
		BOCountry defaultCountry = new BOCountry(country);
		
		return getPurchasePrice(defaultCountry);	
	}

	public BOPurchasePrice getPurchasePrice(BOCountry country)
	{
		return getPurchasePrice(country, Constants.DEFAULT_LOWERBOUND_SCALED_PRICE);
	}

	public BOPurchasePrice getPurchasePrice(BOCountry country, Integer lowerboundScaledprice)
	{
		return PriceBOA.getInstance().findPurchasePrice(this, country, lowerboundScaledprice);
	}

   public List<BOPurchasePrice> getPurchasePrices()
   {
      return PriceBOA.getInstance().findPurchasePrices(this);
   }
   
	//	public void setCustomerPrice(BOSalesPrice customerPrice, BOCountry country)
//	{
//		this.customerPrice = customerPrice;
//		this.customerPriceCountry = country;
//	}
//
//	public String getCutomerContentUnit()
//	{
//		return cutomerContentUnit;
//	}
//
//	public void setCutomerContentUnit(String cutomerContentUnit)
//	{
//		this.cutomerContentUnit = cutomerContentUnit;
//	}
//
//	public String getCutomerOrderUnit()
//	{
//		return cutomerOrderUnit;
//	}
//
//	public void setCutomerOrderUnit(String cutomerOrderUnit)
//	{
//		this.cutomerOrderUnit = cutomerOrderUnit;
//	}
//
//	public List<BODocument> getDescribingDocuments()
//	{
//		return describingDocuments;
//	}
//
//	public void setDescribingDocuments(List<BODocument> describingDocuments)
//	{
//		this.describingDocuments = describingDocuments;
//	}
//
//	public String getEanNumber()
//	{
//		return eanNumber;
//	}
//
//	public void setEanNumber(String eanNumber)
//	{
//		this.eanNumber = eanNumber;
//	}
//
//	public String getId()
//	{
//		return id;
//	}
//
//	public void setId(String id)
//	{
//		this.id = id;
//	}
//
//	public String getImage()
//	{
//		return image;
//	}
//
//	public void setImage(String image)
//	{
//		this.image = image;
//	}

	public String getLongDescription()
	{
		return product.getLongdescription();
	}

	public void setLongDescription(String longDescription)
	{
		product.setLongdescription(longDescription);
	}

	public String getManufacturer()
	{
		return product.getManufacturer();
	}

	public void setManufacturer(String manufacturer)
	{
		product.setManufacturer(manufacturer);
	}

//	public Integer getNumberOfOrderUnitInStock()
//	{
//		return numberOfOrderUnitInStock;
//	}
//
//	public void setNumberOfOrderUnitInStock(Integer numberOfOrderUnitInStock)
//	{
//		this.numberOfOrderUnitInStock = numberOfOrderUnitInStock;
//	}

	public String getOrderNumberCustomer()
	{
		return product.getOrdernumberCustomer();
	}

	public void setOrderNumberCustomer(String orderNumberCustomer)
	{
		product.setOrdernumberCustomer(orderNumberCustomer);
	}

	public String getOrderNumberSupplier()
	{
		return product.getOrdernumberSupplier();
	}

	public void setOrderNumberSupplier(String orderNumberSupplier)
	{
		product.setOrdernumberSupplier(orderNumberSupplier);
	}

//	public BODocument getProductDocument()
//	{
//		return productDocument;
//	}
//
//	public void setProductDocument(BODocument productDocument)
//	{
//		this.productDocument = productDocument;
//	}

	public String getProductType()
	{
		return product.getProducttype();
	}

	public void setProductType(String productType)
	{
		product.setProducttype(productType);
	}

//	public Integer getReorderAmount()
//	{
//		return reorderAmount;
//	}
//
//	public void setReorderAmount(Integer reorderAmount)
//	{
//		this.reorderAmount = reorderAmount;
//	}

	public String getShortDescription()
	{
		return product.getShortdescription();
	}

	public void setShortDescription(String shortDescription)
	{
		product.setShortdescription(shortDescription);
	}

	public String getShortDescriptionCustomer()
	{
		return product.getShortdescriptionCustomer();
	}

	public void setShortDescriptionCustomer(String shortDescriptionCustomer)
	{
		product.setShortdescriptionCustomer(shortDescriptionCustomer);
	}

	public String getLongDescriptionCustomer()
	{
		return product.getLongdescriptionCustomer();
	}

	public void setLongDescriptionCustomer(String longDescriptionCustomer)
	{
		product.setLongdescriptionCustomer(longDescriptionCustomer);
	}

	public String getManufacturerTypeDescription()
	{
		return product.getManufacturertypedescription();
	}

	public void setManufacturerTypeDescription(String manufacturerTypeDescription)
	{
		product.setManufacturertypedescription(manufacturerTypeDescription);
	}

	public String getProductStatus()
	{
		return product.getProductstatus();
	}
	
	public void setProductStatus(String productStatus)
	{
		product.setProductstatus(productStatus);
	}
	
	public String getRemark()
	{
		return product.getRemark();
	}
	
	public void setRemark(String remark)
	{	
		product.setRemark(remark);
	}
	
	public BOSupplier getSupplier()
	{
		return new BOSupplier(product.getSupplier());
	}

	public void setSupplier(BOSupplier supplier)
	{
		product.setSupplier(supplier.supplier);
	}

//	public String getSupplierContentUnit()
//	{
//		return supplierContentUnit;
//	}
//
//	public void setSupplierContentUnit(String supplierContentUnit)
//	{
//		this.supplierContentUnit = supplierContentUnit;
//	}
//
//	public BigDecimal getSupplierNumberContentUnitPerOrderUnit()
//	{
//		return supplierNumberContentUnitPerOrderUnit;
//	}
//
//	public void setSupplierNumberContentUnitPerOrderUnit(
//			BigDecimal supplierNumberContentUnitPerOrderUnit)
//	{
//		this.supplierNumberContentUnitPerOrderUnit = supplierNumberContentUnitPerOrderUnit;
//	}
//
//	public String getSupplierOrderUnit()
//	{
//		return supplierOrderUnit;
//	}
//
//	public void setSupplierOrderUnit(String supplierOrderUnit)
//	{
//		this.supplierOrderUnit = supplierOrderUnit;
//	}
//
//	public BOSalesPrice getSupplierPrice(BOCountry country)
//	{
//		// todo: implement helper to calculate price by country
//		return supplierPrice;
//	}
//
//	public void setSupplierPrice(BOSalesPrice supplierPrice, BOCountry country)
//	{
//		this.supplierPrice = supplierPrice;
//		this.supplierPriceCountry = country;
//	}
//
//	public Integer getThreshold()
//	{
//		return threshold;
//	}
//
//	public void setThreshold(Integer threshold)
//	{
//		this.threshold = threshold;
//	}
//
//	public String getThumbnail()
//	{
//		return thumbnail;
//	}
//
//	public void setThumbnail(String thumbnail)
//	{
//		this.thumbnail = thumbnail;
//	}
}

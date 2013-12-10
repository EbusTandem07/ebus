/* ProductBOA.java
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

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCategory;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOProduct;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Product;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.InventoryVOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.ProductVOA;

/**
 * The {@link ProductBOA} provides common CRUD operations (Create Read Update
 * Delete) for the product entity.
 * <p>
 * 
 * The business object {@link BOProduct} represents a product entity with it's
 * common attributes like materialNumber, description, price, type or category.
 * <p>
 * 
 * The following example code shows how to <b>create and persist a new product
 * entity</b> using the {@link ProductBOA}.
 * 
 * <pre id="example">
 * // load an existing supplier
 * BOSupplier supplier = SupplierBOA.getInstance().getSupplierById(TEST_SUPPLIER_ID);
 * if (supplier == null)
 * {
 *    // do something...
 *    // e.g. leave the procedure or throw an exception...
 * }
 * 
 * // create and save the product
 * BOProduct product = new BOProduct();
 * product.setShortDescription(TEST_PRODUCT_SHORT_DESCR);
 * product.setOrderNumberSupplier(TEST_PRODUCT_ORDER_NUMBER_SUPPLIER);
 * product.setOrderNumberCustomer(TEST_PRODUCT_ORDER_NUMBER_CUSTOMER);
 * product.setSupplier(supplier);
 * ProductBOA.getInstance().saveOrUpdate(product);
 * 
 * // important! store the materialnumber for further use
 * materialnumber = product.getMaterialNumber();
 * 
 * // create a salesprice -&gt; for further use in the read method      
 * // load the country
 * BOCountry country = CountryBOA.getInstance().findCountry(Constants.DEFAULT_COUNTRY_ISO_CODE);
 * if (country == null)
 * {
 *    // do something...
 *    // e.g. leave the procedure or throw an exception...
 * }
 * 
 * // create a sales price
 * BOSalesPrice salesPrice = new BOSalesPrice(TEST_PRODUCT_SALES_PRICE_AMOUNT, TEST_PRODUCT_SALES_PRICE_TAXRATE,
 *       TEST_PRODUCT_SALES_PRICE_TYPE);
 * salesPrice.setProduct(product);
 * salesPrice.setCountry(country);
 * salesPrice.setLowerboundScaledprice(Constants.DEFAULT_LOWERBOUND_SCALED_PRICE);
 * PriceBOA.getInstance().saveOrUpdateSalesPrice(salesPrice);
 * </pre>
 * 
 * The following example code shows how to <b>load an existing product
 * entity</b>, using the ProductBOA.
 * 
 * <pre id="example">
 * // load the created product
 * BOProduct product = ProductBOA.getInstance().findByMaterialNumber(materialnumber);
 * </pre>
 * 
 * The following example code shows how to <b>load and update an existing
 * product entity</b>, using the ProductBOA.
 * 
 * <pre id="example">
 * // load the created product
 * BOProduct product = ProductBOA.getInstance().findByMaterialNumber(materialnumber);
 * 
 * // set a different shortdescription
 * product.setShortDescription(TEST_UPDATED_PRODUCT_SHORT_DESCR);
 * ProductBOA.getInstance().saveOrUpdate(product);
 * </pre>
 * 
 * The following example code shows how to <b>delete an existing product
 * entity</b>, using the ProductBOA.
 * 
 * <pre id="example">
 * // load the created product
 * BOProduct product = ProductBOA.getInstance().findByMaterialNumber(materialnumber);
 * 
 * // delete the sales price first
 * BOSalesPrice salesPrice = product.getSalesPrice();
 * if (salesPrice == null)
 * {
 *    // do something...
 *    // e.g. leave the procedure or throw an exception...
 * }
 * PriceBOA.getInstance().deleteSalesPrice(salesPrice);
 * 
 * // delete the product
 * ProductBOA.getInstance().delete(product);
 * </pre>
 * 
 * @author tdi
 */
public class ProductBOA
{
   private static ProductBOA instance = null;

   private ProductBOA()
   {
      super();
   }

   public static ProductBOA getInstance()
   {
      if (instance == null)
      {
         synchronized (ProductBOA.class)
         {
            if (instance == null)
            {
               instance = new ProductBOA();
            }
         }
      }

      return instance;
   }

   public BOProduct findByMaterialNumber(int materialNumber)
   {
      return findByMaterialNumber(Integer.valueOf(materialNumber));
   }

   public BOProduct findByMaterialNumber(Integer materialNumber)
   {
      Product product = ProductVOA.getInstance().get(materialNumber);
      
      if (product == null)
         return null;

      return new BOProduct(product);
   }

   /**
    * <pre>
    * <code>
    * 	List<BOProduct> boProductList = ProductBOA.getInstance().findByShortdescription("%notebook%")
    * 	</code>
    * </pre>
    * 
    * @author tdi
    * 
    */
   public List<BOProduct> findByShortdescription(String searchString)
   {
      return findByCriteria("Shortdescription", searchString);
   }

   /**
    * <pre>
    * <code>
    * 	List<BOProduct> boProductList = ProductBOA.getInstance().findByLongdescription("%notebook%")
    * 	</code>
    * </pre>
    * 
    * @author tdi
    * 
    */
   public List<BOProduct> findByLongdescription(String searchString)
   {
      return findByCriteria("Longdescription", searchString);
   }

   /**
    * <pre>
    *  <code>
    *  BOProduct boProduct = ProductBOA.getInstance().findByOrderNumberCustomer("NB000001")
    *  </code>
    * </pre>
    * 
    * @author tdi
    * 
    */
   public BOProduct findByOrderNumberCustomer(String orderNumberCustomer)
   {
      return findByCriteriaUnique("OrdernumberCustomer", orderNumberCustomer);
   }

   /**
    * <pre>
    *  <code>
    *  BOProduct boProduct = ProductBOA.getInstance().findByOrderNumberSupplier("10")
    *  </code>
    * </pre>
    * 
    * @author tdi
    * 
    */
   public BOProduct findByOrderNumberSupplier(String orderNumberSupplier)
   {
      return findByCriteriaUnique("OrdernumberSupplier", orderNumberSupplier);
   }

   /**
    * <pre>
    * <code>
    *    List<BOProduct> boProductList = ProductBOA.getInstance().findByCriteria("Longdescription", "%notebook%")
    *    </code>
    * </pre>
    * 
    * @author tdi
    * 
    */
   @SuppressWarnings("unchecked")
   public List<BOProduct> findByCriteria(String attributeName, String searchString)
   {
      List<BOProduct> products = new ArrayList<BOProduct>();

      Session session = _BaseBOA.getInstance().getSession();
      Criteria crit = session.createCriteria(Product.class);
      crit.add(Expression.eq(attributeName, searchString));
      ArrayList<Product> productList = (ArrayList<Product>)crit.list();
      for (Product product : productList)
      {
         products.add(new BOProduct(product));
      }

      return products;
   }

   /**
    * <pre>
    * <code>
    * 	List<BOProduct> boProductList = ProductBOA.getInstance().findByCriteria("Longdescription", "%notebook%")
    * 	</code>
    * </pre>
    * 
    * @author tdi
    * 
    */
   @SuppressWarnings("unchecked")
   public List<BOProduct> findByCriteriaLike(String attributeName, String searchString)
   {
      List<BOProduct> products = new ArrayList<BOProduct>();

      Session session = _BaseBOA.getInstance().getSession();
      Criteria crit = session.createCriteria(Product.class);
      crit.add(Expression.like(attributeName, searchString));
      ArrayList<Product> productList = (ArrayList<Product>) crit.list();
      for (Product product : productList)
      {
         products.add(new BOProduct(product));
      }

      return products;
   }

   /**
    * <pre>
    *  <code>
    *  List<BOProduct> boProductList = ProductBOA.getInstance().findByCriteria("Longdescription", "%notebook%", false)
    *  </code>
    * </pre>
    * 
    * @author tdi
    * 
    */
   public BOProduct findByCriteriaUnique(String attributeName, String searchString)
   {
      ProductVOA voa = new ProductVOA();
      Session session = voa.getSession();

      Criteria crit = session.createCriteria(Product.class);
      crit.add(Expression.like(attributeName, searchString));
      Product product = (Product) crit.uniqueResult();

      if (product == null)
         return null;

      return new BOProduct(product);
   }

   public List<BOProduct> findAll()
   {
      List<BOProduct> productList = new ArrayList<BOProduct>();
      List<Product> tempProducts = ProductVOA.getInstance().findAll();
      for(Product product : tempProducts)
      {
         productList.add(new BOProduct(product));
      }

      return productList;
   }

   /**
    * <pre>
    * <code>
    * BOCategory boCategory = CategoryBOA.getInstance().getCategoryById("Alle");
    * if (boCategory != null)
    * {
    * 	List<BOProduct> boProductList = ProductBOA.getInstance().findByCategory(boCategory);
    * 	if (boProductList != null)
    * 	{
    * 		for (BOProduct boProduct : boProductList)
    * 		{
    * 			System.out.println(boProduct.getShortDescription());
    * 		}
    * 	}
    * }
    * 	</code>
    * </pre>
    */
   @SuppressWarnings("unchecked")
   public List<BOProduct> findByCategory(BOCategory category)
   {
      ProductVOA voa = new ProductVOA();
      Session session = voa.getSession();

      List<BOProduct> products = new ArrayList<BOProduct>();

      Criteria crit = session.createCriteria(Product.class);
      crit.add(Expression.eq("Productcategory", category.productCategory));
      ArrayList<Product> productList = (ArrayList<Product>)crit.list();
      for (Product product : productList)
      {
         products.add(new BOProduct(product));
      }

      return products;
   }

   /**
    * Save or Update a given BO. If the given BO does exist, it will be updated (overwritten) otherwise it will be created.  
    * 
    * @param product an product to save or update
    * @author tdi
    */
   public void saveOrUpdate(BOProduct product)
   {
      ProductVOA.getInstance().saveOrUpdate(product.product);
      product.inventory.setProduct(product.product.getMaterialnumber());
      InventoryVOA.getInstance().saveOrUpdate(product.inventory);
   }

   public void delete(BOProduct product)
   {
      ProductVOA.getInstance().delete(product.product);
   }

   public void delete(Integer materialNumber)
   {
      ProductVOA.getInstance().delete(materialNumber);
   }
}

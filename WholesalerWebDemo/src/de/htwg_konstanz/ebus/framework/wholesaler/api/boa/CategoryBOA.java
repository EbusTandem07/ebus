/* CategoryBOA.java
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

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCategory;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Productcategory;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.voa.ProductcategoryVOA;


/**
* The {@link CategoryBOA} provides common CRUD operations (Create Read Update Delete) for the category entity.<p> 
* 
* The business object {@link BOCategory} represents a category entity with it's common attributes
* like shortDescription, longDescription or parentCategory.<p>
* 
* The following example code shows how to <b>create and persist a new category entity</b> using the {@link CategoryBOA}. 
* <pre id="example">
* {@link BOCategory} category = new BOCategory();
* category.setShortDescription(TEST_CAT_SHORT_DESCR);
* category.setLongDescription(TEST_CAT_LONG_DESCR);
* {@link CategoryBOA}.getInstance().saveOrUpdate(category);
* </pre>
* 
* The following example code shows how to <b>load an existing category entity</b>, using the {@link CategoryBOA}. 
* <pre id="example">
* {@link BOCategory} category = {@link CategoryBOA}.getInstance().getCategoryById(TEST_CAT_SHORT_DESCR);	
* </pre>
* 
* The following example code shows how to <b>load and update an existing category entity</b>, using the {@link CategoryBOA}. 
* <pre id="example">
* // load the category	
* {@link BOCategory} category = {@link CategoryBOA}.getInstance().getCategoryById(TEST_CAT_SHORT_DESCR);	
* if (category != null)
* {	 	
* 	// set different long description and update
* 	category.setLongDescription(TEST_CAT_UPDATED_LONG_DESCR);
* 	{@link CategoryBOA}.getInstance().saveOrUpdate(category);
* }
* </pre>
* 
* The following example code shows how to <b>delete an existing category entity</b>, using the {@link CategoryBOA}. 
* <pre id="example">
* // load the category	
* {@link BOCategory} category = {@link CategoryBOA}.getInstance().getCategoryById(TEST_CAT_SHORT_DESCR);	
* if (category != null)
* {	 	
* 	// delete the category
* 	{@link CategoryBOA}.getInstance().delete(category);
* }
* </pre>
* 
* @author tdi
*/
public class CategoryBOA
{
	private static CategoryBOA instance = null;


	private CategoryBOA()
	{
		super();
	}

	public static CategoryBOA getInstance()
	{
		if (instance == null)
		{
			synchronized (CategoryBOA.class)
			{
				if (instance == null)
				{
					instance = new CategoryBOA();
				}
			}
		}

		return instance;
	}

	public BOCategory getCategoryById(String catId)
	{
		ProductcategoryVOA voa = new ProductcategoryVOA();
		Productcategory category = voa.get(catId);
		
		if (category == null)
			return null;
		
		return new BOCategory(category);
	}
	
   /**
    * Returns all available categories. If no record could be found, the method will return an empty list.  
    * <pre>
    * <code>
    *    List<BOCategory> boCategoryList = CategoryBOA.getInstance().findAll()
    * </code>
    * </pre>
    * 
    * @return all available categories
    * @author tdi
    */
	@SuppressWarnings("unchecked")
   public List<BOCategory> findAll()
	{
		List<BOCategory> categories = new ArrayList<BOCategory>();
		List<Productcategory> tempCategories = ProductcategoryVOA.getInstance().findAll();
		for (Iterator iter = tempCategories.iterator(); iter.hasNext();)
		{
			categories.add(new BOCategory((Productcategory)iter.next()));		
		}
		
		return categories;
	}
	
   /**
    * Returns all child categories by a given parent. If no record could be found, the method will return an empty list  
    * <pre>
    * <code>
    *    List<BOCategory> boCategoryList = CategoryBOA.getInstance().findAll()
    * </code>
    * </pre>
    * 
    * @param parentCategory an existing category
    * @return all child categories by a given parent
    * @author tdi
    */
   @SuppressWarnings("unchecked")
   public List<BOCategory> findChildren(String parentCategory)
   {
      ProductcategoryVOA voa = new ProductcategoryVOA();
      Session session = voa.getSession();
      
      Criteria crit = session.createCriteria(Productcategory.class);
      crit.add(Expression.eq("Parentcategory", parentCategory));
      
      List<BOCategory> categories = new ArrayList<BOCategory>();
      List tempCategories = crit.list();
      for (Iterator iter = tempCategories.iterator(); iter.hasNext();)
      {
         categories.add(new BOCategory((Productcategory)iter.next()));     
      }
      
      return categories;
   }

   /**
    * Save or Update a given BO. If the given BO does exist, it will be updated (overwritten) otherwise it will be created.  
    * 
    * @param category an category to save or update
    * @author tdi
    */
	public void saveOrUpdate(BOCategory category)
	{
		ProductcategoryVOA.getInstance().saveOrUpdate(category.productCategory);
	}
	
	public void delete(BOCategory category)
	{
		ProductcategoryVOA.getInstance().delete(category.productCategory);
	}
}

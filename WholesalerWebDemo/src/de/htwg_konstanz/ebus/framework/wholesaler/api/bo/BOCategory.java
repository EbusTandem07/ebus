/* BOCategory.java
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

import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.CategoryBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Productcategory;


/**
* The business object {@link BOCategory} represents a category entity with it's common attributes
* like shortDescription, longDescription or parentCategory.
* 
* The following example code shows how to create and persist a new category entity. 
* <pre id="example">
* {@link BOCategory} category = new BOCategory();
* category.setShortDescription(TEST_CAT_SHORT_DESCR);
* category.setLongDescription(TEST_CAT_LONG_DESCR);
*		
* {@link CategoryBOA}.getInstance().saveOrUpdate(category);
* </pre>
* 
* @author tdi
*/
public class BOCategory
{
	public Productcategory productCategory = new Productcategory();
	
	
	public BOCategory()
	{
		this.productCategory = new Productcategory();
	}

	public BOCategory(Productcategory productCategory)
	{
		this.productCategory = productCategory;
	}
	
	public String getShortDescription()
	{
		return productCategory.getShortdescription();
	}
	
	public void setShortDescription(String shortDescription)
	{
		productCategory.setShortdescription(shortDescription);
	}

	public String getLongDescription()
	{
		return productCategory.getDescription();
	}
	
	public void setLongDescription(String longDescription)
	{
		productCategory.setDescription(longDescription);
	}
	
	public BOCategory getParentCategory()
	{
		return new BOCategory();
	}

	public void setParentCategory(BOCategory parentCategory)
	{
		productCategory.setParentcategory(parentCategory.getShortDescription());
	}
}

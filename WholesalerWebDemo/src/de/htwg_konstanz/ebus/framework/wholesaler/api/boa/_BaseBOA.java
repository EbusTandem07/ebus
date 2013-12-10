/* _BaseBOA.java
***********************************************************************************
* 08.07.2010 ** tdi
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

import org.hibernate.Session;

import de.htwg_konstanz.ebus.framework.wholesaler.vo.base.BaseVOA;

/**
* @author tdi
*/
public class _BaseBOA
{
	private static _BaseBOA instance = null;


	private _BaseBOA(){}

	public static _BaseBOA getInstance()
	{
		if (instance == null)
		{
			synchronized (_BaseBOA.class)
			{
				if (instance == null)
				{
					instance = new _BaseBOA();
				}
			}
		}

		return instance;
	}

	public void commit()
	{
		BaseVOA.getInstance().commitTransaction();
	}
	
	public void rollback()
	{
	   BaseVOA.getInstance().rollbackTransaction();
	}
	
	public Session getSession()
	{
	   return BaseVOA.getInstance().getSession();
	}
}

/* Security.java
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
package de.htwg_konstanz.ebus.framework.wholesaler.api.security;

import java.util.Hashtable;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOUserCustomer;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOUserInternal;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOUserSupplier;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.IBOUser;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.UserBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.util.Constants;


/**
* Die Klasse Security ist f�r die Authentisierung und Autorisierung zust�ndig.<p> 
* 
* Die Authentisierung und Autorisierung kann man vereinfacht durch die drei �w� (wer, was ,wo) beschreiben. 
* Wer darf was wo? Das �wer� entspricht hierbei einem bestimmten Benutzer einer Benutzergruppe oder Rolle. 
* Das �was� entspricht der Aktion die ausgef�hrt werden soll. Also beispielsweise einen neuen Benutzer anlegen 
* oder eine Bestellung aufgeben. Das �wo� entspricht der Ressource auf die zugegriffen werden soll. Also 
* beispielsweise eine Rechnung, Bestellung oder die Benutzerverwaltung.<p>
* 
* Unter �Authentisierung� versteht man die Identifikation einer Person (Benutzer). Die Identifikation erfolgt 
* i.d.R. durch die Eingabe von �Login� und �Passwort�. Die Authentisierung ist also nach obiger Definition 
* f�r das �wer� zust�ndig.<p>
* �ber die �Autorisierung� wird der Zugriff auf die Ressourcen gesteuert. D.h.: Welche Ressourcen gibt es, 
* welche Aktionen k�nnen auf eine bestimmte Ressource angewandt werden. Und welche Berechtigung (i.d.R. eine 
* bestimmte Benutzergruppe oder Rolle) ist f�r die Ausf�hrung der jeweiligen Aktion notwenig. Die Autorisierung 
* ist also nach obiger Definition f�r das �wo� und �was� zust�ndig.<p>
* 
* <b>Rollenkonzept</b><br>
* Das eBusiness-Framework unterscheidet die Benutzer anhand Ihrer Rolle. Die vorhandenen Rollen sind 
* <ul>
* <li>User-Internal</li>
* <li>User-Customer</li>
* <li>User-Supplier</li>
* </ul>
* <p>
* <b>User-Internal</b><br>
* Ein Benutzer mit der Rolle �User-Internal� entspricht einem internen Benutzer. Dies kann beispielsweise ein 
* Administrator sein der sich um die Systempflege k�mmert. Ebenso ist der Betreiber der Plattform (Wholesaler) 
* ein interner Benutzer. Dieser ben�tigt besondere Zugriffsrechte z.B. f�r den Zugriff auf Bestellungen von 
* Kunden �ber das Shop-System. Ebenso muss er auf das Anbieter-System �Supplier� zugreifen k�nnen um beispielsweise 
* Katalogdaten auszulesen.
* <p>
* <b>User-Customer</b><br>
* Ein Benutzer mit der Rolle �User-Customer� entspricht einem Kunden. Dies kann beispielsweise ein 
* Online-Shop-Kunde sein. Ebenso kann es sich aber dabei um einen Kunden aus einem �Procurement-System� handeln.
* <p>
* <b>User-Supplier</b><br>
* Ein Benutzer mit der Rolle �User-Supplier� entspricht einem Anbieter bzw. Lieferant (Supplier). Dieser Benutzer 
* wird beispielsweise f�r die Bestellung von Waren ben�tigt. Des weiteren k�nnen mit Hilfe dieses Benutzers 
* Katalogdaten angefordert werden, welche dann in das H�ndlersystem (Wholesaler) �bertragen werden.
* <p>
* Alle Objekte (Klassen) bez�glich Authentisierung und Autorisierung befinden sich im Paket 
* de.htwg_konstanz.ebus.framework.wholesaler.api.security. F�r die Authentisierung stellt die Klasse �Security� 
* folgende Methoden zur Verf�gung:<p>
* <ul>
* <li>authenticateUser(role, login, passwd)</li>
* <li>authenticateUserCustomer(login, passwd)</li>
* <li>authenticateUserSupplier(login, passwd)</li>
* <li>authenticateUserInternal(login, passwd)</li>
* </ul>
* <p>
* Die Methode �authenticateUser� erwartet als Parameter die Rolle des Benutzers, den Login sowie das entsprechende 
* Passwort. War die Authentisierung erfolgreich, so gibt die Methode �true� zur�ck. Ansonsten �false�.<p>
* Die Methoden �authenticateUser<Role>(login, passwd) sind Rollenspezifisch. Der Teilstring <Role> muss hierbei 
* durch die entsprechende Rolle (Customer, Supplier, Internal) ersetzt werden.
* 
* @author tdi
*/
public class Security
{
	public static final String ACTION_CREATE = "c";
	public static final String ACTION_READ   = "r";
	public static final String ACTION_UPDATE = "u";
	public static final String ACTION_DELETE = "d";
	public static final String ACTION_CRUD = "crud";

	public static final String RESOURCE_ALL      = "*";
	public static final String RESOURCE_ORDER_CUSTOMER      = "order.customer";
	public static final String RESOURCE_ORDER_CUSTOMER_ITEM = "order.customer.item";
	public static final String RESOURCE_ORDER_PURCHASE      = "order.purchase";
	public static final String RESOURCE_ORDER_PURCHASE_ITEM = "order.purchase.item";
	
	@SuppressWarnings("unused")
   private Hashtable<String,String> supplierAccessTable = null;
   private Hashtable<String,String> customerAccessTable = null;
	
	private static Security instance = null;

	
	private Security()
	{
		super();
		
      supplierAccessTable = new Hashtable<String,String>();    
		customerAccessTable = new Hashtable<String,String>();
		setAccessRights();
	}

	public static Security getInstance()
	{
		if (instance == null)
		{
			synchronized (Security.class)
			{
				if (instance == null)
				{
					instance = new Security();
				}
			}
		}

		return instance;
	}

	public boolean isUserAllowed(IBOUser user, String resource, String actions)
	{
		// user internal is allowed to do anything
		if (user instanceof BOUserInternal)
			return true;
		
		if (user instanceof BOUserCustomer)
		{
			// dummy: if action is "read" --> grant access
			// todo: use the access tables
			if (actions.equalsIgnoreCase(ACTION_READ))
				return true;
		}
		
		if (user instanceof BOUserSupplier)
		{
			// dummy: if action is "read" --> grant access
			// todo: use the access tables
			if (actions.equalsIgnoreCase(ACTION_READ))
				return true;
		}

		return false;
	}
	
	public boolean authenticateUser(String login, String passwd, int role)
	{
		IBOUser user = UserBOA.getInstance().findUser(login, role);
		if (user!=null)
		{
			if (user.getPasswd().equals(passwd))
				return true;
		}
		
		return false;
	}

	public boolean authenticateUserInternal(String login, String passwd)
	{
		return authenticateUser(login, passwd, Constants.USER_INTERNAL);
	}
	
	public boolean authenticateUserCustomer(String login, String passwd)
	{
		return authenticateUser(login, passwd, Constants.USER_CUSTOMER);
	}
	
	public boolean authenticateUserSupplier(String login, String passwd)
	{
		return authenticateUser(login, passwd, Constants.USER_SUPPLIER);
	}
		
	private void setAccessRights()
	{
		customerAccessTable.put(RESOURCE_ORDER_CUSTOMER, "r");
		customerAccessTable.put(RESOURCE_ORDER_CUSTOMER_ITEM, "r");
		customerAccessTable.put(RESOURCE_ORDER_PURCHASE, "r");
		customerAccessTable.put(RESOURCE_ORDER_PURCHASE_ITEM, "r");
	}
}

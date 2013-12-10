package de.htwg_konstanz.ebus.wholesaler.main;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOAddress;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCountry;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOSupplier;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.AddressBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.CountryBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.SupplierBOA;

public class NewSupplier {

   private String supplierNumber;


    public NewSupplier(String CompanyName) throws Exception {
        // just as an example DE
        BOCountry country = CountryBOA.getInstance().findCountry("DE");
        if (country == null) {
            throw new Exception("No Country found");
        }

        // adress
        BOAddress address = new BOAddress();
        address.setStreet("TestStrasse");
        address.setZipcode("TestZipCode");
        address.setCity("Konstanz");
        address.setCountry(country);
        AddressBOA.getInstance().saveOrUpdate(address);

        // create the supplier
        BOSupplier supplier = new BOSupplier();
        supplier.setAddress(address);
        supplier.setFirstname("Media");
        supplier.setLastname("Markt");
        supplier.setCompanyname(CompanyName);
        SupplierBOA.getInstance().saveOrUpdate(supplier);
        
        this.setSupplierNumber(supplier.getSupplierNumber());


    }


    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }
}

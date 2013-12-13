package de.htwg_konstanz.ebus.wholesaler.main;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOAddress;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCountry;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOSupplier;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.AddressBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.CountryBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.SupplierBOA;
import de.htwg_konstanz.ebus.wholesaler.demo.util.Constants;

public class SupplierHelper {

    public static String saveNewSupplier(String CompanyName) throws Exception {
        // just as an example DE
        BOCountry country =
                CountryBOA.getInstance().findCountry(Constants.DEFAULT_COUNTRY_ISO_CODE);
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

        BOSupplier supplierBO = new BOSupplier();
        supplierBO.setAddress(address);
        supplierBO.setFirstname("KN Media");
        supplierBO.setLastname("Store");
        supplierBO.setWsUserName("kn_media");
        supplierBO.setWsPassword("kn_store");
        supplierBO.setWsCatalogEndpoint("http://localhost:8080/ess/ProductCatalogService");
        supplierBO.setWsOrderEndpoint("http://localhost:8080/ess/OrderService");


        supplierBO.setCompanyname(CompanyName);

        SupplierBOA.getInstance().saveOrUpdate(supplierBO);

        return supplierBO.getSupplierNumber();


    }

}

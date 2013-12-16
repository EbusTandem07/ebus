package de.htwg_konstanz.ebus.wholesaler.main;

import java.math.BigDecimal;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCountry;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOProduct;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOSalesPrice;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.PriceBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa._BaseBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Country;
import de.htwg_konstanz.ebus.wholesaler.demo.util.Constants;

public class SalesPriceHelper {
    public SalesPriceHelper() {}

    /**
     * 
     * @param nodeList
     * @param boProduct
     * @param taxValue
     * @param price_amount_value
     * @param price_type
     * 
     *        creates and saves salesPrice's from a NodeList
     */
    public static void saveSalesPrice(NodeList nodeList, BOProduct boProduct, BigDecimal taxValue,
            BigDecimal price_amount_value, String price_type) {
        for (int a = 0; a < nodeList.getLength(); a++) {

            Element isocode = (Element) nodeList.item(a);
            String iso = isocode.getFirstChild().getNodeValue();

            Country country = new Country(iso);
            BOCountry bOCountry = new BOCountry(country);

            // create a sales price
            BOSalesPrice salesPrice = new BOSalesPrice();
            salesPrice.setProduct(boProduct);
            salesPrice.setCountry(bOCountry);
            salesPrice.setLowerboundScaledprice(Constants.DEFAULT_LOWERBOUND_SCALED_PRICE);
            salesPrice.setPricetype(price_type);
            // tax-value needs to be parsed as double
            salesPrice.setTaxrate(taxValue);
            salesPrice.setAmount(price_amount_value);

            _BaseBOA.getInstance().commit();
            PriceBOA.getInstance().saveOrUpdateSalesPrice(salesPrice);


        }
    }
}

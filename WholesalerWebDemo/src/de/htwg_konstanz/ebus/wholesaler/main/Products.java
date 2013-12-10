package de.htwg_konstanz.ebus.wholesaler.main;


import java.math.BigDecimal;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCountry;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOProduct;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOPurchasePrice;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.CountryBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.PriceBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.ProductBOA;


public class Products {

    private List<BOProduct> productList;

    /**
     * constructor, if no Search argument is given.
     */
    // das als erstes
    public Products() {
        this.productList = ProductBOA.getInstance().findAll();
    }

    /**
     * 
     * constructor, if a Search argument is given.
     * 
     * @param shortDescr key word
     * 
     */
    public Products(String shortDescr) {

        this.productList = ProductBOA.getInstance().findByCriteriaLike("Shortdescription", "%" + shortDescr + "%");

        if (this.productList == null) {
            throw new IllegalArgumentException("Coudn't find Product!");
        }

    }

    /**
     * @return the productlist includes all requested products
     */
    public final List<BOProduct> getProductList() {
        return productList;
    }

    /**
     * this method creates a xml-file.
     * 
     * @return the created file
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerException
     */
    public final Document createXMLdocument() {
        Document bmeCatdoc = null;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();

            bmeCatdoc = docBuilder.newDocument();

            Element bmecat = bmeCatdoc.createElement("BMECAT");

            bmecat.setAttribute("version", "1.2");
            bmecat.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            bmecat.appendChild(headerStructure(bmeCatdoc));
            Element catalog = bmeCatdoc.createElement("T_NEW_CATALOG");
            bmecat.appendChild(catalog);

            for (BOProduct articel : this.productList) {
                String desc = articel.getShortDescription();
                catalog.appendChild(createAricle(bmeCatdoc, articel));
            }

            bmeCatdoc.appendChild(bmecat);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return bmeCatdoc;
    }

    /**
     * 
     * @param bmeCatdoc is root element in document
     * @param product
     * @return structure as xml
     */
    private Element createAricle(final Document bmeCatdoc, BOProduct product) {
        Element article = bmeCatdoc.createElement("ARTICLE");

        Element supplier_aid = bmeCatdoc.createElement("SUPPLIER_AID");
        supplier_aid.setTextContent(product.getOrderNumberCustomer());
        article.appendChild(supplier_aid);

        Element shortDescr = bmeCatdoc.createElement("ARTICLE_DETAILS");
        article.appendChild(shortDescr);

        Element description_short = bmeCatdoc.createElement("DESCRIPTION_SHORT");
        Element description_long = bmeCatdoc.createElement("DESCRIPTION_LONG");
        description_short.setTextContent(product.getShortDescription());
        description_long.setTextContent(product.getLongDescription());

        shortDescr.appendChild(description_short);
        shortDescr.appendChild(description_long);

        Element article_order_details = bmeCatdoc.createElement("ARTICLE_ORDER_DETAILS");
        Element orderUnit = bmeCatdoc.createElement("ORDER_UNIT");
        Element contentUnit = bmeCatdoc.createElement("CONTENT_UNIT");
        Element no_cu_per_ou = bmeCatdoc.createElement("NO_CU_PER_OU");

        orderUnit.setTextContent("PK");
        contentUnit.setTextContent("C62");
        no_cu_per_ou.setTextContent("10");

        article_order_details.appendChild(orderUnit);
        article_order_details.appendChild(contentUnit);
        article_order_details.appendChild(no_cu_per_ou);

        article.appendChild(article_order_details);

        List<BOCountry> county = CountryBOA.getInstance().findAll();

        Element articlePrice = bmeCatdoc.createElement("ARTICLE_PRICE_DETAILS");

        article.appendChild(articlePrice);
        for (BOCountry boC : county) {

            BOPurchasePrice boPP = PriceBOA.getInstance().findPurchasePrice(product, boC, 1);

            if (boPP != null) {
                articlePrice.appendChild((createPrices(bmeCatdoc, product, boC, boPP)));
            }
        }
        return article;
    }

    /**
     * 
     * @param bmeCatdoc Wurzelelement der Katalogs
     * @param product betreffendes Produkt
     * @param c Laender
     * @param pp Preise
     * @return Preis-Struktur als XML
     */
    private Element createPrices(Document bmeCatdoc, BOProduct product, BOCountry c,
            BOPurchasePrice pp) {

        Element article_price = bmeCatdoc.createElement("ARTICLE_PRICE");

        article_price.setAttribute("price_type", "net_list");
        Element price_amount = bmeCatdoc.createElement("PRICE_AMOUNT");
        price_amount.setTextContent(String.valueOf(pp.getAmount()));
        article_price.appendChild(price_amount);

        Element price_currency = bmeCatdoc.createElement("PRICE_CURRENCY");
        price_currency.setTextContent(c.getCurrency().getCode());
        article_price.appendChild(price_currency);

        Element tax = bmeCatdoc.createElement("TAX");

        BigDecimal big = pp.getTaxrate();
        tax.setTextContent(big + "");
        article_price.appendChild(tax);

        Element territory = bmeCatdoc.createElement("TERRITORY");
        territory.setTextContent(c.getIsocode());
        article_price.appendChild(territory);

        return article_price;
    }

    /**
     * 
     * 
     * @param bmeCatdoc
     * @return BMECat Header Element
     */
    private Element headerStructure(Document bmeCatdoc) {
        Element header = bmeCatdoc.createElement("HEADER");

        Element catalog = bmeCatdoc.createElement("CATALOG");
        Element language = bmeCatdoc.createElement("LANGUAGE");
        Element catalog_ID = bmeCatdoc.createElement("CATALOG_ID");
        Element catalog_version = bmeCatdoc.createElement("CATALOG_VERSION");
        Element catalog_name = bmeCatdoc.createElement("CATALOG_NAME");

        catalog.appendChild(language);
        language.setTextContent("deu");
        catalog.appendChild(catalog_ID);
        catalog_ID.setTextContent("HTWG-EBUT-11");
        catalog.appendChild(catalog_version);
        catalog_version.setTextContent("1.0");
        catalog.appendChild(catalog_name);
        catalog_name.setTextContent("Beispielproduktkatalog fuer E-Business Laborpraktika");

        Element supplier = bmeCatdoc.createElement("SUPPLIER");
        Element supplier_name = bmeCatdoc.createElement("SUPPLIER_NAME");
        supplier.appendChild(supplier_name);
        supplier_name.setTextContent("KN MEDIA Store");

        header.appendChild(catalog);
        header.appendChild(supplier);
        return header;
    }

}

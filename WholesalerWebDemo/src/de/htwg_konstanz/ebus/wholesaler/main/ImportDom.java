package de.htwg_konstanz.ebus.wholesaler.main;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOCountry;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOProduct;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOSalesPrice;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOSupplier;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.PriceBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.ProductBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.SupplierBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa._BaseBOA;
import de.htwg_konstanz.ebus.framework.wholesaler.vo.Country;
import de.htwg_konstanz.ebus.wholesaler.demo.util.Constants;

public class ImportDom {

    public boolean supplierExists = false;
    static Document document;
    static String schemaPath = Constants.XSD_VALIDATION_FILEPATH;
    String supplierNumber;


    public ImportDom(Document document) throws FactoryConfigurationError, Exception {

        // testUploadedFile(document);

        // get DocumentRoot -> <BMECAT>
        Element bmeCat = document.getDocumentElement();
        Element tmpSupplierName = (Element) bmeCat.getElementsByTagName("SUPPLIER_NAME").item(0);
        String supplierName = tmpSupplierName.getTextContent();

        // test if supplier already exists: !if -> createNewSupplier
        SupplierBOA supplierBOA = SupplierBOA.getInstance();

        List<BOSupplier> bOSupplierList = supplierBOA.findAll();
        BOSupplier bOSupplier = null;

        ProductBOA productBOA = ProductBOA.getInstance();
        List<BOProduct> listeBOProduct = productBOA.findAll();

        for (BOSupplier sup : bOSupplierList) {
            if (sup.getCompanyname().equals(supplierName)) {
                bOSupplier = sup;
                supplierNumber = bOSupplier.getSupplierNumber();
                break;
            }
        }

        // create new Supplier
        if (bOSupplier == null) {
            // saves new Supplier and return corresponding supplierNumber
           // supplierNumber = SupplierHelper.saveNewSupplier(supplierName);
            throw new SupplierNotExistsException();
        }

        // get all articles as Nodelist from uploaded xml
        NodeList articles = bmeCat.getElementsByTagName("ARTICLE");

        // loop over articles if exists and not null
        if (articles.getLength() > 0 && articles != null) {

            for (int i = 0; i < articles.getLength(); i++) {

                // get all values
                Element article = (Element) articles.item(i);

                Element supplier_aid =
                        (Element) article.getElementsByTagName("SUPPLIER_AID").item(0);

                String textsupplier_aid = supplier_aid.getFirstChild().getNodeValue();

                Element article_details =
                        (Element) article.getElementsByTagName("ARTICLE_DETAILS").item(0);

                Element description_short =
                        (Element) article_details.getElementsByTagName("DESCRIPTION_SHORT").item(0);

                String desShort = description_short.getFirstChild().getNodeValue();

                Element description_long =
                        (Element) article_details.getElementsByTagName("DESCRIPTION_LONG").item(0);

                String desLong = description_long.getFirstChild().getNodeValue();

                // create Product and set values
                BOProduct bOProduct = new BOProduct();


                bOProduct.setSupplier(SupplierBOA.getInstance().findSupplierById(supplierNumber));
                bOProduct.setShortDescription(desShort);
                bOProduct.setLongDescription(desLong);
                bOProduct.setOrderNumberCustomer(textsupplier_aid);
                bOProduct.setOrderNumberSupplier(textsupplier_aid);


                // delete product because update doesnt work for somereason, maybe bug
                if (listeBOProduct != null) {
                    for (BOProduct boppi : listeBOProduct) {
                        if (boppi.getOrderNumberCustomer().equals(
                                bOProduct.getOrderNumberCustomer())) {
                            productBOA.delete(boppi);

                        }
                    }
                }

                _BaseBOA.getInstance().commit();
                productBOA.saveOrUpdate(bOProduct);

                Integer materialnumber = null;
                materialnumber = bOProduct.getMaterialNumber();

                // get pricedetails from article
                Element priceDetails =
                        (Element) article.getElementsByTagName("ARTICLE_PRICE_DETAILS").item(0);

                // loop over all ARTICLE_PRICE to save in table
                NodeList articlePriceList = priceDetails.getElementsByTagName("ARTICLE_PRICE");

                if (articlePriceList.getLength() > 0 && articlePriceList != null) {
                    for (int j = 0; j < articlePriceList.getLength(); j++) {

                        Element artikelPrice = (Element) articlePriceList.item(j);

                        String price_type = artikelPrice.getAttribute("price_type");

                        Element price_amount =
                                (Element) artikelPrice.getElementsByTagName("PRICE_AMOUNT").item(0);
                        // convertElementToHtml(price_amount);
                        BigDecimal price_amount_value = getBigD(price_amount);

                        Element tax = (Element) artikelPrice.getElementsByTagName("TAX").item(0);
                        BigDecimal taxValue = getBigD(tax);

                        NodeList territoryList = artikelPrice.getElementsByTagName("TERRITORY");

                        // save saleprice for each territory
                        SalesPriceHelper.saveSalesPrice(territoryList, bOProduct, taxValue,
                                price_amount_value, price_type);

                    }

                }
            }
        }
    }

    private void testUploadedFile(Document document) {
        try {
            DOMSource domSource = new DOMSource(document);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            System.out.println(writer.toString());
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }
    }

    public void convertElementToHtml(Element element) throws TransformerException {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter sw = new StringWriter();
        t.transform(new DOMSource(element), new StreamResult(sw));
        System.out.println(sw.toString());
    }

    public BigDecimal getBigD(Element element) {
        double tempPRICE_AMOUNT = Double.valueOf(element.getFirstChild().getNodeValue());
        BigDecimal price_amount_value = BigDecimal.valueOf(tempPRICE_AMOUNT);
        return price_amount_value;
    }

}

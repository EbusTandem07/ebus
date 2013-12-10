package de.htwg_konstanz.ebus.wholesaler.main;

import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOProduct;
import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOSupplier;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.SupplierBOA;
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
        SupplierBOA sboa = SupplierBOA.getInstance();

        List<BOSupplier> listeBOSupplier = sboa.findAll();

        for (BOSupplier supplier : listeBOSupplier) {
            if (supplier.getCompanyname().equals(supplierName)) {
                supplierExists = true;
                
                //TODO hier muss noch Liste gepackt werden und abfrage nach supplier ob in Liste nur 1 element
//                BOSupplier supplierDB = (BOSupplier) SupplierBOA.getInstance().findByCompanyName(supplier.getCompanyname());
//                supplierNumber = supplierDB.getSupplierNumber();
                break;
            }
        }

        // create new Supplier
        if (!supplierExists) {
            NewSupplier nS = new NewSupplier(supplierName);
            supplierNumber = nS.getSupplierNumber();
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


    public static void artikeln(Element artikel, BOProduct bp, int materialnumber, String desShort,
            String supplierNummer) {


    }



}

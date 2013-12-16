package de.htwg_konstanz.ebus.wholesaler.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;

import de.htwg_konstanz.ebus.wholesaler.demo.IAction;
import de.htwg_konstanz.ebus.wholesaler.demo.util.Constants;
import de.htwg_konstanz.ebus.wholesaler.main.Products;

public class ExportAction implements IAction {


    public String execute(HttpServletRequest request, HttpServletResponse response,
            ArrayList<String> errorList) {
        Products all;
        ByteArrayOutputStream out = null;


        // apache string lang lib - if parameter search is set
        if (StringUtils.isNotBlank((String) request.getParameter("search"))) {
            String search = (String) request.getParameter("search");
            all = new Products(search);
        } else {
            all = new Products();
        }

        try {

            Document doc = all.createXMLdocument();
            DOMSource domSource = new DOMSource(doc);
            out = new ByteArrayOutputStream();
            String type = "";
            String option = request.getParameter("exportType");
            String applicationType = "";
            
            // check radioButton if XML was selected, otherwise make XHTML export
            if (option.equals("XML")) {

                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

                transformer.transform(domSource, new StreamResult(out));
                type = "attachment; filename=exportProduct.xml";
                applicationType = "application/xml";

            } else {

                Source xslt =
                        new StreamSource(
                                "C:\\xml\\style3.xsl");

                Transformer xformer = TransformerFactory.newInstance().newTransformer(xslt);
                xformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
                xformer.transform(domSource, new StreamResult(out));
                type = "attachment; filename=exportProduct.xhtml";
                applicationType = "application/xhtml";
            }

            response.setHeader("Content-Disposition", type);
            response.setContentType(applicationType);
            response.setContentLength(out.size());
            response.getWriter().write(out.toString());
            

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (TransformerFactoryConfigurationError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return null;

    }

    public boolean accepts(String actionName) {
        return actionName.equalsIgnoreCase(Constants.PARAM_EXPORT);
    }
}

package de.htwg_konstanz.ebus.wholesaler.action;

import java.util.ArrayList;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.htwg_konstanz.ebus.framework.wholesaler.api.bo.BOProduct;
import de.htwg_konstanz.ebus.framework.wholesaler.api.boa.ProductBOA;
import de.htwg_konstanz.ebus.wholesaler.demo.IAction;
import de.htwg_konstanz.ebus.wholesaler.demo.util.Constants;
import de.htwg_konstanz.ebus.wholesaler.main.*;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.cglib.core.Transformer;

import org.w3c.dom.Document;

public class XhtmlTransformationAction implements IAction{

	
	public XhtmlTransformationAction()
	{
		super();
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response, ArrayList<String> errorList)
	{
		
		
		
		List<BOProduct> productList = ProductBOA.getInstance().findAll();
		List<?> productListTwo = ProductBOA.getInstance().findByShortdescription("Protest Bikini");
		System.out.println("Liste search "+productListTwo.size());
		for (BOProduct prod : productList) {
			System.out.println("Liste "+productList.size()+" ShortDescription " + prod.getShortDescription());	
			
		}
		
		Products all;
		all = new Products();
		
		Document doc = all.createXMLdocument();
		DOMSource domSource = new DOMSource(doc);
		
			try {
				// Der relative Pfad scheint nicht zu funktionieren
				//Source xslt = new StreamSource("..\\files\\style2.xsl");
				Source xslt = new StreamSource("C:/xml/style2.xsl");
				
				javax.xml.transform.Transformer xformer = TransformerFactory.newInstance().newTransformer(xslt);
				xformer.setOutputProperty(OutputKeys.ENCODING, "iso-8859-1");
				xformer.transform(domSource, new StreamResult(response.getWriter()));
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerFactoryConfigurationError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			return null;
		
	


	}

    public boolean accepts(String actionName) {
        return actionName.equalsIgnoreCase(Constants.PARAM_XHTMLTRANSFORMATION);
    }

}

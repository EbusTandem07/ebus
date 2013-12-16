package de.htwg_konstanz.ebus.wholesaler.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.htwg_konstanz.ebus.wholesaler.demo.IAction;
import de.htwg_konstanz.ebus.wholesaler.demo.util.Constants;
import de.htwg_konstanz.ebus.wholesaler.exceptions.NoFileChosenException;
import de.htwg_konstanz.ebus.wholesaler.exceptions.SupplierNotExistsException;
import de.htwg_konstanz.ebus.wholesaler.main.ImportDom;

public class UploadAction implements IAction {

    public UploadAction() {
        super();
    }

    public String execute(HttpServletRequest request, HttpServletResponse response,
            ArrayList<String> errorList) {

        InputStream in = null;

        // process only if its multiPart content
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);

                List<FileItem> items = upload.parseRequest(request);

                // Process the uploaded items
                Iterator<FileItem> iter = items.iterator();

                while (iter.hasNext()) {
                    FileItem item = iter.next();

                    // Process a file upload
                    if (!item.isFormField()) {

                        String fileName = item.getName();
                        if (fileName.length() == 0) {
                            throw new NoFileChosenException();
                        }
                        // xsd, xslt are also text/xml
                        String extension =
                                fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

                        // just in case if its needed
                        String contentType = item.getContentType();
                        boolean isInMemory = item.isInMemory();
                        long sizeInBytes = item.getSize();

                        in = item.getInputStream();
                        // xsd is in root directory
                        File schemaPath = new File(Constants.XSD_VALIDATION_FILEPATH);
                        SchemaFactory factorySchema =
                                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                        Schema schema1;
                        Document document;

                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                        dbf.setNamespaceAware(true);
                        DocumentBuilder parser = dbf.newDocumentBuilder();
                        document = parser.parse(in);

                        // check uploaded filetype: sure, we could use just=> if not xml :)
                        if (!contentType.equals("text/xml") || !extension.equals("xml")) {
                            errorList.add("not a xml file or no file choosed");
                            return "importResult.jsp";
                        }

                        schema1 = factorySchema.newSchema(schemaPath);
                        Validator validator = schema1.newValidator();

                        // throws SaxException/IOException
                        validator.validate(new DOMSource(document));

                        // File uploaded should be successful here
                        try {
                            new ImportDom(document);
                        } catch (FactoryConfigurationError e) {
                            errorList.add("couldnt convert");
                        } catch (SupplierNotExistsException e) {
                            errorList.add("Supplier not found in database");
                        } catch (Exception e) {
                            errorList
                                    .add("error occured, some Elements couldnt be found in database");
                        }
                    }
                }
                // render Result, all errors are already in errorList
                return "importResult.jsp";

            } catch (FileUploadException ex) {
                errorList.add("File Upload Failed due to " + ex);
            } catch (IOException ex) {
                // validate
                errorList.add("ValidationError " + ex);
            } catch (ParserConfigurationException ex) {
                // from DocumentBuilder
                errorList.add("DocumentBuilder Error: " + ex);
            } catch (SAXException ex) {
                // from validate
                errorList.add("ValidationError " + ex);
            } catch (NoFileChosenException e) {
                errorList.add("no file chosen");
            }

        } else {
            errorList.add("Sorry this Servlet only handles file upload request");
        }
        return "importResult.jsp";

    }

    public boolean accepts(String actionName) {
        return actionName.equalsIgnoreCase(Constants.ACTION_UPLOAD);
    }

}

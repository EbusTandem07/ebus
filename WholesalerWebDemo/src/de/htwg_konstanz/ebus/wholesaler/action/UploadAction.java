package de.htwg_konstanz.ebus.wholesaler.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import de.htwg_konstanz.ebus.wholesaler.demo.IAction;
import de.htwg_konstanz.ebus.wholesaler.demo.util.Constants;

public class UploadAction implements IAction {

	public UploadAction() {
		super();
	}

	public String execute(HttpServletRequest request,
			HttpServletResponse response, ArrayList<String> errorList) {

		String parserName = "null";
		InputStream in = null;

		// process only if its multipart content
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
						String fieldName = item.getFieldName();
						String fileName = item.getName();
						//xsd, xslt are also text/xml
						String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
						//just in case if its needed
						String contentType = item.getContentType();
						boolean isInMemory = item.isInMemory();
						long sizeInBytes = item.getSize();
						System.out.println(item.getName());
						//check uploaded filetype: sure, we could use just=> if not xml :)
						if(!contentType.equals("text/xml") || !extension.equals("xml")) {
							errorList.add("not a xml file or no file choosed");
							return "importResult.jsp";
						}
					}
					
				}
				// File uploaded should be successful here
				return "importResult.jsp";
			} catch (Exception ex) {
				errorList.add("File Upload Failed due to " + ex);
				return "importResult.jsp";
			}

		} else {
			errorList
					.add("Sorry this Servlet only handles file upload request");
			return "importResult.jsp";
		}

	}

	public boolean accepts(String actionName) {
		return actionName.equalsIgnoreCase(Constants.PARAM_UPLOAD);
	}

}

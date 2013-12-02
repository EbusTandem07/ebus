<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>eBusiness Framework Demo - Import</title>
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="pragma" content="no-cache">
<link rel="stylesheet" type="text/css" href="default.css">
</head>
<body>
	<%@ include file="header.jsp"%>
	<%@ include file="error.jsp"%>
	<%@ include file="authentication.jsp"%>
	<%@ include file="navigation.jspfragment"%>

	<h1>Import</h1>

	<br />
	
	<FORM
		ACTION="<%= response.encodeURL("controllerservlet?action=upload") %>"
		METHOD="post" enctype="multipart/form-data">

		<input name="Datei" type="file" size="50" maxlength="100000"
			accept="application/xml"> 
			<br />
		<input type="submit" value="Importieren">


	</FORM>
	
</body>
</html>


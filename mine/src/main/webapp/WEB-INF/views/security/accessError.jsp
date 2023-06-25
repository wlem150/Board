<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1><c:out value="${SPRING_SECURITY_403_EXCEPTION.getMessage() }"></c:out></h1>
<h2><c:out value="${msg }"/></h2>
</body>
</html>

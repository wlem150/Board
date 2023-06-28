<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>Custom Login Page</h1>
<h2><c:out value="${error }"/></h2>
<h2><c:out value="${logout }"/></h2>

<form action="/login" method="post">
	<div>
		<input type='text' name='username' value='admin'>
	</div>
	
	<div>
		<input type='password' name='password' value='admin'>
	</div>
	
	<div>
		<input type='checkbox' name='remember-me' >Remember-Me
	</div>
	
	
	
	<div>
		<input type='submit'>
	</div>
	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
</form>
</body>
</html>

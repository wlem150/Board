<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" language="java" pageEncoding="utf-8" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
Logout page
</h1>

<form action="/customLogout" method="post">
	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
	<butto>로그아웃</button>
</form>
</body>
</html>

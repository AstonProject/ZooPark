<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Login Zoopark</title>
</head>
<body>
	<h1>Login Zoopark</h1>
	
	<c:forEach items = "${messages }" var="message" >
		<p>${message }</p>
	</c:forEach>
</body>
</html>
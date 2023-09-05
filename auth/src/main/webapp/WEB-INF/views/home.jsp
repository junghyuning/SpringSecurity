<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SPRING</title>
</head>
<body>
	<h1>메인페이지</h1>
	<hr>
	<h3><a href="<c:url value="/user/page"/>">User</a></h3>
	<h3><a href="<c:url value="/manager/page"/>">Manager</a></h3>
	<h3><a href="<c:url value="/admin/page"/>">Administrator</a></h3>
</body>
</html>
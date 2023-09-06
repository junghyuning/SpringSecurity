<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Security 태그 라이브러리를 JSP 문서에 포함 - Spring Security 태그 사용 가능 --%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SPRING</title>
</head>
<body>
	<h1>메인페이지</h1>
	<hr>
	<h3>
		<a href="<c:url value="/user/page"/>">User</a>
	</h3>
	<h3>
		<a href="<c:url value="/manager/page"/>">Manager</a>
	</h3>
	<h3>
		<a href="<c:url value="/admin/page"/>">Administrator</a>
	</h3>
	<hr>
	<%-- authorize : 권한을 비교하여 비교하여 태그의 포함 여부를 설정하기 위한 태그 --%>
	<%-- access 속성 : 권한(Role)을 속성값으로 설정 - SpEL 사용 가능 --%>
	<%-- 인증을 받지 않은 사용자인 경우 태그 포함되도록 설정 --%>
	<sec:authorize access="isAnonymous()">
		<h3>
			<a href="<c:url value="/login"/>">로그인</a>
		</h3>
	</sec:authorize>
	
	<%-- 인증을 받지않은 사용자의 경우 태그가 포함되도록 설정 --%>
	<sec:authorize access="isAuthenticated()"/>

	<%-- 인증된 사용자인 경우 태그가 포함되도록 설정 --%>
	<sec:authorize access="isAuthenticated()">
		<%-- 로그아웃 처리 기능을 제공하는 페이지는 반드시 form 태그를 사용하여 요청 --%>
		<%-- =>CSRF 토큰을 전달하여 처리되도록 설정 --%>
		<form action="<c:url value="/logout"/>" method="post">
			<sec:csrfInput />
			<button type="submit">로그아웃</button>
		</form>
	</sec:authorize>
	<hr>
	
	
	<%-- authentication 태그 : 인증된 사용자(UserDetails 각체)에 정보를 제공하기위한 태그--%>
	<%-- => 인증된 사용자에게만 필요한 정보를 제공 가능 --%>
	<%-- property 속성 : 인증된 사용자(UserDetails 객체)에 정보를 제공받아  --%>
	<sec:authorize access="isAuthenticated()">
		<%-- <h3><sec:authentication property="priciple"/></h3> --%>
		<h3><sec:authentication property="priciple.username"/></h3>
	</sec:authorize>

</body>
</html>
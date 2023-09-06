<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SPRING</title>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body>
	<h1>로그인</h1>
	<hr>
	<form action="<c:url value="/login"/>" method="post" id="loginForm">
	<table>
		<tr>
			<td>아이디</td>
			<td><input type="text" name="userid" id="userid"></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="passwd" id="passwd"></td>
		</tr>
		<tr>
			<td colspan="2"><button type="submit">로그인</button></td>
		</tr>		
	</table>		
	<%-- CSRF 공격을 방어하기 위해 Spring Security에 의해 발급된 CSRF Token을 hidden 타입으로 전달 --%>
	<%-- => 서버에 전달된 요청이 실제 서버에서 허용된 요청이 맞는지를 확인하기위해 CSRF Token 발행 --%>
	<%-- => 서버에서는 뷰페이지를 "생성할때마다" 랜덤으로 토큰을 발행하여 세션에 저장하고 사용자가 서버에 페이지를 요청할 때 Hidden 타입으로 토큰을 서버에 전달하여 세션의 저장된 토큰과 비교하여 사용자를 확인  --%>
	<%-- CSRF(Cross-Site Request Forgery) 공격 : 사이트간 요청을 위조하는 공격 --%>
	<%--<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"> --%>
	<%-- sec 태그 라이브러리를 사용하면 간단하게 처리 가능 --%>
	<%-- csrfInput : CSRF Token을 hidden 타입으로  --%>
	<sec:csrfInput/>
	</form>
	<%-- SPRING_SECURITY_LAST_EXCEPTION : Spring Security에 의해 마지막에 발생된 예외가 세션의 속성값으로 저장된 세션의 속성명 --%>
	<c:if test="{not empty SPRING_SECURITY_LAST_EXCEPTION}">
		<h3 style="color: red">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message }</h3>
		<%-- 예외가 저장된 세션의 속성값 삭제 -> 다른곳에서 쓸 수 없음  --%>
		<c:remove var="SPRING_SECURITY_LAST_EXCEPTION"/>
	</c:if>
</body>
</html>








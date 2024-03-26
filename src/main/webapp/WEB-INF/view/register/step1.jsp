<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="member.register" /></title>
</head>
<body>
	<h2><spring:message code="term" /></h2>
	<p>약관 내용</p>
	<form action="step2" method="post">
	<label>
		<input type="checkbox" name="agree" value="true">
		<spring:message code="term.agree" />
	</label>
	<input type="submit" value="<spring:message code='next.btn' />" />
	</form>
</body>
</html>
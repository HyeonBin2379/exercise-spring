<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
    <head>
        <title><spring:message code="change.pwd.title"/></title>
    </head>
    <body>
        <p>
            <spring:message code="change.pwd.done"/>
        </p>
        <p>
            <a href="<c:url value='/main'/>">
                [<spring:message code="go.main"/>]
            </a>
        </p>
    </body>
</html>
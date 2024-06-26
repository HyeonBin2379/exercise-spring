<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
    <head>
        <title><spring:message code="login.title"/></title>
    </head>
    <body>
        <form:form modelAttribute="loginCommand">
            <form:errors />
            <p>
                <label><spring:message code="email"/><br>
                    <form:input path="email"/>
                    <form:errors path="email"/>
                </label>
            </p>
            <p>
                <label><spring:message code="password"/><br>
                    <form:password path="password"/>
                    <form:errors path="password"/>
                </label>
            </p>
            <p>
                <label><spring:message code="rememberEmail"/>
                    <form:checkbox path="rememberEmail"/>
                </label>
            </p>
            <input type="submit" value="<spring:message code='login.btn'/>"/>
        </form:form>
    </body>
</html>
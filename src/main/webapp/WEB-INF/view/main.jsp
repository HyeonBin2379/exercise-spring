<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>메인</title>
    </head>
    <body>
        <p>환영합니다.</p>
        <p><a href="<c:url value='/register/step1'/>">[회원 가입하기]</a></p>
        <p><a href="<c:url value='/survey'/>">[설문조사 참여하기]</a></p>
    </body>
</html>
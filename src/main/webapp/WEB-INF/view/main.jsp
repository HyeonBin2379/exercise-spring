<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>메인</title>
    </head>
    <body>
        <c:if test="${empty authInfo}">
            <p>환영합니다.</p>
            <p>
                <a href="<c:url value='/register/step1'/>">[회원 가입하기]</a>
                <a href="<c:url value='/login'/>">[로그인하기]</a>
            </p>
        </c:if>
        
        <c:if test="${! empty authInfo}">
            <p>${authInfo.name}님, 환영합니다.</p>
            <p>
                <a href="<c:url value='/survey'/>">[설문조사 참여하기]</a>
                <a href="<c:url value='/edit/changePassword'/>">[비밀번호 변경]</a>
                <a href="<c:url value='/logout'/>">[로그아웃]</a>
            </p>
        </c:if>
    </body>
</html>
<%-- 
    Document   : registrationCompleted
    Created on : Apr 25, 2020, 3:14:11 AM
    Author     : nklad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test page after account creation</title>
    </head>
    <body>
        <h2>User registered successfully!</h2>
        <hr>
        <a href="${pageContext.request.contextPath}/showLogin">Login with new user</a>
    </body>
</html>

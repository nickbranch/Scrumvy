<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%-- 
    Document   : home
    Created on : Apr 24, 2020, 10:04:01 PM
    Author     : nklad
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Scrumvy Projects Home Page</title>
    </head>

    <body>
        <h2>Scrumvy Company Home Page</h2>
        <hr>
        <p>Welcome to the Scrumvy home page!</p>
        <hr>
        <hr>
        <!-- display user name and role -->
        <div>
            <p>
                User:
            <security:authentication property="principal.username" />
            <br> <br> Role(s)
            <security:authentication property="principal.authorities" />
        </p>
        <br><br>
        <hr>
        <security:authorize access="hasRole('ADMIN')">

            <!-- Add a link to point to /systems... this is for the managers -->
            <p>
                <a href="${pageContext.request.contextPath}/systems">IT Systems
                    Meeting</a> (only for admin peeps)
            </p>
        </security:authorize>

        <!--  Add log out button -->
        <form:form action="${pageContext.request.contextPath}/logout"
                   method="POST">
            <input type="submit" value="Logout">
        </form:form>

        </body>
        </html>

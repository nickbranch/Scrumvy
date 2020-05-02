<%-- 
    Document   : registrationForm
    Created on : Apr 25, 2020, 3:07:33 AM
    Author     : nklad
--%>

<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Scrumvy - Create an account</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet"
              id="bootstrap-css">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body>
        <div class="container">
            <div id="mainRow" class="row">

                <div id="left" class="col-lg-4">
                    <div id="contentleft">
                        <img class="img-fluid" src="${pageContext.request.contextPath}/img/6.png" alt="scrumvy-logo">
                        <h2><strong>Welcome to Scrumvy</strong></h2>
                        <h3><strong>Scrum in.</strong></h3>
                    </div>
                </div>

                <div id="right" class="col-lg-8">
                    <div class="login-form">
                        <!-- Registration Form -->
                        <form:form action="${pageContext.request.contextPath}/register/processRegistrationForm" method="POST"
                                   modelAttribute="userDto"
                                   class="form-horizontal">
                            
                            <div class="form-group">
                                <div class="col-xs-15">
                                    <div>
                                        <!-- Check for registration error -->
                                        <c:if test="${registrationError != null}">
                                            <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                                ${registrationError}
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- User name -->
                            <div class="input-group bottom-fix">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 
                                <form:input path="userName" placeholder="username (*)" class="form-control" /><br>
                            </div>
                            <form:errors path="userName" cssClass="error" />

                            <!-- Password -->
                            <div class="input-group bottom-fix">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 
                                <form:password path="password" placeholder="password (*)" class="form-control" /><br>
                            </div>
                            <form:errors path="password" cssClass="error" />

                            <!-- Confirm Password -->
                            <div class="input-group bottom-fix">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 
                                <form:password path="passwordConfirm" placeholder="confirm password (*)" class="form-control" /><br>
                            </div>
                            <form:errors path="passwordConfirm" cssClass="error" />

                            <!-- First name -->
                            <div class="input-group bottom-fix">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 
                                <form:input path="firstName" placeholder="first name (*)" class="form-control" /><br>
                            </div>
                            <form:errors path="firstName" cssClass="error" />

                            <!-- Last name -->
                            <div class="input-group bottom-fix">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 
                                <form:input path="lastName" placeholder="last name (*)" class="form-control" /><br>
                            </div>
                            <form:errors path="lastName" cssClass="error" />

                            <!-- Email -->
                            <div class="input-group bottom-fix">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span> 
                                <form:input path="email" placeholder="email (*)" class="form-control" /><br>
                            </div>
                            <form:errors path="email" cssClass="error" />

                            <!-- Register Button -->
                            <div style="margin-top: 10px" class="form-group">						
                                    <button type="submit" class="btn btn-primary">Register</button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
    </body>

</html>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Scrumvy Register - Create an Account</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet"
              id="bootstrap-css">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    </head>
    <body>
        <div class="container-fluid">
            <div class="row no-gutter">
                <!-- The image half -->
                <div class="col-md-6 d-none d-md-flex bg-image"></div>

                <!-- The content half -->
                <div class="col-md-6 bg-light">
                    <div class="login d-flex align-items-center py-5">

                        <!-- Demo content-->
                        <div class="container">
                            <div class="row">
                                <div class="col-lg-10 col-xl-7 mx-auto">
                                    <h3 class="display-4">Welcome to Scrumvy!</h3>
                                    <p class="text-muted mb-4">Please complete the fields to register your account.</p>
                                    <form:form action="${pageContext.request.contextPath}/register/processRegistrationForm" method="POST"
                                               modelAttribute="userDto"
                                               class="form-horizontal">
                                        <div class="form-group mb-3">
                                            <div>
                                                <!-- Check for registration error -->
                                                <c:if test="${registrationError != null}">
                                                    <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                                        ${registrationError}
                                                    </div>
                                                </c:if>
                                            </div>
                                            <form:input path="userName" placeholder="Username*" 
                                                        autofocus="" cssClass="form-control rounded-pill border-0 shadow-sm px-4"/>
                                        </div>
                                        <form:errors path="userName" cssClass="error" />

                                        <div class="form-group mb-3">
                                            <form:password path="password" placeholder="Password*"
                                                           cssClass="form-control rounded-pill border-0 shadow-sm px-4 text-primary"/>
                                        </div>
                                        <form:errors path="password" cssClass="error" />
                                        <div class="form-group mb-3">
                                            <form:password path="passwordConfirm" placeholder="Confirm Password*" 
                                                           cssClass="form-control rounded-pill border-0 shadow-sm px-4 text-primary"/>
                                            <form:errors path="password" cssClass="error" />
                                        </div>
                                        <div class="form-group mb-3">
                                            <form:input path="firstName" placeholder="First Name*" 
                                                        cssClass="form-control rounded-pill border-0 shadow-sm px-4 text-primary"/>
                                        </div>
                                        <form:errors path="firstName" cssClass="error" />
                                        <div class="form-group mb-3">
                                            <form:input path="lastName"  placeholder="LastName*" 
                                                        cssClass="form-control rounded-pill border-0 shadow-sm px-4 text-primary"/>
                                        </div>
                                        <form:errors path="lastName" cssClass="error" />
                                        <div class="form-group mb-3">
                                            <form:input path="email" placeholder="Email*"
                                                        cssClass="form-control rounded-pill border-0 shadow-sm px-4 text-primary"/>
                                        </div>

                                        <form:errors path="email" cssClass="error" />
                                        <br>
                                        <button type="submit" class="loginButton">Register Now!</button>
                                        <br>                                    
                                        <div class="text-center d-flex justify-content-between mt-4">
                                        </div>
                                    </form:form>

                                    <button type="submit" class="cancelButton mt-2">Cancel</button>
                                </div>
                            </div>
                        </div><!-- End -->
                    </div>
                </div><!-- End -->

            </div>
        </div>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>

    </body>

</html>
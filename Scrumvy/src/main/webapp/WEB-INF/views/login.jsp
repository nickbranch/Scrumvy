<%-- 
    Document   : login
    Created on : Apr 24, 2020, 7:42:16 PM
    Author     : nklad
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Scrumvy Log in</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet"
              id="bootstrap-css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
    </head>

    <body>
        <div class="container">
            <div id="mainRow" class="row">

                <div id="left" class="col-lg-4">
                    <div id="contentleft">
                        <img class="img-fluid" src="/img/6.png" alt="scrumvy-logo">
                        <h2><strong>Welcome to Scrumvy</strong></h2>
                        <h3><strong>Scrum in.</strong></h3>
                    </div>
                </div>

                <div id="right" class="col-lg-8">
                    <div class="login-form">
                        <!-- Login Form -->
                        <form action="${pageContext.request.contextPath}/authentication" method="POST"
                              class="form-horizontal">
                            <h3 id="hiddenParagraph">Welcome to Scrumvy</h3>

                            <!-- User name -->
                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                <input type="text" name="username" placeholder="username" class="form-control">
                            </div>

                            <!-- Password -->
                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                <input type="password" name="password" placeholder="password" class="form-control">
                            </div>

                            <!-- Login/Submit Button -->
                            <div style="margin-top: 10px" class="form-group">
                                <button id="lgnbtn" type="submit" class="btn btn-black">Scrum in</button>
                            </div>

                            <!-- manually adding tokens csrf protection -->
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        </form>

                        <a id ="regbtn" href="${pageContext.request.contextPath}/register/showRegister" class="btn btn-primary" role="button"
                           aria-pressed="true">Register</a>
                    </div>
                    <div id="loginError" class="form-group">
                        <div class="col-xs-15">
                            <c:if test="${param.error != null}">
                                <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                    Incorrect username or password</div>
                                </c:if>

                            <c:if test="${param.logout != null}">
                                <div class="alert col-xs-offset-1 col-xs-10">
                                    You are logged out.</div>
                                </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
    </body>

</html>
<%-- 
    Document   : login
    Created on : Apr 24, 2020, 7:42:16 PM
    Author     : nklad
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Scrumvy Login</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div>
            <div id="loginbox" style="margin-top: 50px;"
                 class="mainbox col-md-3 col-md-offset-2 col-sm-6 col-sm-offset-2">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <div class="panel-title">Log in</div>
                    </div>
                    <div style="padding-top: 30px" class="panel-body">

                        <!-- Login Form -->
                        <form
                            action="${pageContext.request.contextPath}/authentication" method="POST" class="form-horizontal">

                            <!-- Place for messages: error, alert etc ... -->
                            <div class="form-group">
                                <div class="col-xs-15">
                                    <div>

                                        <!-- Check for login error -->																	
                                        <c:if test="${param.error != null}">

                                            <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                                Invalid username and password.</div>

                                        </c:if>

                                        <c:if test="${param.logout != null}">

                                            <div class="alert alert-success col-xs-offset-1 col-xs-10">
                                                You have been logged out.</div>

                                        </c:if>

                                    </div>
                                </div>
                            </div>

                            <!-- User name -->
                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 

                                <input type="text" name="username" placeholder="username" class="form-control">
                            </div>

                            <!-- Password -->
                            <div style="margin-bottom: 25px" class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 

                                <input type="password" name="password" placeholder="password" class="form-control" >
                            </div>

                            <!-- Login/Submit Button -->
                            <div style="margin-top: 10px" class="form-group">						
                                <div class="col-sm-6 controls">
                                    <button type="submit" class="btn btn-success">Scrum in</button>
                                </div>
                            </div>
                            <!-- manually adding tokens csrf protection -->
                            <input type="hidden" name="${_csrf.parameterName}"
                                   value="${_csrf.token}" />
                        </form>

                    </div>

                </div>

                <div>
                    <a href="${pageContext.request.contextPath}/register/showRegister" 
                       class="btn btn-primary" role="button" aria-pressed="true">Register</a>
                </div>

            </div>

        </div>

    </body>
</html>

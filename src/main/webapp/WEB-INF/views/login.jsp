<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Scrumvy Log in</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" 
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
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
                                    <p class="text-muted mb-4">Login to create and manage SCRUM projects.</p>
                                    <form action="${pageContext.request.contextPath}/authentication" method="POST"
                                          class="form-horizontal">
                                        <div class="form-group mb-3">
                                            <input id="inputUsername" type="text" placeholder="Username" required
                                                   autofocus="" name="username" class="form-control rounded-pill border-0 shadow-sm px-4">
                                        </div>
                                        <div class="form-group mb-3">
                                            <input id="inputPassword" type="password" name = "password" placeholder="Password" required
                                                   class="form-control rounded-pill border-0 shadow-sm px-4 text-primary">
                                        </div>
                                        <button id="lgnbtn" type="submit"
                                                class="loginButton ">Scrum in!</button>

                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                        <br>


                                    </form>
                                    <a href="${pageContext.request.contextPath}/register/showRegister" 
                                       id="regbtn" class="registerButton btn mt-2">Register Now!</a>
                                    <div class="text-center d-flex justify-content-between mt-2">
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
                                                <!-- Registration success --> 
                                                <c:if test="${registrationSuccess == 'Account has been created.'}">
                                                    <div class="alert alert-success col-xs-offset-1 col-xs-10">
                                                        ${registrationSuccess}
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
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

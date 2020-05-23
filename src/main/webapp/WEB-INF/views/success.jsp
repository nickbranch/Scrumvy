<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" 
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
        <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/homepage.css">

        <title>Congratulations. You are now a premium user.</title>
    </head>

    <body>
        <nav class="navbar navbar-expand-lg navbar-dark" style="background-color:rgb(63, 70, 173);">
            <a class="navbar-brand" href="#"><i class="fab fa-stripe-s"></i>crumvy</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/">Home<span class="sr-only"></span></a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/goPremium" class="nav-link">Pricing</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Projects
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="#">Owner</a>
                            <a class="dropdown-item" href="#">Developer</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#">Drafts</a>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">About Us</a>
                    </li>
                </ul>
                <form:form action="${pageContext.request.contextPath}/logout" method="POST"
                           class="form-inline my-2 my-lg-0">
                    <input type="submit" value="&#xf2f5Logout" class=" btn btn-outline-info my-2 my-sm-0">
                </form:form>
            </div>
        </nav>
        <!-- end of navbar -->
        <div class="container-fluid text-center py-4">
            <div class="row content">
                <div class="col-lg-2 md-2 sidenav">
                    <div>
                        <!-- Check for errors --> 
                        <c:if test="${createProjectError != null}">
                            <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                ${createProjectError}
                            </div>
                        </c:if>
                    </div>
                </div>

                <!-- end of left side -->
                <div class="col-lg-8 md-8 text-center">
                    <p><img src="${pageContext.request.contextPath}/img/premscr.png" style="height:500px;;" class="img-fluid"></p>
                    <h1>Congratulations on becoming a Premium Scrumvy User.</h1>
                    <h2>Thank you!</h2>
                    <a id="back" class="back " href="${pageContext.request.contextPath}/"><i class="fas fa-arrow-circle-left"></i></a>
                </div>

                <!-- end of center side -->

                <div class="col-lg-2 md-2 sidenav py-3">
                </div>
            </div>
        </div>




        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
                integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
                integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
                integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
    </body>
</html>

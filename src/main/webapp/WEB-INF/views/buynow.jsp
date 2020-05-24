<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
        <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buynow.css">


        <title>Go Premium</title>
    </head>
    <body>

        <nav class="navbar navbar-expand-lg navbar-dark" style="background-color:rgb(63, 70, 173);">
            <a class="navbar-brand" href="#"><i class="fab fa-stripe-s"></i>crumvy</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item ">
                        <a href="${pageContext.request.contextPath}/" class="nav-link">Home</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link">Pricing</a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/aboutUs" class="nav-link">About Us</a>
                    </li>
                </ul>
                <!--  Add log out button -->
                <form:form action="${pageContext.request.contextPath}/logout"
                           method="POST">
                    <input type="submit" value="&#xf2f5Logout" class=" btn btn-outline-info my-2 my-sm-0">
                </form:form>
            </div>
        </nav>
        <!-- end of navbar -->

        <section class="cards py-5 ">
            <div class="container ">
                <div class="row justify-content-around">
                    <div class="col-lg-4 md-4 ">
                        <div class="card " >
                            <img src="${pageContext.request.contextPath}/img/6.png" class="card-img-top" alt="...">
                            <div class="card-body">
                                <hr>
                                <h5 class="card-title">Srumvy Free*</h5>
                                <p class="card-text">Plan, track, and release
                                    world-class
                                    software. </p>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">Scrumvy Software</li>
                                <li class="list-group-item">Documentation + Help Desk</li>
                            </ul>
                            <div class="card-body">
                                <a href="${pageContext.request.contextPath}/" class="btn btn-dark" style="background-color:rgb(63, 70, 173);">Free plan</a>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-4 md-4">
                        <div class="card " >
                            <img src="${pageContext.request.contextPath}/img/premscr.png" class="card-img-top" alt="...">
                            <div class="card-body">
                                <hr>
                                <h5 class="card-title">Scrumvy Premium</h5>
                                <p class="card-text">Create, build, and
                                    support software,
                                    end to end. Here is What You Get for the  limited price of 50.0€</p>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">Scrumvy Software</li>
                                <li class="list-group-item">Documentation + Help Desk</li>
                                <li class="list-group-item">Unlimited number of projects</li>
                            </ul>
                            <div class="card-body">
                                <form:form action="${pageContext.request.contextPath}/pay"
                                           method="POST">
                                    <strong>Do you want more projects?</strong>
                                    <input type="submit" class="btn btn-dark" style="background-color:rgb(63, 70, 173)" 
                                           value="Go Premium.">
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- footer -->
                <jsp:include page="/WEB-INF/views/footer.jsp"></jsp:include>
                <!-- end of footer -->
            </div>

        </section>
        <!-- end of cards -->

        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    </body>
</html>
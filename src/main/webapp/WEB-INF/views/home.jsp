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
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
        <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/homepage.css">

        <title>Scrumvy - Home</title>
    </head>

    <body>
        <nav class="navbar navbar-expand-lg navbar-dark" style="background-color:rgb(63, 70, 173);">
            <a class="navbar-brand" href="#"><i class="fab fa-stripe-s"></i>crumvy</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link">Home <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/goPremium" class="nav-link">Pricing</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
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
                <form:form action="${pageContext.request.contextPath}/logout"
                           method="POST" class="form-inline my-2 my-lg-0">
                    <input type="submit" value="&#xf2f5Logout" class=" btn btn-outline-info my-2 my-sm-0">
                </form:form>
            </div>
        </nav> 
        <!-- end of navbar -->
        <div class="container-fluid text-center py-4">
            <div class="row content">
                <div class="col-lg-2 md-2 sidenav">
                    <p><img src="img/5.png" class="img-fluid"></p>
                    <br>
                    <a href="${pageContext.request.contextPath}/project/createProject" class="btn btn-success btn-lg" ;><i class="fas fa-plus"> </i> Create New Project</a>
                    <br>
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
                <div class="col-lg-8 md-8 text-left">
                    <h1>Welcome  <security:authentication property="principal.username" />!</h1>
                    <p>Plan and structure work in a way that’s best for you. Set priorities and deadlines. <p> </p> Share details and assign tasks.
                    All in one place.</p>
                    <hr>

                    <ul class="nav nav-tabs">
                        <li class="nav-item "><a class="nav-link active" data-toggle="tab" href="#home">Owned Projects</a></li>
                        <li class="nav-item "><a class="nav-link " data-toggle="tab" href="#menu1">Projects as Scrum Master</a></li>
                        <li class="nav-item "><a class="nav-link " data-toggle="tab" href="#menu2">Projects as Developer</a></li>
                    </ul>
                    <div class="tab-content py-4">
                        <div id="home" class="tab-pane active">
                            <div class="container">
                                <c:forEach items="${ownedProjects}" var="project">
                                    <ul class="list-group">
                                        <li class="list-group-item  ">
                                            <form:form method="GET" modelAttribute="project" action="/project/projectSettings">
                                                ${project.projectName}

                                                <input type="hidden"  name="projectId" value="${project.projectId}" />
                                                <span class="badge badge-pill badge-secondary">Owner</span>
                                                <span class="pull-right button-group d-flex justify-content-end">
                                                    <input type="SUBMIT" class="btn btn-secondary m-1" value="Edit Project" />

                                                </form:form>
                                                <a href="${pageContext.request.contextPath}/project/projectDetails/${project.projectId}" class="btn btn-dark m-1" style="background-color:rgb(63, 70, 173);">Project Workspace</a>
                                            </span>
                                        </li>
                                    </ul>
                                </c:forEach>
                            </div>

                        </div>

                        <!-- Projects as Scrum Master -->
                        <div id="menu1" class="tab-pane fade">

                            <div class="container">
                                <c:forEach items="${joinedAsScrumMaster}" var="project">
                                    <ul class="list-group">
                                        <li class="list-group-item ">
                                            ${project.projectName} <span class="badge badge-pill badge-secondary ">Scrum Master</span>
                                            <span class="pull-right button-group d-flex justify-content-end">
                                                <a href="${pageContext.request.contextPath}/project/projectDetails/${project.projectId}" class="btn btn-dark m-1" style="background-color:rgb(63, 70, 173);">Project Workspace</a>
                                            </span>
                                        </li>  
                                    </ul>
                                </c:forEach>
                            </div>
                        </div>
                        <!-- Projects As Developer -->
                        <div id="menu2" class="tab-pane fade">

                            <div class="container">
                                <c:forEach items="${joinedAsDevTeam}" var="project">
                                    <ul class="list-group">
                                        <li class="list-group-item ">
                                            ${project.projectName} <span class="badge badge-pill badge-secondary">Dev</span>
                                            <span class="pull-right button-group d-flex justify-content-end">
                                                <a href="${pageContext.request.contextPath}/project/projectDetails/${project.projectId}" class="btn btn-dark m-1" style="background-color:rgb(63, 70, 173);">Project Workspace</a>
                                            </span>
                                        </li>

                                    </ul>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- end of center side -->


                <div class="col-lg-2 md-2 sidenav py-3">
                    <ul class="list-group">
                        <li class="list-group-item d-flex justify-content-between align-items-start ">
                            <a href="#" class="href" data-toggle="modal" data-target="#exampleModal ">Received Invites</a>
                            <span class="badge badge-primary badge-pill ">${fn:length(receivedInvites)}</span>
                        </li>
                        <li class="list-group-item d-flex justify-content-between align-items-start ">
                            <a href="#" class="href" data-toggle="modal" data-target="#exampleModal2 ">Sent Invites</a>
                            <span class="badge badge-primary badge-pill m-1">${fn:length(sentInvites)}</span>
                        </li>

                        <li class="list-group-item d-flex ">
                            <div class="wrapper">
                                <a class="cta" href="#">
                                    <span>
                                        <form:form action="${pageContext.request.contextPath}/pay"
                                                   method="POST">
                                            <input type="submit" value="Go Premium!" class="btn btn-dark btn-lg mb-2"
                                                   style="background-color: #3F46AD; border:none;">
                                        </form:form>
                                    </span>
                                    <span>
                                        <svg width="66px" height="43px" viewbox="0 0 66 43" version="1.1"
                                             xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                        <g id="arrow" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                        <path class="one"
                                              d="M40.1543933,3.89485454 L43.9763149,0.139296592 C44.1708311,-0.0518420739 44.4826329,-0.0518571125 44.6771675,0.139262789 L65.6916134,20.7848311 C66.0855801,21.1718824 66.0911863,21.8050225 65.704135,22.1989893 C65.7000188,22.2031791 65.6958657,22.2073326 65.6916762,22.2114492 L44.677098,42.8607841 C44.4825957,43.0519059 44.1708242,43.0519358 43.9762853,42.8608513 L40.1545186,39.1069479 C39.9575152,38.9134427 39.9546793,38.5968729 40.1481845,38.3998695 C40.1502893,38.3977268 40.1524132,38.395603 40.1545562,38.3934985 L56.9937789,21.8567812 C57.1908028,21.6632968 57.193672,21.3467273 57.0001876,21.1497035 C56.9980647,21.1475418 56.9959223,21.1453995 56.9937605,21.1432767 L40.1545208,4.60825197 C39.9574869,4.41477773 39.9546013,4.09820839 40.1480756,3.90117456 C40.1501626,3.89904911 40.1522686,3.89694235 40.1543933,3.89485454 Z"
                                              fill="#FFFFFF"></path>
                                        <path class="two"
                                              d="M20.1543933,3.89485454 L23.9763149,0.139296592 C24.1708311,-0.0518420739 24.4826329,-0.0518571125 24.6771675,0.139262789 L45.6916134,20.7848311 C46.0855801,21.1718824 46.0911863,21.8050225 45.704135,22.1989893 C45.7000188,22.2031791 45.6958657,22.2073326 45.6916762,22.2114492 L24.677098,42.8607841 C24.4825957,43.0519059 24.1708242,43.0519358 23.9762853,42.8608513 L20.1545186,39.1069479 C19.9575152,38.9134427 19.9546793,38.5968729 20.1481845,38.3998695 C20.1502893,38.3977268 20.1524132,38.395603 20.1545562,38.3934985 L36.9937789,21.8567812 C37.1908028,21.6632968 37.193672,21.3467273 37.0001876,21.1497035 C36.9980647,21.1475418 36.9959223,21.1453995 36.9937605,21.1432767 L20.1545208,4.60825197 C19.9574869,4.41477773 19.9546013,4.09820839 20.1480756,3.90117456 C20.1501626,3.89904911 20.1522686,3.89694235 20.1543933,3.89485454 Z"
                                              fill="#FFFFFF"></path>
                                        <path class="three"
                                              d="M0.154393339,3.89485454 L3.97631488,0.139296592 C4.17083111,-0.0518420739 4.48263286,-0.0518571125 4.67716753,0.139262789 L25.6916134,20.7848311 C26.0855801,21.1718824 26.0911863,21.8050225 25.704135,22.1989893 C25.7000188,22.2031791 25.6958657,22.2073326 25.6916762,22.2114492 L4.67709797,42.8607841 C4.48259567,43.0519059 4.17082418,43.0519358 3.97628526,42.8608513 L0.154518591,39.1069479 C-0.0424848215,38.9134427 -0.0453206733,38.5968729 0.148184538,38.3998695 C0.150289256,38.3977268 0.152413239,38.395603 0.154556228,38.3934985 L16.9937789,21.8567812 C17.1908028,21.6632968 17.193672,21.3467273 17.0001876,21.1497035 C16.9980647,21.1475418 16.9959223,21.1453995 16.9937605,21.1432767 L0.15452076,4.60825197 C-0.0425130651,4.41477773 -0.0453986756,4.09820839 0.148075568,3.90117456 C0.150162624,3.89904911 0.152268631,3.89694235 0.154393339,3.89485454 Z"
                                              fill="#FFFFFF"></path>
                                        </g>
                                        </svg>
                                    </span>
                                </a>
                            </div>
                        </li>

                    </ul>
                    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog ">


                            <div class="modal-content ">

                                <div class="modal-header justify-content-center">
                                    <h5 class="modal-title" id="exampleModalLabel">Received Invites </h5>

                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        <c:forEach items="${receivedInvites}" var="receivedInvite">

                                            You have been invited to project ${receivedInvite.projectId.projectName}
                                            in the position of ${receivedInvite.projectRoleId.roleDescription}
                                            <form:form cssClass="form1" method="POST" modelAttribute="invite"
                                                       action="${pageContext.request.contextPath}/invites/handleAccept">
                                                <input type="hidden" name="theRecInvite"
                                                       value="${receivedInvite.inviteId}" />
                                                <input type="submit" class="btn btn-success " name="Accept" 
                                                       value="&#xf00c" />
                                            </form:form>
                                            <form:form cssClass="form2" method="POST" modelAttribute="invite"
                                                       action="${pageContext.request.contextPath}/invites/handleReject">
                                                <input type="hidden" name="theRecInvite" value="${receivedInvite.inviteId}" />
                                                <input type="submit" class="btn btn-danger" name="Reject"  value="&#xf00d" />
                                            </form:form>

                                        </c:forEach>
                                </div>

                            </div>
                        </div>
                    </div>


                    <div class="modal fade" id="exampleModal2" tabindex="-1" role="dialog"
                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header justify-content-center">
                                    <h5 class="modal-title" id="exampleModalLabel2">Sent Invites</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <c:forEach items="${sentInvites}" var="sentInvite">
                                        You have invited ${sentInvite.receivingUserId.username} 
                                        to project ${sentInvite.projectId.projectName}
                                        in the position of ${sentInvite.projectRoleId.roleDescription}
                                        <form:form method="POST"
                                                   modelAttribute="invite"
                                                   action="${pageContext.request.contextPath}/invites/cancelInvite">
                                            <input type="hidden" name="theRecInvite" value="${sentInvite.inviteId}"/>
                                            <input type="submit" class="btn btn-danger" name="Cancel"  value="&#xf00d" />
                                        </form:form>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
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

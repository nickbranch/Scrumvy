<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
        <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/homepage.css">
        <title>Manage Your Team</title>
    </head>

    <body>
        <jsp:include page="/WEB-INF/views/generalNavigation.jsp"></jsp:include>
            <!-- end of navbar -->

            <div class="container">
                <div class="row ">

                    <div class="col-md-2 py-5">
                        <a id="back" class="back" href="${pageContext.request.contextPath}/redirectToProject/${project.projectId}"><i class="fas fa-arrow-circle-left"></i></a>
                </div>

                <div class="col-md-8 bg-light ">
                    <div class=" d-flex align-items-start py-5">

                        <div class="container">
                            <div class="row ">
                                <div class="col-lg-10 col-xl-7 mx-auto">
                                    <h2 class="display">Manage Project "${project.projectName}" Team</h2>

                                    <div class="col-auto">
                                        <form method="POST" action="/projectTeam/searchTeamMember">
                                            <input type="hidden" name="projectId" value="${project.projectId}" />
                                            <label for="search">Search with Email</label>
                                            <label class="sr-only" for="inlineFormInputGroup">Email..</label>
                                            <div class="input-group mb-2">
                                                <div class="input-group-prepend">
                                                    <div class="input-group-text">@</div>
                                                </div>
                                                <input type="text" name="searchTerm" placeholder="Username" class="form-control mr-2"/>
                                                <input type="submit" value="&#xf002 Search" class="btn btn-dark btn-lg"
                                                       style="background-color: #3F46AD;">
                                            </div>                                            
                                            <!-- manually adding tokens csrf protection -->
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                        </form>
                                    </div>

                                    <c:if test="${userFound != null}">
                                        <div class="alert alert-success col-xs-offset-1 col-xs-10">
                                            ${userFound}
                                        </div>
                                        <form:form method="POST" modelAttribute="invite"
                                                   action="${pageContext.request.contextPath}/invites/sendInvite">
                                            <form:select path="projectRoleId">
                                                <form:options items="${availableRoles}" itemValue="projectRoleId" itemLabel="roleDescription" />
                                            </form:select>
                                            <input type="hidden" name="userName" value="${userFound}" />
                                            <input type="hidden" name="project" value="${project.projectId}" />
                                            <input type="submit" value="&#xf4c4 Send Invite" class="btn btn-dark btn-lg"
                                                   style="background-color: #3F46AD;">
                                        </form:form>
                                    </c:if>
                                    <div class="form-group">
                                        <div class="col-xs-15">
                                            <div>
                                                <!-- Check for errors -->
                                                <c:if test="${couldNotFind != null}">
                                                    <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                                        ${couldNotFind}
                                                    </div>
                                                </c:if>
                                                <c:if test="${inviteHasBeenSent != null}">
                                                    <div class="alert alert-success col-xs-offset-1 col-xs-10">
                                                        ${inviteHasBeenSent}
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>

                                    <c:forEach items="${project.projectTeamCollection}" var="teamMember">
                                        <div class="card" style="width: 30rem;">
                                            <div class="card-body">
                                                <div class="form1">
                                                    <h5 class="card-title">${teamMember.projectRoleId.roleDescription}</h5>
                                                    <p class="card-text">${teamMember.userId.firstName}</p>
                                                </div>
                                                <c:set var="projectRoleId" scope="session" value="${teamMember.projectRoleId.projectRoleId}" />
                                                <c:if test="${projectRoleId != 1}">
                                                    <form:form class="form2 float-right" method="POST" modelAttribute="project"
                                                               action="/projectTeam/releaseTeamMember">
                                                        <input type="hidden" name="projectTeamId" value="${teamMember.projectTeamId}" />
                                                        <input type="submit" class="btn btn-danger" name="Reject"  value="&#xf00d" />                                                       
                                                    </form:form>
                                                </c:if>
                                            </div>
                                        </div>
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
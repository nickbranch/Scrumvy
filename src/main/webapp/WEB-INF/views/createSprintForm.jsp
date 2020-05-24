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
        <title>Create Sprint</title>
    </head>

    <body>
        <jsp:include page="/WEB-INF/views/generalNavigation.jsp"></jsp:include>
            <!-- end of navbar -->
            <div class="container">
                <div class="row ">

                    <div class="col-md-2 py-5">

                        <a id="back" class="back "
                           href="${pageContext.request.contextPath}/project/projectDetails/${project.projectId}"><i
                            class="fas fa-arrow-circle-left"></i></a>
                </div>

                <div class="col-md-8 bg-light">
                    <div class="login d-flex align-items-start py-5">


                        <div class="container">
                            <div class="row">
                                <div class="col-lg-10 col-xl-7 mx-auto">
                                    <h3 class="display">Create New Sprint</h3>

                                    <form:form action="${pageContext.request.contextPath}/sprint/saveSprint"  method="POST"
                                               modelAttribute="sprint"
                                               class="form-horizontal">

                                        <c:if test="${createSprintError != null}">
                                            <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                                ${createSprintError}
                                            </div>
                                        </c:if>

                                        <div class="form-row py-1">
                                            <c:if test="${fn:length(activeTasks) > 0}">
                                                <div class="form-group col-md-6">
                                                    <label for="date">Start Date</label>
                                                    <form:input type="date" path="sprintStartDate" class="form-control" id="startdate"/>
                                                    <form:errors path="sprintStartDate" cssClass="error" />
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="date">End Date</label>
                                                    <form:input type="date" path="sprintEndDate" class="form-control" id="enddate"/>
                                                    <form:errors path="sprintStartDate" cssClass="error" />
                                                </div>
                                            </div>
                                            <p class="py-3"><strong>Select tasks. You may control-click (Windows) or command-click (Mac) to
                                                    select more than one.</strong></p>
                                                </c:if>

                                        <c:if test="${fn:length(activeTasks) == 0}">
                                            <p style="color: rgb(0,128,0)"><i class="fa fa-check"></i><em> All tasks are complete</em> </p>
                                                </c:if>
                                        <!-- This works  -->
                                        <c:if test="${fn:length(activeTasks) > 0}">
                                            <form:select path="taskCollection" multiple="true">
                                                <form:options items="${activeTasks}" itemValue="taskId" itemLabel="description"/>
                                            </form:select>
                                        </c:if>
                                        <input type="hidden" name="projectId" value="${project.projectId}" />

                                        <c:if test="${fn:length(activeTasks) > 0}">
                                            <button type="submit" class="btn btn-dark btn-lg " style="background-color:rgb(63, 70, 173);"><i
                                                    class="fas fa-plus m-1"></i>Create</button>
                                            </c:if>
                                        </form:form>
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
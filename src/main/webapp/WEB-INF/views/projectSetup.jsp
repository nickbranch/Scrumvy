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
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
        <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/homepage.css">
        <title>${project.projectName} Project Settings</title>
    </head>

    <body>
        <jsp:include page="/WEB-INF/views/generalNavigation.jsp"></jsp:include>
        <!-- end of navbar -->

        <div class="row ">
            <div class="col-lg-4 md-4 py-5 bg-light col ">
                <a id="back" class="back" href="${pageContext.request.contextPath}/"><i class="fas fa-arrow-circle-left"></i></a>
                <h3 class="display d-flex justify-content-center">
                    <i class="fas fa-cogs mr-1"></i>Project "${project.projectName}" Settings</h3>
                    <form:form method="POST" modelAttribute="project"
                               action="${pageContext.request.contextPath}/project/updateProjectDetails">
                    <input type="hidden" name="projectId" value="${project.projectId}" />
                    <input type="hidden" name="userCollection" value="${user.id}" />

                    <div class="form-group py-3 ml-2">
                        <label for="projectName">Project Name</label>
                        <form:input path="projectName" placeholder="${project.projectName}" class="form-control" /><br>
                        <form:errors path="projectName" cssClass="error" />
                    </div>
                    <div class="form-group py-1 ml-2">
                        <label for="projectDescription">Project Description</label>
                        <form:input path="projectDescription" placeholder="${project.projectDescription}" class="form-control" /><br>
                        <form:errors path="projectDescription" cssClass="error" />
                    </div>
                    <div class="form-row py-1 ml-2">
                        <div class="form-group col-md-6">
                            <label for="date">Start Date</label>
                            <form:input type="date" path="startDate" class="form-control" />
                            <fmt:formatDate type="date" value="${project.startDate}" />
                            <form:errors path="startDate" cssClass="error" />
                        </div>
                        <div class="form-group col-md-6">
                            <label for="date">End Date</label>
                            <form:input type="date" path="endDate" class="form-control" />
                            <fmt:formatDate type="date" value="${project.endDate}" />
                            <form:errors path="endDate" cssClass="error" />
                        </div>
                    </div>
                    <div class="col d-flex justify-content-center ">
                        <input type="submit" value="&#xf079 Update" class="btn btn-dark btn-lg" style="background-color: #3F46AD;">
                    </div>
                    <!-- manually adding tokens csrf protection -->
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </form:form>

                <div class="form-group">
                    <div class="col-xs-15">
                        <div>
                            <!-- Check for errors -->
                            <c:if test="${createProjectError != null}">
                                <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                    ${createProjectError}
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="py-5">
                    <form:form class="form1 ml-2" method="POST" modelAttribute="project" action="${pageContext.request.contextPath}/project/deleteProject">
                        <input type="hidden" name="projectId" value="${project.projectId}" />
                        <input type="SUBMIT" class="btn btn-danger btn-md" value="&#xf1f8 Delete Project" />
                    </form:form>

                    <form:form class="form1" method="POST" modelAttribute="project" action="${pageContext.request.contextPath}/projectTeam/manageTeamMembers">
                        <input type="hidden" name="projectId" value="${project.projectId}" />
                        <input type="SUBMIT" class="btn btn-warning btn-md" value="&#xf509 Manage Team Members" />
                    </form:form>

                    <form:form class="form2" method="GET" modelAttribute="project" action="${pageContext.request.contextPath}/project/projectDetails/${project.projectId}">
                        <input type="hidden" name="projectId" value="${project.projectId}" />
                        <input type="SUBMIT" class="btn btn-md" value="Go to WorkSpace" style="background-color: #3F46AD;color:white" />
                    </form:form>
                </div>

            </div>
            <!-- end of left part -->

            <div class="col-lg-8 md-8 py-5">
                <h3 class="display d-flex justify-content-center mr-5"><i class="fas fa-thumbtack mr-1"></i>Tasks</h3>

                <form:form method="POST" modelAttribute="emptyTask" action="${pageContext.request.contextPath}/tasks/saveTask">
                    <div class="form-row py-1">
                        <div class="col">
                            <label for="description">Description</label>
                            <form:input path="description" placeholder="${emptyTask.description}" class="form-control" /><br>
                            <form:errors path="description" cssClass="error" />
                        </div>
                        <div class="col">
                            <label for="taskStartDate">Start Date</label>
                            <form:input type="date" path="taskStartDate" value="${emptyTask.taskStartDate}" class="form-control" />
                            <fmt:formatDate type="date" value="${emptyTask.taskStartDate}" />
                            <form:errors path="taskStartDate" cssClass="error" />
                        </div>
                        <div class="col">
                            <label for="taskEndDate">End Date</label>
                            <form:input type="date" path="taskEndDate" value="${emptyTask.taskEndDate}" class="form-control" />
                            <fmt:formatDate type="date" value="${emptyTask.taskEndDate}" />
                            <form:errors path="taskEndDate" cssClass="error" />
                        </div>
                        <input type="hidden" name="projectId" value="${project.projectId}" />
                        <div class="col">
                            <br>
                            <input type="submit" value="&#xf0c7 Save New Task" class="btn btn-success btn-lg mt-1" />
                        </div>
                        <!-- manually adding tokens csrf protection -->
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </div>
                </form:form>

                <c:forEach items="${project.taskCollection}" var="task">
                    <form:form method="POST" modelAttribute="emptyTask"
                               action="${pageContext.request.contextPath}/tasks/updateTask">
                        <input type="hidden" name="taskId" value="${task.taskId}" />
                        <input type="hidden" name="projectId" value="${project.projectId}" />
                        <input type="hidden" name="statusId" value="${task.statusId.statusId}" />
                        <div class="form-row py-1">
                            <div class="col-3">
                                <form:input path="description" value="${task.description}" class="form-control" /><br>
                                <form:errors path="description" cssClass="error" />
                            </div>
                            <div class="col">
                                <form:input type="date" path="taskStartDate" value="${task.taskStartDate}" class="form-control" />
                                <fmt:formatDate type="date" value="${task.taskStartDate}" />
                                <form:errors path="taskStartDate" cssClass="error" />
                            </div>
                            <div class="col">
                                <form:input type="date" path="taskEndDate" value="${task.taskEndDate}" class="form-control" />
                                <fmt:formatDate type="date" value="${task.taskEndDate}" />
                                <form:errors path="taskEndDate" cssClass="error" />
                            </div>
                            <div class="col">
                                <input type="submit" value="&#xf067 Update" class="btn btn-dark btn-lg"
                                       style="background-color: #3F46AD;float:right;">
                            </div>
                            <!-- manually adding tokens csrf protection -->
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        </form:form>
                        <div class="col">
                            <form method="POST" action="${pageContext.request.contextPath}/tasks/deleteTask">
                                <input type="hidden" name="taskId" value="${task.taskId}" />
                                <input type="submit" value="&#xf1f8 Delete" class="btn btn-danger btn-lg" />
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="form-group">
            <div class="col-xs-15">
                <div>
                    <!-- Check for errors -->
                    <c:if test="${taskError != null}">
                        <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                            ${taskError}
                        </div>
                    </c:if>
                </div>
            </div>
        </div>

        <!-- end of right part? -->

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
<%-- 
    Document   : projectSetup
    Created on : May 6, 2020, 3:32:14 AM
    Author     : nklad
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <script src="https://kit.fontawesome.com/f651b3da4d.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css" />
        <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
        <title>${project.projectName} Project Settings</title>
    </head>

    <body>
        <table class="table table-dark text-center">
            <thead>
                <tr>
                    <th colspan=6 scope="col" class="text-center"><h1>Project "${project.projectName}" Settings</h1></th>
                    <th colspan="6">                            
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
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th scope="col">Project Name</th>
                    <th scope="col">Project Description</th>
                    <th scope="col">Start Date</th>
                    <th scope="col">End Date</th>
                    <th scope="col">Actions</th>
                </tr>

                <tr>
                    <form:form method="POST"
                               modelAttribute="project"
                               action="${pageContext.request.contextPath}/project/updateProjectDetails">
                <input type="hidden" name="projectId" value="${project.projectId}"/>
                <input type="hidden" name="userCollection" value="${user.id}"/>
                <td> 
                    <form:input path="projectName" placeholder="${project.startDate}" class="form-control" /><br>
                    <form:errors path="projectName" cssClass="error" />
                </td>
                <td>
                    <form:input path="projectDescription" placeholder="${project.projectDescription}" class="form-control" /><br>
                    <form:errors path="projectDescription" cssClass="error" />
                </td>
                <td>
                    <form:input type="date" path="startDate" class="form-control" />
                    <fmt:formatDate type = "date" value = "${project.startDate}" />
                    <form:errors path="startDate" cssClass="error" />
                </td>
                <td>
                    <form:input type="date" path="endDate" class="form-control" />
                    <fmt:formatDate type = "date" value = "${project.endDate}" />
                    <form:errors path="endDate" cssClass="error" />
                </td>
                <td>
                    <button type="submit" class="btn btn-primary">Update</button>
                </td>
                <!-- manually adding tokens csrf protection -->
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form:form>
        </tr>


        <th colspan=6 scope="col" class="text-center"><h1>Tasks</h1></th>
        <th colspan="6">                            
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
        </th>

        <tr>
            <th scope="col">Task Name</th>
            <th scope="col">Start Date</th>
            <th scope="col">End Date</th>
            <th scope="col">Actions</th>
        </tr>


        <c:forEach items="${project.taskCollection}" var="task">
            <form:form method="POST"
                       modelAttribute="emptyTask"
                       action="${pageContext.request.contextPath}/tasks/updateTask">
                <tr>
                <input type="hidden" name="taskId" value="${task.taskId}"/>
                <input type="hidden" name="projectId" value="${project.projectId}"/>
                <input type="hidden" name="statusId" value="${task.statusId.statusId}"/>
                <td>
                    <form:input path="description" value="${task.description}" class="form-control" /><br>
                    <form:errors path="description" cssClass="error" />
                </td>
                <td>
                    <form:input type="date" path="taskStartDate" value = "${task.taskStartDate}" class="form-control" />
                    <fmt:formatDate type = "date" value = "${task.taskStartDate}" />
                    <form:errors path="taskStartDate" cssClass="error" />
                </td>
                <td>
                    <form:input type="date" path="taskEndDate" value = "${task.taskEndDate}" class="form-control" />
                    <fmt:formatDate type = "date" value = "${task.taskEndDate}" />                        
                    <form:errors path="taskEndDate" cssClass="error" />
                </td>
                <td>
                    <button type="submit" class="btn btn-primary">Update</button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </td>
                <!-- manually adding tokens csrf protection -->
            </form:form>
            <td>
                <form method="POST"
                      action="${pageContext.request.contextPath}/tasks/deleteTask">
                    <input type="hidden" name="taskId" value="${task.taskId}"/>
                    <button type="submit" class="btn btn-danger">Delete</button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </form>
            </td>
        </c:forEach>

    </tr>

    <tr>
        <form:form method="POST"
                   modelAttribute="emptyTask"
                   action="${pageContext.request.contextPath}/tasks/saveTask">
            <td>
                <form:input path="description" placeholder="${emptyTask.description}" class="form-control" /><br>
                <form:errors path="description" cssClass="error" />
            </td>
            <td>
                <form:input type="date" path="taskStartDate" value="${emptyTask.taskStartDate}" class="form-control" />
                <fmt:formatDate type = "date" value = "${emptyTask.taskStartDate}" />
                <form:errors path="taskStartDate" cssClass="error" />
            </td>
            <td>
                <form:input type="date" path="taskEndDate" value="${emptyTask.taskEndDate}" class="form-control" />
                <fmt:formatDate type = "date" value = "${emptyTask.taskEndDate}" />
                <form:errors path="taskEndDate" cssClass="error" />
            </td>
        <input type="hidden" name="projectId" value="${project.projectId}"/>
        <td>
            <button type="submit" class="btn btn-primary">Save New Task</button>
        </td>
        <!-- manually adding tokens csrf protection -->
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form:form>
</tr>
<tr>
    <td>
        <form:form method="POST"
                   modelAttribute="project"
                   action="/project/deleteProject">
            <input type="hidden" name="projectId" value="${project.projectId}"/>
            <input type="SUBMIT" class="btn btn-danger" value="Delete Project"/>                       
        </form:form>
    </td>

    <td>
        <form:form method="POST"
                   modelAttribute="project"
                   action="/projectTeam/manageTeamMembers">
            <input type="hidden" name="projectId" value="${project.projectId}"/>
            <input type="SUBMIT" class="btn btn-warning" value="Manage Team Members"/>                       
        </form:form>
    </td>
</tr>

</tbody>
</table>
</body>
</html>

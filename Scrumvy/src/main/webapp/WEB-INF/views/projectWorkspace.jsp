<%-- 
    Document   : projectWorkspace
    Created on : May 5, 2020, 6:53:59 PM
    Author     : user
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Workspace</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/style.css">
    </head>
    <body>
        
        <div class=" px-4 pt-4">
            <div class="row">
            <h1>Project: ${project.projectName}</h1>
            <h3>Product backlog: </h3>
            <div class="col-6">
                <div class="col-3">
                    <div class="row">
                        <table class="table" >
                            <thead class="thead-dark text-center">
                                <tr>
                                    <th scope="col" 
                                        <br>>Tasks</th>  
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${projectTasks}" var="task" varStatus="status"
                                           begin="0"
                                           end="${fn:length(projectTasks)}"
                                           step="1"> 
                                    <tr>
                                        <td >${status.count}. ${task.description}</td>
                                        <td><i class="fa fa-times"></i> </td>
                                    </tr>
                                </c:forEach>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="col-6">
                <div class="col-3">
                    <div class="row">
                        <table class="table" >
                            <thead class="thead-dark text-center">
                                <tr>
                                    <th scope="col" colspan="2">Tasks</th>  
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${projectTasks}" var="task" varStatus="status"
                                           begin="0"
                                           end="${fn:length(projectTasks)}"
                                           step="1"> 
                                    <tr>
                                        <td >${status.count}. ${task.description}</td>
                                        <td><i class="fa fa-times"></i> </td>
                                    </tr>
                                </c:forEach>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            </div>
            <div class="row">
                <div class="col-md-4 col-md-offset-6">
                    <form:form method="POST"
                               modelAttribute="task"
                               action="">
                        <input type="hidden" name="taskId" value="${tast.taskId}"/>
                        <input type="SUBMIT" value="edit"/>                       
                    </form:form> 
                    <%--     <form:form method="POST"
                                    modelAttribute="task"
                                    action="">
                             <input type="hidden" name="taskId" value="${task.taskId}"/>
                             <input type="SUBMIT" class="glyphicon glyphicon-remove" />                       
                         </form:form> --%>
                </div> 
            </div>
        </div> 
        <br> <br>
        <div class="col-3">
            <div class="row">
                <table class="table" >
                    <thead class="thead-dark text-center">
                        <tr>
                            <th scope="col" colspan="2">Sprints</th>    
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${projectSprints}" var="sprint" varStatus="status"
                                   begin="0"
                                   end="${fn:length(projectSprints)}"
                                   step="1"> 
                            <tr>
                                <td >${status.count}. <fmt:formatDate type = "date" value = "${sprint.sprintStartDate}" /> - <fmt:formatDate type = "date" value = "${sprint.sprintEndDate}"/> </td>
                                <td><i class="fa fa-times"></i> </td>
                            </tr>
                        </c:forEach>

                    </tbody>
                </table>
            </div>

            <div class="row">
                <div class="col-md-4 col-md-offset-6">
                    <form:form method="POST"
                               modelAttribute="task"
                               action="">
                        <input type="hidden" name="taskId" value="${sprint.sprintId}"/>
                        <input type="SUBMIT" value="edit"/>                       
                    </form:form> 
                    <form:form method="POST"
                               modelAttribute="project"
                               action="${pageContext.request.contextPath}/sprint/createSprint">
                        <input type="hidden" name="projectId" value="${project.projectId}"/>
                        <input type="SUBMIT" class="glyphicon glyphicon-remove" value="create new sprint" />                       
                    </form:form> 
                </div> 
            </div>
        </div> 
    </body>
</html>

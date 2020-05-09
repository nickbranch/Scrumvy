
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

<!--    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
        integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
<!--    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">-->
</head>

<body>

    <div class=" px-4 pt-4">
        <h1>${project.projectName}</h1>
        <div class="row">
            <div class="col-3">
                <div class="row pt-5">

                    <table class=" table table-secondary table-bordered pr-2">
                        <thead class=" text-center ">
                            <tr>
                                <th scope="col" colspan="4">Tasks</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${projectTasks}" var="task" varStatus="status" begin="0"
                                end="${fn:length(projectTasks)}" step="1">
                                <tr id="row">
 <!--                                    the td id and class are for js-->
                                    <td id="taskId_${task.taskId}" class="editable">${status.count}. ${task.description}</td>
                                    <td><button id="makeButtonEditable" type="button" class="btn btn-light" onclick="">edit</button></td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>

                </div>

                <div class="row  pt-5">
                    <div class="pb-2">
                       
                      <form:form method="POST" 
                                 modelAttribute="project"
                                 action="${pageContext.request.contextPath}/sprint/createSprint">
                        <input type="hidden" name="projectId" value="${project.projectId}" />
                        <input type="SUBMIT" class="glyphicon glyphicon-remove" value="create new sprint" />
                    </form:form>
                        
                        
                    </div>
                    <table class="table table-secondary table-bordered pr-2">
                        <thead class=" text-center">
                            <tr>
                                <th scope="col" colspan="2">Sprints</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${projectSprints}" var="sprint" varStatus="status" begin="0"
                                end="${fn:length(projectSprints)}" step="1">
                                <tr>
<!--                                    the td id and class are for js-->
                                    <td id="sprintId_${sprint.sprintId}" class="editable">${status.count}.
                                        <fmt:formatDate type="date" value="${sprint.sprintStartDate}" /> -
                                        <fmt:formatDate type="date" value="${sprint.sprintEndDate}" />
                                    </td>
                                    <td><button type="button" class="btn btn-light">edit</button></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                  
                </div>
            </div>
            <div class="col-9 pl-5">
                <br>
                <h4>Current Sprint backlog: </h4>
                <h4 class="font-italic">May 9th, 2020 - May 25th, 2020</h4>
                <br>

                <table class="table table-striped col">
                    <thead class=" text-center">
                        <tr>
                            <th scope="col" colspan="4">Sprint Manager</th>
                        </tr>
                        <tr>
                            <th scope="col"> TO DO </th>
                            <th scope="col"> IN PROGRESS </th>
                            <th scope="col"> COMPLETE </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>John</td>
                            <td>Doe</td>
                            <td>john@example.com</td>

                        </tr>
                        <tr>
                            <td>John</td>
                            <td>Doe</td>
                            <td>john@example.com</td>

                        </tr>
                        <tr>
                            <td>John</td>
                            <td>Doe</td>
                            <td>john@example.com</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
        
        
        <script
        src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
        <script rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/workspaceRest.js"></script>
        
</body>

</html>
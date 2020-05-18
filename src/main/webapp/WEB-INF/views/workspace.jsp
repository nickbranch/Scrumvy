
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>



<!DOCTYPE html>
<html>

    <head>
        <meta name="_csrf" content="${_csrf.token}"/>
        <!-- default header name is X-CSRF-TOKEN -->
        <meta name="_csrf_header" content="${_csrf.parameterName}"/>
        <!-- ... -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Workspace</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" 
              crossorigin="anonymous">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <!--    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">-->
    </head>

    <body>
        <!-- Check for errors --> 
        <c:if test="${editTaskError != null}">
            <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                ${editTaskError}
            </div>
        </c:if>
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
                                        <td> ${status.count} </td>
                                        <td id="taskDescription_${task.taskId}" class="editable">${task.description}</td>                             
                                        <td >
                                            <c:if test="${isProductOwner}">
                                                <button type="button" class="btn btn-light" onclick="makeTaskEditable(${task.taskId})">edit</button>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>

                            </tbody>
                        </table>

                    </div>

                    <div class="row pt-5">
                        <div class="pb-2">
                            <c:if test="${isProductOwner}">
                                <form:form method="POST" 
                                           modelAttribute="project"
                                           action="${pageContext.request.contextPath}/sprint/createSprint">
                                    <input type="hidden" name="projectId" value="${project.projectId}" />
                                    <input type="SUBMIT" class="glyphicon glyphicon-remove" value="create new sprint" />
                                </form:form>
                            </c:if>

                        </div>
                        <table class="table table-secondary table-bordered pr-2">
                            <thead class=" text-center">
                                <tr>
                                    <th scope="col" colspan="4">Sprints</th>
                                </tr>
                                <tr>
                                    <th scope="col"></th>
                                    <th scope="col">Start Date</th>
                                    <th scope="col">End Date</th>
                                    <th scope="col"></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${projectSprints}" var="sprint" varStatus="status" begin="0"
                                           end="${fn:length(projectSprints)}" step="1">
                                    <tr>
                                        <td><button class="sprintSelector" data-selectSprintId="${sprint.sprintId}"> <i class="fa fa-eye"></i></button></td>
                                        <td>
                                            <span id="sprintStartDate_${sprint.sprintId}" class="editable"><fmt:formatDate type="date" value="${sprint.sprintStartDate}" /></span>
                                        </td>
                                        <td>
                                            <span id="sprintEndDate_${sprint.sprintId}" class="editable"><fmt:formatDate  type="date" value="${sprint.sprintEndDate}" /><span>
                                                    </td>
                                                    <td>
                                                          <c:if test="${isProductOwner}">
                                                        <form:form method="POST" 
                                                                   modelAttribute="sprint"
                                                                   action="${pageContext.request.contextPath}/sprint/updateSprint">                                                          
                                                                <input type="hidden" name="sprintId" value="${sprint.sprintId}" />
                                                                <input type="SUBMIT" class="btn btn-light" value="edit" />
                                                        </form:form>
                                                                 </c:if>
                                                    </td>
                                                    <td>
                                                        <c:if test="${isProductOwner}">
                                                        <form:form method="POST" 
                                                                   modelAttribute="sprint"
                                                                   action="${pageContext.request.contextPath}/sprint/deleteSprint">                                                            
                                                                <input type="hidden" name="sprintId" value="${sprint.sprintId}" />
                                                                <button type="SUBMIT"><i class="fa fa-trash"></i> </button>                                                            
                                                        </form:form>
                                                        </c:if>
                                                    </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                                </table>

                                                </div>
                                                </div>

                                                <div class="col-9 pl-5">

                                                    <br>
                                                    <h4>Sprint backlog: </h4>

                                                    <div id="sprintDatedFromTask">
                                                        <h4 class="font-italic">

                                                            <fmt:formatDate type="date" value="${currentSprint.sprintStartDate}"/> - 
                                                            <fmt:formatDate  type="date" value="${currentSprint.sprintEndDate}" />

                                                        </h4>
                                                    </div>
                                                    <br>
                                                    <div class="row">
                                                        <table id="tasksTable" class="table table-striped col text-center" data-sprintId="${currentSprint.sprintId}">
                                                            <thead class=" text-center">
                                                                <tr>
                                                                    <th scope="col" colspan="4">Sprint Management</th>
                                                                </tr>
                                                                <tr>
                                                                    <th scope="col"> TO DO </th>
                                                                    <th scope="col"> IN PROGRESS </th>
                                                                    <th scope="col"> COMPLETE </th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="bodyOfTaskTable">

                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <div class="row" style="height:70px;"></div>
                                                    <label for="retrospective"> <strong>Retrospective:</strong></label>
                                                    <div class="row ">
                                                        <div class="col-4 d-flex overflow-auto"  style="height:20vh">

                                                            <br/>
                                                            <ul id="retrospectiveList" class="list-group list-group-flush">
                                                                <c:forEach items="${retroList}" var="retro" >
                                                                    <li class="list-group-item">${retro.description} &nbsp; &nbsp; &nbsp; <small><em><fmt:formatDate type="date" value="${retro.timestamp}"/></em></small> </li>
                                                                    </c:forEach>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <br/>
                                                        <input id="retroProjectId" type="hidden" name="projectIdForRetro" value="${project.projectId}" />
                                                        <c:if test="${isScrumMaster}">
                                                            <input id="retrospectiveStory" type="text">
                                                            <button id="retrospeciveBtn" type="button" class="btn btn-light">Pin</button>
                                                            <div class="row">
                                                                <p id="retroAjaxError"></p>
                                                            </div>
                                                        </c:if>
                                                    </div>


                                                    <br/>
                                                    <label for="dailyScrum"><strong>Daily Scrum:</strong></label>
                                                    <div class="row ">
                                                        <div class="col-4 d-flex overflow-auto"  style="height:20vh">

                                                            <br/>
                                                            <ul id="dailyScrumList" class="list-group list-group-flush">
                                                                <c:forEach items="${dailyScrumList}" var="dailyScrum" >
                                                                    <li class="list-group-item">${dailyScrum.description} &nbsp; &nbsp; &nbsp; <small><em><fmt:formatDate type="date" value="${dailyScrum.timestamp}"/></em></small> </li>
                                                                    </c:forEach>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <br/>
                                                        <input id="dailyScrumProjectId" type="hidden" name="projectIdForDailyScrum" value="${project.projectId}" />
                                                        <input id="dailyScrumStory" type="text">
                                                        <button id="dailyScrumBtn" type="button" class="btn btn-light">Pin</button>
                                                        <div class="row">
                                                            <p id="dailyScrumAjaxError"></p>
                                                        </div>
                                                    </div>
                                                </div>
                                                </div>
                                                </div>
                                                </div>
                                                </div>


                                                <script
                                                    src="https://code.jquery.com/jquery-3.4.1.min.js"
                                                    integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
                                                crossorigin="anonymous"></script>
                                                <script src="${pageContext.request.contextPath}/js/workspaceRest.js"></script>

                                                </body>

                                                </html>
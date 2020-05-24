<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>



<!DOCTYPE html>
<html lang="en">

    <head>
        <meta name="_csrf" content="${_csrf.token}"/>
        <!-- default header name is X-CSRF-TOKEN -->
        <meta name="_csrf_header" content="${_csrf.parameterName}"/>
        <!-- ... -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Workspace</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.0/handlebars.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/list.js/1.1.1/list.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
        <!--    end libs for stomp and sockjs-->
        <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
        <%--        <link href="https://use.fontawesome.com/releases/v5.0.8/css/all.css" rel="stylesheet">--%>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/chat.css">
    </head>

    <body>
        <!--navbar-->
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
                    <input id="logout" type="submit" value="&#xf2f5Logout" class=" btn btn-outline-info my-2 my-sm-0">
                </form:form>
            </div>
        </nav>


        <!-- end of navbar  -->
        <!-- Check for errors --> 
        <c:if test="${editTaskError != null}">
            <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                ${editTaskError}
            </div>
        </c:if>

        <div class="d-flex flex-row bd-highlight ">
            <a class="backToHome" href="${pageContext.request.contextPath}/"><i class="fas fa-arrow-circle-left"></i></a>
            <h1 class="pt-4 bd-highlight"> 
                Project "${project.projectName}"
            </h1>
        </div>
        <h4 class="ml-5 font-italic px-3">
            <fmt:formatDate type="date" value="${project.startDate}"/> /
            <fmt:formatDate  type="date" value="${project.endDate}" />
            <a id="back" class="back" href="${pageContext.request.contextPath}/reports/reportCentral">Get Reports</a>
        </h4>

        <!-- ----------------------------------------------------------- -->
        <div class="row">
            <div class="col-4 pl-5" >

                <div class="row pt-1">

                    <!-- CHAT -->

                    <div class="chat-container clearfix d-flex py-3 bg-light">
                        <div class="row d-flex justify-content-end">
                            <div class="col d-flex">
                                <div class="people-list" id="people-list">
                                    <div class="search">
                                        <input type="hidden" id="userName"/>

                                    </div>
                                    <input type="hidden" id="loggedInUser" value="${loggedInUser}" />
                                    <span class="loggedInChat">Logged in as: <strong>${loggedInUser} </strong> </span>
                                    <ul class="list" id="usersList">
                                        <c:forEach items="${chatUserNames}" var="chatUser">
                                            <li>${chatUser}</li>
                                            </c:forEach>
                                    </ul>
                                </div>

                                <div class="chat">
                                    <div class="chat-header clearfix">


                                        <div class="chat-about">
                                            <div class="chat-with" id="selectedUserId"></div>
                                            <div class="chat-num-messages"></div>
                                        </div>

                                    </div> <!-- end chat-header -->

                                    <div class="chat-history">
                                        <ul>

                                        </ul>

                                    </div> <!-- end chat-history -->

                                    <div class="chat-message clearfix">
                                        <textarea id="message-to-send" name="message-to-send" placeholder="Type your message"
                                                  rows="3"></textarea>                                        
                                        <button id="sendBtn" class="btn btn-light">Send</button>

                                    </div> <!-- end chat-message -->

                                </div> <!-- end chat -->
                            </div>
                        </div> <!-- end of chat row -->
                    </div> <!-- end container -->


                    <table class="table bg-light">

                        <thead class="col-3 text-center" style="background-color:rgb(63, 70, 173); color:white" >
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
                                            <button type="button" class="btn btn-light" style="background-color:rgb(177,239,224) ;" onclick="makeTaskEditable(${task.taskId})">Edit</button>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>
                </div>


                <input type="hidden" id="projectId" name="projectId" value="${project.projectId}" />


                <div class="container">
                    <div class="row pt-5">
                        <table class="table bg-light">
                            <thead class="col-3 text-center" >
                                <tr>
                                    <th scope="col" colspan="3" style="background-color:rgb(63, 70, 173); color:white">Sprints</th>
                                    <th colspan="2" class="createnewsprint">                                <c:if test="${isProductOwner}">
                                            <form:form method="POST" 
                                                       modelAttribute="project"
                                                       action="${pageContext.request.contextPath}/sprint/createSprint">
                                                <input type="hidden" name="projectId" value="${project.projectId}" />
                                                <input type="SUBMIT"  class="btn btn-success" value="Create New Sprint" />
                                            </form:form>
                                        </c:if></th>
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
                                        <td><button class="sprintSelector btn btn-light" data-selectSprintId="${sprint.sprintId}"> <i class="fa fa-eye"></i></button></td>
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
                                                                <input type="SUBMIT" class="btn btn-light" style="background-color:rgb(177,239,224);" value="Edit"/>
                                                            </form:form>
                                                        </c:if>
                                                    </td>
                                                    <td>
                                                        <c:if test="${isProductOwner}">
                                                            <form:form method="POST" 
                                                                       modelAttribute="sprint"
                                                                       action="${pageContext.request.contextPath}/sprint/deleteSprint">                                                            
                                                                <input type="hidden" name="sprintId" value="${sprint.sprintId}" />
                                                                <button type="SUBMIT" class="btn btn-light"><i class="fa fa-trash"></i> </button>                                                            
                                                            </form:form>
                                                        </c:if>
                                                    </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                                </table>

                                                </div>
                                                </div>
                                                </div>

                                                <div class="col-8 px-5">
                                                    <a id="settingsProj" class="float-right" href="${pageContext.request.contextPath}/redirectToProject/${project.projectId}"><i class="fas fa-cogs p-1"></i>Project Settings</a>
                                                    <h2>Sprint Backlog</h2>                     
                                                    <p id="inspectError" class="problemInfo"></p>
                                                    <div id="sprintDatedFromTask">
                                                        <h4 class="font-italic">

                                                            <fmt:formatDate type="date" value="${currentSprint.sprintStartDate}"/> - 
                                                            <fmt:formatDate  type="date" value="${currentSprint.sprintEndDate}" />

                                                        </h4>
                                                    </div>

                                                    <div class="row">
                                                        <table id="tasksTable" class="table  text-center" data-sprintId="${currentSprint.sprintId}">
                                                            <thead class=" text-center">
                                                                <tr>
                                                                    <th scope="col" colspan="4">Sprint Management</th>
                                                                </tr>
                                                                <tr>
                                                                    <th scope="col" style="background-color:rgb(177,239,224); width: 33.33%"> TO DO </th>
                                                                    <th scope="col" style="background-color:rgb(255,190,97); width: 33.33%"> IN PROGRESS </th>
                                                                    <th scope="col" style="background-color: rgb(119,221,119); width: 33.33%"> COMPLETE </th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="bodyOfTaskTable">

                                                            </tbody>
                                                        </table>
                                                    </div>


                                                    <div class="container py-5">
                                                        <div class="row">
                                                            <div class="col">
                                                                <div class=" m-1">
                                                                    <div class="row col-3">
                                                                        <label for="retrospective"> <strong>Retrospective:</strong></label>
                                                                    </div>
                                                                    <div class="row ">
                                                                        <div class="col-12 overflow-auto"  style="height:20vh">

                                                                            <br/>
                                                                            <ul id="retrospectiveList" class="list-group list-group-flush">
                                                                                <c:forEach items="${retroList}" var="retro" >
                                                                                    <li class="list-group-item">${retro.description} &nbsp; &nbsp; &nbsp; <small><em><fmt:formatDate type="date" value="${retro.timestamp}"/></em></small> </li>
                                                                                    </c:forEach>
                                                                            </ul>
                                                                        </div>
                                                                    </div>
                                                                    <div class="row mt-4">
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
                                                                </div>
                                                            </div>

                                                            <div class="col">
                                                                <label for="dailyScrum"><strong>Daily Scrum:</strong></label>
                                                                <div class="row ">
                                                                    <div class="col-12 overflow-auto"  style="height:20vh">


                                                                        <ul id="dailyScrumList" class="list-group list-group-flush">
                                                                            <c:forEach items="${dailyScrumList}" var="dailyScrum" >
                                                                                <li class="list-group-item">${dailyScrum.description} &nbsp; &nbsp; &nbsp; <small><em><fmt:formatDate type="date" value="${dailyScrum.timestamp}"/></em></small> </li>
                                                                                </c:forEach>
                                                                        </ul>
                                                                    </div>
                                                                </div>
                                                                <div class="row mt-4">
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



                                                <script id="message-template" type="text/x-handlebars-template">
                                                    <li class="clearfix theUser">
                                                    <div class="message-data align-right">
                                                    <span class="message-data-time">{{time}}, Today</span> &nbsp; &nbsp;
                                                    <span class="message-data-name strong">You</span> 
                                                    </div>
                                                    <div class="message other-message float-right">
                                                    {{messageOutput}}
                                                    </div>
                                                    </li>
                                                </script>

                                                <script id="message-response-template" type="text/x-handlebars-template">
                                                    <li class="theOtherUser">
                                                    <div class="message-data">
                                                    <span class="message-data-time">{{time}}, Today</span>
                                                    <span class="message-data-name strong"> {{userName}}</span>
                                                    </div>
                                                    <div class="message my-message">
                                                    {{response}}
                                                    </div>
                                                    </li>
                                                </script>                                        

                                                <script src="${pageContext.request.contextPath}/js/custom.js"></script>
                                                <script src="${pageContext.request.contextPath}/js/chat.js"></script>






                                                <script
                                                    src="https://code.jquery.com/jquery-3.4.1.min.js"
                                                    integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
                                                crossorigin="anonymous"></script>
                                                <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
                                                        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
                                                crossorigin="anonymous"></script>
                                                <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
                                                        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
                                                crossorigin="anonymous"></script>
                                                <script src="${pageContext.request.contextPath}/js/workspaceRest.js"></script>


                                                </body>

                                                </html>
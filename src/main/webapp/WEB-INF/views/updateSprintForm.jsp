<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  


<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Scrumvy - Create Sprint</title>
       <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
             integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" 
             crossorigin="anonymous">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body>
        <div class="container">
            <div id="mainRow" class="row">
                <div  class="col-lg-12">
                    <div class="">
                        <!-- Create Srint Form -->
                        <form:form action="${pageContext.request.contextPath}/sprint/saveUpdatedSprint"  method="POST"
                                   modelAttribute="sprint"
                                   class="form-horizontal">
                            <div class="form-group">
                                <div class="col-xs-15">
                                    <div>
                                        <!-- Check for errors --> 
                                        <c:if test="${createSprintError != null}">
                                            <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                                ${createSprintError}
                                            </div>
                                        </c:if> 
                                    </div>
                                </div>
                            </div>

                            <!-- Start Date -->
                            <div class="input-group">
                                <form:input type="date" path="sprintStartDate" class="form-control" />
                            </div>
                            <form:errors path="sprintStartDate" cssClass="error" />

                            <!-- End date -->
                            <div class="input-group">
                                <form:input type="date" path="sprintEndDate" class="form-control" />
                            </div>
                            <form:errors path="sprintStartDate" cssClass="error" />

                            <!-- Create task dropdown -->

                            <p><strong>Current tasks</strong></p>

                            <!-- This works  -->

                            <!--add current tasks column with for each also add clear field -->
                            <form:select path="taskCollection" multiple="true">
                                <form:options items="${currentTasks}" itemValue="taskId" itemLabel="description"/>                               
                            </form:select>
                            
                            <p><strong>Add another task from the project</strong></p>
                            
                            <form:select path="taskCollection" multiple="true">
                                <form:options items="${activeTasks}" itemValue="taskId" itemLabel="description"/>
                            </form:select>
                                                      
                            <input type="hidden" name="projectId" value="${project.projectId}"/>
                            <input type="hidden" name="sprintId" value="${sprint.sprintId}"/>
                            <!-- Create sprint Button -->
                            <div style="margin-top: 10px" class="form-group">						
                                <button type="SUBMIT" class="btn btn-primary">Update</button>
                            </div>
                            <!-- manually adding tokens csrf protection -->
<!--                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />-->


                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <!--        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>-->
        <!--        <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>-->
    </body>

</html>
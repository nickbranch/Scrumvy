<%-- 
    Document   : createProjectForm
    Created on : Apr 25, 2020, 3:07:33 AM
    Author     : nklad
--%>

<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Scrumvy - Create Sprint</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet"
              id="bootstrap-css">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body>
        <div class="container">
            <div id="mainRow" class="row">
                <div  class="col-lg-12">
                    <div class="">
                        <!-- Create Srint Form -->
                        <form:form action="${pageContext.request.contextPath}/sprint/saveSprint"  method="POST"
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
                                <form:input type="date" path="sprintStartDate" value="<%= (new java.util.Date()).toString()%>" class="form-control" />
                            </div>
                            <form:errors path="sprintStartDate" cssClass="error" />

                            <!-- End date -->
                            <div class="input-group">
                                <form:input type="date" path="sprintEndDate" value="" class="form-control" />
                            </div>
                            <form:errors path="sprintStartDate" cssClass="error" />

                            <!-- Create task dropdown -->
                            
                                <p><strong>Select tasks. You may control-click (Windows) or command-click (Mac) to select more than one.</strong></p>

                                <%--  <select multiple size="${fn:length(taskList)}">
                                      <c:forEach items="${taskList}" var="task">                 
                                          <option name="taskCollection" value="${task.id}">${task.description}</option>
                                      </c:forEach> 
                                      </select>--> 

                                      <form:select path="taskCollection" items="${taskList}" itemLabel="description" itemValue="task" multiple="true">
                                                
                                          <form:option value="${task}">${task.description}</form:option>
                                    
                                      </form:select>  --%>
                               
                                
                                <!-- This works  -->
                                <form:select path="taskCollection" multiple="true">
                                             
                                          <form:options items="${activeTasks}" itemValue="taskId" itemLabel="description"/>
                                     
                                </form:select>
  
                                
                            
                             <%--   <form:select  path="taskCollection">
                                    <form:option value="NONE"> --SELECT--</form:option>
                                    
                                    <form:options items="${taskList}" var="task"></form:options>
                                  
                                </form:select>--%>

                           <%--     <form:select multiple="true" path="taskCollection" items="${taskList}" itemLabel="description" itemValue="taskId" />  --%>


                            

                            <!-- Create sprint Button -->
                            <div style="margin-top: 10px" class="form-group">						
                                <button type="submit" class="btn btn-primary">Create</button>
                            </div>

                            <input type="hidden" name="projectId" value="${project.projectId}"/>

                            <!-- manually adding tokens csrf protection -->
<!--                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />-->
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
    </body>

</html>
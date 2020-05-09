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

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Scrumvy - Create a project</title>
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
                        <form:form action="${pageContext.request.contextPath}/project/saveProject"  method="POST"
                                   modelAttribute="project"
                                   class="form-horizontal">
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
                            <!-- Project Name -->
                            <div class="input-group">
                                <form:input path="projectName" placeholder="Name (*)" class="form-control" /><br>
                            </div>
                            <form:errors path="projectName" cssClass="error" />

                            <!-- Project Description -->
                            <div class="input-group">
                                <form:input path="projectDescription" placeholder="Description (*)" class="form-control" /><br>
                            </div>
                            <form:errors path="projectDescription" cssClass="error" />

                            <!-- Start Date -->
                            <div class="input-group">
                                <form:input type="date" path="startDate" value="<%= (new java.util.Date()).toString()%>" class="form-control" />
                            </div>
                            <form:errors path="startDate" cssClass="error" />

                            <!-- End date -->
                            <div class="input-group">
                                <form:input type="date" path="endDate" value="" class="form-control" />
                            </div>
                            <form:errors path="endDate" cssClass="error" />

                            <!-- Create project Button -->
                            <div style="margin-top: 10px" class="form-group">						
                                <button type="submit" class="btn btn-primary">Create</button>
                            </div>
                            <!-- manually adding tokens csrf protection -->
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
    </body>

</html>
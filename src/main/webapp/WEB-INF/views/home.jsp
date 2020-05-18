<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%-- 
    Document   : home
    Created on : Apr 24, 2020, 10:04:01 PM
    Author     : nklad
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Scrumvy Projects Home Page</title>
    </head>

    <body>
        <h2>Scrumvy Company Home Page</h2>
        <hr>
        <p>Welcome to the Scrumvy home page!</p>
        <hr>
        <hr>
        <a href="${pageContext.request.contextPath}/project/createProject" class="btn btn-primary" role="button"
           aria-pressed="true">Create Project</a>
        <div>
            <!-- Check for errors --> 
            <c:if test="${createProjectError != null}">
                <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                    ${createProjectError}
                </div>
            </c:if>
        </div>
           <div>
            <!-- Check for errors --> 
            <c:if test="${viewWorkspaceError != null}">
                <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                    ${viewWorkspaceError}
                </div>
            </c:if>
        </div>
        <h2>Owned Projects</h2>
        <div>
            

                <c:forEach items="${ownedProjects}" var="project">
                <ul>
                    <li>${project.projectName}</li>
                    <a href="${pageContext.request.contextPath}/project/projectSettings/${project.projectId}" class="btn btn-primary" role="button"
                       aria-pressed="true">Edit </a>
                    <br>
                    <a href="${pageContext.request.contextPath}/project/projectDetails/${project.projectId}" class="btn btn-primary" role="button"
                       aria-pressed="true">Project Workspace </a>
                    
<%--                    <form:form method="POST"
                               modelAttribute="projectId"
                               action="/project/projectDetails">
                        <input type="hidden" name="projectId" value="${project.projectId}"/>
                        <input type="SUBMIT" value="Project Workspace"/>                       
                    </form:form> --%>
                </ul>
            </c:forEach>
     
    </div>
    <h2>Projects you joined as a Scrum Master</h2>
    <div>
        <p>
            <c:forEach items="${joinedAsScrumMaster}" var="project">
            <ul>
                <li>${project.projectName}</li> 
                 <a href="${pageContext.request.contextPath}/project/projectDetails/${project.projectId}" class="btn btn-primary" role="button"
                       aria-pressed="true">Project Workspace </a>
            </ul>
        </c:forEach>
    </p>
</div>
<h2>Projects you joined as a Developer</h2>
<div>
    <p>
        <c:forEach items="${joinedAsDevTeam}" var="project">
        <ul>
            <li>${project.projectName}</li> 
             <a href="${pageContext.request.contextPath}/project/projectDetails/${project.projectId}" class="btn btn-primary" role="button"
                       aria-pressed="true">Project Workspace </a>
        </ul>
    </c:forEach>
</p>
</div>



<!-- display user name and role -->
<div>
    <p>
        User:
        <security:authentication property="principal.username" />
        <br> <br> Role(s)
        <security:authentication property="principal.authorities" />
    </p>
    <br><br>
    <hr>
    <security:authorize access="hasRole('ADMIN')">

        <!-- Add a link to point to /systems... this is for the managers -->
        <p>
            <a href="${pageContext.request.contextPath}/systems">IT Systems
                Meeting</a> (only for admin peeps)
        </p>
    </security:authorize>

    <!--  Add log out button -->
    <form:form action="${pageContext.request.contextPath}/logout"
               method="POST">
        <input type="submit" value="Logout">
    </form:form>

    </body>
    </html>

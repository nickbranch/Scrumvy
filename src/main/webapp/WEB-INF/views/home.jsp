<%-- 
    Document   : home
    Created on : Apr 24, 2020, 10:04:01 PM
    Author     : nklad
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Scrumvy Projects Home Page</title>
    </head>

    <body>
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
        <h2>Received Invites</h2>
        <div>
            <p>
                <c:forEach items="${receivedInvites}" var="receivedInvite">
                <ul>
                    <li>
                        <form:form method="POST"
                                   modelAttribute="invite"
                                   action="${pageContext.request.contextPath}/invites/handleAccept">
                            You have been invited to project ${receivedInvite.projectId.projectName}
                            in the position of ${receivedInvite.projectRoleId.roleDescription}
                            <input type="hidden" name="theRecInvite" value="${receivedInvite.inviteId}"/>
                            <input type="submit" class="button" name="Accept" value="Accept"/>        
                            <input type="submit" class="button" name="Reject" value="Reject"/>
                        </form:form>
                    </li>
                </ul>
            </c:forEach>
        </p>
    </div> 


    <h2>Invitations to your projects.</h2>
    <div>
        <p>
            <c:forEach items="${sentInvites}" var="sentInvite">
            <ul>
                <li>
                    <form:form method="POST"
                               modelAttribute="invite"
                               action="${pageContext.request.contextPath}/invites/handleAccept">
                        You have invited  ${sentInvite.receivingUserId.username} 
                        to project ${sentInvite.projectId.projectName}
                        in the position of ${sentInvite.projectRoleId.roleDescription}
                        <input type="hidden" name="projectId" value="${project.projectId}"/>
                        <input type="SUBMIT" value="TO BE IMPLEMENTED"/>                       
                    </form:form>
                </li>
            </ul>
        </c:forEach>
    </p>
</div>




<h2>Owned Projects</h2>
<div>
    <p>
        <c:forEach items="${ownedProjects}" var="project">
        <ul>
            <li>
                <form:form method="POST"
                           modelAttribute="project"
                           action="/project/projectSettings">
                    ${project.projectName}
                    <input type="hidden" name="projectId" value="${project.projectId}"/>
                    <input type="SUBMIT" value="Project Settings"/>                       
                </form:form>
            </li>
        </ul>
    </c:forEach>
</p>
</div>
<h2>Projects you joined as a Scrum Master</h2>
<div>
    <p>
        <c:forEach items="${joinedAsScrumMaster}" var="project">
        <ul>
            <li>
                <form:form method="POST"
                           modelAttribute="project"
                           action="/project/projectSettings">
                    ${project.projectName}
                    <input type="hidden" name="projectId" value="${project.projectId}"/>
                    <input type="SUBMIT" value="Project Settings"/>                       
                </form:form>
            </li>
        </ul>
    </c:forEach>
</p>
</div>
<h2>Projects you joined as a Developer</h2>
<div>
    <p>
        <c:forEach items="${joinedAsDevTeam}" var="project">
        <ul>
            <li>
                <form:form method="POST"
                           modelAttribute="project"
                           action="/project/projectSettings">
                    ${project.projectName}
                    <input type="hidden" name="projectId" value="${project.projectId}"/>
                    <input type="SUBMIT" value="Project Settings"/>                       
                </form:form>
            </li>
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

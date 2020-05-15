<%-- 
    Document   : manageTeam
    Created on : May 9, 2020, 3:16:35 AM
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
        <title>Manage Team Members</title>
    </head>
    <body>
        <h1>Manage Team Members</h1>
        <table>
            <c:forEach items="${project.projectTeamCollection}" var="teamMember">
                <tr>
                    <th scope="row">${teamMember.projectRoleId.roleDescription}</th>
                    <td>${teamMember.userId.firstName}</td>
                    <td>
                        <c:set var = "projectRoleId" scope = "session" value = "${teamMember.projectRoleId.projectRoleId}"/>
                        <c:if test="${projectRoleId != 1}">
                            <form:form method="POST"
                                       modelAttribute="project"
                                       action="/projectTeam/releaseTeamMember">
                                <input type="hidden" name="projectTeamId" value="${teamMember.projectTeamId}"/>
                                <input type="SUBMIT" value="Release Member"/>                       
                            </form:form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <th>
                    Search by email:
                </th>
                <td>
                    <form method="POST" action="/projectTeam/searchTeamMember">
                        <input type="hidden" name="projectId" value="${project.projectId}"/>
                        <input type="text" name="searchTerm"/>
                        <input type="SUBMIT" value="Search"/>
                        <!-- manually adding tokens csrf protection -->
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form>
                </td>
                <c:if test="${userFound != null}">
                    <td>
                        <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                            ${userFound}
                        </div>                    
                    </td>
                    <td>
                        <form:form method="POST"
                                   modelAttribute="invite"
                                   action="${pageContext.request.contextPath}/invites/sendInvite">
                            <form:select path="projectRoleId"> 
                                <form:options items="${availableRoles}" itemValue="projectRoleId" itemLabel="roleDescription"/>
                            </form:select>
                            <input type="hidden" name="userName" value="${userFound}"/>
                            <input type="hidden" name="project" value="${project.projectId}"/>
                        </td>
                        <td>
                            <input type="submit" value="Send Invite"/>                       
                        </form:form>
                    </td>
                </c:if>
            </tr>
            <tr>
                <th colspan="5">                            
                    <div class="form-group">
                        <div class="col-xs-15">
                            <div>
                                <!-- Check for errors --> 
                                <c:if test="${couldNotFind != null}">
                                    <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                        ${couldNotFind}
                                    </div>
                                </c:if>
                                <c:if test="${inviteHasBeenSent != null}">
                                    <div class="alert alert-success col-xs-offset-1 col-xs-10">
                                        ${inviteHasBeenSent}
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </th>
            </tr>
        </table>
    </body>
</html>

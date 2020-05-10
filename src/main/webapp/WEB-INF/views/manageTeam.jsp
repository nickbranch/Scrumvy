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
        <title>Manage Team Members</title>
    </head>
    <body>
        <h1>Manage Team Members</h1>

        <table>
            <tr>
                <th colspan=4 scope="col" class="text-center"><h1>Team Members</h1></th>
            </tr>
            <c:forEach items="${project.projectTeamCollection}" var="teamMember">
                <tr>
                    <th scope="row">${teamMember.projectRoleId.roleDescription}</th>
                    <td>${teamMember.userId.firstName}</td>
                    <td>
                        <c:set var = "projectRoleId" scope = "session" value = "${teamMember.projectRoleId.projectRoleId}"/>
                        <c:if test="${projectRoleId != 1}">
                            <form:form method="POST"
                                       modelAttribute="project"
                                       action="/project/releaseTeamMember">
                                <input type="hidden" name="projectTeamId" value="${teamMember.projectTeamId}"/>
                                <input type="SUBMIT" value="Release Member"/>                       
                            </form:form>
                        </c:if>
                    </td>

                    <td>
                        <c:set var = "projectRoleId" scope = "session" value = "${teamMember.projectRoleId.projectRoleId}"/>
                        <c:if test="${projectRoleId != 1}">
                            <form:form method="POST"
                                       modelAttribute="project"
                                       action="/project/editTeamMember">
                                <input type="hidden" name="projectTeamId" value="${teamMember.projectTeamId}"/>
                                <input type="SUBMIT" value="Edit Member"/>                       
                            </form:form>
                        </c:if>
                    </td>

                </tr>
            </c:forEach>

        </table>
    </body>
</html>

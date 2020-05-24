<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" 
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
        <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/homepage.css">
        <title>All Sprints Report</title>        
        <style>

            .header {
                font-weight: bold;               
            }
            .title{
                color:#9f9db6;
            }           
            .table {
                background-color:#f5f5f5;
                border-radius: 15px; 
                color:#5b536e;
            }
            .container {
                font-family:sans-serif;
                letter-spacing: 1px;
            }        
        </style>        
    </head>

    <body>
        <jsp:include page="/WEB-INF/views/generalNavigation.jsp"></jsp:include>
            <!-- end of left side -->
            <div class="container col-lg-5 text-center py-5">
                <h3 class="title"> - ALL SPRINTS REPORT - </h3>
                <br/>
                <div class="col-lg md-8 align-items-center">
                    <table class="table py-5 ">
                        <thead>
                            <tr class="header">
                                <th scope="col"></th>
                                <th scope="col">START DATE</th>
                                <th scope="col">END DATE</th>
                            </tr>
                        </thead>
                        <tbody class="des">
                        <c:forEach items="${showMeAllSprints}" var="sprint" varStatus="status" begin="0"
                                   end="${fn:length(showMeAllSprints)}" step="1">
                            <tr>
                                <th scope="row">${status.count}.</th>
                                <td><fmt:formatDate type="date" value="${sprint.sprintStartDate}" /></td>
                                <td><fmt:formatDate type="date" value="${sprint.sprintEndDate}" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <a id="back" class="back" href="${pageContext.request.contextPath}/reports/reportCentral"><i class="fas fa-arrow-circle-left"></i></a>
            </div>
        </div>
        <!-- end of center side -->

        <div class="col-lg-2 md-2 sidenav py-3">
        </div>
    </div>
</div>




<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
crossorigin="anonymous"></script>
</body>
</html>

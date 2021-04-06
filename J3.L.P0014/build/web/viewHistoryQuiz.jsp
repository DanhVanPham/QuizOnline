<%-- 
    Document   : viewHistoryOrder
    Created on : Jan 13, 2021, 11:19:46 AM
    Author     : DELL
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View History Quiz</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    </head>
    <body>
        <c:if test="${not empty sessionScope.USER and sessionScope.USER.roleId eq 'ROL_2'}" >
            <jsp:include page="menu.jsp"/>
        </c:if>
        <c:if test="${not empty sessionScope.USER and sessionScope.USER.roleId eq 'ROL_1'}" >
            <jsp:include page="navBarAdmin.jsp"/>
        </c:if>
        <h1 style="text-align: center;">View History Quiz</h1>
        <form style="width: 90%;margin-left: 10%;margin-top: 2%;" action="ViewHistoryQuizController" method="POST">
            <div class="form-group" style="width: 90%;" >
                <strong>By name subject:</strong>
                <input type="text" name="txtSearchBySubjectName" class="form-control" aria-describedby="emailHelp"  style="width: 80%;margin-bottom: 1%;" placeholder="Enter name of subject to search:" value="${param.txtSearchBySubjectName}"/>
                <input type="hidden" name="pageIndex" value="1"/>
                <button type="submit" name="action" value="ViewHistoryOrder" style="width: 15%;" class="btn btn-primary">Search</button>
            </div>
        </form>
        <c:if var="checkExisted" scope="page" test="${not empty requestScope.LIST_HISTORY_QUIZ and requestScope.LIST_HISTORY_QUIZ.size() != 0}">
            <table class="table" border="1" style="margin-left: 10%;margin-top: 2%;width: 80%;">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col" style="text-align: center;">NO.</th>
                        <th scope="col" style="text-align: center;">Quiz ID:</th>
                            <c:if test="${sessionScope.USER.roleId eq 'ROL_1'}" >
                            <th scope="col" style="text-align: center;">User Id:</th>
                            </c:if>
                        <th scope="col" style="text-align: center;">Subject Id: </th>
                        <th scope="col" style="text-align: center;">Quiz Time:(minutes) </th>
                        <th scope="col" style="text-align: center;">Question Total: </th>
                        <th scope="col" style="text-align: center;">Point: </th>
                        <th scope="col" style="text-align: center;">Create Date: </th>
                        <th scope="col" style="text-align: center;">Action:</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="quiz" items="${requestScope.LIST_HISTORY_QUIZ}" varStatus="counter">
                        <tr>
                            <c:url value="ViewQuizDetailController" var="linkViewQuizDetail" scope="page">
                                <c:param name="quizId" value="${quiz.quizId}"/>
                            </c:url>
                            <th scope="row">${counter.count}</th>
                            <td style="text-align: center;"><strong>${quiz.quizId}</strong></td>
                                    <c:if test="${sessionScope.USER.roleId eq 'ROL_1'}" >
                                <td style="text-align: center;">${quiz.userId}</td>
                            </c:if>
                            <td style="text-align: center;">${quiz.subjectId}</td>
                            <td style="text-align: center;">${quiz.quizTime}</td>
                            <td style="text-align: center;">${quiz.questionTotal}</td>
                            <td style="text-align: center;">${quiz.pointUser} / ${quiz.maxPoint}</td>
                            <td style="text-align: center;"><fmt:formatDate type = "both" pattern="dd/MM/yyyy HH:mm:ss" value = "${quiz.createDate}" /></td>
                            <td style="text-align: center;">
                                <a href="${linkViewQuizDetail}" class="btn btn-primary" >View Quiz Details</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <nav aria-label="Page navigation example" style="margin-left: 42%;margin-top: 2%;margin-right: 42%;">
                <ul class="pagination pagination-sm" >
                    <c:forEach begin="1" end="${requestScope.ENDPAGE}" var="i">
                        <c:url value="ViewHistoryQuizController" var="linkPagningQuiz" scope="page">
                            <c:param name="pageIndex" value="${i}"/>
                            <c:param name="txtSearchBySubjectName" value="${param.txtSearchBySubjectName}"/>
                        </c:url>
                        <c:if var="checkPageIndex" scope="page" test="${i eq param.pageIndex}">
                            <li class="page-item"><a class="active" href="${linkPagningQuiz}" style="font-size: 250%;">${i}</a></li>
                            </c:if>
                            <c:if test="${!checkPageIndex}">
                            <li class="page-item"><a class="page-link" href="${linkPagningQuiz}" style="font-size: 250%;">${i}</a></li>
                            </c:if>
                        </c:forEach>
                </ul>
            </nav>
        </c:if>
        <c:if test="${!checkExisted}" >
            <h2 style="text-align: center;color: red;">Can not find history quiz!</h2>
        </c:if>
        <jsp:include page="footer.jsp"></jsp:include>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
    </body>
</html>

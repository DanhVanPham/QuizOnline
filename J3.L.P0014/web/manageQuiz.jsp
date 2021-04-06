<%-- 
    Document   : manageFoods.jsp
    Created on : Jan 6, 2021, 4:14:35 PM
    Author     : DELL
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="../css/style.css"/>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous"><link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Manage Quiz Page</title>
    </head>
    <body>
        <jsp:include page="navBarAdmin.jsp"/>     
        <c:if test="${requestScope.LISTQUESTION == null}" >
            <h1 style="color: red; text-align: center;">Can not find List Question</h1>
        </c:if>
        <c:if test="${requestScope.LISTQUESTION != null}" >
            <h3 style="text-align: center;font-size: 250%;text-transform: uppercase;font-weight: bold;margin-top: 3%;margin-bottom: 3%;">Manage Questions</h3>
            <div style="margin-left: 5%;">
                <h2>Search:</h2>
                <div style="margin-left: 4%;">
                    <form action="ViewQuestionByAdminController" method="POST" >
                        <strong>By Question Name:</strong><input type="text" name="txtSearchByQuestionName" class="form-control" aria-describedby="emailHelp"  style="width: 70%;margin-bottom: 1%;" placeholder="Enter name question search:" value="${param.txtSearchByQuestionName}"/>
                        <input type="hidden" name="pageIndex" value="1"/>
                        <strong>By Subject:</strong>
                        <div style="display: block;margin-left: 5%;">
                            <input class="form-check-input" type="radio" name="checkRadioSubject" id="exampleRadios1" value="All" checked>
                            <label class="form-check-label" for="exampleRadios1">
                                All
                            </label><br/>
                            <c:forEach var="subject" items="${applicationScope.LIST_SUBJECT}">
                                <c:if var="checkEqual" test="${subject.subjectId eq param.checkRadioSubject}" scope="page">
                                    <input class="form-check-input" type="radio" name="checkRadioSubject" id="exampleRadios2" value="${subject.subjectId}" checked>
                                    <label class="form-check-label" for="exampleRadios2">
                                        ${subject.subjectName}
                                    </label><br/>
                                </c:if>
                                <c:if test="${!checkEqual}" >
                                    <input class="form-check-input" type="radio" name="checkRadioSubject" id="exampleRadios2" value="${subject.subjectId}">
                                    <label class="form-check-label" for="exampleRadios2">
                                        ${subject.subjectName}
                                    </label><br/>
                                </c:if>
                            </c:forEach>
                        </div>
                        <strong>By Status:</strong>
                        <div style="display: block;margin-left: 5%;">
                            <input class="form-check-input" type="radio" name="checkRadioStatus" id="exampleRadios1" value="All"checked>
                            <label class="form-check-label" for="exampleRadios1">
                                All
                            </label><br/>
                            <input class="form-check-input" type="radio" name="checkRadioStatus" id="exampleRadios2" value="selectedTrue" <c:if test="${param.checkRadioStatus eq 'selectedTrue' }">checked</c:if>>
                            <label class="form-check-label" for="exampleRadios2">
                                True
                            </label><br/>
                            <input class="form-check-input" type="radio" name="checkRadioStatus" id="exampleRadios2" value="selectedFalse" <c:if test="${param.checkRadioStatus eq 'selectedFalse' }">checked</c:if>>
                            <label class="form-check-label" for="exampleRadios2">
                                False
                            </label><br/>
                        </div>
                        <button type="submit" class="btn btn-primary">Search</button>
                    </form>
                </div>
            </div>
            <form action="formCreateQuestion.jsp" method="POST">
                <button type="submit" class="btn btn-primary" style="margin-left: 45%;margin-bottom: 1%;width: 14%;">Create Question</button>
            </form>
            <c:if test="${not empty requestScope.LISTQUESTION}" var="checkList" scope="page">
                <form>
                    <table class="table" border="1" style="width: 90%;margin-left: 5%;">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col" style="text-align: center;">NO.</th>
                                <th scope="col" style="text-align: center;">Question ID:</th>
                                <th scope="col" style="text-align: center;">Question Content:</th>
                                <th scope="col" style="text-align: center;">Subject:</th>
                                <th scope="col" style="text-align: center;">Create Date:</th>
                                <th scope="col" style="text-align: center;">Status</th>
                                <th scope="col" style="text-align: center;">Action:</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="dto" items="${requestScope.LISTQUESTION}" varStatus="counter">
                                <c:url value="ViewQuestionDetailsController" var="linkQuestionDetail" >
                                    <c:param name="questionId" value="${dto.questionId}"/>
                                </c:url>
                                <c:url value="ViewFormUpdateQuestionController" var="linkFormUpdateQuestion" >
                                    <c:param name="questionId" value="${dto.questionId}"/>
                                </c:url>
                                <c:url value="RemoveQuestionController" var="linkRemoveQuestion" >
                                    <c:param name="questionId" value="${dto.questionId}"/>
                                    <c:param name="txtSearchByQuestionName" value="${param.txtSearchByQuestionName}"/>
                                    <c:param name="checkRadioSubject" value="${param.checkRadioSubject}"/>
                                    <c:param name="checkRadioStatus" value="${param.checkRadioStatus}"/>
                                </c:url>
                                <tr>
                                    <th scope="row">${counter.count}</th>
                                    <td>${dto.questionId}</td>
                                    <td>${dto.questionContent}</td>
                                    <td>${dto.subjectId}</td>
                                    <td><fmt:formatDate type = "both" pattern="dd/MM/yyyy HH:mm:ss" value = "${dto.createDate}" /></td>
                                    <td>
                                        <select class="custom-select custom-select-sm mb-3" name="categories" >
                                            <c:if var="testStatus" test="${dto.status == 1}" scope="page">
                                                <option value="${dto.status}" selected>Active</option>
                                                <option >Inactive</option>
                                            </c:if>
                                            <c:if test="${!testStatus}" >
                                                <option value="${dto.status}" selected>Inactive</option>
                                                <option >Active</option>
                                            </c:if>
                                        </select>
                                    </td>
                                    <c:if test="${testStatus}" >
                                        <td>
                                            <a class="btn btn-primary" href="${linkQuestionDetail}">View Question Details</a>
                                            <a href="${linkFormUpdateQuestion}" class="btn btn-success" >Update Question</a>
                                            <a href="${linkRemoveQuestion}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this item?');">Remove Question</a>
                                        </td>
                                    </c:if>
                                    <c:if test="${!testStatus}">
                                        <td style="text-align: center;">
                                            <a href="${linkQuestionDetail}" class="btn btn-primary" >View Question Details</a>
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </form>
                <nav aria-label="Page navigation example" style="margin-left: 42%;margin-top: 2%;margin-right: 42%;">
                    <ul class="pagination pagination-sm" >
                        <c:forEach begin="1" end="${requestScope.ENDPAGE}" var="i">
                            <c:url value="ViewQuestionByAdminController" var="linkPagningQuiz">
                                <c:param name="pageIndex" value="${i}"/>
                                <c:param name="txtSearchByQuestionName" value="${param.txtSearchByQuestionName}"/>
                                <c:param name="checkRadioSubject" value="${param.checkRadioSubject}"/>
                                <c:param name="checkRadioStatus" value="${param.checkRadioStatus}"/>
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
            <c:if test="${!checkList}">
                <h1 style="color: red;text-align: center;">No record found</h1>
            </c:if>
        </c:if>
        <jsp:include page="footer.jsp"></jsp:include>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
    </body>
</html>

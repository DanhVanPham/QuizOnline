<%-- 
    Document   : formUpdateFood
    Created on : Jan 11, 2021, 5:51:13 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <title>View Question Details</title>
    </head>
    <body>
        <c:if test="${not empty sessionScope.USER}">
            <jsp:include page="navBarAdmin.jsp"/>
            <h3 style="text-align: center;font-size: 250%;text-transform: uppercase;font-weight: bold;margin-top: 3%;">Question Details</h3>   
            <c:if test="${requestScope.QUESTION_DETAILS != null and requestScope.ANSWER_DETAILS != null}" >
                <c:set var="question" value="${requestScope.QUESTION_DETAILS}" scope="page"/>
                <div class="form-group" style="margin-left: 20%;">
                    <label for="exampleInputEmail1">Question Id:</label>
                    <input type="text" disabled class="form-control"  value="${question.questionId}" style="width: 70%;"/>
                </div>
                <div class="form-group" style="margin-left: 20%;">
                    <label for="exampleInputEmail1">Subject Id:</label>
                    <input type="text" disabled class="form-control" aria-describedby="emailHelp"  value="${question.subjectId}" style="width: 70%;"/>
                </div>
                <div class="form-group" style="margin-left: 20%;">
                    <label for="exampleFormControlTextarea1">Content Question:</label>
                    <textarea disabled class="form-control" id="exampleFormControlTextarea1" rows="3" style="width: 70%;">${question.questionContent}</textarea>
                </div>
                <c:forEach var="answerDetail" items="${requestScope.ANSWER_DETAILS}" varStatus="counter">
                    <c:if test="${counter.count eq 1}">
                        <div class="form-group" style="margin-left: 20%;">
                            <label for="exampleFormControlTextarea1">Answer A:</label>
                            <input type="text" disabled class="form-control" id="exampleFormControlTextarea1"  style="width: 70%;" value="${answerDetail.answerContent}">
                        </div>
                    </c:if>
                    <c:if test="${counter.count eq 2}">
                        <div class="form-group" style="margin-left: 20%;">
                            <label for="exampleFormControlTextarea1">Answer B:</label>
                            <input type="text" disabled class="form-control" id="exampleFormControlTextarea1" style="width: 70%;" value="${answerDetail.answerContent}">
                        </div>
                    </c:if>
                    <c:if test="${counter.count eq 3}">
                        <div class="form-group" style="margin-left: 20%;">
                            <label for="exampleFormControlTextarea1">Answer C:</label>
                            <input type="text" disabled class="form-control" id="exampleFormControlTextarea1" style="width: 70%;" value="${answerDetail.answerContent}">
                        </div>
                    </c:if>
                    <c:if test="${counter.count eq 4}">
                        <div class="form-group" style="margin-left: 20%;">
                            <label for="exampleFormControlTextarea1">Answer D:</label>
                            <input type="text" disabled class="form-control" id="exampleFormControlTextarea1" style="width: 70%;" value="${answerDetail.answerContent}">
                        </div>
                    </c:if>
                </c:forEach>
                <div class="form-group" style="margin-left: 20%;">
                    <label for="exampleFormControlTextarea1">Answer Correct:</label>
                </div>
                <select class="custom-select custom-select-sm mb-3" disabled name="subject" id="subject" style="width: 56%; margin-left: 20%;">
                    <option  selected>(Please select:)</option>
                    <c:forEach var="answerDetail" items="${requestScope.ANSWER_DETAILS}" varStatus="counter">
                        <c:if test="${counter.count eq 1}"><option <c:if test="${answerDetail.type}">selected</c:if>>A</option></c:if>
                        <c:if test="${counter.count eq 2}"><option <c:if test="${answerDetail.type}">selected</c:if>>B</option></c:if>
                        <c:if test="${counter.count eq 3}"><option <c:if test="${answerDetail.type}">selected</c:if>>C</option></c:if>
                        <c:if test="${counter.count eq 4}"><option <c:if test="${answerDetail.type}">selected</c:if>>D</option></c:if>
                    </c:forEach>
                </select><br/>
                <div class="form-group" style="margin-left: 20%;">
                    <label for="exampleInputEmail1">Status:</label>
                    <c:if var="testStatus" test="${question.status == 1}" scope="page">
                        <input type="text" disabled class="form-control" aria-describedby="emailHelp"  value="ACTIVE" style="width: 70%;" />
                    </c:if>
                    <c:if test="${!testStatus}" >
                        <input type="text" disabled class="form-control" aria-describedby="emailHelp"  value="INACTIVE" style="width: 70%;" />
                    </c:if>
                </div>
            </c:if> 
        </c:if>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
    </body>
</html>

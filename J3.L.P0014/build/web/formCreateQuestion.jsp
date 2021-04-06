<%-- 
    Document   : formCreateQuiz
    Created on : Feb 1, 2021, 3:44:38 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Create Question Page</title>
    </head>
    <body>
        <jsp:include page="navBarAdmin.jsp"/>     
        <h3 style="text-align: center;font-size: 250%;text-transform: uppercase;font-weight: bold;margin-top: 3%;">Form Create New Question</h3>   
        <form action="CreateQuestionController" method="POST" onsubmit="return validateForm();">
            <div class="form-group" style="margin-left: 20%;">
                <label for="exampleSelectCategory">Subject:</label>
            </div>
            <select class="custom-select custom-select-sm mb-3" name="subject" id="subject" style="width: 56%; margin-left: 20%;">
                <option value="" selected>(Please select:)</option>
                <c:forEach var="itemSubject" items="${applicationScope.LIST_SUBJECT}" varStatus="counter">
                    <option value="${itemSubject.subjectId}" <c:if test="${param.subject eq itemSubject.subjectId}">selected</c:if>>${itemSubject.subjectName}</option>
                </c:forEach>
            </select><br/>
            <font color="red" style="margin-left: 20%;" id="validEmail">${requestScope.INVALID.subjectIdError}</font>
            <div class="form-group" style="margin-left: 20%;">
                <label for="exampleFormControlTextarea1">Content Question:</label>
                <textarea class="form-control" name="txtAreaContent" id="contentQuestion" rows="3" style="width: 70%;">${param.txtAreaContent}</textarea>
            </div>
            <font color="red" style="margin-left: 20%;" id="validEmail">${requestScope.INVALID.questionContent}</font>
            <div class="form-group" style="margin-left: 20%;">
                <label for="exampleFormControlTextarea1">Answer A:</label>
                <input type="text" name="txtAnswerA" class="form-control" oninput="fillValueAnswerA();" id="answerA"  style="width: 70%;" value="${param.txtAnswerA}">
            </div>
            <font color="red" style="margin-left: 20%;" id="validEmail">${requestScope.INVALID.aAnswerError}</font>
            <div class="form-group" style="margin-left: 20%;">
                <label for="exampleFormControlTextarea1">Answer B:</label>
                <input type="text" name="txtAnswerB" class="form-control" id="answerB" oninput="fillValueAnswerB();" style="width: 70%;" value="${param.txtAnswerB}">
            </div>
            <font color="red" style="margin-left: 20%;" id="validEmail">${requestScope.INVALID.bAnswerError}</font>
            <div class="form-group" style="margin-left: 20%;">
                <label for="exampleFormControlTextarea1">Answer C:</label>
                <input type="text" name="txtAnswerC" class="form-control" id="answerC" oninput="fillValueAnswerC();" style="width: 70%;" value="${param.txtAnswerC}">
            </div>
            <font color="red" style="margin-left: 20%;" id="validEmail">${requestScope.INVALID.cAnswerError}</font>
            <div class="form-group" style="margin-left: 20%;">
                <label for="exampleFormControlTextarea1">Answer D:</label>
                <input type="text" name="txtAnswerD" class="form-control" id="answerD" oninput="fillValueAnswerD();" style="width: 70%;" value="${param.txtAnswerD}">
            </div>
            <font color="red" style="margin-left: 20%;" id="validEmail">${requestScope.INVALID.dAnswerError}</font>
            <div class="form-group" style="margin-left: 20%;">
                <label for="exampleFormControlTextarea1">Answer Correct:</label>
            </div>
            <select class="custom-select custom-select-sm mb-3" name="txtAnswerCorrect" id="answerCorrect" style="width: 56%; margin-left: 20%;">
                <option value="" selected>(Please select:)</option>
                <option  id="optionA" >A</option>
                <option  id="optionB" >B</option>
                <option  id="optionC" >C</option>
                <option  id="optionD" >D</option>
            </select><br/>
            <font color="red" style="margin-left: 20%;" id="validEmail">${requestScope.INVALID.answerCorrectError}</font><br/>
            <button type="submit" class="btn btn-primary mb-5 mt-3" style="margin-left: 45%;" >Create Question</button>
            <font color="red">${requestScope.ERROR}</font>
        </form>
        <jsp:include page="footer.jsp"></jsp:include>
        <script>
            function fillValueAnswerA() {
                var value = document.getElementById("answerA").value;
                var answerA = document.getElementById("optionA");
                answerA.value = value;
            }
            function fillValueAnswerB() {
                var value = document.getElementById("answerB").value;
                var answerB = document.getElementById("optionB");
                answerB.value = value;
            }
            function fillValueAnswerC() {
                var value = document.getElementById("answerC").value;
                var answerC = document.getElementById("optionC");
                answerC.value = value;
            }
            function fillValueAnswerD() {
                var value = document.getElementById("answerD").value;
                var answerD = document.getElementById("optionD");
                answerD.value = value;
            }
            function validateForm() {
                var subject = document.getElementById("subject").value.trim();
                var contentQuestion = deleteRedundancyWhiteSpace(document.getElementById("contentQuestion").value.trim());
                var answerA = document.getElementById("answerA").value.trim();
                var answerB = document.getElementById("answerB").value.trim();
                var answerC = document.getElementById("answerC").value.trim();
                var answerD = document.getElementById("answerD").value.trim();
                var answerCorrect = document.getElementById("answerCorrect").value.trim();
                if (subject === "" || subject === null || subject.length === 0) {
                    document.getElementById("subject").focus();
                    window.alert("Required select subject for question!");
                    return false;
                } else if (contentQuestion === "" || contentQuestion === null || contentQuestion.length === 0) {
                    document.getElementById("contentQuestion").focus();
                    window.alert("Content of question is not null!");
                    document.getElementById("contentQuestion").value = contentQuestion;
                    return false;
                } else if (answerA === "" || answerA === null || answerA.length === 0) {
                    document.getElementById("answerA").focus();
                    window.alert("Required input answer A!");
                    return false;
                } else if (answerB === "" || answerB === null || answerB.length === 0) {
                    document.getElementById("answerB").focus();
                    window.alert("Required input answer B!");
                    return false;
                } else if (answerC === "" || answerC === null || answerC.length === 0) {
                    document.getElementById("answerC").focus();
                    window.alert("Required input answer C!");
                    return false;
                } else if (answerD === "" || answerD === null || answerD.length === 0) {
                    document.getElementById("answerD").focus();
                    window.alert("Required input answer D!");
                    return false;
                }else if(answerCorrect === "" || answerCorrect === null || answerCorrect.length === 0) {
                    document.getElementById("answerCorrect").focus();
                    window.alert("Required select answer correct!");
                    return false;
                }
            }

            function deleteRedundancyWhiteSpace(contentQuestion) {
                contentQuestion = contentQuestion.replace(/\s/g, " ");
                return contentQuestion;
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
    </body>
</html>

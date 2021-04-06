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
        <title>Select Subject Page</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"/> 
        <div style="border: 1px solid black; width: 60%; margin-left: 20%;height: 400px;">
            <h3 style="text-align: center;font-size: 250%;text-transform: uppercase;font-weight: bold;margin-top: 3%;">Form Select Subject</h3>   
            <form action="GetQuizController" method="POST" onsubmit="return validateForm();">
                <div class="form-group" style="margin-left: 20%;margin-top: 5%;">
                    <label for="exampleSelectCategory"><strong>Subject:</strong></label>
                </div>
                <select class="custom-select custom-select-sm mb-3" name="subject" id="subject" style="width: 56%; margin-left: 20%;">
                    <option value="" selected>(Please select:)</option>
                    <c:forEach var="itemSubject" items="${applicationScope.LIST_SUBJECT}" varStatus="counter">
                        <option value="${itemSubject.subjectId}">${itemSubject.subjectName}</option>
                    </c:forEach>
                </select><br/>
                <button type="submit" class="btn btn-primary mb-5 mt-3" style="margin-left: 45%;" >Do Quiz</button>
                <font color="red">${requestScope.ERROR}</font>
            </form>
        </div>
        <jsp:include page="footer.jsp"></jsp:include>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
        <script>
            function validateForm() {
                var subject = document.getElementById("subject").value.trim();
                if (subject === "" || subject === null || subject.length === 0) {
                    document.getElementById("subject").focus();
                    window.alert("Required select subject for question!");
                    return false;
                }
            }
        </script>
    </body>
</html>

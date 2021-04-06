<%-- 
    Document   : formCreateAccount
    Created on : Jan 31, 2021, 8:53:14 PM
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
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Create Account Page</title>
    </head>
    <body>
        <h3 style="text-align: center;font-size: 250%;text-transform: uppercase;font-weight: bold;margin-top: 3%;">Form Create New Account</h3>   
        <form action="CreateAccountController" method="POST" onsubmit="return validateForm()">
            <div class="form-group" style="margin-left: 20%;">
                <label for="exampleInputEmail1">Email:</label>
                <input type="email" name="txtEmail" class="form-control" id="email" value="${param.txtEmail}" placeholder="Enter Email:" aria-describedby="emailHelp"  style="width: 70%;"/>
                <font color="red" id="validEmail">${requestScope.INVALID.emailError}</font>
            </div>
            <div class="form-group" style="margin-left: 20%;">
                <label for="exampleInputEmail1">Student Name:</label>
                <input type="text" name="txtStudentName" class="form-control" id="studentName" value="${param.txtStudentName}" aria-describedby="emailHelp" placeholder="Enter Student Name:"  required style="width: 70%;"/>
                <font color="red" id="validStudentName">${requestScope.INVALID.nameError}</font>
            </div>
            <div class="form-group" style="margin-left: 20%;">
                <label for="exampleInputEmail1">Password: </label>
                <input type="password" name="txtPassword" class="form-control" id="password" required aria-describedby="emailHelp" placeholder="Enter Password:"  required style="width: 70%;"/>
                <font color="red" id="validPassword">${requestScope.INVALID.passwordError}</font>
            </div>
            <button type="submit" name="action" value="CreateNewFood" class="btn btn-primary mb-5 mt-3" style="margin-left: 45%;" >Create Food</button>
            <font color="red">${requestScope.ERROR}</font>
        </form>
        <jsp:include page="footer.jsp"></jsp:include>
        <script>
            function validateForm() {
                var email = document.getElementById("email").value.trim();
                var studentName = document.getElementById("studentName").value.trim();
                var password = document.getElementById("password").value.trim();
                if (email === "" || email === null || email.length === 0) {
                    document.getElementById("email").focus();
                    window.alert("Email name is not null!");
                    return false;
                } else if (!validateEmail(email)) {
                    window.alert("Email is no valid!");
                    return false;
                }
                if (studentName === "" || studentName === null || studentName.length === 0) {
                    window.alert("Student name is not null!");
                    return false;
                }
                if (password === "" || password === null || password.length === 0) {
                    window.alert("Password is not null!");
                    return false;
                }
            }
            function validateEmail(email) {
                const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return re.test(email);
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
    </body>
</html>

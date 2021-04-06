<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--begin of menu-->
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="ViewQuestionByAdminController">Quiz-Online</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <c:url value="LogoutController" var="linkLogout" scope="page">
            <c:param name= "txtUsername" value="${sessionScope.USER.email}"/>
        </c:url>
        <c:url value="ViewQuestionByAdminController" var="linkViewQuizs" scope="page">
        </c:url>
        <c:url value="formCreateQuestion.jsp" var= "linkCreateQuiz" scope="page">
        </c:url>
        <c:url value="ViewHistoryQuizController" var="linkViewQuizHistory" scope="page">
        </c:url>
        <c:if var="checkLogin" test="${not empty sessionScope.USER}" scope="page">
            <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
                <ul class="navbar-nav m-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${linkViewQuizs}">Manage Question</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${linkViewQuizHistory}">History Quiz</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${linkCreateQuiz}">Create Question</a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a class="btn btn-success btn-sm ml-3" href="${linkViewQuizs}">
                            <i class="fa fa-user"></i> ${sessionScope.USER.name}
                        </a>
                    </li>
                    <li >
                        <a class="btn btn-success btn-sm ml-3" href="${linkLogout}">Logout</a>
                    </li>
                </ul>
            </div>
        </c:if>
    </div>
</nav>

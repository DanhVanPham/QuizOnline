<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--begin of menu-->
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="#">Quiz-Online</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <c:if var="checkLogin" test="${not empty sessionScope.USER}" scope="page">
            <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
                <ul class="navbar-nav m-auto">
                    <li class="nav-item">
                        <form action="formSelectQuiz.jsp" method="POST" id="form-submit-quiz" <c:if test="${sessionScope.TIME != null or sessionScope.cart != null and sessionScope.cart.getCart().size() lt requestScope.ENDPAGE}">onsubmit="return confirm('Required submit before get new quiz! Do you want to continue?');"</c:if>>
                                <button type="submit" class="nav-link" style="background-color: #343a40;border: none;">Get Quiz</button>
                            </form>
                        </li>
                        <li class="nav-item">
                            <form action="ViewHistoryQuizController" method="POST" id="form-submit-quiz" <c:if test="${sessionScope.TIME != null or sessionScope.cart != null and sessionScope.cart.getCart().size() lt requestScope.ENDPAGE}">
                              onsubmit="return confirm('Required submit before get history quiz! Do you want to continue?');"</c:if>>
                                <button type="submit" class="nav-link" style="background-color: #343a40;border: none;">History Quiz</button>
                            </form>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a class="btn btn-success btn-sm ml-3" href="#">
                                <i class="fa fa-user"></i> ${sessionScope.USER.name}
                        </a>
                    </li>
                    <li >
                        <form action="LogoutController" method="POST" id="form-submit-quiz" <c:if test="${sessionScope.TIME != null or sessionScope.cart != null and sessionScope.cart.getCart().size() lt requestScope.ENDPAGE}">
                              onsubmit="return confirm('Required submit before logout! Do you want to continue logout?');"</c:if>>
                            <input type="hidden" name="txtUsername" value="${sessionScope.USER.email}"/>
                            <button type="submit" class="btn btn-success btn-sm ml-3" >Logout</button>
                        </form>
                    </li>
                </ul>
            </div>
        </c:if>
        <c:if test="${!checkLogin}" >
            <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
                <ul class="nav navbar-nav navbar-right">
                    <li><a class="btn btn-success btn-sm ml-3" href="login.jsp">
                            <i class="fa fa-user"></i>
                        </a>
                    </li>
                    <li >
                        <a class="btn btn-success btn-sm ml-3" href="login.jsp">Login</a>
                    </li>
                </ul>
            </div>
        </c:if>
    </div>
</nav>
<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading">Welcome to Quiz-Online: </h1>
        <p class="lead text-muted mb-0">Kiểm tra kiến thức, "tiếp nối thành công"</p>
    </div>
</section>
<!--end of menu-->

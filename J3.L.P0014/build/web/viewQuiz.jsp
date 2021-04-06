<%-- 
    Document   : viewQuiz
    Created on : Feb 3, 2021, 12:16:29 PM
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
        <link rel="stylesheet" type="text/css" href="css/style.css"/><link rel="stylesheet" type="text/css" href="css/viewQuiz.css">
        <title>View Quiz Page</title>
    </head>
    <body>
        <jsp:include page="menu.jsp"/> 
        <div class="container mt-5" style="margin-bottom: 5%;">
            <div class="d-flex justify-content-center row">
                <div class="col-md-10 col-lg-10">
                    <c:set var="question" value="${requestScope.LIST_ANSWER}" scope="page"></c:set>
                        <div class="border">
                            <div class="question bg-white p-3 border-bottom">
                                <div class="d-flex flex-row justify-content-between align-items-center mcq">
                                    <h4>MCQ Quiz</h4><span>(${requestScope.INDEX_PAGE} of ${requestScope.ENDPAGE})</span>
                            </div>
                        </div>
                        <div class="question bg-white p-3 border-bottom">
                            <div class="d-flex flex-row align-items-center question-title">
                                <h3 class="text-danger">Q.${requestScope.INDEX_PAGE}</h3>
                                <h5 class="mt-1 ml-2">${question.get(0).questionContent}</h5>
                            </div>
                            <c:forEach var="answer" items="${question}" varStatus="counter">
                                <form id="form-update-answer" method="post" action="AddAnswerToCartController">
                                    <input type="hidden" name="answerUserId" value="${answer.answerUserId}"/>
                                    <input type="hidden" name="answerId" value="${answer.answerId}"/>
                                    <input type="hidden" name="quizId" value="${requestScope.QUIZ.quizId}"/>
                                    <input type="hidden" name="pageIndex" value="${requestScope.INDEX_PAGE}"/>
                                    <div style="display: block;margin-left: 5%;">
                                        <c:if var="test" test="${sessionScope.cart.getCart().containsKey(answer.answerUserId) and sessionScope.cart.getCart().get(answer.answerUserId) eq answer.answerContent}" scope="page">
                                        </c:if>
                                        <input class="form-check-input" type="radio" onclick="submit()" name="checkRadioAnswer" id="exampleRadios1" value="${answer.answerContent}" 
                                               <c:if test="${test}">checked</c:if>>
                                               <label class="form-check-label" for="exampleRadios1">
                                               <c:if test="${counter.count eq 1}">A. ${answer.answerContent}</c:if>
                                               <c:if test="${counter.count eq 2}">B. ${answer.answerContent}</c:if>
                                               <c:if test="${counter.count eq 3}">C. ${answer.answerContent}</c:if>
                                               <c:if test="${counter.count eq 4}">D. ${answer.answerContent}</c:if>
                                               </label><br/>
                                        </div>
                                    </form>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="d-flex flex-row justify-content-between align-items-center p-3 bg-white">
                        <c:url value="ViewQuestionInQuizController" var="linkPagningQuizPrevious">
                            <c:param name="pageIndex" value="${requestScope.INDEX_PAGE - 1}"/>
                            <c:param name="quizId" value="${requestScope.QUIZ.quizId}"/>
                        </c:url>
                        <c:url value="ViewQuestionInQuizController" var="linkPagningQuizNext">
                            <c:param name="pageIndex" value="${requestScope.INDEX_PAGE + 1}"/>
                            <c:param name="quizId" value="${requestScope.QUIZ.quizId}"/>
                        </c:url>
                        <c:if var="testPrevious" test="${requestScope.INDEX_PAGE eq 1}" scope="page">
                            <a class="btn btn-primary d-flex disabled align-items-center btn-danger" href="${linkPagningQuizPrevious}">
                                <i class="fa fa-angle-left mt-1 mr-1"></i>&nbsp;previous
                            </a>
                        </c:if>
                        <c:if test="${!testPrevious}">
                            <a class="btn btn-primary d-flex align-items-center btn-danger" href="${linkPagningQuizPrevious}">
                                <i class="fa fa-angle-left mt-1 mr-1"></i>&nbsp;previous
                            </a>
                        </c:if>
                        <c:if var="testNext" test="${requestScope.INDEX_PAGE eq requestScope.ENDPAGE}" scope="page">
                            <a class="btn btn-primary border-success disabled align-items-center btn-success" type="button" href="${linkPagningQuizNext}">
                                Next<i class="fa fa-angle-right ml-2"></i>
                            </a>
                        </c:if>
                        <c:if test="${!testNext}">
                            <a class="btn btn-primary border-success align-items-center btn-success" type="button" href="${linkPagningQuizNext}">
                                Next<i class="fa fa-angle-right ml-2"></i>
                            </a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div style="margin-left: 40%;display: block;border: red solid;width: 280px;">
            <p id="days" style="display: inline;font-size: 40px;margin-top: 0px;"></p>
            <p id="hours" style="display: inline;font-size: 40px;margin-top: 0px;"></p>
            <p id="mins" style="display: inline;font-size: 40px;margin-top: 0px;"></p>
            <p id="secs" style="display: inline;font-size: 40px;margin-top: 0px;"></p>
            <h2 id="end" style="display: inline;font-size: 40px;margin-top: 0px;"></h2>
        </div>
        <form action="SubmitQuizController" method="POST" id="form-submit-quiz" <c:if test="${sessionScope.cart.getCart().size() lt requestScope.ENDPAGE}">onsubmit="return confirm('Required choice all question! Do you want to continue submit?');"</c:if> >
                <div class="d-flex flex-row justify-content-between align-items-center p-3" style="margin-left: 45%;">
                    <input type="hidden" name="quizId" value="${requestScope.QUIZ.quizId}"/>
                <button class="btn btn-primary d-flex align-items-center btn-danger">
                    Submit
                </button>
            </div>
        </form>
        <input type="hidden" id="timeStart" value="${sessionScope.TIME}"/>
        <jsp:include page="footer.jsp"></jsp:include>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
        <script>
            var time = document.getElementById("timeStart").value;
            var countDownDate = new Date(time).getTime();

            // Run myfunc every second
            var myfunc = setInterval(function () {

                var now = new Date().getTime();
                var timeleft = countDownDate - now;

                // Calculating the days, hours, minutes and seconds left
                var days = Math.floor(timeleft / (1000 * 60 * 60 * 24));
                var hours = Math.floor((timeleft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                var minutes = Math.floor((timeleft % (1000 * 60 * 60)) / (1000 * 60));
                var seconds = Math.floor((timeleft % (1000 * 60)) / 1000);

                // Result is output to the specific element
                document.getElementById("days").innerHTML = days + "d ";
                document.getElementById("hours").innerHTML = hours + "h ";
                document.getElementById("mins").innerHTML = minutes + "m ";
                document.getElementById("secs").innerHTML = seconds + "s ";

                // Display the message when countdown is over
                if (timeleft < 0) {
                    clearInterval(myfunc);
                    document.getElementById("days").innerHTML = "";
                    document.getElementById("hours").innerHTML = "";
                    document.getElementById("mins").innerHTML = "";
                    document.getElementById("secs").innerHTML = "";
                    document.getElementById("end").innerHTML = "TIME UP!!";
                    document.getElementById("form-submit-quiz").submit();
                }
            }, 1000);


        </script>
        <script>
            function submit() {
                document.getElementById("form-update-answer").submit();
            }
        </script>
    </body>
</html>

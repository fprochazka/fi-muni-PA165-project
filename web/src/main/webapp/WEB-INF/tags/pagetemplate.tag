<%@ tag dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">

    <title><c:out value="${title}"/></title>
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-flex.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.1/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet">

    <jsp:invoke fragment="head"/>
</head>
<body>
    <nav class="bg-faded bg-success navbar navbar-dark navbar-full">
        <a class="navbar-brand"><span>Soccer Records</span></a>
        <sec:authorize access="authenticated">
            <ul class="lead nav navbar-nav pull-left">
                <li class="nav-item">
                    <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Matches</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Teams</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Players</a>
                </li>
                <sec:authorize access="hasRole('admin')">
                    <li class="nav-item">
                        <a class="nav-link" href="#">Users</a>
                    </li>
                </sec:authorize>
            </ul>
            <ul class="lead nav navbar-nav pull-right">
                <li class="nav-item">
                    <%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfToken"--%>
                    <form action="<c:url value="/sign-out"/>" method="post">
                        <button class="btn btn-link nav-link" href="<c:url value="/sign-out"/>">Sign out</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    </form>
                </li>
            </ul>
        </sec:authorize>
        <sec:authorize access="!authenticated">
            <ul class="lead nav navbar-nav pull-right">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/sign-in"/>">Sign in</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/register"/>">Register</a>
                </li>
            </ul>
        </sec:authorize>
    </nav>

    <!-- page body -->
    <jsp:invoke fragment="body"/>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js" integrity="sha384-THPy051/pYDQGanwU6poAc/hOdQxjnOEXzbT+OuUAFqNqFjL+4IGLBgCJC3ZOShY" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.2.0/js/tether.min.js" integrity="sha384-Plbmg8JY28KFelvJVai01l8WyZzrYWG825m+cZ0eDDS1f7d/js6ikvy1+X+guPIB" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js" async defer></script>
    <script src="${pageContext.request.contextPath}/resources/dist/js/app.min.js" async defer></script>
</body>
</html>

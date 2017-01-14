<%@ page pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--@elvariable id="team" type="cz.muni.fi.pa165.team.Team"--%>
<%--@elvariable id="teamPlayers" type="java.util.Collection<cz.muni.fi.pa165.team.TeamPlayer>"--%>

<my:pagetemplate>
    <jsp:attribute name="body">
    <div class="container">
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <h1><c:out value="${team.name}"/></h1>

                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <a href="<c:url value="/team/${team.id}"/>" class="nav-link">Overview</a>
                    </li>
                    <li class="nav-item">
                        <a href="<c:url value="/team/${team.id}/results"/>" class="nav-link">Results</a>
                    </li>
                    <li class="nav-item">
                        <a href="<c:url value="/team/${team.id}/schedule"/>" class="nav-link">Program</a>
                    </li>
                    <li class="nav-item">
                        <a href="<c:url value="/team/${team.id}/players"/>" class="active nav-link">Players</a>
                    </li>
                </ul>
                <!-- Content centered -->
                <div class="tab-content">
                    <div id="overview" class="tab-pane fade in active">
                        <div class="table-div table-responsive">
                            <table class="table table-fixed-no-margin table-hover table-striped">
                                <thead>
                                <tr class="tr-header-team">
                                    <th class="text-xs-left">Name</th>
                                    <th class="text-xs-right">Height (cm)</th>
                                    <th class="text-xs-right">Weight (kg)</th>
                                </tr>
                                </thead>
                                <c:forEach items="${teamPlayers}" var="teamPlayer">
                                <tr class="tr-fixed-team" >
                                    <td>
                                        <a href="<c:url value="/team/${team.id}/player/${teamPlayer.id}"/>">
                                            <c:out value="${teamPlayer.surname}"/>
                                            <c:out value="${teamPlayer.firstname}"/>
                                        </a>
                                    </td>
                                    <td class="text-xs-right"><c:out value="${teamPlayer.height}"/></td>
                                    <td class="text-xs-right"><c:out value="${teamPlayer.weight}"/></td>
                                </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>

                <sec:authorize access="hasAnyRole('MODERATOR', 'ADMIN')">
                    <div class="row">
                        <div class="col-md-10 offset-md-1 text-md-right">
                            <a href="<c:url value="/team/${team.id}/player/create"/>" class="btn btn-success" role="button">Create Player</a>
                            <!-- Button trigger modal -->
                        </div>
                    </div>
                </sec:authorize>
            </div>
        </div>
    </div>

    </jsp:attribute>
</my:pagetemplate>

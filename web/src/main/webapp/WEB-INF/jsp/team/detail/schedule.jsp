<%@ page pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--@elvariable id="team" type="cz.muni.fi.pa165.team.Team"--%>
<%--@elvariable id="plannedMatches" type="cz.muni.fi.pa165.team.match.result.MatchResult"--%>

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
                        <a href="<c:url value="/team/${team.id}/schedule"/>" class="active nav-link">Program</a>
                    </li>
                    <li class="nav-item">
                        <a href="<c:url value="/team/${team.id}/players"/>" class="nav-link">Players</a>
                    </li>
                </ul>
                <!-- Content centered -->
                <div class="tab-content">
                    <div id="schedule" class="tab-pane fade in active">
                        <div class="table-responsive">
                            <table class="table table-hover table-striped table-fixed">
                                <thead>
                                <tr class="tr-fixed">
                                    <th>Date</th>
                                    <th>Home</th>
                                    <th>Away</th>
                                    <th>Score</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${plannedMatches}" var="plannedMatch">
                                         <tr class="tr-fixed">
                                             <td><c:out value="${plannedMatch.startTime.format(formatter)}"/></td>
                                             <td><c:out value="${plannedMatch.homeTeam.name}"/></td>
                                             <td><c:out value="${plannedMatch.awayTeam.name}"/></td>
                                             <td> - </td>
                                             <td>
                                                 <a href="<c:url value="/match/${plannedMatch.id}"/>" title="View detail" name="matchdetailbutton" class="btn btn-success center-block btn-outline-info">
                                                     Detail
                                                 </a>
                                             </td>
                                         </tr>
                                     </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

    </jsp:attribute>
</my:pagetemplate>

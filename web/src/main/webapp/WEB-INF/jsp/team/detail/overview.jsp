<%@ page pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:pagetemplate>
    <jsp:attribute name="body">
    <div class="container">
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <h1><c:out value="${teamStats.team.name}"/></h1>

                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <a href="<c:url value="/team/${teamStats.team.id}"/>" class="active nav-link">Overview</a>
                    </li>
                    <li class="nav-item">
                        <a href="<c:url value="/team/${teamStats.team.id}/results"/>" class="nav-link">Results</a>
                    </li>
                    <li class="nav-item">
                        <a href="<c:url value="/team/${teamStats.team.id}/schedule"/>" class="nav-link">Program</a>
                    </li>
                </ul>
                <!-- Content centered -->
                <div class="tab-content">
                    <div id="overview" class="tab-pane fade in active">
                        <h6 class="m-2">Statistics</h6>
                        <div class="table-div table-responsive">
                            <table class="table table-fixed-no-margin table-hover table-striped">
                                <thead>
                                <tr class="tr-header-team">
                                    <th class="th-center">Matches</th>
                                    <th class="th-center">Wins</th>
                                    <th class="th-center">Draws</th>
                                    <th class="th-center">Losses</th>
                                    <th class="th-center">Scored</th>
                                    <th class="th-center">Conceded</th>
                                </tr>
                                </thead>
                                <tr class="tr-fixed-team">
                                    <td><c:out value="${teamStats.matchesPlayedCnt}"/></td>
                                    <td><c:out value="${teamStats.winsCnt}"/></td>
                                    <td><c:out value="${teamStats.drawsCnt}"/></td>
                                    <td><c:out value="${teamStats.lossesCnt}"/></td>
                                    <td><c:out value="${teamStats.goalsScoredCnt}"/></td>
                                    <td><c:out value="${teamStats.goalsConcededCnt}"/></td>
                                </tr>
                            </table>
                        </div>
                        <h6 class="m-2">Roster</h6>
                        <div class="table-div table-responsive">
                            <table class="table table-fixed table-hover table-striped">
                                <thead>
                                <tr class="tr-header-team">
                                    <th class="th-center">Firstname</th>
                                    <th class="th-center">Surname</th>
                                    <th class="th-center">Team</th>
                                    <th class="th-center">Height</th>
                                    <th class="th-center">Weight</th>
                                    <th class="th-center">Goals</th>
                                    <th class="th-center">Assists</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${playersStats}" var="playerInfo">
                                <tr class="tr-fixed-team">
                                    <td><c:out value="${playerInfo.player.firstname}" /></td>
                                    <td><c:out value="${playerInfo.player.surname}" /></td>
                                    <td><c:out value="${playerInfo.player.team.name}" /></td>
                                    <td><c:out value="${playerInfo.player.height}" /></td>
                                    <td><c:out value="${playerInfo.player.weight}" /></td>
                                    <td><c:out value="${playerInfo.goals}" /></td>
                                    <td><c:out value="${playerInfo.assists}" /></td>
                                </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
                <!-- Tabs content -->
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">

                            <div class="modal-body">
                                Are you sure you want to delete this item?
                            </div>
                            <div class="modal-footer">
                                <c:url value="/team/${teamStats.team.id}/delete" var="deleteTeamActionUrl"/>
                                <form:form method="post" action="${deleteTeamActionUrl}" autocomplete="off">
                                    <fieldset>
                                        <div id="form-buttons" class="team-form-item">
                                            <a class="btn btn-success" role="button" id="pcancel" data-dismiss="modal">Cancel</a>
                                            <input id="psubmit" name="submit" type="submit" class="btn btn-success" value="Delete">
                                        </div>
                                    </fieldset>
                                 </form:form>
                            </div>
                        </div>
                    </div>
                </div>

                <sec:authorize access="hasAnyRole('MODERATOR', 'ADMIN')">
                    <div class="row">
                        <div class="col-md-10 offset-md-1 delete-team-button-area text-md-right">
                            <a href="<c:url value="/team/${teamStats.team.id}/edit"/>" class="btn btn-success edit-team-button-area" role="button">Edit Team</a>
                            <a href="#myModal" class="btn btn-outline-danger" data-toggle="modal" role="button">Delete Team</a>
                            <!-- Button trigger modal -->
                        </div>
                    </div>
                </sec:authorize>
            </div>
        </div>
    </div>

    </jsp:attribute>
</my:pagetemplate>

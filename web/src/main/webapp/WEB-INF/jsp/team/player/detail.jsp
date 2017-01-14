<%@ page pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--@elvariable id="teamPlayer" type="cz.muni.fi.pa165.team.TeamPlayer"--%>
<%--@elvariable id="playerAssistGoals" type="java.util.Collection<cz.muni.fi.pa165.team.match.TeamMatchGoal>"--%>
<%--@elvariable id="playerScorerGoals" type="java.util.Collection<cz.muni.fi.pa165.team.match.TeamMatchGoal>"--%>

<my:pagetemplate>
    <jsp:attribute name="body">
    <div class="container">
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <h1><c:out value="${teamPlayer.firstname} ${teamPlayer.surname}"/></h1>
                <h2>
                    <a href="<c:url value="/team/${teamPlayer.team.id}"/>" class="nav-link"><c:out value="${teamPlayer.team.name}"/></a>
                </h2>
                <hr>

                <!-- Content centered -->
                <div class="tab-content">
                    <div id="player-goals" class="tab-pane fade in active">
                        <h6 class="m-2">Scored</h6>
                        <div class="table-div table-responsive">
                            <table class="table table-fixed-no-margin table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>Goals</th>
                                    <th>Against</th>
                                </tr>
                                </thead>
                                <c:forEach items="${playerScorerGoals}" var="goal">
                                <tr class="tr-fixed-team">
                                    <td>1</td>
                                    <td><c:out value="${goal.match.homeTeam == teamPlayer.team ? goal.match.awayTeam.name : goal.match.homeTeam.name}"/></td>
                                </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <h6 class="m-2">Assisted</h6>
                        <div class="table-div table-responsive">
                            <table class="table table-fixed table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>Goals</th>
                                    <th>Against</th>
                                </tr>
                                </thead>
                                <c:forEach items="${playerAssistGoals}" var="goal">
                                <tr class="tr-fixed-team">
                                    <td>1</td>
                                    <td><c:out value="${goal.match.homeTeam == teamPlayer.team ? goal.match.awayTeam.name : goal.match.homeTeam.name}"/></td>
                                </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>

                <!-- Tabs content -->
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" style="display: none;">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">

                            <div class="modal-body">
                                Are you sure you want to delete this player?
                            </div>
                            <div class="modal-footer">
                                <c:url value="/team/${teamPlayer.team.id}/player/${teamPlayer.id}/delete" var="deleteTeamPlayerActionUrl"/>
                                <form:form method="post" action="${deleteTeamPlayerActionUrl}" autocomplete="off">
                                    <fieldset>
                                        <div id="form-buttons" class="team-form-item">
                                            <a class="btn btn-outline-warning" role="button" data-dismiss="modal">Cancel</a>
                                            <input name="submit" type="submit" class="btn btn-danger" value="Delete">
                                        </div>
                                    </fieldset>
                                 </form:form>
                            </div>
                        </div>
                    </div>
                </div>

                <sec:authorize access="hasAnyRole('MODERATOR', 'ADMIN')">
                    <div class="row">
                        <div class="col-md-10 offset-md-1 text-md-right">
                            <a href="<c:url value="/team/${teamPlayer.team.id}/player/${teamPlayer.id}/edit"/>" class="btn btn-success" role="button">Edit Player</a>
                            <a href="#myModal" class="btn btn-outline-danger" data-toggle="modal" role="button">Delete Player</a>
                            <!-- Button trigger modal -->
                        </div>
                    </div>
                </sec:authorize>
            </div>
        </div>
    </div>

    </jsp:attribute>
</my:pagetemplate>

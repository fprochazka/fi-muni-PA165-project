<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:pagetemplate title="MatchDetail">
    <jsp:attribute name="body">

        <div class="container">
            <div class="row">
                <div class="col-md-8 offset-md-2">

                    <legend class="m-t-3 text-md-center" id="match-header">
                        <strong><c:out value="${matchResult.match.homeTeam.name}"/></strong>
                        <span> </span>
                        <c:out value="${matchResult.homeGoals} - ${matchResult.awayGoals}"/>
                        <span> </span>
                        <strong><c:out value="${matchResult.match.awayTeam.name}"/></strong>
                    </legend>
                    <p class="m-a-0 text-md-center">
                        <strong><span>Start: </span></strong>
                        <c:out value="${matchResult.match.startTime.format(formatter)}"/>
                    </p>
                    <p class="text-md-center">
                        <strong><span>End: </span></strong>
                        <c:out value="${matchResult.match.endTime == null ? ' - ' : matchResult.match.endTime.format(formatter)}"/>
                    </p>

                    <div class="row">
                        <div class="col-md-6">
                            <div class="table-div table-responsive">
                                <table class="table table-fixed table-hover table-striped table-fixed-small">
                                    <thead>
                                        <tr class="tr-fixed-small">
                                            <th>Time</th>
                                            <th>Scorer</th>
                                            <th>Assist</th>
                                            <th> </th>
                                        </tr>
                                    </thead>
                                    <tbody class="tbody-scrollable-small">

                                        <c:forEach items="${matchDetail.homeGoals}" var="homeGoal">
                                            <tr class="tr-fixed-small">
                                                <td><c:out value="${homeGoal.matchTime.format(formatter)}"/></td>
                                                <td><c:out value="${homeGoal.scorer.firstname} ${homeGoal.scorer.surname}"/></td>
                                                <td><c:out value="${homeGoal.assistant.firstname} ${homeGoal.assistant.surname}"/></td>

                                                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')">
                                                    <td>
                                                        <a href="#deleteHomeGoalModal" title="Delete goal" class="btn btn-block btn-goal-delete" data-toggle="modal" role="button">
                                                            <strong>X</strong>
                                                        </a>
                                                    </td>
                                                    <div class="modal fade" id="deleteHomeGoalModal" tabindex="-1" role="dialog" style="display: none;">
                                                        <div class="modal-dialog" role="document">
                                                            <div class="modal-content">

                                                                <div class="modal-body">
                                                                    Are you sure you want to delete this goal?
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <c:url value="/match/${matchResult.match.id}/goal/${homeGoal.id}/delete" var="deleteHomeGoalActionUrl"/>
                                                                    <form:form method="post" action="${deleteHomeGoalActionUrl}" autocomplete="off">
                                                                        <fieldset>
                                                                            <div class="team-form-item">
                                                                                <a class="btn btn-outline-warning" role="button" data-dismiss="modal">Cancel</a>
                                                                                <input name="submit" type="submit" class="btn btn-danger" value="Delete">
                                                                            </div>
                                                                        </fieldset>
                                                                    </form:form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </sec:authorize>

                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </table>

                                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')">
                                    <a href="<c:url value="/match/${matchResult.match.id}/team/${matchResult.match.homeTeam.id}/goal/create"/>" class="btn btn-outline-primary pull-right">Add Goal</a>
                                </sec:authorize>

                            </div>

                            <h4 class="m-a-0 roster-block">Roster</h4>
                            <div class="table-responsive">
                                <table class="table table-hover table-striped">
                                    <thead>
                                        <tr>
                                            <th>Firstname</th>
                                            <th>Lastname</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${matchDetail.homeRoster}" var="player">
                                            <tr>
                                                <td>${player.firstname}</td>
                                                <td>${player.surname}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="table-div table-responsive">
                                <table class="table table-fixed table-hover table-striped table-fixed-small">
                                    <thead>
                                    <tr class="tr-fixed-small">
                                        <th>Time</th>
                                        <th>Scorer</th>
                                        <th>Assist</th>
                                    </tr>
                                    </thead>
                                    <tbody class="tbody-scrollable-small">

                                        <c:forEach items="${matchDetail.awayGoals}" var="awayGoal">
                                            <tr class="tr-fixed-small">
                                                <td><c:out value="${awayGoal.matchTime.format(formatter)}"/></td>
                                                <td><c:out value="${awayGoal.scorer.firstname} ${awayGoal.scorer.surname}"/></td>
                                                <td><c:out value="${awayGoal.assistant.firstname} ${awayGoal.assistant.surname}"/></td>

                                                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')">
                                                    <td>
                                                        <a href="#deleteAwayGoalModal" title="Delete goal" class="btn btn-block btn-goal-delete" data-toggle="modal" role="button">
                                                            <strong>X</strong>
                                                        </a>
                                                    </td>
                                                    <div class="modal fade" id="deleteAwayGoalModal" tabindex="-1" role="dialog" style="display: none;">
                                                        <div class="modal-dialog" role="document">
                                                            <div class="modal-content">

                                                                <div class="modal-body">
                                                                    Are you sure you want to delete this goal?
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <c:url value="/match/${matchResult.match.id}/goal/${awayGoal.id}/delete" var="deleteAwayGoalActionUrl"/>
                                                                    <form:form method="post" action="${deleteAwayGoalActionUrl}" autocomplete="off">
                                                                        <fieldset>
                                                                            <div class="team-form-item">
                                                                                <a class="btn btn-outline-warning" role="button" data-dismiss="modal">Cancel</a>
                                                                                <input name="submit" type="submit" class="btn btn-danger" value="Delete">
                                                                            </div>
                                                                        </fieldset>
                                                                    </form:form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </sec:authorize>

                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </table>

                                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')">
                                    <a href="<c:url value="/match/${matchResult.match.id}/team/${matchResult.match.awayTeam.id}/goal/create"/>" class="btn btn-outline-primary pull-right">Add Goal</a>
                                </sec:authorize>

                            </div>

                            <h4 class="m-a-0 roster-block">Roster</h4>
                            <div class="table-responsive">
                                <table class="table table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th>Firstname</th>
                                        <th>Lastname</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${matchDetail.awayRoster}" var="player">
                                            <tr>
                                                <td>${player.firstname}</td>
                                                <td>${player.surname}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <div class="modal fade" id="deleteMatchModal" tabindex="-1" role="dialog" style="display: none;">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">

                                <div class="modal-body">
                                    Are you sure you want to delete this match?
                                </div>
                                <div class="modal-footer">
                                    <c:url value="/match/${matchResult.match.id}/delete" var="deleteMatchActionUrl"/>
                                    <form:form method="post" action="${deleteMatchActionUrl}" autocomplete="off">
                                        <fieldset>
                                            <div class="team-form-item">
                                                <a class="btn btn-outline-warning" role="button" data-dismiss="modal">Cancel</a>
                                                <input name="submit" type="submit" class="btn btn-danger" value="Delete">
                                            </div>
                                        </fieldset>
                                     </form:form>
                                </div>

                            </div>
                        </div>
                    </div>

                    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')">
                        <div class="text-md-center btn-match-detail">
                            <a href="<c:url value="/match/${matchResult.match.id}/edit"/> " class="btn btn-success">Edit</a>
                            <a href="#deleteMatchModal" class="btn btn-outline-danger" data-toggle="modal" role="button">Delete</a>
                        </div>
                    </sec:authorize>

                </div>
            </div>
        </div>

    </jsp:attribute>
</my:pagetemplate>

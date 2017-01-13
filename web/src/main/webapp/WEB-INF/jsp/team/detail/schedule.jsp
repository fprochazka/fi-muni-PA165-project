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
                <!-- Tabs content -->
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">

                            <div class="modal-body">
                                Are you sure you want to delete this item?
                            </div>
                            <div class="modal-footer">
                                <c:url value="/team/${team.id}/delete" var="deleteTeamActionUrl"/>
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
                            <a href="<c:url value="/team/${team.id}/edit"/>" class="btn btn-success edit-team-button-area" role="button">Edit Team</a>
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

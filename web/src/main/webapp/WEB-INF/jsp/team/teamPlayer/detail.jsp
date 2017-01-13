<%@ page pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--@elvariable id="team" type="cz.muni.fi.pa165.team.TeamPlayer"--%>

<my:pagetemplate>
    <jsp:attribute name="body">
    <div class="container">
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <h1><c:out value="${teamPlayer.firstname}"/></h1>
                <h1><c:out value="${teamPlayer.surname}"/></h1>
                <h2><c:out value="${teamPlayer.team}"/></h2>
                <td><a href="<c:url value="/teamPlayer/${teamPlayer.id}"/>"><c:out value="${teamPlayer.height}"/></a></td>
                <td><a href="<c:url value="/teamPlayer/${teamPlayer.id}"/>"><c:out value="${teamPlayer.weight}"/></a></td>
                <td><a href="<c:url value="/teamPlayer/${teamPlayer.id}"/>"><c:out value="${teamPlayer.height}"/></a></td>
                <td>Goals? 3</td>
                <td>Assists: 1</td>
                <!-- Content centered -->
                <div class="tab-content">
                    <div id="overview" class="tab-pane fade in active">
                        <div class="table-div table-responsive">
                            <table class="table table-fixed-no-margin table-hover table-striped">
                                <thead>
                                <tr class="tr-header-team">
                                    <th class="th-center">Match Date</th>
                                    <th class="th-center">Home</th>
                                    <th class="th-center">Away</th>
                                    <th class="th-center">Score</th>
                                    <th class="th-center">Goal Time</th>
                                    <th class="th-center">Goal</th>
                                    <th class="th-center">Assist</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${goals}" var="goals">
                                         <tr class="tr-fixed-team clickable-row">
                                             <td><a href="<c:url value="/goal/${goal.id}"/>"><c:out value="${goal.starttime}"/></a></td>
                                             <td><a href="<c:url value="/goal/${goal.id}"/>"><c:out value="${goal.hometeam}"/></a></td>
                                             <td><a href="<c:url value="/goal/${goal.id}"/>"><c:out value="${goal.awayteam}"/></a></td>
                                             <td>1:1</td>
                                             <td><a href="<c:url value="/goal/${goal.id}"/>"><c:out value="${goal.goaltime}"/></a></td>
                                                 <%--sloupce pro Scored a Assisted?--%>
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
                                Are you sure you want to delete this player?
                            </div>
                            <div class="modal-footer">
                                <c:url value="/teamplayer/${teamplayer.id}/delete" var="deleteTeamPlayerActionUrl"/>
                                <form:form method="post" action="${deleteTeamPlayerActionUrl}" autocomplete="off">
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
                            <a href="<c:url value="/teamplayer/${teamplayer.id}/edit"/>" class="btn btn-success edit-team-button-area" role="button">Edit Player</a>
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

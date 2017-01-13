<%@ page pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--@elvariable id="teamPlayer" type="cz.muni.fi.pa165.team.TeamPlayer"--%>

<my:pagetemplate>
    <jsp:attribute name="body">
    <div class="container">
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <h1><c:out value="Players Overview"/></h1>

                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <a href="<c:url value="/teamPlayer/${team.id}"/>" class="nav-link">Overview</a>
                    </li>
                    <li class="nav-item">
                        <a href="<c:url value="/teamPlayer/${team.id}/results"/>" class="active nav-link">Top Scorers</a>
                    </li>
                    <li class="nav-item">
                        <a href="<c:url value="/teamPlayer/${team.id}/schedule"/>" class="nav-link">Top Assistants</a>
                    </li>
                </ul>
                <!-- Content centered -->
                <div id="players_overview" class="tab-pane fade in active">
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
                            <c:forEach items="${players}" var="teamPlayer">
                                         <tr class="tr-fixed-team clickable-row">
                                             <td><a href="<c:url value="/teamPlayer/${teamPlayer.id}"/>"><c:out value="${teamPlayer.firstname}"/></a></td>
                                             <td><a href="<c:url value="/teamPlayer/${teamPlayer.id}"/>"><c:out value="${teamPlayer.surname}"/></a></td>
                                             <td><a href="<c:url value="/teamPlayer/${teamPlayer.id}"/>"><c:out value="${teamPlayer.team}"/></a></td>
                                             <td><a href="<c:url value="/teamPlayer/${teamPlayer.id}"/>"><c:out value="${teamPlayer.height}"/></a></td>
                                             <td><a href="<c:url value="/teamPlayer/${teamPlayer.id}"/>"><c:out value="${teamPlayer.weight}"/></a></td>
                                             <td>4</td>
                                             <td>3</td>
                                         </tr>
                                     </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- Tabs content -->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="newteamplayerbutton"></label>
                    <div class="col-md-4 center-block">
                        <a href="<c:url value="/teamplayer/create"/>" name="newteamplayerbutton" class="btn btn-success center-block team-button-area">New Player</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    </jsp:attribute>
</my:pagetemplate>

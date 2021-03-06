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
                     <h1 class="m-y-3">Teams Overview</h1>
                     <!-- Content centered -->
                     <div class="m-y-1 tab-content">
                         <!-- BEGIN teams_overview -->
                         <div id="players_overview" class="tab-pane fade in active">
                             <div class="table-div table-responsive">
                                 <table class="table table-fixed table-hover table-striped">
                                     <thead>
                                     <tr class="tr-header-team">
                                         <th class="th-center">Team</th>
                                         <th class="th-center">M</th>
                                         <th class="th-center">W</th>
                                         <th class="th-center">D</th>
                                         <th class="th-center">L</th>
                                         <th class="th-center">Scored</th>
                                         <th class="th-center">Conceded</th>
                                     </tr>
                                     </thead>
                                     <tbody>
                                     <c:forEach items="${teams}" var="team">
                                         <tr class="tr-fixed-team clickable-row">
                                             <td><a href="<c:url value="/team/${team.team.id}"/>"><c:out value="${team.team.name}" /></a></td>
                                             <td><c:out value="${team.matchesPlayedCnt}"/></td>
                                             <td><c:out value="${team.winsCnt}"/></td>
                                             <td><c:out value="${team.drawsCnt}"/></td>
                                             <td><c:out value="${team.lossesCnt}"/></td>
                                             <td><c:out value="${team.goalsScoredCnt}"/></td>
                                             <td><c:out value="${team.goalsConcededCnt}"/></td>
                                         </tr>
                                     </c:forEach>
                                     </tbody>
                                 </table>
                             </div>
                         </div>
                         <!-- END teams_overview -->
                         <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')">
                             <div class="form-group">
                                 <label class="col-md-4 control-label" for="newteambutton"></label>
                                 <div class="col-md-4 center-block">
                                     <a href="<c:url value="/team/create"/>" name="newteambutton" class="btn btn-success center-block team-button-area">New Team</a>
                                 </div>
                             </div>
                         </sec:authorize>
                     </div>
                 </div>
             </div>
         </div>


    </jsp:attribute>
</my:pagetemplate>

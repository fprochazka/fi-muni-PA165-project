<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="PlayedMatchesOverview">
    <jsp:attribute name="body">
         <div class="container">
             <div class="row">
                 <div class="col-md-8 offset-md-2">

                     <h1>Matches overview</h1>
                     <ul class="nav nav-tabs">
                         <li class="nav-item">
                             <a href="${pageContext.request.contextPath}/matches" class="active nav-link">Played</a>
                         </li>
                         <li class="nav-item">
                             <a href="${pageContext.request.contextPath}/matches/upcomming" class="nav-link">Upcomming</a>
                         </li>
                     </ul>

                     <div class="m-y-1 tab-content">
                         <div id="played" class="tab-pane fade in active">
                             <div class="table-div table-responsive">
                                 <table class="table table-fixed table-hover table-striped table-fixed">
                                     <thead>
                                         <tr class="tr-header">
                                             <th>Date</th>
                                             <th>Home</th>
                                             <th>Away</th>
                                             <th>Score</th>
                                             <th></th>
                                         </tr>
                                     </thead>
                                     <tbody>
                                     <c:forEach items="${pmContainer.matches}" var="playedMatch">
                                         <tr>
                                             <td><c:out value="${playedMatch.startTime.format(pmContainer.formatter)}" /></td>
                                             <td><c:out value="${playedMatch.homeTeam.name}" /></td>
                                             <td><c:out value="${playedMatch.awayTeam.name}" /></td>
                                             <td><c:out value="${pmContainer.homeGoals.get(playedMatch)} - ${pmContainer.awayGoals.get(playedMatch)}" /></td>
                                             <td>
                                                 <a href="${pageContext.request.contextPath}/match/${playedMatch.id}" title="View detail" name="matchdetailbutton" class="btn btn-success center-block" style="background-color: grey; border: 0px">
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

                     <div class="form-group">
                         <label class="col-sm-5 control-label" for="newmatchbutton"></label>
                         <div class="col-sm-3 center-block">
                             <a href="${pageContext.request.contextPath}/match/new" name="newmatchbutton" class="btn btn-success center-block player-button-area">New match</a>
                         </div>
                     </div>
                 </div>
             </div>

         </div>

    </jsp:attribute>
</my:pagetemplate>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="MatchesOverview">
    <jsp:attribute name="body">
         <div class="container">
             <div class="row">
                 <div class="col-md-2"></div>
                 <div class="col-md-8">

                     <h1 class="m-y-3">Matches overview</h1>
                     <ul class="nav nav-tabs" draggable="true">
                         <li class="nav-item">
                             <a href="#played" class="active nav-link" data-toggle="tab">Played</a>
                         </li>
                         <li class="nav-item">
                             <a href="#upcomming" class="nav-link" data-toggle="tab">Upcomming</a>
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
                                         </tr>
                                     </thead>
                                     <tbody>
                                     <c:forEach items="${playedMatches}" var="playedMatch" varStatus="status">
                                         <tr class="tr-fixed clickable-row" data-href="/match/${playedMatch.id}">
                                             <td><c:out value="${playedMatch.startTime.format(formatter)}" /></td>
                                             <td><c:out value="${playedMatch.homeTeam.name}" /></td>
                                             <td><c:out value="${playedMatch.awayTeam.name}" /></td>
                                             <td><c:out value="${homeTeamsGoals[status.index]} - ${awayTeamsGoals[status.index]}" /></td>
                                         </tr>
                                     </c:forEach>
                                     </tbody>
                                 </table>
                             </div>
                         </div>

                         <div id="upcomming" class="tab-pane fade">
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
                                     <c:forEach items="${plannedMatches}" var="plannedMatch" varStatus="status">
                                         <tr class="tr-fixed clickable-row" data-href="/match/${plannedMatch.id}">
                                             <td><c:out value="${plannedMatch.startTime}" /></td>
                                             <td><c:out value="${plannedMatch.homeTeam}" /></td>
                                             <td><c:out value="${plannedMatch.awayTeam}" /></td>
                                             <td><c:out value=" - " /></td>
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
                             <a href="<c:url value="/match/new" />" name="newmatchbutton" class="btn btn-success center-block player-button-area">New match</a>
                         </div>
                     </div>
                 </div>

                 <div class="col-md-2"></div>
             </div>

         </div>

         <script>
            jQuery(document).ready(function ($) {
                $(".clickable-row").click(function () {
                    window.document.location = $(this).data("href");
                });
            });
         </script>

    </jsp:attribute>
</my:pagetemplate>

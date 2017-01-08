<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
    <jsp:attribute name="body">
         <div class="container">
             <div class="row">
                 <div class="col-md-2"></div>
                 <div class="col-md-8">
                     <h2 class="m-t-3 text-center" id="match-header">${match.homeTeam.name} 	${homeGoals.size()} - ${awayGoals.size()} 	${match.awayTeam.name}</h2>
                     <p class="m-a-0 text-center">Start: ${match.startTime.format(formatter)}</p>
                     <p class="text-center">End: ${match.endTime.format(formatter)}</p>
                     <div class="container">
                         <div class="row">
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
                                         <tbody>
                                         <c:forEach items="homeGoals" var="homeGoal">
                                             <tr class="tr-fixed-small clickable-row" data-href="/match/${homeGoal.id}">
                                                 <td>${homeGoal.matchTime.format(formatter)}</td>
                                                 <td>${homeGoal.scorer.surname}</td>
                                                 <td>${homeGoal.assistant.surname}</td>
                                             </tr>
                                         </c:forEach>
                                         </tbody>
                                     </table>
                                     <a href="<c:url value="/match/newgoal">" class="btn btn-default btn-link pull-right" data-dismiss="modal">Add Goal</a>
                                 </div>
                                 <div class="p-y-1"></div>
                                 <p class="m-a-0">Roster</p>
                                 <div class="table-responsive">
                                     <table class="table table-hover table-striped">
                                         <thead>
                                         <tr>
                                             <th>Firstname</th>
                                             <th>Lastname</th>
                                         </tr>
                                         </thead>
                                         <tbody>
                                         <c:forEach items="homePlayers" var="homePlayer">
                                             <tr>
                                                 <td>${homePlayer.firstname}</td>
                                                 <td>${homePlayer.surname}</td>
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
                                         <tbody>
                                         <c:forEach items="awayGoals" var="awayGoal">
                                             <tr class="tr-fixed-small clickable-row" data-href="/match/${awayGoal.id}">
                                                 <td>${awayGoal.matchTime.format(formatter)}</td>
                                                 <td>${awayGoal.scorer.surname}</td>
                                                 <td>${awayGoal.assistant.surname}</td>
                                             </tr>
                                         </c:forEach>
                                         </tbody>
                                     </table>
                                     <a href="<c:url value="/match/newgoal">" class="btn btn-default btn-link pull-right" data-dismiss="modal">Add Goal</a>
                                 </div>
                                 <div class="p-y-1"></div>
                                 <p class="m-a-0">Roster</p>
                                 <div class="table-responsive">
                                     <table class="table table-hover table-striped">
                                         <thead>
                                         <c:forEach items="awayPlayers" var="awayPlayer">
                                         <tr>
                                             <td>${awayPlayer.firstname}</td>
                                             <td>${awayPlayer.surname}</td>
                                         </tr>
                                         </c:forEach>
                                         </tbody>
                                     </table>
                                 </div>
                             </div>
                         </div>
                     </div>
                     <div class="m-t-1">
                         <a href="match_form.html" class="btn btn-success">Edit</a>
                         <a href="#myModal" class="btn btn-success m-l-1" data-toggle="modal" role="button">Delete</a>
                         <!-- Button trigger modal -->
                     </div>
                 </div>
                 <!-- Empty column -->
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

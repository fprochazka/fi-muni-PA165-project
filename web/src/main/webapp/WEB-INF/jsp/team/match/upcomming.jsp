<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:pagetemplate title="UpcommingMatchesOverview">
    <jsp:attribute name="body">

        <div class="container">
             <div class="row">
                 <div class="col-md-8 offset-md-2">

                     <h1>Matches overview</h1>
                     <ul class="nav nav-tabs">
                         <li class="nav-item">
                             <a href="<c:url value="/"/>" class="nav-link">Played</a>
                         </li>
                         <li class="nav-item">
                             <a href="<c:url value="/upcomming"/>" class="active nav-link">Upcomming</a>
                         </li>
                     </ul>

                     <div class="tab-content">
                         <div id="upcomming" class="tab-pane fade in active">
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
                                     <c:forEach items="${plannedMatches}" var="plannedMatch">
                                         <tr>
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

                         <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')">
                             <div class="form-group">
                                 <div class="col-sm-3 center-block">
                                     <a href="<c:url value="/match/create"/>" name="newmatchbutton" class="btn btn-success center-block">New match</a>
                                 </div>
                             </div>
                         </sec:authorize>
                     </div>
                 </div>
             </div>
        </div>

    </jsp:attribute>
</my:pagetemplate>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="PrepareMatchCreationForm">
    <jsp:attribute name="body">
         <div class="container">
             <div class="row">
                 <div class="col-md-2"></div>
                 <div class="col-md-8">

                     <c:url value="${matchRequest.target}" var="matchRequestActionUrl"/>
                     <form class="form-horizontal match-form" id="match-form" method="post" modelAttribute="matchRequest" action="${matchRequestActionUrl}" autocomplete="off">
                         <fieldset>
                             <legend id="match-form-legend">Match information form</legend>

                             <%--<s:bind path="homeTeam">--%>
                                 <div class="form-group row match-form-item">
                                     <label for="hteam" class="col-md-3 col-form-label col-form-label-lg">Home Team</label>
                                     <div class="col-md-7">
                                         <select id="hteam" class="match-dropdown-items match-form-team">
                                             <option>Select</option>
                                             <c:forEach items="homeTeams" var="homeTeam">
                                                 <option><c:out value="${homeTeam.name}" /></option>
                                             </c:forEach>
                                         </select>
                                     </div>
                                 </div>
                             <%--</s:bind>--%>

                             <%--<s:bind path="awayt">--%>
                                 <div class="form-group row match-form-item">
                                     <label for="ateam" class="col-md-3 col-form-label col-form-label-lg">Away Team</label>
                                     <div class="col-md-7">
                                         <select id="ateam" class="match-dropdown-items match-form-team">
                                             <option>Select</option>
                                             <c:forEach items="awayTeams" var="awayTeam">
                                                 <option><c:out value="${awayTeam.name}" /></option>
                                             </c:forEach>
                                         </select>
                                     </div>
                                 </div>
                             <%--</s:bind>--%>

                             <%--<s:bind path="startt">--%>
                                 <div class="form-group row match-form-item">
                                     <label for="start-time" class="col-md-3 col-form-label col-form-label-lg">Start Time</label>
                                     <div class="col-md-7 match-time-picker">
                                         <input class="form-control form-control-sm" type="datetime-local" id="start-time" required>
                                     </div>
                                 </div>
                             <%--</s:bind>--%>

                             <%--<s:bind path="endt">--%>
                                 <div class="form-group row match-form-item">
                                     <label for="end-time" class="col-md-3 col-form-label col-form-label-lg">End Time</label>
                                     <div class="col-md-7 match-time-picker">
                                         <input class="form-control form-control-sm" type="datetime-local" id="end-time">
                                     </div>
                                 </div>
                             <%--</s:bind>--%>

                             <div id="form-buttons" class="match-form-item">
                                 <a href="<c:url value="/matches" />" class="btn btn-success mbuttons" role="button" id="mcancel">Cancel</a>
                                 <input id="msubmit" name="submit" type="submit" class="btn btn-success mbuttons" value="Save">
                             </div>

                         </fieldset>
                     </form>

                 </div>
             </div>
         </div>

    </jsp:attribute>
</my:pagetemplate>

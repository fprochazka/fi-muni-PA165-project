<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="EditMatchForm">
    <jsp:attribute name="body">

        <div class="container">
             <div class="row">
                 <div class="col-md-8 offset-md-2">

                     <form:form class="form-horizontal match-form" id="match-form-edit" method="post" action="${requestScope['javax.servlet.forward.request_uri']}" modelAttribute="matchRequest" autocomplete="off">
                         <fieldset>
                             <legend>Edit match</legend>

                             <s:bind path="homeTeamId">
                                 <div class="form-group row match-form-item ${status.error ? 'has-danger' : ''}">
                                     <form:label path="homeTeamId" for="hteam" cssClass="col-md-3 col-form-label col-form-label-lg form-control-label">Home Team</form:label>
                                     <div class="col-md-7">
                                         <form:select path="homeTeamId" id="hteam" cssClass="match-dropdown-items match-form-team form-control">
                                             <form:option value="${null}" label="--- Select ---" disabled="true"/>
                                             <c:forEach items="${teams}" var="team">
                                                 <form:option value="${team.id}" label="${team.name}" selected="${team.id.equals(matchRequest.homeTeamId) ? 'selected' : ''}" disabled="${!team.id.equals(matchRequest.homeTeamId) ? 'true' : 'false'}"/>
                                             </c:forEach>
                                         </form:select>
                                         <form:errors path="homeTeamId" cssClass="form-control-label" />
                                     </div>
                                 </div>
                             </s:bind>

                             <s:bind path="awayTeamId">
                                 <div class="form-group row match-form-item ${status.error ? 'has-danger' : ''}">
                                     <form:label path="awayTeamId" for="ateam" cssClass="col-md-3 col-form-label col-form-label-lg form-control-label">Away Team</form:label>
                                     <div class="col-md-7">
                                         <form:select path="awayTeamId" id="ateam" cssClass="match-dropdown-items match-form-team form-control">
                                             <form:option value="${null}" label="--- Select ---" disabled="true"/>
                                             <c:forEach items="${teams}" var="team">
                                                 <form:option value="${team.id}" label="${team.name}" selected="${team.id.equals(matchRequest.awayTeamId) ? 'selected' : ''}" disabled="${!team.id.equals(matchRequest.awayTeamId) ? 'true' : 'false'}"/>
                                             </c:forEach>
                                         </form:select>
                                         <form:errors path="awayTeamId" cssClass="form-control-label" />
                                     </div>
                                 </div>
                             </s:bind>


                             <s:bind path="startTime">
                                 <div class="form-group row match-form-item ${status.error ? 'has-danger' : ''}">
                                     <form:label path="startTime" for="start-time" cssClass="col-md-3 col-form-label col-form-label-lg form-control-label">Start Time</form:label>
                                     <div class="col-md-7 match-time-picker">
                                         <form:input path="startTime" cssClass="form-control form-control-sm" type="datetime-local" id="start-time" required="required"/>
                                         <form:errors path="startTime" cssClass="form-control-label"/>
                                     </div>
                                 </div>
                             </s:bind>

                             <s:bind path="endTime">
                                 <div class="form-group row match-form-item ${status.error ? 'has-danger' : ''}">
                                     <form:label path="endTime" for="end-time" cssClass="col-md-3 col-form-label col-form-label-lg form-control-label">End Time</form:label>
                                     <div class="col-md-7 match-time-picker">
                                         <form:input path="endTime" cssClass="form-control form-control-sm" type="datetime-local" id="end-time" required="${matchRequest.endTime == null ? '' : 'required'}"/>
                                         <form:errors path="endTime" cssClass="form-control-label"/>
                                     </div>
                                 </div>
                             </s:bind>

                             <div class="match-form-item text-md-right">
                                 <a href="<c:url value="/"/>" class="btn btn-secondary mbuttons" role="button">Cancel</a>
                                 <input name="submit" type="submit" class="btn btn-success mbuttons" value="Save">
                             </div>
                         </fieldset>
                     </form:form>

                 </div>
             </div>
        </div>

    </jsp:attribute>
</my:pagetemplate>

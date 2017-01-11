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
                    <form:form class="form-horizontal match-form" id="goal-form" method="post" modelAttribute="goalRequest" action="${requestScope['javax.servlet.forward.request_uri']}" autocomplete="off">
                        <fieldset>
                            <legend>Create goal</legend>

                            <s:bind path="scorerId">
                                <div class="form-group row match-form-item ${status.error ? 'has-danger' : ''}">
                                    <form:label path="scorerId" for="scorer" cssClass="col-md-3 col-form-label col-form-label-lg form-control-label">Scorer</form:label>
                                    <div class="col-md-7">
                                         <form:select path="scorerId" id="scorer" cssClass="match-dropdown-items match-form-team form-control">
                                             <c:choose>
                                                 <c:when test="${goalRequest.scorerId != null}">
                                                     <form:option value="${null}" label="--- Select ---" disabled="true"/>
                                                     <c:forEach items="${players}" var="scorer">
                                                         <form:option value="${scorer.id}" label="${scorer.firstname} ${scorer.surname}" selected="${scorer.id.equals(goalRequest.scorerId) ? 'selected' : ''}"/>
                                                     </c:forEach>
                                                 </c:when>
                                                 <c:otherwise>
                                                     <form:option value="${null}" label="--- Select ---" disabled="true" selected="selected"/>
                                                     <c:forEach items="${players}" var="scorer">
                                                         <form:option value="${scorer.id}" label="${scorer.firstname} ${scorer.surname}"/>
                                                     </c:forEach>
                                                 </c:otherwise>
                                             </c:choose>
                                         </form:select>
                                        <form:errors path="scorerId" cssClass="form-control-label" />
                                    </div>
                                </div>
                            </s:bind>

                            <s:bind path="assistantId">
                                <div class="form-group row match-form-item ${status.error ? 'has-danger' : ''}">
                                    <form:label path="assistantId" for="assistant" cssClass="col-md-3 col-form-label col-form-label-lg form-control-label">Assistant</form:label>
                                    <div class="col-md-7">
                                         <form:select path="assistantId" id="assistant" cssClass="match-dropdown-items match-form-team form-control">
                                             <c:choose>
                                                 <c:when test="${goalRequest.assistantId != null}">
                                                     <form:option value="${null}" label="--- Select ---" disabled="true"/>
                                                     <c:forEach items="${players}" var="assistant">
                                                         <form:option value="${assistant.id}" label="${assistant.firstname} ${assistant.surname}" selected="${assistant.id.equals(goalRequest.assistantId) ? 'selected' : ''}"/>
                                                     </c:forEach>
                                                 </c:when>
                                                 <c:otherwise>
                                                     <form:option value="${null}" label="--- Select ---" disabled="true" selected="selected"/>
                                                     <c:forEach items="${players}" var="assistant">
                                                         <form:option value="${assistant.id}" label="${assistant.firstname} ${assistant.surname}"/>
                                                     </c:forEach>
                                                 </c:otherwise>
                                             </c:choose>
                                         </form:select>
                                        <form:errors path="assistantId" cssClass="form-control-label" />
                                    </div>
                                </div>
                            </s:bind>

                            <s:bind path="matchTime">
                                 <div class="form-group row match-form-item ${status.error ? 'has-danger' : ''}">
                                     <form:label path="matchTime" for="goalMatchTime" cssClass="col-md-3 col-form-label col-form-label-lg form-control-label">Match time</form:label>
                                     <div class="col-md-7 match-time-picker">
                                         <form:input path="matchTime" cssClass="form-control form-control-sm" type="datetime-local" min="${match.startTime.plusSeconds(1)}" max="${match.endTime == null ? '' : match.endTime.minusSeconds(1)}" step="1" id="goalMatchTime" required="required"/>
                                         <form:errors path="matchTime" cssClass="form-control-label"/>
                                     </div>
                                 </div>
                             </s:bind>

                            <div class="match-form-item text-md-right">
                                <a href="<c:url value="/match/${match.id}"/>" class="btn btn-secondary mbuttons" role="button">Cancel</a>
                                <input name="submit" type="submit" class="btn btn-success mbuttons" value="Save">
                            </div>
                        </fieldset>
                    </form:form>
                </div>
            </div>
        </div>

    </jsp:attribute>
</my:pagetemplate>

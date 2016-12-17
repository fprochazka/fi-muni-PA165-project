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
                <form:form class="team-form" id="team-form" method="post" modelAttribute="teamDetailsRequest" action="${requestScope['javax.servlet.forward.request_uri']}" autocomplete="off">
                    <fieldset>
                        <legend id="team-form-legend">Edit team</legend>
                        <s:bind path="name">
                            <p class="team-form-item ${status.error ? 'has-danger' : ''}">
                                <label for="pteamname">Team Name</label>
                                <form:input path="name" type="text" class="form-control team-text-field" id="pteamname" placeholder="e.g. Arsenal"/>
                                <form:errors path="name" class="control-label"/>
                            </p>
                        </s:bind>
                        <div id="form-buttons" class="team-form-item">
                            <input id="psubmit" name="submit" type="submit" class="btn btn-primary" value="Save">
                            <a href="<c:url value="/teams"/>" class="btn btn-outline-warning pull-right" role="button" id="pcancel">Cancel</a>
                        </div>
                    </fieldset>
                </form:form>
            </div>
        </div>
    </div>

    </jsp:attribute>
</my:pagetemplate>

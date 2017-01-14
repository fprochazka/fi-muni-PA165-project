<%@ page pageEncoding="UTF-8" %>
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
                <form:form class="player-form" modelAttribute="teamPlayerRequest" action="${requestScope['javax.servlet.forward.request_uri']}" autocomplete="off">
                    <fieldset>
                        <legend id="player-form-legend">Create Player</legend>
                        <s:bind path="firstname">
                            <p class="player-form-item ${status.error ? 'has-danger' : ''}">
                                <label for="pteamplayerfirstname">Firstname</label>
                                <form:input path="firstname" type="text" class="form-control team-text-field" id="pteamplayerfirstname" placeholder="e.g. John"/>
                                <form:errors path="firstname" class="control-label"/>
                            </p>
                        </s:bind>
                        <s:bind path="surname">
                            <p class="player-form-item ${status.error ? 'has-danger' : ''}">
                                <label for="pteamplayersurname">Surname</label>
                                <form:input path="surname" type="text" class="form-control team-text-field" id="pteamplayersurname" placeholder="e.g. Doe"/>
                                <form:errors path="surname" class="control-label"/>
                            </p>
                        </s:bind>

                        <s:bind path="height">
                            <p class="player-form-item ${status.error ? 'has-danger' : ''}">
                                <label for="pteamplayerheight">Player height in cm</label>
                                <form:input path="height" type="text" class="form-control team-text-field" id="pteamplayerheight" placeholder="e.g. 180"/>
                                <form:errors path="height" class="control-label"/>
                            </p>
                        </s:bind>

                        <s:bind path="weight">
                            <p class="player-form-item ${status.error ? 'has-danger' : ''}">
                                <label for="pteamplayerweight">Player weight in kg</label>
                                <form:input path="weight" type="text" class="form-control team-text-field" id="pteamplayerweight" placeholder="e.g. 70"/>
                                <form:errors path="weight" class="control-label"/>
                            </p>
                        </s:bind>

                        <div id="form-buttons" class="player-form-item">
                            <input name="submit" type="submit" class="btn btn-primary" value="Create">
                            <a href="<c:url value="/team/${team.id}/players"/>" class="btn btn-outline-warning pull-right" role="button">Cancel</a>
                        </div>
                    </fieldset>
                </form:form>
            </div>
        </div>
    </div>

    </jsp:attribute>
</my:pagetemplate>

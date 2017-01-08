<%@ page session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--<fmt:message var="title" key="location.view.title"><fmt:param value="${location.name}"/></fmt:message>--%>
<my:pagetemplate title="Player ??name??">
    <jsp:attribute name="body">
        <div class="container">
            <div class="row">
                <div class="col-md-2"></div>
                <div class="col-md-8">
                    <div class="col-md-12">
                        <h2 class="m-t-3">John Doe</h2>
                        <h4>FCB</h4>
                        <p id="player-params">
                  <span class="player-detail-param">
                    <strong>Height:
                      <span></span>
                    </strong>185 cm</span>
                            <span class="player-detail-param">
                    <strong>Weight:
                      <span></span>
                    </strong>90 kg</span>
                            <span class="player-detail-param">
                    <strong>Goals scored:
                      <span></span>
                    </strong>4</span>
                            <span class="player-detail-param">
                    <strong>Goals assisted:
                      <span></span>
                    </strong>3</span>
                        </p>
                        <div class="player-detail-hr">
                            <hr>
                        </div>
                    </div>
                    <div class="table-div table-responsive">
                        <table class="table table-fixed table-hover table-striped">
                            <thead>
                            <tr class="tr-header-player">
                                <th class="th-center">Match Date</th>
                                <th class="th-center">Home</th>
                                <th class="th-center">Away</th>
                                <th class="th-center">Score</th>
                                <th class="th-center">Goal Time</th>
                                <th class="th-center">Score</th>
                                <th class="th-center">Assist</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr class="tr-fixed-player">
                                <td>3.12.2016 21:30</td>
                                <td>FCB</td>
                                <td>RLM</td>
                                <td>1 - 1</td>
                                <td>3.12.2016 21:31</td>
                                <td>✓</td>
                                <td>X</td>
                            </tr>
                            <tr class="tr-fixed-player">
                                <td>3.12.2016 21:30</td>
                                <td>FCB</td>
                                <td>RLM</td>
                                <td>1 - 1</td>
                                <td>3.12.2016 21:31</td>
                                <td>X</td>
                                <td>✓</td>
                            </tr>
                            <tr class="tr-fixed-player">
                                <td>3.12.2016 21:30</td>
                                <td>FCB</td>
                                <td>RLM</td>
                                <td>1 - 1</td>
                                <td>3.12.2016 21:31</td>
                                <td>✓</td>
                                <td>X</td>
                            </tr>
                            <tr class="tr-fixed-player">
                                <td>3.12.2016 21:30</td>
                                <td>FCB</td>
                                <td>RLM</td>
                                <td>1 - 1</td>
                                <td>3.12.2016 21:31</td>
                                <td>✓</td>
                                <td>X</td>
                            </tr>
                            <tr class="tr-fixed-player">
                                <td>3.12.2016 21:30</td>
                                <td>FCB</td>
                                <td>RLM</td>
                                <td>1 - 1</td>
                                <td>3.12.2016 21:31</td>
                                <td>X</td>
                                <td>✓</td>
                            </tr>
                            <tr class="tr-fixed-player">
                                <td>3.12.2016 21:30</td>
                                <td>FCB</td>
                                <td>RLM</td>
                                <td>1 - 1</td>
                                <td>3.12.2016 21:31</td>
                                <td>✓</td>
                                <td>X</td>
                            </tr>
                            <tr class="tr-fixed-player">
                                <td>3.12.2016 21:30</td>
                                <td>FCB</td>
                                <td>RLM</td>
                                <td>1 - 1</td>
                                <td>3.12.2016 21:31</td>
                                <td>✓</td>
                                <td>X</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2"></div>
                <div class="m-t-1">
                    <a href="#" class="btn btn-success player-button-area" role="button">Edit player</a>
                    <a href="#" class="btn btn-success m-l-1 player-button-area" role="button">Delete player</a>
                    <!-- Button trigger modal -->
                </div>
            </div>
        </div>
    </jsp:attribute>
</my:pagetemplate>

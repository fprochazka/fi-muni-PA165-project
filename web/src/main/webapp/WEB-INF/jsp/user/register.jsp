<%@ page pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:pagetemplate title="Register">
    <jsp:attribute name="body">
        <div class="container">
            <div class="row">
                <div class="col-sm-8">

                    <h1>Registration</h1>
                    <br>

                    <c:url value="/register" var="registerActionUrl" />
                    <form:form class="form-horizontal" method="post" modelAttribute="registerRequest" action="${registerActionUrl}">

                        <s:bind path="email">
                            <div class="form-group row ${status.error ? 'has-danger' : ''}">
                                <label class="col-sm-2 control-label">Email</label>
                                <div class="col-sm-8">
                                    <form:input path="email" type="email" class="form-control" id="email" />
                                    <form:errors path="email" class="control-label" />
                                </div>
                            </div>
                        </s:bind>

                        <s:bind path="password">
                            <div class="form-group row ${status.error ? 'has-danger' : ''}">
                                <label class="col-sm-2 control-label">Heslo</label>
                                <div class="col-sm-8">
                                    <form:input path="password" type="password" class="form-control" id="password" />
                                    <form:errors path="password" class="control-label" />
                                </div>
                            </div>
                        </s:bind>

                        <s:bind path="passwordRepeat">
                            <div class="form-group row ${status.error ? 'has-danger' : ''}">
                                <label class="col-sm-2 control-label">Znovu heslo</label>
                                <div class="col-sm-8">
                                    <form:input path="passwordRepeat" type="password" class="form-control" id="passwordRepeat" />
                                    <form:errors path="passwordRepeat" class="control-label" />
                                </div>
                            </div>
                        </s:bind>

                        <div class="form-group row">
                            <div class="offset-sm-2 col-sm-10">
                                <button type="submit" class="btn-lg btn-primary">Register</button>
                            </div>
                        </div>

                    </form:form>

                </div>
            </div>
        </div>
    </jsp:attribute>
</my:pagetemplate>

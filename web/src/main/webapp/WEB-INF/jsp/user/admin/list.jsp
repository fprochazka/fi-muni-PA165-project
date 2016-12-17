<%@ page pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:pagetemplate>
    <jsp:attribute name="body">

        <div class="container">
            <div class="row">
                <div class="col-md-10 offset-md-1">
                    <h1>Users</h1>
                    <div class="table-div table-responsive">
                        <table class="table table-fixed table-hover table-striped">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Role</th>
                                    <th class="text-xs-right">Promote to</th>
                                </tr>
                            </thead>
                            <c:forEach items="${users}" var="user">
                                <tr class="text-left">
                                    <td><c:out value="${user.email}"/></td>
                                    <td><c:out value="${user.role}"/></td>
                                    <td class="text-xs-right">
                                        <c:if test="${user.role.toString() == 'user' || user.role.toString() == 'moderator'}">
                                            <form action="<c:url value="/admin/users/${user.id}/promote"/>" method="post" class="pull-right">
                                                <button type="submit" name="submit" class="btn ${user.role.toString() == 'user' ? 'btn-outline-warning' : 'btn-outline-danger'}">
                                                    <c:out value="${user.role.toString() == 'user' ? 'Moderator' : 'Admin'}"/>
                                                </button>
                                                <input type="hidden" name="role" value="<c:out value="${user.role.toString() == 'user' ? 'MODERATOR' : 'ADMIN'}"/>" />
                                                <sec:csrfInput />
                                            </form>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>

    </jsp:attribute>
</my:pagetemplate>

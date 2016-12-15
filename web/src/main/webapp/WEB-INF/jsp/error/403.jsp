<%@ page trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:pagetemplate>
    <jsp:attribute name="body">

         <div class="container">
             <div class="starter-template">
                 <h1>Access denied</h1>
                 <p>You do not have permission to view this page. Please try contact the web
                     site administrator if you believe you should be able to view this page.
                 </p>
                 <p><small>error 403</small></p>
             </div>
         </div><!-- /.container -->

    </jsp:attribute>
</my:pagetemplate>

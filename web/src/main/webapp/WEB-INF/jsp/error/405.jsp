<%@ page trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:pagetemplate>
    <jsp:attribute name="body">

         <div class="container">
             <div class="starter-template">
                 <h1>Method Not Allowed</h1>
                 <p>The requested method is not allowed for the URL.</p>
                 <p><small>error 405</small></p>
             </div>
         </div><!-- /.container -->

    </jsp:attribute>
</my:pagetemplate>

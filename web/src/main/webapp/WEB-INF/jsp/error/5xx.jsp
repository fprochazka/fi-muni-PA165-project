<%@ page trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:pagetemplate>
    <jsp:attribute name="body">

         <div class="container">
             <div class="starter-template">
                 <h1>Server Error</h1>
                 <p>We're sorry! The server encountered an internal error and was unable to complete your request. Please try again later.</p>
                 <p><small>error 500</small></p>
             </div>
         </div><!-- /.container -->

    </jsp:attribute>
</my:pagetemplate>

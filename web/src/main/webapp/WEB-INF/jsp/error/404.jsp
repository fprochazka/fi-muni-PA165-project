<%@ page trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:pagetemplate>
    <jsp:attribute name="body">

         <div class="container">
             <div class="starter-template">
                 <h1>Page not found</h1>
                 <p>The page you requested could not be found. It is possible that the address is
                     incorrect, or that the page no longer exists. Please use a search engine to find
                     what you are looking for.
                 </p>
                 <p><small>error 404</small></p>
             </div>
         </div><!-- /.container -->

    </jsp:attribute>
</my:pagetemplate>

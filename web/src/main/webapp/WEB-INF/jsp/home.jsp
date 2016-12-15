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
                 <!-- Navbar -->
                 <div class="col-md-2"></div>
                 <!-- Navbar -->
                 <div class="col-md-8">
                     <h1 class="m-y-3">Overview</h1>
                     <p class="m-a-0">Upcomming matches</p>
                     <div class="table-responsive">
                         <table class="table table-hover table-striped">
                             <thead>
                             <tr>
                                 <th>Date</th>
                                 <th>Home</th>
                                 <th>Away</th>
                                 <th>Score</th>
                             </tr>
                             </thead>
                             <tbody>
                             <tr>
                                 <td>3.12. 21:30</td>
                                 <td>FCB</td>
                                 <td>RLM</td>
                                 <td>-</td>
                             </tr>
                             <tr>
                                 <td>3.12. 21:30</td>
                                 <td>FCB</td>
                                 <td>RLM</td>
                                 <td>-</td>
                             </tr>
                             <tr>
                                 <td>3.12. 21:30</td>
                                 <td>FCB</td>
                                 <td>RLM</td>
                                 <td>-</td>
                             </tr>
                             </tbody>
                         </table>
                         <a data-dismiss="modal" href="" class="btn btn-default btn-link pull-right">View more</a>
                     </div>
                     <div class="p-y-1"></div>
                     <p class="m-a-0">Recent matches</p>
                     <div class="table-responsive">
                         <table class="table table-hover table-striped">
                             <thead>
                             <tr>
                                 <th>Date</th>
                                 <th>Home</th>
                                 <th>Away</th>
                                 <th>Score</th>
                             </tr>
                             </thead>
                             <tbody>
                             <tr>
                                 <td>3.12. 21:30</td>
                                 <td>FCB</td>
                                 <td>RLM</td>
                                 <td>-</td>
                             </tr>
                             <tr>
                                 <td>3.12. 21:30</td>
                                 <td>FCB</td>
                                 <td>RLM</td>
                                 <td>-</td>
                             </tr>
                             <tr>
                                 <td>3.12. 21:30</td>
                                 <td>FCB</td>
                                 <td>RLM</td>
                                 <td>-</td>
                             </tr>
                             </tbody>
                         </table>
                         <a class="btn btn-default btn-link pull-right" data-dismiss="modal">View more</a>
                     </div>
                 </div>
                 <!-- Empty column -->
                 <div class="col-md-2"></div>
             </div>
         </div>

    </jsp:attribute>
</my:pagetemplate>

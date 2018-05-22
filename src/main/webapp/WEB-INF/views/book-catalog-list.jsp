<%@include file="common/header.jspf"%>
<%@include file="common/navigation.jspf"%>

<div class="container" >
    <table class="table">
        <thead>
            <tr>
                <th scope="col" hidden>id</th>
                <th scope="col"><fmt:message key="message.namelabel" /></th>
                <th scope="col"><fmt:message key="message.authorslabel" /></th>
                <th scope="col"><fmt:message key="message.fullnamelabel" /></th>

                <th scope="col"><fmt:message key="message.onshelveslabel" /></th>
                <c:if test="${userType == 'ADMINISTRATOR'}">
                    <th scope="col"><fmt:message key="message.actionlabel" /></th>
                </c:if>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${bookcataloglist}" var="bookcataloglist">
                <tr>
                    <th scope="row" hidden>${bookcataloglist.id}</th>
                    <td><a class="nav-link"
                           href = "controller?command=viewbookcatalogentity&bookcatalogid=${bookcataloglist.id}">
                            ${bookcataloglist.name}</a></td>
                    <td>
                        <c:forEach items="${bookcataloglist.authors}"
                                   var="authors" varStatus="loop">
                            ${authors.name}
                            <c:if test="${!loop.last}">,</c:if>
                        </c:forEach>
                    </td>
                    <td>${bookcataloglist.fullName}</td>

                    <td>
                        <a class="btn btn-primary"
                           href="controller?command=listbookitems&bookcatalogid=${bookcataloglist.id}"
                           role="button"><fmt:message key="message.viewlabel" /></a>
                    </td>
                    <c:if test="${userType == 'ADMINISTRATOR'}">
                        <td>
                            <a class="btn btn-primary"
                               href="controller?command=deletebookcatalog&bookcatalogid=${bookcataloglist.id}"
                               role="button"><fmt:message key="message.deletebutton" /></a>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <c:if test="${userType == 'ADMINISTRATOR'}">
        <a class="btn btn-primary" href="controller?command=addbookcatalog" role="button"><fmt:message key="message.addbutton" /></a>
    </c:if>
</div>
<nav aria-label="Book catalog result">
    <ul class="pagination justify-content-center fixed-bottom">
        <li class="page-item  ${currentpage == 1 ? 'disabled' : ''}">
            <a class="page-link"
               href="controller?command=${command}&currentpage=${currentpage-1}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
                <span class="sr-only">Previous</span>
            </a>
        </li>
        <li class="page-item active">
            <a class="page-link"
               href="controller?command=${command}&currentpage=${currentpage}">${currentpage}</a></li>
        <li class="page-item ${currentpage + 1 > maxpages ? 'disabled' : ''}">
            <a class="page-link"
               href="controller?command=${command}&currentpage=${currentpage+1}">${currentpage+1}</a></li>
        <li class="page-item ${currentpage + 2 > maxpages ? 'disabled' : ''}">
            <a class="page-link"
               href="controller?command=${command}&currentpage=${currentpage+2}">${currentpage+2}</a></li>
        <c:if test="${currentpage < maxpage}">
            <c:set var="x" value="1"/>
        </c:if>
        <li class="page-item ${currentpage == maxpages ? 'disabled' : ''}">
            <a class="page-link"
               href="controller?command=${command}&currentpage=${currentpage+1}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
                <span class="sr-only">Next</span>
            </a>
        </li>
    </ul>
</nav>

<%@include file="common/footer.jspf"%>

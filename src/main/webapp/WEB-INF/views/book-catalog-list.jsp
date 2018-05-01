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
                    <th scope="col"><fmt:message key="message.deletebutton" /></th>
                </c:if>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${bookcataloglist}" var="bookcataloglist">
                <tr>
                    <th scope="row" hidden>${bookcataloglist.getId()}</th>
                    <td><a class="nav-link"
                           href = "controller?command=viewbookcatalogentity&bookcatalogid=${bookcataloglist.getId()}">
                            ${bookcataloglist.getName()}</a></td>
                    <td>
                        <c:forEach items="${bookcataloglist.getAuthors()}"
                                   var="authors" varStatus="loop">
                            ${authors.getName()}
                            <c:if test="${!loop.last}">,</c:if>
                        </c:forEach>
                    </td>
                    <td>${bookcataloglist.getFullName()}</td>

                    <td>
                        <a class="btn btn-primary"
                           href="controller?command=listbookitems&bookcatalogid=${bookcataloglist.getId()}"
                           role="button"><fmt:message key="message.viewlabel" /></a>
                    </td>
                    <c:if test="${userType == 'ADMINISTRATOR'}">
                        <td>
                            <a class="btn btn-primary"
                               href="controller?command=deletebookcatalog&bookcatalogid=${bookcataloglist.getId()}"
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


<%@include file="common/footer.jspf"%>

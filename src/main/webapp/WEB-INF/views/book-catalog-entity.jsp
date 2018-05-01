<%@include file="common/header.jspf"%>
<%@include file="common/navigation.jspf"%>

<div class="container">
    <form action = "controller" method="post" >
        <input type="hidden" name="command" value="savebookcatalog">
            <table class="table">
                <tbody>
                    <tr>
                        <th scope="row"><fmt:message key="message.namelabel" /></th>
                        <c:choose>
                            <c:when  test="${editmode}">
                                <td>
                                    <input type="text" class="form-control"
                                           id="name" name="name"
                                           value="${bookcatalogentity.getName()}">
                                </td>
                            </c:when>

                            <c:otherwise>
                                <td>${bookcatalogentity.getName()}</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <th scope="row"><fmt:message key="message.fullnamelabel" /></th>
                        <c:choose>
                            <c:when  test="${editmode}">
                                <td>
                                    <textarea class="form-control" id="fullname"
                                              name="fullname" rows="5">${bookcatalogentity.getFullName()}</textarea>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>${bookcatalogentity.getFullName()}</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <th scope="row"><fmt:message key="message.descriptionlabel" /></th>
                        <c:choose>
                            <c:when  test="${editmode}">
                                <td>
                                    <textarea class="form-control" id="description" name="description"
                                              rows="6">${bookcatalogentity.getDescription()}</textarea>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>${bookcatalogentity.getDescription()}</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <th scope="row"><fmt:message key="message.keywordslabel" /></th>
                        <c:choose>
                            <c:when  test="${editmode}">
                                <td>
                                    <textarea class="form-control" id="keywords" name="keywords"
                                              rows="4">${bookcatalogentity.getKeywords()}</textarea>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>${bookcatalogentity.getKeywords()}</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <th scope="row"><fmt:message key="message.authorslabel" /></th>
                        <td>
                            <c:forEach items="${bookcatalogentity.getAuthors()}" var="authors" varStatus="loop">
                                ${authors.getName()}
                                <c:if test="${!loop.last}">,</c:if>
                            </c:forEach>
                            <c:if  test="${editmode}">
                                <a class="btn btn-primary float-right"
                                   href="controller?command=editauthors"
                                   role="button">Edit</a>
                            </c:if>
                        </td>
                    </tr>
                </tbody>
            </table>
            <c:if test="${userType == 'ADMINISTRATOR'}">
                <c:if  test="${not editmode}">
                    <a class="btn btn-primary"
                       href="controller?command=editbookcatalog&bookcatalogid=${bookcatalogentity.getId()}"
                       role="button"><fmt:message key="message.editbooklabel" /></a>
                </c:if>
                <c:if  test="${editmode}">
                    <button type="submit"  value = "ok" class="btn btn-primary"><fmt:message key="message.savelabel" /></button>
                </c:if>
            </c:if>
    </form>
</div>


<%@include file="common/footer.jspf"%>

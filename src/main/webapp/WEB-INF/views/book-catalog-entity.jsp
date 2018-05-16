<%@include file="common/header.jspf"%>
<%@include file="common/navigation.jspf"%>

<div class="container">
    <form action = "controller" method="post" >
        <!--<input type="hidden" name="command" value="savebookcatalog">-->
            <table class="table">
                <tbody>
                    <tr>
                        <th scope="row"><fmt:message key="message.namelabel" /></th>
                        <c:choose>
                            <c:when  test="${editmode}">
                                <td>
                                    <input type="text" class="form-control"
                                           id="name" name="name"
                                           value="${bookcatalogentity.name}">
                                </td>
                            </c:when>

                            <c:otherwise>
                                <td>${bookcatalogentity.name}</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <th scope="row"><fmt:message key="message.fullnamelabel" /></th>
                        <c:choose>
                            <c:when  test="${editmode}">
                                <td>
                                    <textarea class="form-control" id="fullname"
                                              name="fullname" rows="5">${bookcatalogentity.fullName}</textarea>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>${bookcatalogentity.fullName}</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <th scope="row"><fmt:message key="message.descriptionlabel" /></th>
                        <c:choose>
                            <c:when  test="${editmode}">
                                <td>
                                    <textarea class="form-control" id="description" name="description"
                                              rows="6">${bookcatalogentity.description}</textarea>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>${bookcatalogentity.description}</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <th scope="row"><fmt:message key="message.keywordslabel" /></th>
                        <c:choose>
                            <c:when  test="${editmode}">
                                <td>
                                    <textarea class="form-control" id="keywords" name="keywords"
                                              rows="4">${bookcatalogentity.keywords}</textarea>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>${bookcatalogentity.keywords}</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <th scope="row"><fmt:message key="message.authorslabel" /></th>
                        <td>
                            <c:forEach items="${bookcatalogentity.authors}" var="authors" varStatus="loop">
                                ${authors.name}
                                <c:if test="${!loop.last}">,</c:if>
                            </c:forEach>
                            <c:if  test="${editmode}">
                                <!--
                                <a class="btn btn-primary float-right"
                                   href="controller?command=editauthors"
                                   role="button">Edit</a>-->
                                <button type="submit"  name="command" value = "editauthors" class="btn btn-primary">
                                    <fmt:message key="message.editauthors" />
                                </button>
                            </c:if>
                        </td>
                    </tr>
                </tbody>
            </table>
            <c:if test="${userType == 'ADMINISTRATOR'}">
                <c:if  test="${not editmode}">
                    <a class="btn btn-primary"
                       href="controller?command=editbookcatalog&bookcatalogid=${bookcatalogentity.id}"
                       role="button"><fmt:message key="message.editbooklabel" /></a>
                </c:if>
                <c:if  test="${editmode}">
                    <button type="submit"  name="command" value="savebookcatalog" class="btn btn-primary"><fmt:message key="message.savelabel" /></button>
                    <a class="btn btn-primary"
                       href="controller?command=bookcatalog&currentpage=1"
                       role="button"><fmt:message key="message.cancel" /></a>
                </c:if>
            </c:if>
    </form>
</div>

<%@include file="common/footer.jspf"%>

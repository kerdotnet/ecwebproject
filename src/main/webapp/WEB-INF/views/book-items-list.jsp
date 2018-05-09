<%@include file="common/header.jspf"%>
<%@include file="common/navigation.jspf"%>

<div class="container">
    <table class="table">
        <thead>
        <tr>
            <th scope="col" hidden>id</th>
            <c:if test="${(userType == 'ADMINISTRATOR' && takenbooks) || mybooks}">
                <c:if test="${(userType == 'ADMINISTRATOR' && takenbooks)}">
                    <th scope="col"><fmt:message key="message.userlabel" /></th>
                </c:if>
                <th scope="col"><fmt:message key="message.datefromlabel" /></th>
            </c:if>
            <th scope="col"><fmt:message key="message.descriptionlabel" /></th>
            <th scope="col"><fmt:message key="message.bookshelf" /></th>
            <c:if test="${userType == 'ADMINISTRATOR'}">
                <th scope="col"><fmt:message key="message.actionlabel" /></th>
                <th scope="col"><fmt:message key="message.deletebutton" /></th>
            </c:if>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${bookitemlist}" var="bookitemlist">
                <tr>
                    <th scope="row" hidden>${bookitemlist.getId()}</th>
                    <c:if test="${(userType == 'ADMINISTRATOR' && takenbooks)||mybooks}">
                        <c:if test="${(userType == 'ADMINISTRATOR' && takenbooks)}">
                                <td>
                                        ${bookitemlist.getBookItemUser().getUser().toString()}
                                </td>
                        </c:if>
                            <td>
                                <c:if test="${overdue}">
                                    <div class="alert alert-warning" role="alert">
                                </c:if>
                                    ${bookitemlist.getBookItemUser().getFarmatedDate()}
                                <c:if test="${overdue}">
                                    </div>
                                </c:if>
                            </td>

                    </c:if>
                    <td>${bookitemlist.getDescription()}</td>
                    <td>${bookitemlist.getBookShelfAddress()}</td>
                    <td>
                        <c:if test="${takenbooks || mybooks && userType == 'ADMINISTRATOR'}">
                            <a class="btn btn-primary"
                               href="controller?command=returnbookitem&bookitemid=${bookitemlist.getId()}"
                               role="button"><fmt:message key="message.returnlabel" /></a>
                        </c:if>
                        <c:if test="${!(takenbooks || mybooks )}">
                            <a class="btn btn-primary"
                               href="controller?command=takebookitem&bookitemid=${bookitemlist.getId()}"
                               role="button"><fmt:message key="message.takelabel" /></a>
                        </c:if>
                    </td>
                    <c:if test="${userType == 'ADMINISTRATOR'}">
                        <td>
                            <a class="btn btn-primary"
                               href="controller?command=deletebookitem&bookitemid=${bookitemlist.getId()}"
                               role="button"><fmt:message key="message.deletebutton" /></a>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <c:if test="${userType == 'ADMINISTRATOR' && !(takenbooks || mybooks )}">
        <a class="btn btn-primary" href="controller?command=addbookitem" role="button">Add on shelve</a>
    </c:if>
</div>


<%@include file="common/footer.jspf"%>

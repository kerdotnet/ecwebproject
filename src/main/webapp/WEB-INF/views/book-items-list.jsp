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
            <c:if test="${(takenbooks || mybooks )}">
                <th scope="col"><fmt:message key="message.status"/></th>
            </c:if>
            <th scope="col"><fmt:message key="message.actionlabel" /></th>
            <c:if test="${userType == 'ADMINISTRATOR'}">
                <th scope="col"><fmt:message key="message.deletebutton" /></th>
            </c:if>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${bookitemlist}" var="bookitemlist">
                <tr>
                    <th scope="row" hidden>${bookitemlist.id}</th>
                    <c:if test="${(userType == 'ADMINISTRATOR' && takenbooks)||mybooks}">
                        <c:if test="${(userType == 'ADMINISTRATOR' && takenbooks)}">
                                <td>
                                    <ctg:user-info user="${bookitemlist.bookItemUser.user}"/>
                                </td>
                        </c:if>
                            <td>
                                <c:if test="${overdue}">
                                    <div class="alert alert-warning" role="alert">
                                </c:if>
                                    <fmt:formatDate type ="date" value="${bookitemlist.bookItemUser.dateUtil}"/>
                                <c:if test="${overdue}">
                                    </div>
                                </c:if>
                            </td>

                    </c:if>
                    <td>${bookitemlist.description}</td>
                    <td>${bookitemlist.bookShelfAddress}</td>
                    <c:if test="${(takenbooks || mybooks )}">
                        <td>
                            <ctg:status-info status="${bookitemlist.bookItemUser.status}"/>
                        </td>
                    </c:if>
                    <td>
                        <!-- Here button to manage already taken books-->
                        <c:if test="${bookitemlist.bookItemUser.status == 'REQUESTED'}" >
                            <c:if test="${userType == 'ADMINISTRATOR'}">
                                <a class="btn btn-primary"
                                   href="controller?command=confirmtakebookitem&bookitemid=${bookitemlist.id}"
                                   role="button"><fmt:message key="message.confirm" />
                                </a>
                            </c:if>
                        </c:if>
                        <c:if test="${bookitemlist.bookItemUser.status == 'TAKEN'}" >
                            <!-- FOR USER OR ADMINISTRATOR-->
                            <a class="btn btn-primary"
                               href="controller?command=returnbookitem&bookitemid=${bookitemlist.id}"
                               role="button"><fmt:message key="message.returnlabel" />
                            </a>
                        </c:if>
                        <c:if test="${bookitemlist.bookItemUser.status == 'TORETURN'}" >
                            <c:if test="${userType == 'ADMINISTRATOR'}">
                                <a class="btn btn-primary"
                                   href="controller?command=confirmreturnbookitem&bookitemid=${bookitemlist.id}"
                                   role="button"><fmt:message key="message.confirm" />
                                </a>
                            </c:if>
                        </c:if>
                        <!-- Here button to take a book from catalog -->
                        <c:if test="${!(takenbooks || mybooks )}">
                            <a class="btn btn-primary"
                               href="controller?command=takebookitem&bookitemid=${bookitemlist.id}"
                               role="button"><fmt:message key="message.takelabel" /></a>
                        </c:if>
                    </td>
                    <c:if test="${userType == 'ADMINISTRATOR'}">
                        <td>
                            <a class="btn btn-primary"
                               href="controller?command=deletebookitem&bookitemid=${bookitemlist.id}"
                               role="button"><fmt:message key="message.deletebutton" /></a>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div class="form-group  has-danger  w-25">
        <label><font color = "red">${errorMessage}</font></label>
        <label><font color = "red">${wrongCommand}</font></label>
        <label><font color = "red">${nullPage}</font></label>
    </div>
    <c:if test="${userType == 'ADMINISTRATOR' && !(takenbooks || mybooks )}">
        <a class="btn btn-primary" href="controller?command=addbookitem" role="button">Add on shelve</a>
    </c:if>
</div>


<%@include file="common/footer.jspf"%>

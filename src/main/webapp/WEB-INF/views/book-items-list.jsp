<%@include file="common/header.jspf"%>
<%@include file="common/navigation.jspf"%>

<div class="container">
    <table class="table">
        <thead>
        <tr>
            <th scope="col" hidden>id</th>
            <th scope="col">Description</th>
            <th scope="col">Bookshelf</th>
            <th scope="col">Take for reading</th>
            <c:if test="${userType == 'ADMINISTRATOR'}">
                <th scope="col">Action</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${bookitemlist}" var="bookitemlist">
            <tr>
                <th scope="row" hidden>${bookitemlist.getId()}</th>
                <td>${bookitemlist.getDescription()}</td>
                <td>${bookitemlist.getBookShelfAddress()}</td>
                <td>
                    <a class="btn btn-primary"
                       href="controller?command=takebookitem&bookitemid=${bookitemlist.getId()}"
                       role="button">Take</a>
                </td>
                <c:if test="${userType == 'ADMINISTRATOR'}">
                    <td>
                        <a class="btn btn-primary"
                           href="controller?command=deletebookitem&bookitemid=${bookitemlist.getId()}"
                           role="button">Delete</a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${userType == 'ADMINISTRATOR'}">
        <a class="btn btn-primary" href="controller?command=addbookitem" role="button">Add on shelve</a>
    </c:if>
</div>


<%@include file="common/footer.jspf"%>

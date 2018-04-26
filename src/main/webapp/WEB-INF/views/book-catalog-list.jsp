<%@include file="common/header.jspf"%>
<%@include file="common/navigation.jspf"%>

<div class="container">
    <table class="table">
        <thead>
        <tr>
            <th scope="col" hidden>id</th>
            <th scope="col">Name</th>
            <th scope="col">Authors</th>
            <th scope="col">Full name</th>
            <th scope="col">Description</th>
            <c:if test="${userType == 'ADMINISTRATOR'}">
                <th scope="col">Action</th>
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
                    <c:forEach items="${bookcataloglist.getAuthors()}" var="authors">
                        ${authors.getName()}
                    </c:forEach>
                </td>
                <td>${bookcataloglist.getFullName()}</td>
                <td>${bookcataloglist.getDescription()}</td>
                <c:if test="${userType == 'ADMINISTRATOR'}">
                    <td>
                        <a class="btn btn-primary"
                           href="controller?command=deletebookcatalog&bookcatalogid=${bookcataloglist.getId()}"
                           role="button">Delete</a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${userType == 'ADMINISTRATOR'}">
        <a class="btn btn-primary" href="controller?command=addbookcatalog" role="button">Add new book</a>
    </c:if>
</div>


<%@include file="common/footer.jspf"%>

<%@include file="common/header.jspf"%>
<%@include file="common/navigation.jspf"%>

<div class="container">
    <table class="table">
        <tbody>
            <tr>
                <th scope="row">Name</th>
                <td>${bookcatalogentity.getName()}</td>
            </tr>
            <tr>
                <th scope="row">Authors</th>
                <td>
                    <c:forEach items="${bookcatalogentity.getAuthors()}" var="authors">
                        ${authors.getName()}
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <th scope="row">Full name</th>
                <td>${bookcatalogentity.getFullName()}</td>
            </tr>
            <tr>
                <th scope="row">Description</th>
                <td>${bookcatalogentity.getDescription()}</td>
            </tr>
        </tbody>
    </table>
    <c:if test="${userType == 'ADMINISTRATOR'}">
        <a class="btn btn-primary"
           href="controller?command=editbookcatalog&bookcatalogid=${bookcatalogentity.getId()}"
           role="button">Edit book</a>
        <a class="btn btn-primary"
           href="controller?command=editbookcatalog&bookcatalogid=${bookcatalogentity.getId()}"
           role="button">Ok</a>
    </c:if>
</div>


<%@include file="common/footer.jspf"%>

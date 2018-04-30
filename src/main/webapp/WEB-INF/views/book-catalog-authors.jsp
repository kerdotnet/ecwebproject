<%@include file="common/header.jspf"%>
<%@include file="common/navigation.jspf"%>

<div class="container">
    <form action = "controller" method="post" >
        <input type="hidden" name="command" value="addnewauthor">
        <ul class="list-group">
            <c:forEach items="${bookcatalogentity.getAuthors()}" var="authors" varStatus="loop">
                <li class="list-group-item" value="${authors.getId()}">
                    ${authors.getName()}<a class="btn btn-primary float-right"
                                           href="controller?command=deletebookcatalogauthor&deleteauthorid=${authors.getId()}"
                                           role="button">Delete</a>
                </li>
            </c:forEach>
        </ul>
        <br><hr><br>
        <div class="form-group w-50">
            <select class="form-control" name="newauthor" id="newauthor">
                <c:forEach items="${authors}" var="author" varStatus="loop">
                    <option value="${author.getId()}">${author.getName()}</option>
                </c:forEach>
            </select>
        </div>

        <c:if test="${userType == 'ADMINISTRATOR'}">
            <button type="submit"  value = "ok" class="btn btn-primary">Add</button>
            <a class="btn btn-primary"
               href="controller?command=editbookcatalog"
               role="button">Ok</a>
        </c:if>
    </form>
<%@include file="common/footer.jspf"%>

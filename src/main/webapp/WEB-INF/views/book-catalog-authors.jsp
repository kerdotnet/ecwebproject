<%@include file="common/header.jspf"%>
<%@include file="common/navigation.jspf"%>

<div class="container">
    <form action = "controller" method="post" >
        <input type="hidden" name="command" value="addnewauthor">
        <ul class="list-group">
            <c:forEach items="${bookcatalogentity.authors}" var="authors" varStatus="loop">
                <li class="list-group-item" value="${authors.id}">
                    ${authors.name}<a class="btn btn-primary float-right"
                                           href="controller?command=deletebookcatalogauthor&deleteauthorid=${authors.id}"
                                           role="button"><fmt:message key="message.deletebutton" /></a>
                </li>
            </c:forEach>
        </ul>
        <br><hr><br>
        <div class="form-group w-50">
            <select class="form-control" name="newauthor" id="newauthor">
                <c:forEach items="${authors}" var="author" varStatus="loop">
                    <option value="${author.id}">${author.name}</option>
                </c:forEach>
            </select>
        </div>

        <c:if test="${userType == 'ADMINISTRATOR'}">
            <button type="submit"  value = "ok" class="btn btn-primary"><fmt:message key="message.addbutton" /></button>
            <a class="btn btn-primary"
               href="controller?command=editbookcatalog"
               role="button"><fmt:message key="message.okbutton" /></a>
        </c:if>
    </form>
<%@include file="common/footer.jspf"%>

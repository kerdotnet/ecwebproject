<%@include file="common/header.jspf"%>
<%@include file="common/navigation.jspf"%>

<div class="container">
    <form action = "controller" method="post" >
        <input type="hidden" name="command" value="savebookitem">
        <table class="table">
            <tbody>
                <tr>
                    <th scope="row"><fmt:message key="message.descriptionlabel" /></th>
                    <td>
                        <input type="text" class="form-control"
                               id="description" name="description"
                               value="${description}">
                    </td>
                </tr>
                <tr>
                    <th scope="row"><fmt:message key="message.bookshelf" /></th>
                    <td>
                        <input type="text" class="form-control"
                               id="bookshelf" name="bookshelf"
                               value="${bookshelf}">
                    </td>
                </tr>
            </tbody>
        </table>
        <c:if test="${userType == 'ADMINISTRATOR'}">
            <button type="submit"  value = "ok" class="btn btn-primary">
                <fmt:message key="message.savelabel" /></button>
        </c:if>
    </form>
</div>


<%@include file="common/footer.jspf"%>

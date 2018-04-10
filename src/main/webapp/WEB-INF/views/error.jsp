
<%@ page isErrorPage="true"%>
<%@include file="common/header.jspf"%>
<%@include file="common/navigation.jspf"%>

<div class="container">
    Request from ${pageContext.errorData.requestURI} is failed
    <br>
    Servlet name or type: ${pageContext.errorData.servletName}
    <br>
    Status code: ${pageContext.errorData.statusCode}
    <br>
    Exception: ${pageContext.errorData.throwable}
</div>


<%@include file="common/footer.jspf"%>

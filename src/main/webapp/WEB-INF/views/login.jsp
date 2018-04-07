
<%@include file="common/header.jspf"%>

<%@include file="common/navigation.jspf"%>

<div class="container">
    <form action = "/login.do" method="post">
        <p><font color = "red">${errorMessage}</font></p>
        Enter your name <input type = "text" name = "name"/>
        password <input type = "password" name = "password"/>
        <input type = "submit" value = "login">
    </form>
</div>


<%@include file="common/footer.jspf"%>

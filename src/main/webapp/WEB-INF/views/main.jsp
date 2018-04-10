
<%@include file="common/header.jspf"%>

<%@include file="common/navigation.jspf"%>

<div class="container">
    <form action = "controller" method="post">
        <input type="hidden" name="command" value="login">

        Login:<br>
        <input type = "text" name = "login"/>
        <br>Password:<br>
        <input type = "password" name = "password"/>
        <br>
        <p><font color = "red">${errorMessage}</font></p>
        <p><font color = "red">${wrongCommand}</font></p>
        <p><font color = "red">${nullPage}</font></p>
        <input type = "submit" value = "login">
    </form>
</div>


<%@include file="common/footer.jspf"%>

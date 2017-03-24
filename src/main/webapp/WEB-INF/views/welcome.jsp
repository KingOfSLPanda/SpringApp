<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<link>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Welcome</title>
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">
<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>

<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${name} |
            <button><a onclick="document.forms['logoutForm'].submit()">Logout</a></button>
        </h2>
        <h3>Black list:</h3>
        <%--<span class="glyphicon glyphicon-remove">- </span>--%>
        <c:forEach items="${users}" var="item">
            <c:if test="${item.enabled==true}">
                <h3>
                    <button><span id="${item.username}" class="fa fa-unlock"
                                  onclick='changeStatusUser("${item.username}","${currentUsername}")'></span></button>
                        ${item.name}<br>
                </h3>
            </c:if>
            <c:if test="${item.enabled==false}">
                <h3>
                    <button><span id="${item.username}" class="fa fa-lock"
                                  onclick='changeStatusUser("${item.username}","${currentUsername}")'></span></button>
                        ${item.name}<br>
                </h3>
            </c:if>
        </c:forEach>

    </c:if>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

<script type="text/javascript">
    function changeStatusUser(username, currentUsername) {
        $.ajax({
            url: '/block',
            type: 'POST',
            data: ({
                "username": username,
                "currentUsername": currentUsername
            }),
            success: function (redirect) {
//                alert(redirect)
                if (redirect=="REDIRECT") window.location="${contextPath}/logout"
                if ($("#" + username).attr("class") == "fa fa-unlock")
                {
                    $("#" + username).attr("class", "fa fa-lock");
                }
                else
                {
                    $("#" + username).attr("class", "fa fa-unlock");
                }

            }
        });
    }
</script>
</body>
</html>


<%@ taglib prefix="cl" uri="http://cloudinary.com/jsp/taglib" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<img src='${imgMap.get("secure_url")}' />
<button><span onclick='toIncrement("${imgMap}")'>+</span></button>
<button>-</button>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<script type="text/javascript">
    function toIncrement(imgMap) {
        alert("OK")
        <%--$.ajax({--%>
            <%--url: '/img+',--%>
            <%--type: 'POST',--%>
            <%--data: ({--%>
                <%--"imgMap": imgMap--%>
            <%--}),--%>
            <%--success: function (redirect) {--%>
                <%--if (redirect=="REDIRECT") window.location="${contextPath}/logout"--%>
                <%--else--%>
                <%--{--%>
                    <%--$("#" + imgMap).attr("effect", "sepia");--%>
                <%--}--%>
            <%--}--%>
    <%--})--%>
//        window.location = "/img+"
    }
</script>

</body>
</html>

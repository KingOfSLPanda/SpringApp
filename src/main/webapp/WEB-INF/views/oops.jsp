<%--
  Created by IntelliJ IDEA.
  User: Y50-70
  Date: 23.03.2017
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script type="text/javascript">
    var token = window.location.href.split("access_token=")[1];
    window.location = "/fk1?access_token=" + token;
</script>
</body>
</html>

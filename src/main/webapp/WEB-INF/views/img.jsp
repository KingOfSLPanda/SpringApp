<%@ taglib prefix="cl" uri="http://cloudinary.com/jsp/taglib" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        /*#enter .imgURL{*/
            /*align-content: center;*/
            /*align-items: center;*/
            /*text-align: center;*/
        /*}*/
        /*#enter .imgURL:hover {*/
            /*border: 1px solid #29B0D9; !* Параметры рамки *!*/
        /*}*/

        .holder{
            position:relative;
            width:auto;
            height:auto;
            /*align-self: center;*/
            margin: auto;
        }
        .block{
            height: 30px;
            position:absolute;
            left:0;
            /*bottom:0;*/
            /*right:0;*/
            top:92%;
            background:rgba(255,255,255, 0.7);
            padding:5px;
            display:none;
        }
        .holder:hover .block{
            display:inline-block;
        }
    </style>
</head>
<body>
    <div class="holder">
        <span class="imgURL"><img src="${image.getUrl()}" id="${image.getId()}"/>
        <div class="block">
            <button><span class="glyphicon glyphicon-plus-sign" onclick='changeSize("${image.getId()}", "+")'></span></button>
            <button><span class="glyphicon glyphicon-minus-sign" onclick='changeSize("${image.getId()}", "-")'></span></button>
            <button><span class="glyphicon glyphicon-adjust" onclick='changeGray("${image.getId()}")'></span></button>
            <button><span class="glyphicon glyphicon-eye-open" onclick='changeSepia("${image.getId()}")'></span></button>
            <button><span class="glyphicon glyphicon-retweet" onclick='changeOriginal("${image.getId()}")'></span></button>
    <%--<button><span class="fa-minus-square-o" onclick='changeSize("${image.getUrl()}")'></span></button>--%>
        </div>
        </span>

    </div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<script type="text/javascript">
    function changeSize(id, s) {
        imgURL=$("#"+id).attr("src");
        $.ajax({
            type: 'POST',
            url: '/img+',
            data: ({
                "imgURL": imgURL,
                "s": s
            }),
            success: function (url) {
                    $("#" + id).attr("src", url);
//                    $(".fa-plus-square-o[onclick=changeSize(\""+id+"\")]").attr("onclick", "changeSize(\""+id+"\")");
//                    $(".fa-minus-square-o[onclick=changeSize(\""+id+"\")]").attr("onclick", "changeSize(\""+id+"\")");
            }
        });
    }
</script>

    <script type="text/javascript">
        function changeGray(id) {
            imgURL=$("#"+id).attr("src");
            $.ajax({
                type: 'POST',
                url: '/imggray',
                data: ({
                    "imgURL": imgURL
                }),
                success: function (url) {
                    $("#" + id).attr("src", url);
                }
            });
        }
    </script>


    <script type="text/javascript">
        function changeSepia(id) {
            imgURL=$("#"+id).attr("src");
            $.ajax({
                type: 'POST',
                url: '/imgsepia',
                data: ({
                    "imgURL": imgURL
                }),
                success: function (url) {
                    $("#" + id).attr("src", url);
                }
            });
        }
    </script>

    <script type="text/javascript">
        function changeOriginal(id) {
            $.ajax({
                type: 'POST',
                url: '/img',
                data: ({
                }),
                success: function (url) {
                    $("#" + id).attr("src", url);
                }
            });
        }
    </script>

</body>
</html>

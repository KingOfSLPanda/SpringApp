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
            width:300px;
            height:400px;
            /*align-self: center;*/
            margin: auto;
        }
        .block{
            position:absolute;
            align-self: center;
            /*left:0;*/
            /*bottom:0;*/
            /*right:0;*/
            /*top:0;*/
            background:rgba(255,255,255, 0.8);
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
        <span class="imgURL"><img src="${imgURL}" id="${imgURL.get}"/></span>
        <div class="block">
    <button><span class="fa-plus-square-o" onclick='changeSize("${imgURL}")'></span></button>
    <button><span class="fa-minus-square-o" onclick='changeSize("${imgURL}")'></span></button>
        </div>
    </div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<script type="text/javascript">
    function changeSize(imgURL) {
        alert("OK")
        $.ajax({
            type: 'POST',
            url: '/img+',
            data: ({
                "imgURL": imgURL
            }),
            success: function (redirect) {
                    $("#" + "imgURL").attr("src", redirect);

            }
        });
    }
</script>

</body>
</html>

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
            height:auto;
            align-self: center;
            margin: auto;
        }
        .block{
            height: 30px;
            position:absolute;
            left:0;
            /*bottom:0;*/
            /*right:0;*/
            top:89%;
            background:rgba(255,255,255, 0.7);
            padding:5px;
            display:none;
        }

        .holder:hover{
            box-shadow: 0 0 10px rgba(0,0,0,0.5);
        }

        .holder:hover .block{
            display:inline-block;
        }
    </style>

</head>
<body id="my-files">
    <div class="holder" >
        <span class="imgURL" >
            <%--<div id="div1" ondrop="drop(event)" ondragover="allowDrop(event)">--%>
                <img onmousedown="ball" onmousemove="ball" onmouseup="ball" ondragstart="ball" src="${image.getUrl()}" id="${image.getId()}"/>
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
        function getWidth(id) {
            imgURL=$("#"+id).attr("src");
            $.ajax({
                type: 'POST',
                url: '/getimgwidth',
                data: ({
                    "imgURL": imgURL
                }),
                success: function (width) {
                    document.getElementsByClassName('holder').style.width = width + 'px';
                }
            });
        }
    </script>

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
            imgURL=$("#"+id).attr("src");
            $.ajax({
                type: 'POST',
                url: '/getoriginal',
                data: ({
                    "imgURL": imgURL
                }),
                success: function (url) {
                    $("#" + id).attr("src", url);
                }
            });
        }
    </script>

    <script>
        var ball = document.getElementById("${image.getId()}");

        ball.onmousedown = function(e) { // 1. отследить нажатие

            // подготовить к перемещению
            // 2. разместить на том же месте, но в абсолютных координатах
            ball.style.position = 'absolute';
            moveAt(e);
            // переместим в body, чтобы мяч был точно не внутри position:relative
            document.body.appendChild(ball);

//            ball.style.zIndex = 1000; // показывать мяч над другими элементами

            // передвинуть мяч под координаты курсора
            // и сдвинуть на половину ширины/высоты для центрирования
            function moveAt(e) {
                ball.style.left = e.pageX - ball.offsetWidth / 2 + 'px';
                ball.style.top = e.pageY - ball.offsetHeight / 2 + 'px';
            }

            // 3, перемещать по экрану
            document.onmousemove = function (e) {
                moveAt(e);
            }

            // 4. отследить окончание переноса
            ball.onmouseup = function () {
                document.onmousemove = null;
                ball.onmouseup = null;
            }

            ball.ondragstart = function () {
                return false;
            }
        }
    </script>

    <script type="text/javascript">

        var htmlelement = document.getElementById("my-files");
        htmlelement.addEventListener("dragover", function (event) {
            /*отменяем действие по умолчанию*/
            event.preventDefault();
        }, false);

        htmlelement.addEventListener("drop", function (event) {
            //отменяем действие по умолчанию
            event.preventDefault();
            var files = event.dataTransfer.files;
            for (var i = 0; i < files.length; i++) {
                console.log("Имя файла: " + files[i].name);
                console.log("Тип файла: " + files[i].type);
                console.log("Размер файла: "+files[i].size)
            }
        }, false);

    </script>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set value="${pageContext.request.contextPath }" var="ctx"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%--多核浏览器默认启用急速内核--%>
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">

    <link rel="shortcut icon" href="${ctx}/assets/images/favicon_1.ico">

    <title>admin后台</title>

    <!-- Base Css Files -->
    <link href="${ctx}/assets/css/bootstrap.min.css" rel="stylesheet" />

    <!-- Font Icons -->
    <link href="${ctx}/assets/css/font-awesome.min.css" rel="stylesheet" />
    <link href="${ctx}/assets/js/common/ionicon/css/ionicons.min.css" rel="stylesheet" />
    <link href="${ctx}/assets/css/material-design-iconic-font.min.css" rel="stylesheet">

    <!-- animate css -->
    <link href="${ctx}/assets/css/animate.css" rel="stylesheet" />

    <!-- Waves-effect -->
    <link href="${ctx}/assets/css/waves-effect.css" rel="stylesheet">

    <!-- Custom Files -->
    <link href="${ctx}/assets/css/helper.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/assets/css/style.css" rel="stylesheet" type="text/css" />

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

    <script src="${ctx}/assets/js/common/modernizr.min.js"></script>

</head>
<body>


<div class="wrapper-page">
    <div class="panel panel-color panel-primary panel-pages">
        <div class="panel-heading bg-img">
            <div class="bg-overlay"></div>
            <h3 class="text-center m-t-10 text-white" style="margin-top: 100px;"> <strong>admin后台</strong> </h3>
        </div>


        <div class="panel-body">
            <form class="form-horizontal m-t-20" action="${ctx}/login"
                  data-toggle="validator" method="post">

                <div class="form-group ">
                    <div class="col-xs-12">
                        <input class="form-control input-lg" name="username" type="text" data-error="请输入登录账号" required placeholder="账号">
                        <div class="help-block with-errors"></div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-xs-12">
                        <input class="form-control input-lg" type="password" name="password" data-error="请输入密码" required placeholder="密码">
                        <div class="help-block with-errors"></div>
                    </div>
                </div>
                <div class="form-group has-error has-danger">
                    <div class="col-xs-12">
                        <input type="hidden" name="errorMsg" id="errorMsg" value="${message}">
                        <div class="help-block with-errors">
                            <ul class="list-unstyled">
                                <li></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <%--<div class="form-group ">--%>
                    <%--<div class="col-xs-12">--%>
                        <%--<div class="checkbox checkbox-primary">--%>
                            <%--<input id="checkbox-signup" type="checkbox">--%>
                            <%--<label for="checkbox-signup">--%>
                                <%--Remember me--%>
                            <%--</label>--%>
                        <%--</div>--%>

                    <%--</div>--%>
                <%--</div>--%>

                <div class="form-group text-center m-t-40">
                    <div class="col-xs-12">
                        <button class="btn btn-primary btn-lg w-lg waves-effect waves-light" type="submit">登录</button>
                    </div>
                </div>

                <%--<div class="form-group m-t-30">--%>
                    <%--<div class="col-sm-7">--%>
                        <%--<a href="recoverpw.html"><i class="fa fa-lock m-r-5"></i> Forgot your password?</a>--%>
                    <%--</div>--%>
                    <%--<div class="col-sm-5 text-right">--%>
                        <%--<a href="register.html">Create an account</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
            </form>
        </div>

    </div>
</div>


<script>
    var resizefunc = [];
</script>
<script src="${ctx}/assets/js/common/jquery.min.js"></script>
<script src="${ctx}/assets/js/common/bootstrap.min.js"></script>
<script src="${ctx}/assets/js/common/waves.js"></script>
<script src="${ctx}/assets/js/common/wow.min.js"></script>
<script src="${ctx}/assets/js/common/jquery.nicescroll.js" type="text/javascript"></script>
<script src="${ctx}/assets/js/common/jquery.scrollTo.min.js"></script>
<script src="${ctx}/assets/js/common/jquery-detectmobile/detect.js"></script>
<script src="${ctx}/assets/js/common/fastclick/fastclick.js"></script>
<script src="${ctx}/assets/js/common/jquery-slimscroll/jquery.slimscroll.js"></script>
<script src="${ctx}/assets/js/common/jquery.blockUI.js"></script>
<script src="${ctx}/assets/js/common/bootstrapValidator.js"></script>


<!-- CUSTOM JS -->
<script src="${ctx}/assets/js/common/jquery.app.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        if($("#errorMsg").val() != ''){
            $("#errorMsg").parents("div").find(".list-unstyled").find("li").html($("#errorMsg").val());
            setTimeout(function(){
                $("#errorMsg").parents(".form-group").remove();
            },3000);
        }
        //回车事件
        document.onkeydown=function(e){
            var theEvent = e || window.event;
            var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
            if (code == 13) {
               $(".btn-primary").click();
            }
        };
    });
</script>
</body>
</html>
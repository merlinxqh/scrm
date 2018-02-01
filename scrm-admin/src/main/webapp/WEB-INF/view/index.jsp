<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="/WEB-INF/tld/sys-permission.tld"%>
<c:set value="${pageContext.request.contextPath }" var="ctx"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%--多核浏览器默认启用急速内核--%>
        <meta name="renderer" content="webkit">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>admin后台</title>
        <!-- Base Css Files -->
        <link href="${ctx}/assets/css/bootstrap.min.css" rel="stylesheet" />
        <!-- Font Icons -->
        <link href="${ctx}/assets/css/font-awesome.min.css" rel="stylesheet" />
		<link href="${ctx}/assets/js/common/ionicon/css/ionicons.min.css" rel="stylesheet" />
        <link href="${ctx}/assets/css/material-design-iconic-font.min.css" rel="stylesheet" />

        <!-- Waves-effect -->
        <link href="${ctx}/assets/css/waves-effect.css" rel="stylesheet" type="text/css" />
        <!-- animate css -->
        <link href="${ctx}/assets/css/animate.css" rel="stylesheet"type="text/css" />

        <!-- Custom Files -->
        <link href="${ctx}/assets/css/helper.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/assets/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/assets/css/index.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/assets/css/common.css" rel="stylesheet" type="text/css" />
         <link href="${ctx}/assets/css/notification.css" rel="stylesheet" type="text/css" />
         <link href="${ctx}/assets/js/common/magnific-popup/magnific-popup.css" rel="stylesheet" type="text/css"  />
         <link href="${ctx}/assets/js/common/timepicker/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
         <link href="${ctx}/assets/js/common/fileinput/fileinput.css" rel="stylesheet" type="text/css" />
         <link href="${ctx}/assets/css/summernote.css" rel="stylesheet" type="text/css" />
         <link href="${ctx}/assets/css/bootstrap-wysihtml5.css" rel="stylesheet" type="text/css" />
         <link href="${ctx}/assets/css/wysiwyg-color.css" rel="stylesheet" type="text/css" />
         <link href="${ctx}/assets/js/common/colorpicker/colorpicker.css" rel="stylesheet" type="text/css" />
         <link href="${ctx}/assets/js/common/steps/jquery.steps.css" rel="stylesheet" type="text/css" />
         <link href="${ctx}/assets/js/common/select2/select2.min.css" rel="stylesheet" type="text/css" />
         <link href="${ctx}/assets/js/common/datatables/jquery.dataTables.min.css" rel="stylesheet" type="text/css" />
         <link href="${ctx}/assets/js/common/gridster/jquery.gridster.min.css" rel="stylesheet" type="text/css"/>
         <link href="${ctx}/assets/js/common/viewer/viewer.css" rel="stylesheet" type="text/css"/>
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->    
        
    </head>



    <body class="fixed-left main-body" style="overflow: hidden;">
        
        <!-- Begin page -->
        <div id="wrapper">
        
            <!-- Top Bar Start -->
            <div class="topbar">
                <!-- LOGO -->
                <div class="topbar-left">
                    <div class="text-center">
                        <a href="javascript:void(0);" class="logo"><span>家视天下</span></a>
                    </div>
                </div>
				
                <!-- Button mobile view to collapse sidebar menu -->
                <div class="navbar navbar-default" role="navigation">
                    <div class="container">
                        <div class="">
                           <div  class="pull-left">
								<button class="button-menu-mobile open-left">
		                            <i class="fa fa-bars"></i>
		                        </button>
								<span class="clearfix"></span>
							</div>
							
                            <ul class="nav navbar-nav navbar-right pull-right">
                                <li class="dropdown hidden-xs">
                                </li>
                                <li class="hidden-xs">
                                </li>
                                <li class="dropdown">
                                    <a href="" class="dropdown-toggle profile" data-toggle="dropdown" aria-expanded="true"><img src="${ctx}/assets/images/img_js.jpg" alt="user-img" class="img-circle"> </a>
                                    <ul class="dropdown-menu">
                                        <%--<li><a href="javascript:void(0)"><i class="md md-face-unlock"></i> 个人中心</a></li>--%>
                                        <li><a href="${ctx}/user/modifyPwd" data-toggle="modal" data-target="#base_modal"><i class="md md-settings"></i> 修改密码</a></li>
                                        <li><a href="${ctx}/logout"><i class="md md-settings-power"></i> 退出</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                        <!--/.nav-collapse -->
                    </div>
                </div>
            </div>
            <!-- Top Bar End -->


            <!-- ========== Left Sidebar Start ========== -->

            <div class="left side-menu">
                <div class="sidebar-inner slimscrollleft">
                    <!--- Divider -->
                    <div id="sidebar-menu">
                        <ul>

                            <c:if test="${fn:length(menuList) gt 0}">
                                <c:forEach items="${menuList}" var="item">
                                    <c:choose>
                                        <%--有子集--%>
                                        <c:when test="${fn:length(item.childList) gt 0}">
                                            <li class="has_sub">
                                                <a href="#" class="waves-effect"><i class="${item.iconCode}"></i><span>${item.name}</span><span class="pull-right"><i class="md md-add"></i></span></a>
                                                <ul class="list-unstyled">
                                                    <c:forEach items="${item.childList}" var="child">
                                                        <li>
                                                            <a href="${ctx}/${child.url}" data-target="divload" data-div="baseContainer" class="waves-effect">
                                                                <i class="${child.iconCode}"></i>
                                                                <span>${child.name}</span>
                                                            </a>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </li>
                                        </c:when>
                                        <%--只有一级--%>
                                        <c:otherwise>
                                            <li>
                                                <a href="${ctx}/${item.url}" data-target="divload" data-div="baseContainer" class="waves-effect">
                                                    <i class="${item.iconCode }"></i>
                                                    <span>${item.name } </span>
                                                </a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:if>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
            <!-- Left Sidebar End --> 

            <!-- ============================================================== -->
            <!-- Start right Content here -->
            <!-- ============================================================== -->                      
            <div class="content-page">
                <!-- Start content -->
                <div class="content">
                    <div class="container" id="baseContainer" style="overflow: auto;">
                    
                    </div> <!-- container -->
                </div> <!-- content -->
            </div>
            <!-- ============================================================== -->
            <!-- End Right content here -->
            <!-- ============================================================== -->


           

        </div>
        <!-- END wrapper -->

        <!-- jQuery  -->
         <script>
            var resizefunc = [];
        </script>

        <!-- jQuery  -->
        <script src="${ctx}/assets/js/common/jquery.min.js"></script>
        <script src="${ctx}/assets/js/common/jquery.lazyload.js"></script>
        <script src="${ctx}/assets/js/common/gridster/jquery.gridster.min.js"></script>
        <script src="${ctx}/assets/js/common/bootstrap.js"></script>
        <script src="${ctx}/assets/js/common/bootstrap-treeview.js"></script>
        <script src="${ctx}/assets/js/common/bootstrap-paginator.js"></script>
        <script src="${ctx}/assets/js/common/jquery.nicescroll.js" type="text/javascript"></script>
        <script src="${ctx}/assets/js/common/jquery-detectmobile/detect.js"></script>
        <script src="${ctx}/assets/js/common/fastclick/fastclick.js"></script>
        <script src="${ctx}/assets/js/common/jquery-slimscroll/jquery.slimscroll.js"></script>
        <script src="${ctx}/assets/js/common/fileinput/fileinput.js"></script>
        <script src="${ctx}/assets/js/common/datatables/jquery.dataTables.min.js"></script>
        <script src="${ctx}/assets/js/common/datatables/dataTables.bootstrap.js"></script>
        
        <!-- CUSTOM JS -->
        <script src="${ctx}/assets/js/common/wow.min.js"></script>
        <script src="${ctx}/assets/js/common/jquery.app.js"></script>
        <script src="${ctx}/assets/js/common/base/base-ui.js"></script>
        <script src="${ctx}/assets/js/common/base/base-tips.js"></script>
        <script src="${ctx}/assets/js/common/base/base-plugins.js"></script>
        <script src="${ctx}/assets/js/common/base/base-render.js"></script>
        <script src="${ctx}/assets/js/common/base/base-fun.js"></script>
        <script src="${ctx}/assets/js/common/base/ajax.util.js"></script>

        <script src="${ctx}/assets/js/common/template.js"></script>
        <script src="${ctx}/assets/js/common/bootstrap-table/bootstrap-table.js"></script>
        <script src="${ctx}/assets/js/common/bootstrap-table/bootstrap-table-zh-CN.js"></script>
        <script src="${ctx}/assets/js/common/bootstrapValidator.js"></script>
 		<script src="${ctx}/assets/js/common/notifications/notify.min.js"></script>
        <script src="${ctx}/assets/js/common/notifications/notify-metro.js"></script>
        <script src="${ctx}/assets/js/common/notifications/notifications.js"></script>
		<script src="${ctx}/assets/js/common/magnific-popup/magnific-popup.js"></script>
        <script src="${ctx}/assets/js/common/timepicker/bootstrap-datetimepicker.min.js"></script>
        <script src="${ctx}/assets/js/common/timepicker/bootstrap-datetimepicker.zh-CN.js"></script>
        <script src="${ctx}/assets/js/common/colorpicker/bootstrap-colorpicker.js"></script>
        <script src="${ctx}/assets/js/common/select2/select2.min.js"></script>

        <!-- flot Chart -->
        <script src="${ctx}/assets/js/common/flot-chart/jquery.flot.js"></script>
        <script src="${ctx}/assets/js/common/flot-chart/jquery.flot.pie.js"></script>
        <script src="${ctx}/assets/js/common/counterup/waypoints.min.js" type="text/javascript"></script>
        <script src="${ctx}/assets/js/common/counterup/jquery.counterup.min.js" type="text/javascript"></script>

		
		<script type="text/javascript" src="${ctx}/assets/js/common/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script>
        <script type="text/javascript" src="${ctx}/assets/js/common/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
		<script type="text/javascript" src="${ctx}/assets/js/common/summernote.min.js"></script>

        <script type="text/javascript" src="${ctx}/assets/js/common/viewer/viewer.js"></script>
        <script type="text/javascript" src="${ctx}/assets/js/common/jquery-confirm/jquery-confirm.min.js"></script>
        <link rel="stylesheet" href="${ctx}/assets/js/common/jquery-confirm/jquery-confirm.min.css"/>
        <script type="text/javascript" src="${ctx}/assets/js/common/jquery-confirm/wrapper_confirm.js"></script>
        <script type="text/javascript" src="${ctx}/assets/js/common/html5shiv.js"></script>
        <script type="text/javascript" src="${ctx}/assets/js/common/respond.min.js"></script>
        <!-- modal -->
        <div id="base_modal" class="modal fade" tabindex="-1" role="dialog"
             aria-hidden="true"
             style="display: none;">
            <div class="modal-dialog modal-lg">
                <div class="modal-content" style="overflow: auto;"></div>
            </div>
        </div>

        <div id="base_modal_center" class="modal fade text-center" tabindex="-1" role="dialog"
             aria-hidden="true"
             style="display: none;">
            <div class="modal-dialog modal-lg" style="display: inline-block; width: auto;min-width: 400px;min-height: 300px;">
                <div class="modal-content" style="overflow: auto;"></div>
            </div>
        </div>
        <!-- /.modal -->
    </body>
    <script type="text/javascript">
		var rootPath="${ctx}";
		var imgPath="${imgPath}";//图片服务器路径
	</script>
    
</html>
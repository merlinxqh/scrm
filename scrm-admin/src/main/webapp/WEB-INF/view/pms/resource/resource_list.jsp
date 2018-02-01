<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="/WEB-INF/tld/sys-permission.tld"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div class="row">
    <!-- Left sidebar -->
    <div class="col-lg-3 col-md-4">
        <a href="javascript:void(0);" class="btn btn-danger waves-effect waves-light btn-block ">菜单</a>
        <div class="panel panel-default p-0  m-t-20">
            <div class="panel-body p-0">
                <div id="resource_tree" class="tree-fixed-height" style="overflow: auto;"></div>

            </div>
        </div>
    </div>
    <!-- End Left sidebar -->

    <!-- Right Sidebar -->
    <div class="col-lg-9 col-md-8" id="right_panel">
        <div class="row">
            <div class="col-sm-12">
                <h4 class="pull-left page-title">权限管理</h4>
                <ol class="breadcrumb pull-right">
                    <li><a data-target="divload" data-div="baseContainer">权限管理</a></li>
                    <li class="active">权限列表</li>
                </ol>
            </div>
        </div>
        <div class="panel adapt-window-height">
            <div class="panel-body">
                <div class="mt20">
                    <div class="m-b-10">
                        <form id="paramForm" class="form-inline" tableId="resourceTableId" onsubmit="return false;">
                            <input type="hidden" id="searchCode" name="searchCode" value="">
                            <input type="hidden" id="isMenu" name="isMenu" value="2">
                            <label>权限名称：<input name="name" id="name" type="text" class="form-control" placeholder="权限名称"></label>
                            <div class="form-group fr">
                                <a class="btn btn-primary" style="display: none;" id="add_resource_btn"  href="javascript:void(0);" onclick="addResource();">添加权限</a>
                                <button class="btn btn-primary waves-effect searchBtn">查询</button>
                                <button class="btn btn-primary waves-effect clearSearch">清空</button>
                            </div>
                        </form>
                    </div>
                    <table  data-classes="table table-hover"
                            data-pagination="true"
                            data-url="${pageContext.request.contextPath}/pms/resource/listData" id="resourceTableId"
                            data-side-pagination="server">
                        <thead>
                        <tr>
                            <th data-field="" data-align="left" data-formatter="operateRender">操作</th>
                            <th data-field="name" data-align="left">权限名称</th>
                            <th data-field="url" data-align="left">路径</th>
                            <th data-field="permission" data-align="left">权限标识</th>
                            <th data-field="remark" data-align="left">备注</th>
                            <th data-field="createBy" data-align="left" >创建者</th>
                            <th data-field="createDate" data-align="left"  data-formatter="defaultDateFormatter">创建日期</th>
                            <th data-field="lastUpdateBy" data-align="left" >最后修改人</th>
                            <th data-field="lastUpdateDate" data-align="left"  data-formatter="defaultDateFormatter">最后修改日期</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- end Col-9 -->
</div>

<div class="pull-right" style="display: none;" id="operateDivClone">
    <button class="btn btn-icon btn-success m-b-5 btn-xs " data-toggle="modal" data-target="#base_modal" href="${pageContext.request.contextPath}">
        <i class="md md-add"></i>
    </button>
    <button class="btn btn-icon btn-warning m-b-5 btn-xs " data-toggle="modal" data-target="#base_modal" href="${pageContext.request.contextPath}">
        <i class="md md-border-color"></i>
    </button>
    <button class="btn btn-icon btn-danger m-b-5 btn-xs ajaxToDo" href="${pageContext.request.contextPath}">
        <i class="md md-clear"></i>
    </button>
</div>
</body>
<script src="${pageContext.request.contextPath}/assets/js/module/pms/resource/resource_list.js"/>
</html>

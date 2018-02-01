<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<div class="row">
    <div class="col-sm-12">
        <h4 class="pull-left page-title">权限管理</h4>
        <ol class="breadcrumb pull-right">
            <li><a data-target="divload" data-div="baseContainer">权限管理</a></li>
            <li class="active">用户管理</li>
        </ol>
    </div>
</div>

<div class="panel adapt-window-height">
    <div class="panel-body">
        <div class="mt20">
            <div class="m-b-10">
                <form id="paramForm" class="form-inline" tableId="userTableId" onsubmit="return false;">
                    <label>用户名称：<input name="name" id="name" type="text" class="form-control" placeholder="用户名称"></label>
                    <label>登录账号：<input name="username" id="username" type="text" class="form-control" placeholder="登录账号"></label>
                    <label>登录账号：
                        <div class='input-group date' id='datetimepicker1'>
                            <input type='text' id="startDate" style="width: 170px;" name="startDate" class="form-control" />
                        </div>
                    </label>
                    <label>状态：
                        <select class="select2 select2-offscreen" id="status" name="status" style="width: 240px;" data-placeholder="请选择" tabindex="" title="">
                            <option value=" ">全部</option>
                            <option value="1">有效</option>
                            <option value="0">无效</option>
                        </select>
                    </label>
                    <div class="form-group fr">
                        <a class="btn btn-primary"  href="${pageContext.request.contextPath}/pms/user/edit" data-toggle="modal" data-target="#base_modal">添加用户</a>
                        <button class="btn btn-primary waves-effect searchBtn">查询</button>
                        <button class="btn btn-primary waves-effect clearSearch">清空</button>
                    </div>
                </form>
            </div>
            <table  data-toggle="table"  data-classes="table table-hover"
                    data-pagination="true"
                    data-url="${pageContext.request.contextPath}/pms/user/listData" id="userTableId"
                    data-side-pagination="server">
                <thead>
                <tr>
                    <th data-field="" data-align="left" data-formatter="operateRender">操作</th>
                    <th data-field="name" data-align="left">名称</th>
                    <th data-field="username" data-align="left">登录账号</th>
                    <th data-field="email" data-align="left">邮箱</th>
                    <th data-field="defaultRole.name" data-align="left">角色</th>
                    <th data-field="status" data-align="left" data-formatter="statusRenderValid">状态</th>
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
<script src="${pageContext.request.contextPath}/assets/js/module/pms/user/user_list.js"></script>
</html>
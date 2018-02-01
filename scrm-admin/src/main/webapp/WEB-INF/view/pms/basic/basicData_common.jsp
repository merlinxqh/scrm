<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div class="row">
    <div class="col-sm-12">
        <h4 class="pull-left page-title">运营</h4>
        <ol class="breadcrumb pull-right">
            <li><a data-target="divload" data-div="baseContainer">运营</a></li>
            <li class="active">${title}</li>
        </ol>
    </div>
</div>

 <div class="panel adapt-window-height">
    <div class="panel-body">
        <div class="mt20">
            <div class="m-b-10">
                <form id="paramForm" class="form-inline" tableId="${typeCode}" onsubmit="return false;">
                    <label>标签：<input name="label" type="text" class="form-control" placeholder=""></label>
                    <label>状态：
                        <select class="select2 select2-offscreen" id="status" style="width: 240px;" name="status" data-placeholder="请选择" tabindex=" " title="">
                            <option value=" ">全部</option>
                            <option value="1">有效</option>
                            <option value="0">无效</option>
                        </select>
                    </label>
                    <div class="form-group fr">
                        <a class="btn btn-primary"  href="${pageContext.request.contextPath}/basicData/addData?typeCode=${typeCode}" data-toggle="modal" data-target="#base_modal">添加</a>
                        <button class="btn btn-primary waves-effect searchBtn">查询</button>
                        <button class="btn btn-primary waves-effect clearSearch">清空</button>
                    </div>
                </form>
            </div>
            <table  data-toggle="table" data-classes="table table-hover"
                   data-pagination="true"
                   data-url="${pageContext.request.contextPath}/basicData/listData?typeCode=${typeCode}" id="${typeCode}"
                   data-side-pagination="server">
                <thead>
                <tr>
                    <th  data-checkbox="true" data-align="left">序号</th>
                    <th data-field="label" data-align="left" data-formatter="autoWrapRender">标签</th>
                    <th data-field="typeCode" data-align="left">类型</th>
                    <th data-field="value" data-align="left"  data-formatter="autoWrapRender">内容</th>
                    <th data-field="description" data-align="left" data-formatter="autoWrapRender">描述</th>
                    <th data-field="createDate" data-align="left" data-formatter="defaultDateFormatter">创建日期</th>
                    <th data-field="createBy.userName" data-align="left">创建者</th>
                    <th data-field="status" data-align="left" data-formatter="renderStatusFun">状态</th>
                    <th data-align="left" data-formatter="operateFormatter">操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/assets/js/module/pms/basic/basicData_common.js"/>
</html>
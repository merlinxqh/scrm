<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="/WEB-INF/tld/sys-permission.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<div class="row">
    <div class="col-sm-12">
        <h4 class="pull-left page-title">系统设置</h4>
        <ol class="breadcrumb pull-right">
            <li><a data-target="divload" data-div="baseContainer">系统设置</a></li>
            <li class="active">定时调度</li>
        </ol>
    </div>
</div>

 <div class="panel adapt-window-height">
    <div class="panel-body">
        <div class="mt20">
            <div class="m-b-10">
                <form id="paramForm" class="form-inline" tableId="scheduleTableId" onsubmit="return false;">
                    <label>任务名称：<input name="name" type="text" class="form-control" placeholder="任务名称"></label>
                    <label>任务分组：<input name="jobGroup" type="text" class="form-control" placeholder="任务分组"></label>
                    <div class="form-group fr">
                        <a class="btn btn-primary"  href="${pageContext.request.contextPath}/schedule/edit" data-toggle="modal" data-target="#base_modal">添加调度任务</a>
                        <button class="btn btn-primary waves-effect searchBtn">查询</button>
                        <button class="btn btn-primary waves-effect clearSearch">清空</button>
                    </div>
                </form>
            </div>
            <table data-toggle="table" data-classes="table table-hover"
                   data-pagination="true"
                   data-url="${pageContext.request.contextPath}/schedule/listData" id="scheduleTableId"
                   data-side-pagination="server">
                <thead>
                <tr>
                    <th data-field="" data-align="left" data-formatter="operateRender">操作</th>
                    <th data-checkbox="true" data-align="left">序号</th>
                    <th data-field="name" data-align="left" data-formatter="autoWrapRender">任务名称</th>
                    <th data-field="jobGroup" data-align="left">任务分组</th>
                    <th data-field="beanClass" data-align="left" data-formatter="autoWrapRender">类名</th>
                    <th data-field="methodName" data-align="left">方法名</th>
                    <th data-field="springId" data-align="left">实例ID</th>
                    <th data-field="cronExpression" data-align="left">执行时间</th>
                    <th data-field="status" data-align="left" data-formatter="statusRenderValid">状态</th>
                    <th data-field="createDate" data-align="left" data-formatter="defaultDateFormatter" >创建时间</th>
                    <th data-field="createBy" data-align="left" >创建者</th>
                    <th data-field="lastUpdateBy" data-align="left" >最后操作者</th>
                    <th data-field="lastUpdateDate" data-align="left" data-formatter="defaultDateFormatter">修改日期</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/assets/js/module/schedule/schedule_list.js"></script>
</html>
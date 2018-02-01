<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div class="row">
    <!-- Left sidebar -->
    <div class="col-lg-3 col-md-4">
        <a href="javascript:void(0);" class="btn btn-danger waves-effect waves-light btn-block">基础数据类型</a>
        <div class="panel panel-default p-0 m-t-20">
            <div class="panel-body p-0">
                <div id="basic_type_tree" class="tree-fixed-height" style="overflow: auto;"></div>

            </div>
        </div>
    </div>
    <!-- End Left sidebar -->

    <!-- Right Sidebar -->
    <div class="col-lg-9 col-md-8" id="right_panel">
        <div class="row">
            <div class="col-sm-12">
                <h4 class="pull-left page-title">系统设置</h4>
                <ol class="breadcrumb pull-right">
                    <li><a href="#">系统设置</a></li>
                    <li class="active">基础数据</li>
                </ol>
            </div>
        </div>

         <div class="panel adapt-window-height">
            <div class="panel-body">
                <div class="mt20">
                    <div class="m-b-10">
                        <form id="paramForm" class="form-inline" tableId="basicDataTable" onsubmit="return false;">
                            <label>标签：<input name="label" type="text" class="form-control" placeholder="标签"></label>
                            <label>状态：
                                <select class="select2 select2-offscreen" id="status" style="width: 240px;" name="status" data-placeholder="请选择" tabindex=" " title="">
                                    <option value=" ">全部</option>
                                    <option value="1">有效</option>
                                    <option value="0">无效</option>
                                </select>
                            </label>
                            <input type="hidden" id="searchTypeCode" name="searchTypeCode" value="">
                            <div class="form-group fr">
                                <a class="btn btn-primary" id="add_resource_btn" onclick="addBasicData();">添加基础数据</a>
                                <button class="btn btn-primary waves-effect searchBtn">查询</button>
                                <button class="btn btn-primary waves-effect clearSearch">清空</button>
                            </div>
                        </form>
                    </div>
                    <table data-classes="table table-hover"
                           data-pagination="true"
                           data-url="${pageContext.request.contextPath}/basicData/listData" id="basicDataTable"
                           data-side-pagination="server">
                        <thead>
                        <tr>
                            <th  data-checkbox="true" data-align="left">序号</th>
                            <th data-field="label" data-align="left" data-formatter="autoWrapRender">标签</th>
                            <th data-field="typeCode" data-align="left">类型</th>
                            <th data-field="value" data-align="left"  data-formatter="autoWrapRender">值</th>
                            <th data-field="description" data-align="left" data-formatter="autoWrapRender">描述</th>
                            <th data-field="createDate" data-align="left" data-formatter="defaultDateFormatter">创建日期</th>
                            <th data-field="createBy" data-align="left">创建者</th>
                            <th data-field="status" data-align="left" data-formatter="renderStatusFun">状态</th>
                            <th data-align="left" data-formatter="operateFormatter">操作</th>
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
    <button class="btn btn-icon m-b-5 btn-xs btn-success" data-toggle="modal" data-target="#base_modal" href="${pageContext.request.contextPath}">
        <i class="md md-add"></i>
    </button>
    <button class="btn btn-icon btn-warning m-b-5 btn-xs " data-toggle="modal" data-target="#base_modal" href="${pageContext.request.contextPath}">
        <i class="md md-border-color"></i>
    </button>
</div>
</body>
<script src="${pageContext.request.contextPath}/assets/js/module/pms/basic/basicData_list.js"/>
</html>

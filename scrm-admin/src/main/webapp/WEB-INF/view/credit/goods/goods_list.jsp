<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<div class="row">
    <div class="col-sm-12">
        <h4 class="pull-left page-title">积分商城</h4>
        <ol class="breadcrumb pull-right">
            <li><a data-target="divload" data-div="baseContainer">积分商城</a></li>
            <li class="active">商品列表</li>
        </ol>
    </div>
</div>
<div class="panel adapt-window-height">
    <div class="panel-body">
        <div class="mt20">
            <div class="m-b-10">
                <form id="paramForm" class="form-inline" tableId="goodsTableId" onsubmit="return false;">
                    <label style="display:inline-block;">商品名称：</label>
                    <div class="btn-group">
                        <input type="text" id="goodsName" name="goodsName" type="text" class="form-control" placeholder="商品名称">
                    </div>
                    <div class="clearfix" style="display:inline-block;position: relative;top:0px;">
                        <label style="display:inline-block;">状态：</label>
                        <select id="goodsStatus" name="goodsStatus" style="width: 240px;" data-placeholder="请选择" tabindex="" title="">
                            <option value="">全部</option>
                            <option value="0">待审核</option>
                            <option value="1">审核中</option>
                            <option value="2">审核通过</option>
                            <option value="3">已上架</option>
                            <option value="4">审核未通过</option>
                            <option value="5">已下架</option>
                        </select>
                        &nbsp;&nbsp;
                        <label>上架时间：
                            <div class='input-group date' id='datetimepicker1'>
                                <input type='text' id="startDate" style="width: 170px;" name="startDate" class="form-control" />
                            </div>
                            ~
                            <div class='input-group date' id='datetimepicker2'>
                                <input type='text' id="endDate" name="endDate" style="width: 170px;" class="form-control" />
                            </div>
                        </label>
                        <div class="form-group fr">
                            <a class="btn btn-primary"  href="${pageContext.request.contextPath}/credit/goods/add" data-toggle="modal" data-target="#base_modal">添加商品</a>
                            <button class="btn btn-primary waves-effect searchBtn">查询</button>
                            <button class="btn btn-primary waves-effect clearSearch">清空</button>
                        </div>
                    </div>
                    <%--<div class="clearfix" style="display:inline-block;position: relative;top:3px;">
                        <input type="hidden" id="groupNameForImport">
                        <div class="btn-group">
                            <button id="btn_add" class="btn yellow"  onclick="toAdd()">
                                添加商品
                            </button>
                        </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <div class="btn-group">
                            <button id="btn_manage" class="btn yellow" type="button" onclick="toMessageTemplate()">短信模板</button>
                        </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <div class="btn-group">
                            <button id="importVirtualCard" class="btn_import btn yellow" type="button" onclick="toAddVirtualCard()">导入虚拟卡</button>
                        </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </div>--%>
                    <table class="table table-striped table-bordered table-hover dataTable" id="goodsList"
                           aria-describedby="sample_1_info">
                    </table>
                    <div class="row-fluid">
                        <div class="span6" style="display:none">
                            <div class="dataTables_info" id="sample_1_info"></div>
                        </div>
                    </div>
                </form>
            </div>

            <table data-toggle="table" data-classes="table table-hover"
                   data-pagination="true" data-striped="true"
                   data-url="${pageContext.request.contextPath}/credit/goods/listData" id="goodsTableId"
                   data-side-pagination="server">
                <thead>
                <tr>
                    <th data-checkbox="true" data-align="left">序号</th>
                    <th data-field="" data-align="left" data-formatter="operateRender">操作</th>
                    <th data-field="id" data-align="left">商品ID</th>
                    <th data-field="goodsName" data-align="left" data-formatter="autoWrapRender">商品名称</th>
                    <th data-field="goodsSubtitle" data-align="left"  data-formatter="autoWrapRender">商品副标题</th>
                    <th data-field="goodsType" data-align="left" data-formatter="renderGoodsType">商品类型</th>
                    <th data-field="goodsPrice" data-align="left">商品价格</th>
                    <th data-field="actualGoodsStockDto.stock" data-align="left">库存</th>
                    <th data-field="goodsStatus" data-align="left" data-formatter="renderGoodsStatus">审核状态</th>
                    <th data-field="goodsAuditDescription" data-align="left" data-formatter="autoWrapRender" >审核原因</th>
                    <th data-field="goodsCoverUrl" data-align="left" data-formatter="commonRenderImg">封面图片</th>
                    <th data-field="goodsShelfTime" data-align="left" data-formatter="defaultDateFormatter">上架时间</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/assets/js/module/credit/goods/goods_list.js"></script>
</html>

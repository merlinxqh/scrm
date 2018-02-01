<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="${pageContext.request.contextPath}/assets/js/common/bootstrapValidator.js"></script>
</head>
<form class="form-horizontal tasi-form" method="post"
      action="${pageContext.request.contextPath}/basicData/saveTypeData?callbackMethod=ajaxTreeDone"
      data-toggle="validator"
      onsubmit="return validateCallback(this,modalAjaxDone);">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">×</button>
        <h4 class="modal-title">
            <c:choose>
                <c:when test="${data ne null}">
                    修改数据类型
                </c:when>
                <c:otherwise>
                    添加数据类型
                </c:otherwise>
            </c:choose>
        </h4>
    </div>
    <input type="hidden" id="id" name="id" value="${data.id }" />
    <input type="hidden" id="parentCode" name="parentCode" value="${parent.code }" />
    <div class="modal-body">
        <c:if test="${parent ne null}">
            <div class="form-group">
                <label for="parentName" class="col-sm-2 col-xs-2 control-label">上级类型</label>
                <div class="col-sm-4 col-md-offset-2">
                    <input type="text" class="form-control" id="parentName" readonly="readonly"  value="${parent.name }" >
                    <div class="help-block with-errors"></div>
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label for="name" class="col-sm-2 col-xs-2 control-label">分类名称</label>
            <div class="col-sm-4 col-md-offset-2">
                <input type="text" class="form-control" id="name" name="name" placeholder="分类名称" data-error="请输入分类名称" value="${data.name }"  required/>
                <div class="help-block with-errors"></div>
            </div>
        </div>
        <div class="form-group">
            <label for="type" class="col-sm-2 col-xs-2 control-label">数据类型</label>
            <div class="col-sm-4 col-md-offset-2">
                <select class="select2 select2-offscreen" id="type"  name="type" style="width: 240px;" data-error="请选择类型" data-placeholder="请选择" <c:if test="${data ne null}">disabled</c:if> tabindex="-1" title="" required>
                    <option value="TEXT" <c:if test="${data ne null and data.type eq 'TEXT'}">selected</c:if>>文本</option>
                    <option value="NUMBER" <c:if test="${data ne null and data.type eq 'NUMBER'}">selected</c:if>>数值</option>
                    <option value="FILE" <c:if test="${data ne null and data.type eq 'FILE'}">selected</c:if>>文件</option>
                </select>
                <div class="help-block with-errors"></div>
            </div>
        </div>
        <div class="form-group">
            <label for="multipleOnline" class="col-sm-2 col-xs-2 control-label">限制</label>
            <div class="col-sm-4 col-md-offset-2">
                <div class="checkbox checkbox-success checkbox-inline">
                    <input type="checkbox" id="multipleOnline" name="multipleOnline" value="false" <c:if test="${!data.multipleOnline}">checked</c:if>>
                    <label for="multipleOnline">限制启用一条数据</label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="code" class="col-sm-2 col-xs-2 control-label">唯一编码</label>
            <div class="col-sm-4 col-md-offset-2">
                <input type="text" class="form-control" id="code" <c:if test="${data ne null}">readonly="readonly"</c:if> name="code" placeholder="唯一编码" data-error="请输入唯一编码" value="${data.code }"  required/>
                <div class="help-block with-errors"></div>
            </div>
        </div>
        <div class="form-group">
            <label for="orders" class="col-sm-2 col-xs-2 control-label">排序</label>
            <div class="col-sm-4 col-md-offset-2">
                <input type="number" class="form-control" id="orders" name="orders" placeholder="排序" data-error="请输入排序"  value="${data.orders }" required>
                <div class="help-block with-errors"></div>
            </div>
        </div>

        <div class="form-group no-margin">
            <label for="remark" class="col-sm-2 col-xs-2 control-label">描述 </label>
            <div class="col-sm-4 col-md-offset-2">
                <textarea class="form-control autogrow" id="remark" name="remark" style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 104px;">${data.remark }</textarea>
            </div>
        </div>

    </div>
    <div class="modal-footer">
        <button type="submit" class="btn btn-info waves-effect waves-light">确定</button>
        <button type="button" class="btn btn-default waves-effect" data-dismiss="modal">取消</button>
    </div>
</form>
<script type="text/javascript">
    $(document).ready(function(){
        $(".select2").select2({});
    });
</script>
</html>

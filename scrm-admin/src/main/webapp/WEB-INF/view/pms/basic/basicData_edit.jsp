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
      action="${pageContext.request.contextPath}/basicData/saveData?callbackMethod=refreshRightTable"
      data-toggle="validator"
      onsubmit="return validateCallback(this,modalAjaxDone);">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">×</button>
        <h4 class="modal-title">
            <c:choose>
                <c:when test="${data ne null}">
                    修改基础数据
                </c:when>
                <c:otherwise>
                    添加基础数据
                </c:otherwise>
            </c:choose>
        </h4>
    </div>
    <input type="hidden" id="id" name="id" value="${data.id }" />
    <div class="modal-body">
        <div class="form-group">
            <label for="label" class="col-sm-2 col-xs-2 control-label">标签</label>
            <div class="col-sm-4 col-md-offset-2">
                <input type="text" class="form-control" id="label" name="label" placeholder="标签" data-error="请输入标签" value="${data.label }"  required/>
                <div class="help-block with-errors"></div>
            </div>
        </div>
        <div class="form-group">
            <label for="typeCode" class="col-sm-2 col-xs-2 control-label">数据类型</label>
            <div class="col-sm-4 col-md-offset-2">
                <input type="text" class="form-control" id="typeCode" readonly="readonly" name="typeCode" placeholder="数据类型"
                       data-error="请输入数据类型" <c:choose><c:when test="${data ne null}">value="${data.typeCode}"</c:when><c:otherwise>value="${typeCode}"</c:otherwise></c:choose>  required/>
                <div class="help-block with-errors"></div>
            </div>
        </div>
        <div class="form-group">
            <label for="value" class="col-sm-2 col-xs-2 control-label">值</label>
            <div class="col-sm-4 col-md-offset-2">
                <input type="text" class="form-control" id="value" name="value" placeholder="值" data-error="请输入值" value="${data.value }"  required/>
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
            <label for="description" class="col-sm-2 col-xs-2 control-label">描述 </label>
            <div class="col-sm-4 col-md-offset-2">
                <textarea class="form-control autogrow" id="description" name="description" style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 104px;">${data.description }</textarea>
            </div>
        </div>

    </div>
    <div class="modal-footer">
        <button type="submit" class="btn btn-info waves-effect waves-light">确定</button>
        <button type="button" class="btn btn-default waves-effect" data-dismiss="modal">取消</button>
    </div>
</form>
</html>

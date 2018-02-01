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
      action="${pageContext.request.contextPath}/pms/role/saveData"
      data-toggle="validator"
      onsubmit="return validateCallback(this,modalAjaxDone);">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">×</button>
        <h4 class="modal-title">
            <c:choose>
                <c:when test="${data ne null}">
                    修改角色
                </c:when>
                <c:otherwise>
                    添加角色
                </c:otherwise>
            </c:choose>
        </h4>
    </div>
    <input type="hidden" id="id" name="id" value="${data.id }" />
    <div class="modal-body">
        <div class="form-group">
            <label for="name" class="col-sm-2 col-xs-2 control-label">角色名称</label>
            <div class="col-sm-4 col-md-offset-2">
                <input type="text" class="form-control" id="name" name="name" placeholder="角色名称" data-error="请输入角色名称" value="${data.name }"  required/>
                <div class="help-block with-errors"></div>
            </div>
        </div>

        <div class="form-group no-margin">
            <label for="remark" class="col-sm-2 col-xs-2 control-label">备注 </label>
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
<div id="cover_img_modal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content"></div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        $(".select2").select2({

        });
    });
</script>
</html>

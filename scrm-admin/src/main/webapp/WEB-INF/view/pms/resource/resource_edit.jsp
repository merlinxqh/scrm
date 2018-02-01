<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="${pageContext.request.contextPath}/assets/js/common/bootstrapValidator.js"></script>
</head>
<body>
<form class="form-horizontal tasi-form" method="post"
      action="${pageContext.request.contextPath}/pms/resource/saveData?callbackMethod=ajaxTreeDone"
      data-toggle="validator"
      onsubmit="return validateCallback(this,modalAjaxDone);">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">×</button>
        <h4 class="modal-title">
            <c:choose>
                <c:when test="${data ne null}">
                    <c:if test="${data.isMenu eq 1}">
                      修改菜单
                    </c:if>
                    <c:if test="${data.isMenu eq 2}">
                        修改权限
                    </c:if>
                </c:when>
                <c:otherwise>

                    <c:if test="${isMenu eq 1}">
                        添加菜单
                    </c:if>
                    <c:if test="${isMenu eq 2}">
                        添加权限
                    </c:if>
                </c:otherwise>
            </c:choose>
        </h4>
    </div>
    <input type="hidden" id="id" name="id" value="${data.id }" />
    <c:choose>
        <c:when test="${data ne null}">
            <input type="hidden" id="isMenu" name="isMenu" value="${data.isMenu }" />
        </c:when>
        <c:otherwise>
            <input type="hidden" id="isMenu" name="isMenu" value="${isMenu }" />
        </c:otherwise>
    </c:choose>

    <div class="modal-body">
        <c:if test="${parent ne null}">
            <div class="form-group">
                <label for="name" class="col-sm-2 col-xs-2 control-label">上级资源</label>
                <div class="col-sm-4 col-md-offset-2">
                    <input type="text" class="form-control" id="parentName" readonly="readonly" placeholder="名称" value="${parent.name }"  required/>
                    <input type="hidden" id="parentCode" name="parentCode" value="${parent.code}">
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label for="name" class="col-sm-2 col-xs-2 control-label">名称</label>
            <div class="col-sm-4 col-md-offset-2">
                <input type="text" class="form-control" id="name" name="name" placeholder="名称" data-error="请输入名称" value="${data.name }"  required/>
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
        <div class="form-group">
            <label for="url" class="col-sm-2 col-xs-2 control-label">资源路径</label>
            <div class="col-sm-4 col-md-offset-2">
                <input type="text" class="form-control" id="url" name="url" placeholder="资源路径" data-error="请输入资源路径"  value="${data.url }"  required/>
                <div class="help-block with-errors"></div>
            </div>
        </div>
        <div class="form-group">
            <label for="permission" class="col-sm-2 col-xs-2 control-label">权限标识</label>
            <div class="col-sm-4 col-md-offset-2">
                <input type="text" class="form-control" id="permission" name="permission" placeholder="权限标识" data-error="请输入权限标识"  value="${data.permission }"/>
            </div>
        </div>
        <div class="form-group">
            <label for="iconCode" class="col-sm-2 col-xs-2 control-label">图标</label>
            <div class="col-sm-4 col-md-offset-2">
                <input type="text" class="form-control" id="iconCode" placeholder="图标" name="iconCode" value="${data.iconCode }">
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
<script type="text/javascript">
    $(document).ready(function(){
        $(".select2").select2({

        });
    });
</script>
</body>
</html>

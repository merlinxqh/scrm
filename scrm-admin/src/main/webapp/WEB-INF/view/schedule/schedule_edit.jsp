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
      action="${pageContext.request.contextPath}/schedule/saveData"
      data-toggle="validator"
      onsubmit="return validateCallback(this,modalAjaxDone);">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">×</button>
        <h4 class="modal-title">
            <c:choose>
                <c:when test="${data ne null}">
                    修改调度任务
                </c:when>
                <c:otherwise>
                    添加调度任务
                </c:otherwise>
            </c:choose>
        </h4>
    </div>
    <input type="hidden" id="id" name="id" value="${data.id }" />
    <div class="modal-body">
        <div class="form-group">
            <label for="name" class="col-sm-2 col-xs-2 control-label">任务名称</label>
            <div class="col-sm-4 col-md-offset-2">
                <input type="text" class="form-control" id="name" name="name" placeholder="名称" data-error="请输入名称" value="${data.name }"  required="false"/>
                <div class="help-block with-errors"></div>
            </div>
        </div>
        <div class="form-group">
            <label for="jobGroup" class="col-sm-2 col-xs-2 control-label">分组</label>
            <div class="col-sm-4 col-md-offset-2">
                <input type="text" class="form-control" id="jobGroup" name="jobGroup" placeholder="分组" data-error="请输入分组"  value="${data.jobGroup }" required>
                <div class="help-block with-errors"></div>
            </div>
        </div>
        <div class="form-group">
            <label for="jobGroup" class="col-sm-2 col-xs-2 control-label">执行方式</label>
            <div class="col-sm-4 col-md-offset-2">
                <select class="select2 select2-offscreen" id="scheduleType"  name="scheduleType" style="width: 240px;" data-error="请选择执行方式" data-placeholder="执行方式" tabindex="-1" title="" required>
                    <option value="RPC">RPC调用</option>
                    <option value="HTTP">HTTP调用</option>
                </select>
                <div class="help-block with-errors"></div>
            </div>
        </div>
        <div id="rpc_invoke_div">
            <div class="form-group">
                <label for="beanClass" class="col-sm-2 col-xs-2 control-label">类名</label>
                <div class="col-sm-4 col-md-offset-2">
                    <input type="text" class="form-control" id="beanClass" name="beanClass" placeholder="类名" data-error="请输入类名"  value="${data.beanClass }"  required/>
                    <div class="help-block with-errors"></div>
                </div>
            </div>
            <div class="form-group">
                <label for="methodName" class="col-sm-2 col-xs-2 control-label">方法名</label>
                <div class="col-sm-4 col-md-offset-2">
                    <input type="text" class="form-control" id="methodName" name="methodName" placeholder="方法名" data-error="请输入方法名"  value="${data.methodName }" required/>
                    <div class="help-block with-errors"></div>
                </div>
            </div>
            <div class="form-group">
                <label for="springId" class="col-sm-2 col-xs-2 control-label">实例ID</label>
                <div class="col-sm-4 col-md-offset-2">
                    <input type="text" class="form-control" id="springId" placeholder="实例ID" data-error="请输入实例ID" name="springId" value="${data.springId }" required>
                    <div class="help-block with-errors"></div>
                </div>
            </div>
        </div>
        <div id="http_invoke_div">
            <div class="form-group">
                <label for="httpUrl" class="col-sm-2 col-xs-2 control-label">HTTP调用url</label>
                <div class="col-sm-4 col-md-offset-2">
                    <input type="text" class="form-control" id="httpUrl" name="httpUrl" placeholder="HTTP调用url" data-error="请输入HTTP调用url"  value="${data.httpUrl }" required/>
                    <div class="help-block with-errors"></div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="cronExpression" class="col-sm-2 col-xs-2 control-label">cron表达式</label>
            <div class="col-sm-4 col-md-offset-2">
                <input type="text" class="form-control" id="cronExpression" placeholder="cron表达式" data-error="请输入cron表达式" name="cronExpression" value="${data.cronExpression }" required>
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
</body>
<script src="${pageContext.request.contextPath}/assets/js/module/schedule/schedule_edit.js"></script>
</html>

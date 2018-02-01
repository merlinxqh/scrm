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
      action="${pageContext.request.contextPath}/pms/user/saveData?callbackMethod=refreshTable"
      data-toggle="validator"
      onsubmit="return modifyPwdSave(this,modalAjaxDone);">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">×</button>
        <h4 class="modal-title">
            <c:choose>
                <c:when test="${data ne null}">
                    修改用户
                </c:when>
                <c:otherwise>
                    添加用户
                </c:otherwise>
            </c:choose>
        </h4>
    </div>
    <input type="hidden" id="id" name="id" value="${data.id }" />
    <div class="modal-body">
        <div class="form-group">
            <label for="name" class="col-sm-2 col-xs-2 control-label">用户名称</label>
            <div class="col-sm-4 col-md-offset-2">
                <input type="text" class="form-control" id="name" name="name" placeholder="用户名称" data-error="请输入用户名称" value="${data.name }"  required/>
                <div class="help-block with-errors"></div>
            </div>
        </div>
        <div class="form-group no-margin">
            <label for="email" class="col-sm-2 col-xs-2 control-label">邮箱 </label>
            <div class="col-sm-4 col-md-offset-2">
                <input type="email" class="form-control" id="email" name="email" <c:if test="${data ne null}">disabled</c:if>  data-error="请输入邮箱" placeholder="邮箱" value="${data.email }" required/>
                <div class="help-block with-errors"></div>
            </div>
        </div>
        <div class="form-group no-margin">
            <label for="defaultRole" class="col-sm-2 col-xs-2 control-label">角色</label>
            <div class="col-sm-4 col-md-offset-2">
                <select class="select2 select2-offscreen" id="defaultRole"  name="defaultRole.code" style="width: 240px;" data-error="请选择角色" data-placeholder="请选择" tabindex="-1" title="" required>
                   <c:forEach items="${roleList}" var="item">
                       <option value="${item.code}" <c:if test="${data ne null and data.defaultRole.code eq item.code}">selected</c:if>>${item.name}</option>
                   </c:forEach>
                </select>
                <div class="help-block with-errors"></div>
            </div>
        </div>
        <c:if test="${data eq null}">
            <div class="form-group no-margin">
                <label for="passWord" class="col-sm-2 col-xs-2 control-label">密码 </label>
                <div class="col-sm-4 col-md-offset-2">
                    <input type="password" class="form-control" id="password1" name="password" placeholder="密码" data-error="请输入密码" value="" required/>
                    <div class="help-block with-errors"></div>
                </div>
            </div>
            <div class="form-group no-margin">
                <label for="confirmPassword" class="col-sm-2 col-xs-2 control-label">确认密码 </label>
                <div class="col-sm-4 col-md-offset-2">
                    <input type="password" class="form-control" id="confirmPassword1" name="confirmPassword" data-error="请输入确认密码" placeholder="确认密码" value="" required/>
                    <div class="help-block with-errors"></div>
                </div>
            </div>
        </c:if>
        <c:if test="${data ne null}">
            <div class="form-group no-margin">
                <label for="passWord" class="col-sm-2 col-xs-2 control-label">修改密码 </label>
                <div class="col-sm-4 col-md-offset-2">
                    <input type="password" class="form-control" id="password" name="password" placeholder="密码" data-error="请输入修改密码" value="" />
                    <div class="help-block with-errors"></div>
                </div>
            </div>
            <div class="form-group no-margin">
                <label for="confirmPassword" class="col-sm-2 col-xs-2 control-label">确认修改密码 </label>
                <div class="col-sm-4 col-md-offset-2">
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" data-error="请输入确认修改密码" placeholder="确认密码" value="" />
                    <div class="help-block with-errors"></div>
                </div>
            </div>
        </c:if>

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

<script type="text/javascript">
    function modifyPwdSave(form,modalAjaxDone){
        if($("#confirmPassword").val() != $("#password").val() || $("#confirmPassword1").val() != $("#password1").val()){
            _errorTipsFun("新密码和确认密码不匹配");
            return false;
        }
        return validateCallback(form,modalAjaxDone);
    }
</script>
</html>

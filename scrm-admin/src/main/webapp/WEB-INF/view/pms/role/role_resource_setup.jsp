<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">×</button>
        <h4 class="modal-title">资源设置 </h4>
    </div>
    <div class="modal-body">
        <div class="row">
            <!-- Left sidebar -->
            <div class="col-md-6 col-md-offset-3">
                <div class="checkbox checkbox-primary">
                    <input id="containChild" type="checkbox">
                    <label for="containChild">
                        操作包含下级
                    </label>
                </div>
            </div>
            <div class="col-md-6 col-md-offset-3">
                <input type="input" class="form-control" id="input-check-node" placeholder="按回车键搜索" value="">
                <div class="panel panel-default p-0  m-t-20">
                    <div class="panel-body p-0">
                        <div id="checkable_tree" style="overflow: auto;height: 450px;"></div>

                    </div>
                </div>
            </div>
            <!-- End Left sidebar -->

            <!-- Right Sidebar -->
            <div class="col-lg-9 col-md-8" id="right_panel">

            </div>
            <!-- end Col-9 -->
        </div>
        <input type="hidden" id="refRoleCode" name="refRoleCode" value="${roleCode }" />
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default waves-effect" data-dismiss="modal">取消</button>
        <button type="button" onclick="submitData();" class="btn btn-info waves-effect waves-light">保存</button>
    </div>
    <script src="${pageContext.request.contextPath}/assets/js/common/checkableTree-util.js"/>
    <script src="${pageContext.request.contextPath}/assets/js/module/pms/role/resource_role_setup.js"/>

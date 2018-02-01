<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title">文件上传</h4>
</div>
<div class="container kv-main">
    <br>
    <form enctype="multipart/form-data">                                                                  
        <div class="form-group">

            <input id="file-1" name="file" <c:if test="${maxNum gt 1 }">multiple="multiple"</c:if> type="file" class="file" data-overwrite-initial="false">
        </div>
    </form>
    <input type="hidden" name="purpose" id="purpose" value="${purpose }"/>
    <input type="hidden" name="maxNum" id="maxNum" value="${maxNum }"/>
    <input type="hidden" name="fileFormat" id="fileFormat" value="${fileFormat }"/>
    <input type="hidden" name="fileMaxSize" id="fileMaxSize" value="${fileMaxSize }"/>
    <input type="hidden" name="callBackMethod" id="callBackMethod" value="${callBackMethod }"/>
    <input type="hidden" name="callBackParam" id="callBackParam" value="${callBackParam }"/>
    <input type="hidden" name="uploadUrl" id="uploadUrl" value="${uploadUrl}"/>
    <input type="hidden" name="uploadSuccessMethod" id="uploadSuccessMethod" value="${uploadSuccessMethod}">
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default waves-effect" data-dismiss="modal">取消</button>
    <button type="button" onclick="makeSureUpload();" class="btn btn-info waves-effect waves-light">确定</button>
</div>
<script src="${pageContext.request.contextPath}/assets/js/module/upload/file_upload.js" />
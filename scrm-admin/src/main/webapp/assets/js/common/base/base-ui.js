/**
 *
 */
var _globalParamObj={};//存储查询参数
var _globalTableViewer;//列表图片查看viewer对象
$(function() {
    // 处理session 超时
    $.ajaxSetup({
        contentType : "application/x-www-form-urlencoded;charset=utf-8",
        cache : false,// 关闭AJAX相应的缓存
        dataType : 'json',
        complete : function(xhr, status) {
            switch(xhr.status) {
                case 404 :
                    wrapperHideModal('base_modal');
                    _errorTipsFun("此链接地址已失效，请联系管理员！");
                    break;
                case 500 :
                    wrapperHideModal('base_modal');
                    _errorTipsFun("服务器处理请求过程中出现未处理异常，请联系管理员！");
                    break;
                case 403 :
                    wrapperHideModal('base_modal');
                    _errorTipsFun("你没有操作权限！");
                    break;
                case 401 :
                    _errorTipsFun("登录信息已失效,请重新登录");
                    setTimeout(function () {
                        location.href = rootPath + '/login';
                    }, 1500);
                    break;
            }
        }
    });

    // navTab 方式打开页面
    $("body").delegate("*[data-target='navTab']","click",function() {
        var url = $(this).attr("href") || $(this).data("url");
        $("#navTab").load(url, initHtml);
        return false;
    });

    // navTab 方式打开页面
    $("body").delegate( "*[data-target='divload']", "click", function() {
        var url = $(this).attr("href") || $(this).data("url");
        var div = $(this).data("div");
        $("#"+div).data("url",url);
        $("#"+div).load(url, initHtml);

        //只加载菜单特殊处理
        if(div=="sidebar-menu"){
            $("#baseContainer").html("");
        }
        return false;
    });

    // 防止modal缓存
    $("#base_modal,#base_modal_center").on("loaded.bs.modal", function() {
        initHtml();
        // 重置校验
        resetValidator();
    });

    $("#base_modal,#base_modal_center").on("hidden.bs.modal", function() {
        // 清除modal缓存
        modalHideClearCache($(this));
        resetValidator();
    });

    // modal 打开完后激活 校验
    $('#base_modal,#base_modal_center').on('shown.bs.modal', function() {
        initValidator();
    });

    //回车事件
    document.onkeydown=function(e){
        var theEvent = e || window.event;
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
        if (code == 13) {
            e.stopPropagation();
            enterKeyPressSearch();
            return false;
        }
    };
    //解决modal 弹框 select2搜索框不能输入问题
    $.fn.modal.Constructor.prototype.enforceFocus = function () { };
    //监控 浏览器窗口大小变化事件
    window.onresize = function(){
        autoSetWidthHeight();
    }
});

/**
 * modal 隐藏清缓存 方法
 * @param obj
 */
function modalHideClearCache(obj){
    obj.removeData("bs.modal");
    obj.find(".modal-content").empty();
}

/**
 * 隐藏modal方法
 * @param targetId
 */
function wrapperHideModal(targetId){
    $("#"+targetId).modal('hide');
    modalHideClearCache($("#"+targetId));
}

function autoSetWidthHeight(){
    $(".adapt-window-height").css({
        height:($(window).height()-180)+'px'
    });

    if($(".goods-edit-content").length>0){
        //商品新增编辑页
        $(".goods-edit-content").css({
            height: ($(window).height()-203)+'px'
        });
    }

    if($(".auction-content").length>0){
        //商品新增编辑页
        $(".auction-content").css({
            height: ($(window).height()-300)+'px'
        });
    }

    //设定左边树高度
    if($(".tree-fixed-height").length>0){
        $(".tree-fixed-height").css({
            height: ($(window).height()-180)+'px'
        });
    }
}

/**
 * 初始化html
 */
function initHtml() {
    //设定 panel 高度

    autoSetWidthHeight();
    wrapperSetStoreSearchParam($("#paramForm"));
    // 绑定 ajaxToDo 事件
    $(".ajaxToDo").unbind().bind( "click", function() {
        var url = $(this).attr("href");
        wrapperConfirmFun('ajaxToDo','确定执行该操作吗?',{url:url});
    });
    //绑定 bootstrap-table 事件
    bindBootstrapTableEvent($("#paramForm"));
    // bootstrap-table渲染
    initBootstrapTable();

    /**
     * 设置 dataTable最小宽度 为了解决 小屏电脑 显示问题
     * @type {*}
     * @private
     */
    var _panel=$(".table-hover").parents(".adapt-window-height");
    if(!_panel.hasClass("panel-data-table")
        && !_panel.hasClass("auth-width")){//不需要最小宽度1200px的 添加这个class
        _panel.addClass("panel-data-table");
    }
    /**
     * 清空按钮
     */
    $(".clearSearch").unbind().bind("click",function(){

        wrapperClearFun(this);
    });
    /**
     * 查询按钮
     */
    $(".searchBtn").unbind().bind("click",function(){

        wrapperFormSearch(this);
    });

    // 绑定 tableAjaxTodo 事件
    $(".tableAjaxTodo").unbind().bind( "click", function() {
        var url = $(this).attr("href");
        $.Notification.confirm('success', 'top center', '操作提示！',
            tableAjaxTodo, url);
    });

    $(".ajaxDel").unbind().bind( "click", function(event) {
        event.preventDefault();
        var url = $(this).attr("href");
        ajaxDel(url);
    });
    /**
     * 滚动条问题 回到顶部
     */
}

function refreshTable(){
    enterKeyPressSearch();
}

/**
 * 封装通用查询方法
 * @param obj
 */
function wrapperFormSearch(obj){
    var $form = $(obj).parents("form");
    if($form.attr("searchFunction") != undefined){
        //特殊页面自己实现 查询方法
        var _searchFun=$form.attr("searchFunction");
        eval(_searchFun+'()');
        return;
    }
    wrapperStoreSearchParam($form);
    var tableId = $form.attr("tableId");
    var table = $("#"+tableId);
    var urlJson ={};
    urlJson.url=wrapperParamFormUrl($form);
    if(table.parents(".bootstrap-table").length > 0){
        table.bootstrapTable('refresh', urlJson);
    }else{
        table.bootstrapTable(urlJson);
    }
}

/**
 * 绑定 数据 加载完 执行事件
 * @param objForm
 */
function bindBootstrapTableEvent(objForm){
    var _tableId=objForm.attr("tableId");
    //绑定 数据加载渲染 完成 后执行方法
    $("#"+_tableId).on("load-success.bs.table",function(e,data){
        wrapperOnLoadSuccess(_tableId,data);
    });
}

/**
 * 页面加载成功执行事件
 * @param tableId
 */
function wrapperOnLoadSuccess(tableId,data){
    //
    if($("#"+tableId).find("img").length>0){
        //图片延迟加载
        $("img.lazy").lazyload({effect: "fadeIn"});

        //查看图片插件viewer
        if(_globalTableViewer){
            _globalTableViewer.destroy();
        }
        _globalTableViewer=new Viewer(document.getElementById(tableId), {
            url: 'data-original'
        });
    }
}

/**
 * 当前页面有没有 modal
 * @returns {boolean}
 */
function currentHasModal(){
    if($(".modal-body").length>0){
        return true;
    }
    return false;
}

/**
 * 判断当前 是否有 需要渲染的table
 * @param obj
 */
function judgeHasBootstrapTable(obj){
    if(obj == undefined){
        //查询全局
        if($('[data-toggle="table"]').length>0){
            return true;
        }
    }else{
        if(obj.find('[data-toggle="table"]').length>0){
            return true;
        }
    }

    return false;
}

/**
 * 回车键 查询事件
 */
function enterKeyPressSearch(){
    //当前 有 弹窗 并且 有需要渲染的 分页table
    if(currentHasModal() && judgeHasBootstrapTable($(".modal-body"))){
        if($(".modal-body").find(".searchBtn").length>0){
            $(".modal-body").find(".searchBtn").click();
        }
    }else{
        //没有模态框 找查询按钮 click
        $(".searchBtn").click();
    }

}

/**
 * 初始化bootstrap table
 */
function initBootstrapTable(){
    if(judgeHasBootstrapTable()){//有需要渲染的table
        var urlJson ={};
        if(currentHasModal()){
            //如果 是弹窗 modal 只 查询 modal内的paramForm
            if($(".modal-body").find("#paramForm").length>0){
                urlJson.url=wrapperParamFormUrl($(".modal-body").find("#paramForm"));
            }else if($(".modal-body").find(".table-hover").length>0){
                urlJson.url=$(".modal-body").find(".table-hover").attr("data-url");
            }
            $(".modal-body").find('[data-toggle="table"]').bootstrapTable(urlJson);
        }else{
            urlJson.url=wrapperParamFormUrl($("#paramForm"));
            $('[data-toggle="table"]').bootstrapTable(urlJson);
        }
    }
}

/**
 * 获取form 拼接url
 * @param form
 * @returns {*}
 */
function wrapperParamFormUrl(form){
    var param = form.serialize();
    var tableId = form.attr("tableId");
    var table = $("#"+tableId);
    var baseUrl = table.attr("data-url");
    if(baseUrl.indexOf("?")==-1){
        baseUrl += "?" + param;
    }else{
        baseUrl += "&"+param;
    }
    return baseUrl;
}

/**
 * 查询form 需要tableId唯一
 * 封装存储 查询参数方法
 * @param obj 对应form对象
 */
function wrapperStoreSearchParam(obj){
    var _curParList=obj.serializeArray();
    var _tableId=obj.attr("tableId");
    var _curPar={};
    $.each(_curParList,function(){
        _curPar[this.name]=this.value;
    });
    _globalParamObj[_tableId]=_curPar;
}

/**
 * 根据tableId获取查询 存储参数
 * @param tableId
 * @returns {*}
 * @private
 */
function _getStoreDataByTableId(tableId){
    return _globalParamObj[tableId];
}

/**
 * 清空 存储查询参数方法
 * @param obj
 */
function wrapperClearSearchParam(obj){
    var _tableId=$(obj).attr("tableId");
    if(_globalParamObj.hasOwnProperty(_tableId)){
        delete _globalParamObj[_tableId];
    }
}

/**
 * 设置 存储查询参数
 * @param obj
 */
function wrapperSetStoreSearchParam(obj){
    if(obj.attr("storeSearchParam") == 'false'){
        //不存储 查询参数
        return ;
    }
    var _tableId=obj.attr("tableId");
    if(_globalParamObj.hasOwnProperty(_tableId)){
        var _curPar=_globalParamObj[_tableId];
        obj.find("input[type!='checkbox']").each(function(){
            var _this=this;
            if($(_this).attr("type") != 'hidden'){//隐藏域 不做操作
                var _name=$(_this).attr("name");
                $(_this).val(_curPar[_name]);
            }
        });
        obj.find("input[type='checkbox']").each(function(){
            var _this=this;
            var _name=$(_this).attr("name");
            if(_curPar.hasOwnProperty(_name) && $(_this).val() == _curPar[_name]){
                $(_this).prop("checked",true);
            }
        });
        obj.find(".select2").each(function(){
            var _this=this;
            var _name=$(_this).attr("name");
            $(_this).val(_curPar[_name]);
            $(_this).change();//设置select2 选中对应的值
        });
    }
}

/**
 * 通用 清除查询方法
 * @param table
 */
function wrapperClearFun(obj){
    var _form=$(obj).parents("form")[0];
    var tableId = $(_form).attr("tableId");
    _form.reset();
    $(_form).find("input[type='hidden']").each(function(){
        $(this).val("");
    });
    $(_form).find(".select2").each(function(){
        var _value=$(this).find("option:eq(0)").val();
        $(this).val(_value).trigger("change");
    });
    wrapperClearSearchParam(_form);
    wrapperFormSearch(obj);
}

function initNavTabValidator() {
    // modal form 校验 及提交
    $('.navTabform').bootstrapValidator({
        excluded : ':disabled, :hidden, :not(:visible)',
        submitHandler : function(validator, form, submitButton) {
            $.ajax({
                type : form.method || 'POST',
                url : form.attr("action"),
                data : form.serializeArray(),
                dataType : "json",
                cache : false,
                success : modalAjaxDone,
                error : ajaxError
            });
            this.disableSubmitButtons(true);
        }
    });
    // 带上传文件的form 校验
    $('.iframenavTabform').bootstrapValidator();
}


function initValidator() {
    //TODO
}



function reloadDiv(callback){
    var url = $("#baseContainer").data("url");
    $("#baseContainer").load(url, function(){
        if(callback){
            callback();
        }
        initHtml();
    });
}

function reloadDivTip(data,callback){
    modalAjaxDone(data);
    var url = $("#baseContainer").data("url");
    $("#baseContainer").load(url, function(){
        if(callback){
            callback();
        }
        initHtml();
    });
}

function resetValidator() {
    if ($('.bvform').data('bootstrapValidator')) {
        $('.bvform').data('bootstrapValidator').resetForm();
    }
    if ($('.iframeform').data('bootstrapValidator')) {
        $('.iframeform').data('bootstrapValidator').resetForm();
    }
}

/**
 * 获取 bootstrap table 参数
 * @param tableId
 * @private
 */
function _bootStrapOptions(tableId){
    var _options=$("#"+tableId).bootstrapTable('getOptions');//获取当前table数据
    //当前只返回 当前 页码以及 分页条数
    return {
        pageIndex: _options.pageNumber,
        pageSize: _options.pageSize,
        listSize: _options.data.length
    };
}

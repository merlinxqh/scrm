/**
 * Created by leo on 2017/12/22.
 * 系统通用组件
 */

/**
 * 颜色 组件input绑定事件
 * @param obj
 */
function _colorInputBindingEvent(obj){
    obj.keydown(function(e) {
        if (e.keyCode == 13) {
            _commonRenderColorPlugin(obj);
        }
    });
    obj.focusout(function(){
        _commonRenderColorPlugin(obj);
    });
}


/**
 * 封装颜色选择组件
 * 根据颜色值显示颜色 方法
 */
function _commonRenderColorPlugin(_input){
    if(_input.val() == ''){
        //没有输入值 不操作
        return;
    }
    //颜色值加 "#" 只有七位
    if(_input.val().length > 7){
        _input.val(_input.val().substr(0,7));
    }
    var rgbVal=_input.val().colorRgb();
    if(rgbVal == 'error_color_val'){
        console.log(_input.val()+"颜色值无效...");
        _input.val("");
        return;
    }
    var _parentDiv=_input.parents(".col-xs-11");
    _parentDiv.find("i").css({
        backgroundColor: rgbVal
    });
    _parentDiv.find(".colorpicker-default").attr("data-color",rgbVal);
}


/**
 * 下拉 展示操作项
 * @param action
 */
function _commonDropDownLink(action){
    if(undefined == action || action.trim() == ''){
        return action;
    }
    var _div=$("<div></div>");
    _div.append(action);
    var _drop=$(_getFixedDropDown());
    $.each(_div.find("a"),function(){
        var _li=$("<li></li>");
        _li.append(this);
        _drop.find(".dropdown-menu").append(_li);
    });
    return _drop.prop("outerHTML");
}

/**
 * 获取 下拉操作项样式
 * @returns {string}
 */
function _getFixedDropDown(){
    var _dropDown="<div class=\"btn-group\">\n" +
        "<button type=\"button\" class=\"btn btn-default dropdown-toggle waves-effect\" data-toggle=\"dropdown\" aria-expanded=\"false\">操作<span class=\"caret\"></span></button>\n" +
        "<ul class=\"dropdown-menu\" role=\"menu\">\n" +
        "</ul>\n" +
        "</div>";
    return _dropDown;
}

function _wrapperInitFileInput(targetId,targetInputId){
    var  _allowedFile=['jpg', 'png','gif'];

    var _maxFileSize=2000;

    $("#"+targetId).fileinput({
        uploadUrl: rootPath+'/filesystem/upload', // you must set a valid URL here else you will get an error
        allowedFileExtensions : _allowedFile,
        overwriteInitial: false,
        maxFileSize: _maxFileSize,
        maxFileCount: 1,
        showPreview: false,
        fileActionSettings: {showZoom: false},//不显示 预览按钮 因 用modal有问题
        slugCallback: function(filename) {
            return filename.replace('(', '_').replace(']', '_');
        },
        preferIconicPreview: true,//自定义上传类型图标
        autoReplace:true,//自动替换当前图片
        previewFileIconSettings: {
            'doc': '<i class="fa fa-file-word-o text-primary"></i>',
            'xls': '<i class="fa fa-file-excel-o text-success"></i>',
            'ppt': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
            'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',
            'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
            'htm': '<i class="fa fa-file-code-o text-info"></i>',
            'txt': '<i class="fa fa-file-text-o text-info"></i>',
            'mov': '<i class="fa fa-file-movie-o text-warning"></i>',
            'mp3': '<i class="fa fa-file-audio-o text-warning"></i>',
            'apk': '<i class="fa fa-file-archive-o text-muted"></i>',
        },
        previewFileExtSettings: {
            'doc': function(ext) {
                return ext.match(/(doc|docx)$/i);
            },
            'xls': function(ext) {
                return ext.match(/(xls|xlsx)$/i);
            },
            'ppt': function(ext) {
                return ext.match(/(ppt|pptx)$/i);
            },
            'zip': function(ext) {
                return ext.match(/(zip|rar|tar|gzip|gz|7z)$/i);
            },
            'htm': function(ext) {
                return ext.match(/(htm|html)$/i);
            },
            'txt': function(ext) {
                return ext.match(/(txt|ini|csv|java|php|js|css)$/i);
            },
            'mov': function(ext) {
                return ext.match(/(avi|mpg|mkv|mov|mp4|3gp|webm|wmv)$/i);
            },
            'mp3': function(ext) {
                return ext.match(/(mp3|wav)$/i);
            },
            'apk': function(ext) {
                return ext.match(/(apk)$/i);
            }
        }
    });

    /**
     * 文件上传成功事件
     * $.Event
     */
    $("#"+targetId).on("fileuploaded", function (event, data, previewId, index){
        _commonReplaceImg(data.response.data.filename,targetInputId);
    });
}

/**
 * 替换图片
 * @param fileName
 */
function _commonReplaceImg(fileName,targetId){
    $("#"+targetId).val(fileName);
    if(_commonCheckImg(fileName)){
        var _parent=$("#"+targetId).parent();
        _parent.find("img").attr("src",imgPath+'/'+fileName);
        _parent.show();
        setTimeout(function(){
            $("#"+targetId).parents(".form-group").find(".fileinput-remove-button").click();
        },1000);
    }
}


var _color_reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
/*16进制颜色转为RGB格式*/
String.prototype.colorRgb = function(){
    var sColor = this.toLowerCase();
    if(sColor && _color_reg.test(sColor)){
        if(sColor.length === 4){
            var sColorNew = "#";
            for(var i=1; i<4; i+=1){
                sColorNew += sColor.slice(i,i+1).concat(sColor.slice(i,i+1));
            }
            sColor = sColorNew;
        }
        //处理六位的颜色值
        var sColorChange = [];
        for(var i=1; i<7; i+=2){
            sColorChange.push(parseInt("0x"+sColor.slice(i,i+2)));
        }
        return "RGB(" + sColorChange.join(",") + ")";
    }else{
        return "error_color_val";
    }};

/*RGB颜色转换为16进制*/
String.prototype.colorHex = function(){
    var that = this;
    if(/^(rgb|RGB)/.test(that)){
        var aColor = that.replace(/(?:\(|\)|rgb|RGB)*/g,"").split(",");
        var strHex = "#";
        for(var i=0; i<aColor.length; i++){
            var hex = Number(aColor[i]).toString(16);
            if(hex === "0"){
                hex += hex;
            }
            strHex += hex;
        }
        if(strHex.length !== 7){
            strHex = that;
        }
        return strHex;
    }else if(_color_reg.test(that)){
        var aNum = that.replace(/#/,"").split("");
        if(aNum.length === 6){
            return that;
        }else if(aNum.length === 3){
            var numHex = "#";
            for(var i=0; i<aNum.length; i+=1){
                numHex += (aNum[i]+aNum[i]);
            }
            return numHex;
        }
    }else{
        return that;
    }
};

/**
 * 分页组件
 * @param targetDiv 目标元素
 * @param targetElementId 分页组件渲染标签id
 * @param pageSize  每页数量
 * @param total     总条数
 * @param curPage   当前页
 * @returns
 */
function initPagination(targetDiv,targetElementId,pageSize,total,curPage) {
    //总条数 等于0 不执行分页
    if(isNaN(total) || parseInt(total)==0){
        return;
    }
    var element = targetDiv.find('#'+targetElementId);
    var totalPages = Math.ceil(total/pageSize)
    var url = targetDiv.attr("url");
    var numberOfPages = totalPages>10?10:totalPages;
    var options = {
        bootstrapMajorVersion: 3,
        currentPage: curPage,
        numberOfPages: numberOfPages,
        totalPages: totalPages,
        itemTexts: function(type, page, current) {
            switch(type) {
                case "first":
                    return "首页";
                case "prev":
                    return "上一页";
                case "next":
                    return "下一页";
                case "last":
                    return "尾页";
                case "page":
                    return page;
            }
        },
        useBootstrapTooltip:true,
        pageUrl: function(type, page, current) {
            return "javascript:void(0);";
        },
        tooltipTitles: function(type, page, current) {
            return "第"+page+"页";
        },
        onPageClicked:function(event, originalEvent,type,page){
            var param={size:pageSize,page:page};
            //获取 查询参数
            var _searchParamFun=targetDiv.attr("searchParamFun");
            if(_searchParamFun){
                var _resParam=eval(_searchParamFun+"()");
                param=$.extend(param,_resParam);
            }
            var ajaxRes=ajaxBsPaginatorData(url,param);
            ajaxRes.then(function(){
                var resData=ajaxRes.pageData;//分页返回数据
                //渲染数据方法
                var renderPageFun=targetDiv.attr("renderPageFun");
                if(renderPageFun){
                    eval(renderPageFun+'(resData)');
                }
            });
        }
    };
    element.bootstrapPaginator(options);
}

/**
 * 异步加载分页数据
 */
function ajaxBsPaginatorData(url, param){
    var dfd=$.Deferred();
    $.ajax({
        url:url,
        type:"POST",
        data: param,
        cache : false,
        success: function(data){
            dfd.pageData=data;
            dfd.resolve();
        }
    });
    return dfd;
}
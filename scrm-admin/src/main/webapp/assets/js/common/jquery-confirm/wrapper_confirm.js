/**
 * Created by leo on 2017/12/21.
 * jquery confirm 插件
 */

/**
 * 成功提示
 * @param msg
 */
function successJqueryConfirm(msg){
    wrapperJqueryConfirm('green','success',(msg == undefined ? '操作成功' :msg));
}

/**
 * 信息提示
 * @param msg
 */
function infoJqueryConfirm(msg){
    wrapperJqueryConfirm('green','info',msg);
}

/**
 * 错误提示
 * @param msg
 */
function errorJqueryConfirm(msg){
    wrapperJqueryConfirm('red','error',(msg == undefined ? '操作失败' :msg),'md md-error');
}

/**
 * 警告
 * @param msg
 */
function warnJqueryConfirm(msg){
    wrapperJqueryConfirm('orange','warn',msg,'md md-warning');
}


/**
 * 通用确认提示窗
 * @param sureFun 确定按钮 执行事件 , 可以传 null, '' , undefined
 * @param content 指定展示内容
 * @param sureExt 确定按钮执行事件参数,格式 {param1:'',param2:''}参数名 值, 按顺序排列
 * @param cancelFun 取消按钮执行方法
 */
function confirmJqueryConfirm(sureFun,content,sureExt,cancelFun){
    if(checkIsExistsConfirmDialog()){
        return;
    }
    $.confirm({
        title: '操作提示',
        content: content == undefined || content == null || content.trim() == '' ? '确认执行该操作吗?': content,
        useBootstrap: false,
        boxWidth: '420px',
        theme: 'material',
        type: 'orange',
        icon: 'md md-warning',
        buttons: {
            sureBtn: {
                text: '确定',
                action: function(){
                    try{
                        if(sureFun != undefined && sureFun != null && sureFun != ''){
                            eval(getExecuteParams(sureExt,sureFun));
                        }
                    }catch(e){
                        console.log(e);
                    }
                }
            },
            closeBtn: {
                 text: '取消',
                 action: function(){
                     try{
                         if(cancelFun != undefined && cancelFun != null && cancelFun != ''){
                             eval(cancelFun+'()');
                         }
                     }catch(e){
                         console.log(e);
                     }
                 }
            }
        }
    });
}

/**
 * 获取 方法执行参数
 * @param extObj
 * @param sureFun
 * @returns {*}
 */
function getExecuteParams(extObj,sureFun){
    //处理没有执行参数情况
    if(extObj == undefined || typeof extObj != 'object'){
        return sureFun+'()';
    }
    var params='';
    for(var p in extObj){
        var _cobj=extObj[p];
        if(typeof _cobj === 'object'){
            params+=p+"='"+JSON.stringify(extObj[p])+"';"
        }else{
            params+=p+"='"+extObj[p]+"';"
        }
    }
    params+=sureFun+"(";
    for(var p in extObj){
        params+=p+",";
    }
    if(params.indexOf(",") != -1){
        params=params.substr(0,params.length-1);
    }
    params+=")";
    return params;
}

/**
 * 通用封装 提示插件
 * @param type
 * @param title
 * @param content
 * @param icon
 */
function wrapperJqueryConfirm(type,title,content,icon){
    if(checkIsExistsConfirmDialog()){
        return;
    }
    $.confirm({
        title: title,
        content: content,
        // autoClose: 'cancelAction|3000', //自动倒计时关闭
        useBootstrap: false,
        boxWidth: '350px',
        theme: 'material',
        type: type,
        icon: icon,
        escapeKey: true,
        backgroundDismiss: true,
        buttons: {
            closeBtn: {
                text: '关闭'
            }
        }
    });
}

/**
 * 判断当前窗口是否有 confirm dialog
 * @returns {boolean}
 */
function checkIsExistsConfirmDialog(){
    if($(".jconfirm-open").length > 0){
        return true;
    }
    return false;
}
/**
 * Created by leo on 2017/12/22.
 * 系统提示信息 插件封装
 */
/**
 * 错误消息提示
 * @param msg
 */
function _errorTipsFun(msg,delayTime){
    errorJqueryConfirm(msg);
    // if(delayTime == undefined){
    //     delayTime=1500;//默认1.5秒
    // }
    // //解决 多个提示同时弹出
    // if($(".notifyjs-wrapper").length == 0){
    //     $.Notification.autoHideNotify('error', 'top center', '消息提示！',msg, delayTime);
    // }
}

/**
 * 警告消息提示
 * @param msg
 */
function _warnTipsFun(msg, delayTime){
    warnJqueryConfirm(msg);
    // if(delayTime == undefined){
    //     delayTime=1500;//默认1.5秒
    // }
    // if($(".notifyjs-wrapper").length == 0) {
    //     $.Notification.autoHideNotify('warning', 'top center', '消息提示！', msg, delayTime);
    // }
}

/**
 * 提示信息
 * @param msg
 * @private
 */
function _infoTipsFun(msg,delayTime){
    infoJqueryConfirm(msg);
    // if(delayTime == undefined){
    //     delayTime=1500;//默认1.5秒
    // }
    // if($(".notifyjs-wrapper").length == 0){
    //     $.Notification.autoHideNotify('info', 'top center', '消息提示！',msg, delayTime);
    // }
}

/**
 * 成功消息提示
 * @param msg
 */
function _successTipsFun(msg,delayTime){
    // successJqueryConfirm(msg);
    if(delayTime == undefined){
        delayTime=1500;//默认1.5秒
    }
    if($(".notifyjs-wrapper").length == 0) {
        $.Notification.autoHideNotify('success', 'top center', '消息提示！', msg, delayTime);
    }
}


function wrapperConfirmFun(sureFun,content,sureExt,cancelFun){
    confirmJqueryConfirm(sureFun,content,sureExt,cancelFun);
}
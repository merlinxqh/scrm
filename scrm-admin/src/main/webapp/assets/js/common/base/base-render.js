/**
 * Created by leo on 2017/12/22.
 * 通用渲染方法
 */
/**
 * 时间格式化
 * @param value
 * @returns {String}
 */
function defaultDateFormatter(value) {
    if(value == undefined){//没有值 不显示
        return '';
    }
    var d = new Date(value);

    var month;
    var date;
    var hours;
    var minutes;
    var seconds;

    if ((d.getMonth() + 1) < 10 ) {
        month = "0" + (d.getMonth()+1);
    } else
        month = d.getMonth()+1;

    if (d.getDate() < 10 ) {
        date = "0" + d.getDate();
    } else
        date = d.getDate();

    if (d.getHours() < 10 ) {
        hours = "0" + d.getHours();
    } else
        hours = d.getHours();

    if (d.getMinutes() < 10 ) {
        minutes = "0" + d.getMinutes();
    } else
        minutes = d.getMinutes();

    if (d.getSeconds() < 10 ) {
        seconds = "0" + d.getSeconds();
    } else
        seconds = d.getSeconds();


    var formatdate= d.getFullYear()+"-"+(month)+"-"+date+" "+hours+":"+minutes+":"+seconds;
    return formatdate;
}

/**
 * 状态渲染方法
 * 1: 有效 0:失效
 * @param value
 * @param row
 * @param index
 */
function statusRenderValid(value, row ,index){
    if(value == 1){
        return '有效';
    }
    return '无效';
}

/**
 * 上线 下线
 * @param value
 * @param row
 * @param index
 */
function statusRenderOnline(value, row, index){
    if(value == 1){
        return '已上线';
    }
    return '已下线';
}

/**
 * 自动换行
 * @param value
 */
function autoWrapRender(value){
    if(value != undefined){
        return '<div style="max-width: 153px;word-wrap: break-word;word-break:break-all;">'+value+'</div>';
    }else{
        return '';
    }
}

/**
 * 渲染图片通用方法
 * @param value
 * @param row
 * @param index
 */
function commonRenderImg(value, row, index){
    if(value != '' && value != undefined){
        if(_commonCheckImg(value)){
            return '<img data-original="'+imgPath+'/'+value+'" class="lazy" style="width: 60px;height: 40px; cursor: pointer;" />';
        }
    }
    return '';
}


/**
 * 图片渲染方法 没有图片延迟加载
 * @param value
 * @param row
 * @param index
 * @returns {*}
 */
function renderImgNoLazyLoad(value, row, index){
    if(value != '' && value != undefined){
        if(_commonCheckImg(value)){
            return '<img src="'+imgPath+'/'+value+'" style="width: 60px;height: 40px; cursor: pointer;">';
        }
    }
    return '';
}

/**
 * 验证是否为数字
 * @param value
 * @returns {boolean}
 */
function isNumber(value) {
    var patrn = /^(-)?\d+(\.\d+)?$/;
    if (patrn.exec(value) == null || value == "") {
        return false
    } else {
        return true
    }
}
/**
 * Created by leo on 2017/12/22.
 * 系统通用方法
 */


/**
 * 校验标签对象 必填 并且提示信息
 * @param tagObj jquery 对象
 * @param dataObj 如果 验证通过 将 值赋到对应obj
 */
function checkTagAndTips(tagObj,dataObj){
    var _flag=true;
    if(tagObj.val() == ''){
        _flag=false;
        var tipMsg='请完善信息';
        if(tagObj.attr("data-placeholder") != undefined){
            tipMsg=tagObj.attr("data-placeholder");
        }else if(tagObj.attr("placeholder") != undefined){
            tipMsg=tagObj.attr("placeholder");
        }
        _errorTipsFun(tipMsg);
        // tagObj.focus();
    }
    dataObj[tagObj.attr("id")]=tagObj.val();
    return _flag;
}


/**
 * 限制输入两位小数点 正数
 * @param obj
 */
function limitTwoPoint(obj){
    obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数
    if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
        obj.value= parseFloat(obj.value);
    }
}

/**
 * 限制输入数字
 * @param obj
 */
function limitInputNumber(obj){
    obj.value = obj.value.replace(/[^\d]/g,"");  //清除“数字”以外的字符
    if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
        obj.value= parseFloat(obj.value);
    }
}


/**
 * 处理 名称过长
 * @param fileName
 * @param lenFlag 需要保留的长度 默认 10
 * @returns {*}
 */
function _handleLongFileName(fileName, lenFlag){
    if(lenFlag == undefined){
        lenFlag=10;
    }
    if(fileName.length > lenFlag){
        return fileName.substr(fileName.length-lenFlag,fileName.length);
    }
    return fileName;
}


/**
 * 判断是否是图片
 * @param fileName
 */
function _commonCheckImg(fileName){
    if(fileName.indexOf(".jpg") != -1 || fileName.indexOf(".png") != -1 || fileName.indexOf(".gif") != -1){
        return true;
    }
    return false;
}

/**
 * 打开点击阴影层&esc键 不自动关闭的模态框
 */
function manualModal(url,divId){
    $("#"+divId).modal({
        backdrop: 'static', //点击空白处 不关闭
        keyboard: false, //点esc不关闭
        remote: url
    });
}

/**
 * 跳转到列表页
 * @param url
 */
function returnToListPage(url){
    $("#baseContainer").load(url, initHtml);
}


function popTips() {
    wrapperConfirmFun(null,'V1.0订单数据不支持该操作!');
}

function getCurrentDateDetail(){
    var d=new Date();
    return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
}
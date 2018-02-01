/**
 * 商品列表
 * */
$(document).ready(function(){
    $(".goods-select2").select2({

    });


    $("#startDate").datetimepicker({
        format: "yyyy-mm-dd hh:ii:ss",
        autoclose: true,
        language: 'zh-CN',//显示中文
        todayBtn: true,
        minuteStep: 10
    }).on('change',function(ev){
        var startDate = $('#startDate').val();
        $("#endDate").datetimepicker('setStartDate',startDate);
        $("#startDate").datetimepicker('hide');
    });
    $("#endDate").datetimepicker({
        format: "yyyy-mm-dd hh:ii:ss",
        autoclose: true,
        language: 'zh-CN',//显示中文
        todayBtn: true,
        minuteStep: 10
    }).on('change',function(ev){
        var endDate = $("#endDate").val();
        $("#startDate").datetimepicker('setEndDate',endDate);
        $("#endDate").datetimepicker('hide');
    });
});

function saveData(){
    $.ajax({
        type : 'POST',
        url : rootPath+'/credit/goods/saveData',
        data : $('form').serializeArray(),
        cache : false,
        dataType : 'json',
        success : function(resObj){
            if(resObj.returnValue == 0){
                //保存成功 跳转至 列表页
                _successTipsFun("商品保存成功");
                setTimeout(function(){
                    returnToListPage(rootPath+'/credit/goods/list');
                },1500);
            }else{
                btnSwitchStatus('able');
                _errorTipsFun(resObj.data);//提示错误信息
            }
        },
        error : function(response){
            btnSwitchStatus('able');
            console.log(response);
        }
    });
    return false;
}

/**
 * 保存按钮切换启用禁用
 */
function btnSwitchStatus(type){
    if(type == 'disable'){
        $(".btn-group a:eq(0)").unbind("click").text("保存中...");
    }else if(type == 'able'){
        $(".btn-group a:eq(0)").bind("click",function(){
            finishBtnClick();
        }).text("保存");
    }
}
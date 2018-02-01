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
 * 商品类型 渲染
 * @param value
 * @param row
 * @param index
 */
function renderGoodsType(value, row, index){
    if(value == 1){
        return '实物';
    }else{
        return '虚拟';
    }
}

var statusObj={0:'待审核',1:'审核中',2:'审核通过',3:'已上架',4:'审核未通过'};
function renderGoodsStatus(value, row, index){
    return statusObj[value];
}


function operateRender(value, row, index){
    var action='';
    action+='<a data-toggle="modal" data-target="#base_modal" href="'+rootPath+'/credit/goods/edit?id='+row.id+'"  class="view-link">编辑</a>';
    if(row.status != 1) {
        action += ' <a href="javascript:void(0);" onclick="ajaxDel(\'' + rootPath + '/credit/goods/delete?id=' + row.id + '\')" class="view-link">删除</a>';
    }
    return _commonDropDownLink(action);
}
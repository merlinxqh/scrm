/**
 * Created by leo on 2017/6/14.
 */
$(document).ready(function(){
    //品牌选择
    $(".select2").select2({

    });
});

function operateFormatter(value, row, index) {
    var  action='';
    action+='<a data-toggle="modal" data-target="#base_modal" href="'+rootPath+'/basicData/editData?id='+row.id+'"  class="view-link">编辑</a>';
    if(row.status == 1){
        action+=' <a href="javascript:void(0);" onclick="ajaxDel(\''+rootPath+'/basicData/modifyData?id='+row.id+'&modifyType=disable&callbackMethod=refreshRightTable\')" class="view-link">禁用</a>';
    }else{
        action+=' <a href="javascript:void(0);" onclick="ajaxDel(\''+rootPath+'/basicData/modifyData?id='+row.id+'&modifyType=enable&callbackMethod=refreshRightTable\')" class="view-link">启用</a>';
    }
    return action;
}

function renderStatusFun(value, row, index){
    if(value == 1){
        return '有效';
    }
    return '无效';
}

function refreshRightTable(){
    refreshTable();
}
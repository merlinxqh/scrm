/**
 * 用户管理
 */

$(document).ready(function(){
    $(".select2").select2({

    });
    $("#startDate").datetimepicker({
        format: "yyyy-mm-dd hh:ii:ss",
        autoclose: true,
        language: 'zh-CN',//显示中文
        todayBtn: true,
        minuteStep: 10
    })
});

function operateRender(value, row, index){
    var action='';
    if(null != row.defaultRole && row.defaultRole.roleType == 'SUPER_ADMIN'){
        return action;
    }
    action+='<a data-toggle="modal" data-target="#base_modal" href="'+rootPath+'/pms/user/edit?id='+row.id+'"  class="view-link">编辑</a>';
    if(row.status == 1){
        action+=' <a href="javascript:void(0);" onclick="ajaxDel(\''+rootPath+'/pms/user/modifyData?id='+row.id+'&status=2\')" class="view-link">禁用</a>';
    }else{
        action+=' <a href="javascript:void(0);" onclick="ajaxDel(\''+rootPath+'/pms/user/modifyData?id='+row.id+'&status=1\')" class="view-link">启用</a>';
    }
    return _commonDropDownLink(action);
}


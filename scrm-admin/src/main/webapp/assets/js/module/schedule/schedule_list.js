/**
 * Created by leo on 2017/6/9.
 */


function operateRender(value, row, index){
    var action='';
    if(row.status == 2){
        action+='<a href="javascript:void(0);" onclick="ajaxDel(\''+rootPath+'/schedule/modifyData?id='+row.id+'&modifyType=enable\')" class="view-link">启用</a>';
        action+=' <a data-toggle="modal" data-target="#base_modal" href="'+rootPath+'/schedule/edit?id='+row.id+'"  class="view-link">编辑</a>';
        action+=' <a href="javascript:void(0);" onclick="ajaxDel(\''+rootPath+'/schedule/modifyData?id='+row.id+'&modifyType=delete\')" class="view-link">删除</a>';
    }else{
        action+='<a href="javascript:void(0);" onclick="ajaxDel(\''+rootPath+'/schedule/modifyData?id='+row.id+'&modifyType=disable\')" class="view-link">禁用</a>';
        action+=' <a href="javascript:void(0);" onclick="ajaxDel(\''+rootPath+'/schedule/modifyData?id='+row.id+'&modifyType=execute\')" class="view-link">立即执行</a>';
    }
    return _commonDropDownLink(action);
}
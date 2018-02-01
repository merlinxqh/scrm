/**
 * 基础数据分类 树
 * @returns {___anonymous171_173}
 */

$(document).ready(function(){
    //品牌选择
    $(".select2").select2({

    });
    //设置 左边树 高度
    //加载树
    var _dfdObj=ajaxTreeData();
    _dfdObj.then(function(){
        var $disabledTree = $('#basic_type_tree').treeview({
            data: _dfdObj.treeData,
            levels:2,
            clickHandler: true, //行点击事件
            onNodeSelected : function(event, node) {
                $("#searchTypeCode").val(node.code);
                refreshRightTable();
            }
        });
        $(".list-group li:eq(0)").click();
    });

});
//load tree json data
function ajaxTreeData(){
    var dfd=$.Deferred();
    $.post(rootPath+'/basicData/typeTreeData',{},function(res){
        dfd.treeData=renderTreeData(res);
        dfd.resolve();
    },'json');

    return dfd;
}


//执行完  更新删除 新增 树节点 操作 后 执行
function ajaxTreeDone(){
    //关闭mode
    wrapperHideModal('base_modal');
    //加载树
    var _dfdObj=ajaxTreeData();
    _dfdObj.then(function(){
        var $disabledTree = $('#basic_type_tree').treeview({
            data: _dfdObj.treeData,
            levels: 2,
            clickHandler: true, //行点击事件
            onNodeSelected : function(event, node) {
                $("#searchTypeCode").val(node.code);
                refreshRightTable();
            }
        });
        $(".list-group li:eq(0)").click();
    });
}



//treedata添加操作项
function renderTreeData(_treeData){
    $.each(_treeData,function(){
        var _htmlObj=$("#operateDivClone").clone();
        _htmlObj.removeAttr("id");
        _htmlObj.show();
        renderOperate(this,_htmlObj);
        if(this.hasOwnProperty('nodes')){//有下级 递归 渲染
            var _childList=this.nodes;
            renderTreeData(_childList);
        }
        this.href=rootPath+"/category/listPage";
    });
    return _treeData;
}

//增加操作按钮
function renderOperate(json,htmlObj){
    var _addbtn=htmlObj.find("button:eq(0)");
    var _rootpath=_addbtn.attr("href");
    _addbtn.attr("href",_rootpath+"/basicData/addType?parentCode="+json.code+"&callbackMethod=ajaxTreeDone");
    var _editbtn=htmlObj.find("button:eq(1)");
    _editbtn.attr("href",_rootpath+"/basicData/editType?id="+json.id+"&callbackMethod=ajaxTreeDone");
    if(json.text == 'HOME'){//HOME节点 是虚拟节点 不能 编辑
        _editbtn.remove();
    }

    if(json.hasOwnProperty('nodes')){//有下级节点

    }

    json.actionHtml=htmlObj.prop("outerHTML");
}


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
    //刷新数据
    var _table=$("#basicDataTable");
    var _form=$("#paramForm");
    var _param = _form.serialize();
    var baseUrl = _table.attr("data-url");
    if(baseUrl.indexOf("?")==-1){
        baseUrl += "?" + _param;
    }else{
        baseUrl += "&"+_param;
    }
    var urlJson ={};
    urlJson.url=baseUrl;
    if(_table.parents(".bootstrap-table").length > 0){
        _table.bootstrapTable('refresh', urlJson);
    }else{
        _table.bootstrapTable(urlJson);
    }

}


/**
 * 添加权限
 */
function addBasicData(){
    var selectData=$('#basic_type_tree').treeview('getSelected');
    if(selectData.length == 0){
        _warnTipsFun("请选择数据类型");
        return ;
    }
    var _node=selectData[0];
    if(_node.code == ''){
        _warnTipsFun("不能选择根节点");
        return;
    }
    manualModal(rootPath+'/basicData/addData?typeCode='+_node.code,'base_modal');
}
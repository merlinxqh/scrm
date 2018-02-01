/**
 * Created by leo on 2017/11/24.
 * 资源管理
 */
$(document).ready(function(){
    //加载树
    var _dfdObj=ajaxTreeData();
    _dfdObj.then(function(){
        $('#resource_tree').treeview({
            data: _dfdObj.treeData,
            levels: 2,
            clickHandler: true, //行点击事件
            onNodeSelected : function(event, node) {
                selectNodeMethod(node);
            }
        });
        initRefreshRight();
    });
});
//load tree json data
function ajaxTreeData(){
    var dfd=$.Deferred();
    $.post(rootPath+'/pms/resource/treeData',{isMenu:1},function(res){
        dfd.treeData=renderTreeData(res);
        dfd.resolve();
    },'json');

    return dfd;
}

function initRefreshRight(){
    $(".list-group li:eq(0)").click();
}


//执行完  更新删除 新增 树节点 操作 后 执行
function ajaxTreeDone(){
    //关闭mode
    wrapperHideModal('base_modal');
    //加载树
    var _dfdObj=ajaxTreeData();
    _dfdObj.then(function(){
        $('#resource_tree').treeview({
            data: _dfdObj.treeData,
            levels: 2,
            clickHandler: true, //行点击事件
            onNodeSelected : function(event, node) {
                selectNodeMethod(node);
            }
        });
        initRefreshRight();
    });
}


function selectNodeMethod(node){
    $("#searchCode").val(node.code);
    refreshRightTable();
    if(node.level == 2){
        //选中最下级菜单
        $("#add_resource_btn").show();
    }else{
        $("#add_resource_btn").hide();
    }
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
    });
    return _treeData;
}

//增加操作按钮
function renderOperate(json,htmlObj){
    var _addbtn=htmlObj.find("button:eq(0)");
    var _rootpath=_addbtn.attr("href");
    // json.code=json.id;//bootstrap-treeview.js 已封装 参数code
    _addbtn.attr("href",_rootpath+"/pms/resource/addChildMenu?code="+json.code+"&callbackMethod=ajaxTreeDone");
    var _editbtn=htmlObj.find("button:eq(1)");
    _editbtn.attr("href",_rootpath+"/pms/resource/edit?id="+json.id+"&callbackMethod=ajaxTreeDone");
    var _delbtn=htmlObj.find("button:eq(2)");
    _delbtn.attr("href",_rootpath+"/pms/resource/deleteData?id="+json.id+"&callbackMethod=ajaxTreeDone");
    if(json.text == 'HOME'){//HOME节点 是虚拟节点 不能 编辑
        _editbtn.remove();
        _delbtn.remove();
    }
    if(json.level == '2'){
        _addbtn.remove();
    }
    if(json.hasOwnProperty('nodes')){//有下级节点
        //有 添加 修改操作
        _delbtn.remove();//移除  删除操作
    }
    json.actionHtml=htmlObj.prop("outerHTML");
}

/**
 * 刷新 右边表格数据
 */
function refreshRightTable(){
    //刷新数据
    var _table=$("#resourceTableId");
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
function addResource(){
    var selectData=$('#resource_tree').treeview('getSelected');
    if(selectData.length == 0){
        _warnTipsFun("请选择菜单节点");
        return ;
    }
    var _node=selectData[0];
    if(_node.level != 2){
        _warnTipsFun("请选择菜单节点");
        return ;
    }
    manualModal(rootPath+'/pms/resource/addResource?code='+$("#searchCode").val(),'base_modal');
}

function operateRender(value, row, index){
    var action= '<a data-toggle="modal" data-target="#base_modal" href="'+rootPath+'/pms/resource/edit?id='+row.id+'"  class="view-link">编辑</a>' +
        ' <a href="javascript:void(0);" onclick="ajaxDel(\''+rootPath+'/pms/resource/modifyData?modifyType=delete&id='+row.id+'\')" class="view-link">删除</a>';
    return _commonDropDownLink(action);
}
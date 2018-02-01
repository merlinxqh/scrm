/**
 * Created by leo on 2017/12/6.
 * 角色权限设置
 */
var checkableNodes;//
$(document).ready(function(){
    //加载树
    var _dfdObj=ajaxTreeData();
    _dfdObj.then(function(){
        $checkableTree = $('#checkable_tree').treeview({
            data: _dfdObj.treeData,
            levels:4,
            showCheckbox: true,
            onNodeChecked : function(event, node) {
                nativeCheckedNode(node);
            },onNodeUnchecked : function(event, node) {
                nativeUnCheckNode(node);
            }
        });
    });
    $('#input-check-node').keydown(function(e) {
        if (e.keyCode == 13) {
            var checkableNodes = findCheckableNodes();
            checkableNodes.then(function(){
                scrollToSearchResult('checkable_tree');
            });
        }
    });
});

/**
 * 本地选中节点方法
 * @param node
 */
function nativeCheckedNode(node){
    if($("#containChild").get(0).checked){
        if(node.id != 0){
            //选中 节点 自动 包含下级节点
            _checkedNodeFunWithChild(node);
        }
    }else{
        _checkedNodeFun(node);
    }
}

/**
 * 本地取消选中节点方法
 * @param node
 */
function nativeUnCheckNode(node){
    _unCheckNodeFun(node);
}

/**
 * 数据保存
 */
function submitData(){
    $.ajax({
        type : 'POST',
        url : rootPath+'/pms/role/roleResourceSave',
        data : {
            roleCode:$("#refRoleCode").val(),
            resourceCodes:_getCheckedIds()
        },
        cache : false,
        success : modalAjaxDone,
        error : ajaxError,
        dataType : 'json'
    });
}

/**
 * 获取 选中节点数据 提交
 */
function _getCheckedIds(){
    var _resIds="";
    var _checkeds=$checkableTree.treeview("getChecked");
    $.each(_checkeds, function(){
        if(this.parentId != undefined){//过滤掉 最高节点
            _resIds+=this.code+",";
        }
    });
    return _resIds;
}



//load tree json data
function ajaxTreeData(){
    var dfd=$.Deferred();
    $.post(rootPath+'/pms/resource/treeData',{refRoleCode:$("#refRoleCode").val()},function(res){
        dfd.treeData=renderTreeData(res);
        dfd.resolve();
    },'json');

    return dfd;
}
//对tree数据 去除行点击 跳转href
function renderTreeData(_treeData){
    $.each(_treeData,function(){
        if(this.hasOwnProperty('nodes')){//有下级 递归 渲染
            var _childList=this.nodes;
            renderTreeData(_childList);
        }
    });
    return _treeData;
}

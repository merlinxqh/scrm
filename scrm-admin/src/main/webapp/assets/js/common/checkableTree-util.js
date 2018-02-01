/**
 * bootstrap treeview
 * 使用包含复选框 的 树 一些 可用的方法 
 *  实现 
 *  1. 选中下级节点 ,自动选中 上级节点
 *  2. 取消选中节点,自动 取消选中下级节点
 * */
var $checkableTree;//资源树 对象
/*
 * 例如:
 * 		  $checkableTree = $('#checkable_tree').treeview({
            data: treeData,
            levels:4,
            showCheckbox: true,
			onNodeChecked : function(event, node) {
				_checkedNodeFun(node);
			},onNodeUnchecked : function(event, node) {
				_unCheckNodeFun(node);
			}
          });
 */

//选中节点
function _checkedNodeFun(node){
	//选中下级 把上级节点选中
	var _parray=[];
	getAllParents(node, _parray);
	$.each(_parray, function(){
		if(!this.state.checked){//上级没有选中 
			//执行选中操作
			checkNodeLocalFun(this.nodeId);
		}
	});
}


/**
 * 选中节点 将下级节点全部选中
 * @param node
 */
function _checkedNodeFunWithChild(node){
	//选中下级 把上级节点选中
	var _parray=[];
	getAllParents(node, _parray);
	$.each(_parray, function(){
		if(this.state != undefined && !this.state.checked){//上级没有选中 
			//执行选中操作
			checkNodeLocalFun(this.nodeId);
		}
	});
	var _childArry=[];
	getAllChilds(node, _childArry);
	$.each(_childArry, function(){
			//选中
			checkNodeLocalFun(this.nodeId);
	});
}


//不选中节点
function _unCheckNodeFun(node){
	var _carray=[];
	getAllChilds(node, _carray);
	$.each(_carray, function(){
		if(this.state.checked){//下级 有选中的节点
			//去除选中操作
			uncheckNodeLocalFun(this.nodeId);
		}
	});
}

/**
 * 获取所有子节点
 * @param parent
 * @param array
 */
function getAllChilds(parent, array){
	if(parent.nodes != undefined){
		var _childs=parent.nodes;
		$.each(_childs, function(){
	        array.push(this);
	        if(this.nodes != undefined && this.nodes.length > 0 ){
	        	getAllChilds(this, array);
	        }
		});
	}
}

/**
 * 获取所有父节点
 * @param child
 * @param array
 */
function getAllParents(child, array){
	var _node=getNodeLocalFun(child.parentId);
	array.push(_node);
	if(_node.parentId != undefined){
		getAllParents(_node, array);
	}
}

/**
 * 根据节点id获取node对象
 * @param nodeId
 * @returns
 */
function getNodeLocalFun(nodeId){
	return $checkableTree.treeview("getNode",nodeId);
}

/**
 * 本地选中某一节点方法
 * @param node
 */
function checkNodeLocalFun(nodeId){
	$checkableTree.treeview('checkNode', [ nodeId, { silent: true } ])
}

/**
 * 本地去除选中某一节点方法
 * @param node
 */
function uncheckNodeLocalFun(nodeId){
	$checkableTree.treeview('uncheckNode', [ nodeId, { silent: true } ])
}


var findCheckableNodes = function() {
    var dfd=$.Deferred();
    $checkableTree.treeview('search', [
        $('#input-check-node').val().trim(), {
            ignoreCase : false,
            exactMatch : false
        }]);
    if($(".search-result").length == 0){
        _infoTipsFun("查询不到\""+$('#input-check-node').val()+"\"相关的信息!");
    }
    dfd.resolve();
    return dfd;
};


/**
 * 树结构 search滚动条定位方法
 * @param divId 树结构div ID
 */
function scrollToSearchResult(divId){
    if($(".search-result").length>0){
        var _first=$(".search-result:eq(0)");
        $("#"+divId).animate({ scrollTop: $("#"+divId).scrollTop() + _first.offset().top - $("#"+divId).offset().top }, 1000); //有动画效果
        // $("#checkable_tree").scrollTop($("#checkable_tree").scrollTop() + _first.offset().top - $("#checkable_tree").offset().top);
    }
}

/**
 * Created by leo on 2018/1/11.
 */

$(document).ready(function(){
    $(".select2").select2({

    });
    $("#scheduleType").on("change",function(){
        scheduleTypeChangeFun();
    });

    scheduleTypeChangeFun();
});

function scheduleTypeChangeFun(){
    var _scheduleType= $("#scheduleType").val();
    if(_scheduleType == 'RPC'){
        $("#rpc_invoke_div").show();
        $("#http_invoke_div").hide();
        modifyDomObjRequired($("#rpc_invoke_div"),'add');
        modifyDomObjRequired($("#http_invoke_div"),'remove');
    }else{
        $("#rpc_invoke_div").hide();
        $("#http_invoke_div").show();
        modifyDomObjRequired($("#http_invoke_div"),'add');
        modifyDomObjRequired($("#rpc_invoke_div"),'remove');
    }
}

/**
 * 添加/ 去除required
 * @param dom
 * @param type
 */
function modifyDomObjRequired(dom,type){
   if(type == 'remove'){
       dom.find("input[type='text']").removeAttr("required");
   }else{
       dom.find("input[type='text']").attr("required","");
   }
}

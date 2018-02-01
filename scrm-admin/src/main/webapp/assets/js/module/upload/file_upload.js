$(document).ready(function(){
	/**
	 * 获取文件上传参数
	 */
	var _allowedFile=[];//默认上传文件格式
	if($("#fileFormat").val() != ''){//自定义上传文件格式","逗号隔开
		_allowedFile=$("#fileFormat").val().split(",");
	}else{
		_allowedFile=['jpg', 'png','gif','apk', 'jpeg'];
	}
	var _maxFileSize=10000; //10M
	if(!isNaN($("#fileMaxSize").val()) && $("#fileMaxSize").val() != ''){//自定义上传文件大小 上限
		_maxFileSize=parseInt($("#fileMaxSize").val());
	}

    /**
     * 自定义上传文件路径
     * @type {*}
     * @private
     */
	var _uploadUrl='/file/upload';
	if($("#uploadUrl").val() != ''){
        _uploadUrl=$("#uploadUrl").val();
    }

    $("#file-1").fileinput({
        uploadUrl: rootPath+_uploadUrl, // you must set a valid URL here else you will get an error
        allowedFileExtensions : _allowedFile,
        overwriteInitial: false,
        maxFileSize: _maxFileSize,
        fileActionSettings: {showZoom: false},//不显示 预览按钮 因 用modal有问题
        maxFileCount: parseInt($("#maxNum").val()),
        slugCallback: function(filename) {
            return filename.replace('(', '_').replace(']', '_');
        },
        preferIconicPreview: true,//自定义上传类型图标
        previewFileIconSettings: {
            'doc': '<i class="fa fa-file-word-o text-primary"></i>',
            'xls': '<i class="fa fa-file-excel-o text-success"></i>',
            'ppt': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
            'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',
            'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
            'htm': '<i class="fa fa-file-code-o text-info"></i>',
            'txt': '<i class="fa fa-file-text-o text-info"></i>',
            'mov': '<i class="fa fa-file-movie-o text-warning"></i>',
            'mp3': '<i class="fa fa-file-audio-o text-warning"></i>',
            'apk': '<i class="fa fa-file-archive-o text-muted"></i>',
        },
        previewFileExtSettings: {
            'doc': function(ext) {
                return ext.match(/(doc|docx)$/i);
            },
            'xls': function(ext) {
                return ext.match(/(xls|xlsx)$/i);
            },
            'ppt': function(ext) {
                return ext.match(/(ppt|pptx)$/i);
            },
            'zip': function(ext) {
                return ext.match(/(zip|rar|tar|gzip|gz|7z)$/i);
            },
            'htm': function(ext) {
                return ext.match(/(htm|html)$/i);
            },
            'txt': function(ext) {
                return ext.match(/(txt|ini|csv|java|php|js|css)$/i);
            },
            'mov': function(ext) {
                return ext.match(/(avi|mpg|mkv|mov|mp4|3gp|webm|wmv)$/i);
            },
            'mp3': function(ext) {
                return ext.match(/(mp3|wav)$/i);
            },
            'apk': function(ext) {
                return ext.match(/(apk)$/i);
            }
        }
    });

    /**
     * 文件上传成功事件
     * $.Event
     */
    $("#file-1").on("fileuploaded", function (event, data, previewId, index){
        //上传成功 将结果返回到对应 图片预览框
        var filename=data.response.data.filename;
        $("#"+previewId).append('<input type="hidden" name="filePath" id="filePath" value="'+filename+'" />');
        var _uploadSuccessMethod=$("#uploadSuccessMethod").val();
        if(_uploadSuccessMethod != ''){
            eval(_uploadSuccessMethod+'(filename)');
        }
    });
	
});


/**
 * 点击确定 拼装已上传图片 filepath+url
 */
function makeSureUpload(){
	var filepath=[];
	$(".file-preview-success").each(function(){
		filepath.push({
			filepath:$(this).find("#filePath").val(),
			filename:$(this).find(".file-footer-caption").attr("title")
		});
	});
	if(filepath.length == 0){
		_errorTipsFun("你还没有上传任何文件");
		return false;
	}
	var callBackParam=$("#callBackParam").val();
	/**
	 * 调用回调函数
	 */
	var _callBackMethod=$("#callBackMethod").val();
	if(callBackParam != ''){
        eval(_callBackMethod+"(filepath,callBackParam)");
    }else{
        eval(_callBackMethod+"(filepath)");
    }
}


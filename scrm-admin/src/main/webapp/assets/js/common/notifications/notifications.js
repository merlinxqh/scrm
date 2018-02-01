
/**
* Theme: Moltran Admin Template
* Author: Coderthemes
* Notification
*/

!function($) {
    "use strict";

    var Notification = function() {};

    //simple notificaiton
    Notification.prototype.notify = function(style,position, title, text) {
        var icon = 'fa fa-adjust';
        if(style == "error"){
            icon = "fa fa-exclamation";
        }else if(style == "warning"){
            icon = "fa fa-warning";
        }else if(style == "success"){
            icon = "fa fa-check";
        }else if(style == "info"){
            icon = "fa fa-question";
        }else{
            icon = "fa fa-adjust";
        }
        $.notify({
            title: title,
            text: text,
            image: "<i class='"+icon+"'></i>"
        }, {
            style: 'metro',
            className: style,
            globalPosition:position,
            showAnimation: "show",
            showDuration: 0,
            hideDuration: 0,
            autoHide: false,
            clickToHide: true
        });
    },

        //confirmation notification
        Notification.prototype.poptips = function(style,position, title,okcall,url) {
            $('#notifications').modal({
                keyboard: false,
                backdrop:'static'
            });

            var icon = "fa fa-adjust";
            if(style == "error"){
                icon = "fa fa-exclamation";
            }else if(style == "warning"){
                icon = "fa fa-warning";
            }else if(style == "success"){
                icon = "fa fa-check";
            }else if(style == "info"){
                icon = "fa fa-question";
            }else{
                icon = "fa fa-adjust";
            }
            $.notify({
                title: title,
                text: '<div class="clearfix"></div><br><a class="btn btn-sm btn-danger no">Yes</a>',
                image: "<i class='"+icon+"'></i>"
            }, {
                style: 'metro',
                className: style,
                globalPosition:position,
                showAnimation: "show",
                showDuration: 0,
                hideDuration: 0,
                autoHide: false,
                clickToHide: false
            });
            //listen for click events from this style

            $(".notifyjs-metro-base .yes").unbind().bind("click",function(){
                okcall(url);
                $(this).trigger('notify-hide');
                $('#notifications').modal('hide');
            });
            $(document).on('click', '.notifyjs-metro-base .no', function() {
                //programmatically trigger propogating hide event
                $(this).trigger('notify-hide');
                $('#notifications').modal('hide');
            });
            // $(document).on('click', '.notifyjs-metro-base .yes', function() {
            //show button text
            //	okcall(url);
            //hide notification
            //  $(this).trigger('notify-hide');
            // });
        },

    //auto hide notification
    Notification.prototype.autoHideNotify = function (style,position, title, text, delayTime) {
        var icon = "fa fa-adjust";
        if(style == "error"){
            icon = "fa fa-exclamation";
        }else if(style == "warning"){
            icon = "fa fa-warning";
        }else if(style == "success"){
            icon = "fa fa-check";
        }else if(style == "info"){
            icon = "fa fa-question";
        }else{
            icon = "fa fa-adjust";
        }
        if(delayTime == undefined){
            delayTime=1500;
        }
        $.notify({
            title: title,
            text: text,
            image: "<i class='"+icon+"'></i>"
        }, {
            style: 'metro',
            className: style,
            globalPosition:position,
            showAnimation: "show",
            showDuration: 0,
            hideDuration: 0,
            autoHideDelay: delayTime,
            autoHide: true,
            clickToHide: true
        });
    },
    //confirmation notification
    Notification.prototype.confirm = function(style,position, title,okcall,url) {
    	$('#notifications').modal({
    		  keyboard: false,
    		  backdrop:'static'
    	});

        var icon = "fa fa-adjust";
        if(style == "error"){
            icon = "fa fa-exclamation";
        }else if(style == "warning"){
            icon = "fa fa-warning";
        }else if(style == "success"){
            icon = "fa fa-check";
        }else if(style == "info"){
            icon = "fa fa-question";
        }else{
            icon = "fa fa-adjust";
        }   
        $.notify({
            title: title,
            text: '确定要执行该操作吗?<div class="clearfix"></div><br><a class="btn btn-sm btn-default yes">Yes</a> <a class="btn btn-sm btn-danger no">No</a>',
            image: "<i class='"+icon+"'></i>"
        }, {
            style: 'metro',
            className: style,
            globalPosition:position,
            showAnimation: "show",
            showDuration: 0,
            hideDuration: 0,
            autoHide: false,
            clickToHide: false
        });
        //listen for click events from this style
        
    	$(".notifyjs-metro-base .yes").unbind().bind("click",function(){
    		okcall(url);
    		$(this).trigger('notify-hide');
    		$('#notifications').modal('hide');
        });
        $(document).on('click', '.notifyjs-metro-base .no', function() {
          //programmatically trigger propogating hide event
          $(this).trigger('notify-hide');
          $('#notifications').modal('hide');
        });
       // $(document).on('click', '.notifyjs-metro-base .yes', function() {
          //show button text
        //	okcall(url);
          //hide notification
        //  $(this).trigger('notify-hide');
       // });
    },
    //init - examples
    Notification.prototype.init = function() {

    },
    //init
    $.Notification = new Notification, $.Notification.Constructor = Notification
}(window.jQuery),

//initializing 
function($) {
    "use strict";
    $.Notification.init()
}(window.jQuery);

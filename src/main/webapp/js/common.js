/**
 * Created by Administrator on 2015/10/8 0008.
 */

/*-----------------扩展在jquery下的工具函数---------------------*/
$.extend({
    /*动态生成确认框的方法*/
    confirmBox: function (option) {
        var setting = $.extend({
            id: "confirmBox",
            title: "确认框",
            confirm: "您确定执行该操作吗？"
        }, option || {});
        return $(' <div class="tool_cover confirmBox_cover">\
			           <article id=' + setting.id + ' class="tool_confirmBox">\
			               <header>\
			                   <h2>' + setting.title + '</h2>\
			                   <i class="icon-remove confirmBox_closeBtn"></i>\
			               </header>\
			               <section>\
			                   <i class="icon-question-sign tool_icon_Remind"></i>\
			                   <p>' + setting.confirm + '</p>\
		                   </section>\
		                   <footer>\
		                       <a class="tool_btn tool_btn_Blue confirmBox_submitBtn">确定</a>\
			                   <a class="tool_btn confirmBox_cancelBtn">取消</a>\
			               </footer>\
			           </article>\
			      </div>').appendTo($('body'));
    },

    /*动态生成提醒框的方法*/
    remindBox: function (option) {
        var setting = $.extend({
            id: "confirmBox",
            title: "提醒框",
            remind: "您的操作出现错误！"
        }, option || {});
        return $(' <div class="tool_cover remindBox_cover">\
			           <article id=' + setting.id + ' class="tool_remindBox">\
			               <header>\
			                   <h2>' + setting.title + '</h2>\
			                   <i class="icon-remove remindBox_closeBtn"></i>\
			               </header>\
			               <section>\
			                   <i class="icon-info-sign tool_icon_Remind"></i>\
			                   <p>' + setting.remind + '</p>\
		                   </section>\
		                   <footer>\
		                       <a class="tool_btn tool_btn_Blue remindBox_submitBtn">确定</a>\
			               </footer>\
			           </article>\
			      </div>').appendTo($('body'));
    },

    /*动态生成提醒框的方法*/
    tip: function (option) {
        var setting = $.extend({
            content: "提醒内容"
        }, option || {});
        return $('<div class="tool_tip">\
			           <span class="tool_tip_content">' + setting.content + '</span>\
			      </div>').appendTo($('body'));
    }
});


var doc = {}; //全局的命名空间
/*--------------------------------项目中的工具函数------------------------------*/
doc.tool = {
    /*dataTables 通过ajax后台取数据的方法*/
    retrieveData: function (sSource, aoData, fnCallback) {
        var data = doc.tool.formatArgument(aoData);
        console.log(JSON.stringify(data, null, 4));
        $.ajax({
            "type": "post",
            "url": sSource,
            "dataType": "json",
            //"data": {"paramsData":JSON.stringify(data,null,4)}, //以json格式传递
            "data": data, //以json格式传递
            "success": fnCallback,
        });
    },

    /*格式化dataTables传到后台的参数*/
    formatArgument: function (aoData) {
        var i = 0,
            len = aoData.length,
            obj = null,
            start = 0,
            sortCol = 0,
            mData = [],
        //oSearch = {},
            oData = {};
        for (; i < len; i++) {
            obj = aoData[i];
            switch (obj.name) {
                case 'iDisplayStart':
                    start = obj.value;
                    break;
                case 'iDisplayLength':
                    oData.pageSize = obj.value >= 1 ? obj.value : '';
                    oData.page = oData.pageSize != '' ? (start / oData.pageSize + 1) : '';
                    break;
                case 'sSortDir_0':
                    oData.sortDir = obj.value;
                    break;
                case 'iSortCol_0':
                    sortCol = obj.value;
                    break;
                default :
                    format(obj.name, obj);
            }
        }
        function format(name, obj) {
            if (/mDataProp_/.test(name)) {
                mData.push(obj.value);
            } else if (/search_/.test(name)) {
                name = name.substring(7);
                //oSearch[name] = obj.value;
                oData[name] = obj.value;
            }
        }

        oData.sortCol = mData[sortCol];
        //oData.oSearch = oSearch;
        return oData;
    },

    /*通过ajax获取JSON数据的方法*/
    getJSON: function (option) {
        var setting = $.extend({
            async: true,
            url: "#",
            data: null,
            type: "post",
            loading: false,
            success: function (data) {
            },
            error: {}
        }, option || {});
        if (setting.loading) {
            $('.com-loadCover').show();
        }
        console.log("传到后台的参数=" + JSON.stringify(setting.data, null, 4));
        $.ajax({
            type: "post",
            url: setting.url,
            async: setting.async,
            data: setting.data,
            dataType: "json",
            success: function (data) {
                console.log("后台返回的参数=" + JSON.stringify(data, null, 4));
                if (setting.loading) {
                    $('.com-loadCover').hide();
                }
                if (data.success) {
                    setting.success(data);
                } else {
                    setting.error.remind = data.msg;
                    $.remindBox(setting.error);
                }
            }
        });
    },

    getOptionsOfSelect: function (data) {
        var arr = [];
        $.each(data, function (i, value) {
            arr[i] = '<option value="' + data[i].id + '">' + data[i].name + '</option>';
        });
        return arr;
    },

    /*反序列化,将数据填入表单中*/
    deserialize: function (seletor, data) {
        $(seletor + ' [name]').each(function () {
            //console.log(this.name);
            $(this).val(data[this.name]);
        });
    },

    /*序列化函数*/
    serialize: function (form, data) {
        var obj = data || {};
        $(form).find('[name]').each(function () {
            obj[this.name] = $(this).val();
        });
        return obj;
    },

    //获取url中的参数
    search: function (str) {
        var obj = {}
        var arr = str.slice(1).split('&');
        for (var i = 0, len = arr.length; i < len; i++) {
            var arr1 = arr[i].split("=");
            obj[arr1[0]] = arr1[1];
        }
        return obj;
    },

    //格式化时间
    formatTime: function (date) {
        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
    },

    //form 表单提交后返回的信息
    success: function (str, callbackForSuccess, callbackForFail) {
        if (str != "") {
            var obj = doc.tool.search(str);
            if (obj.success) {
                callbackForSuccess();
            } else {
                callbackForFail(obj.msg);
            }
        }
    }
}

/*--------------------------------项目中的公共运用函数------------------------------*/
doc.app = {} //公共的实现方法
/*主导航的事件绑定函数*/
//doc.app.nav = function(){
//	$('.nav dt').on('click',function(){
//		if(!$(this).siblings('dd').hasClass('dd-active')){
//			$(this).siblings('dd').toggle();
//		}
//	});
//	$('.nav dl').delegate('dd','click',function(){
//		$('.nav dt').removeClass('dt-active');
//		$('.nav dd').removeClass('dd-active');
//		$(this).addClass('dd-active').siblings('dt').addClass('dt-active');
//	});
//	$('.face').on('click',function(){
//		$.remindBox({
//			remind:'该系统暂无提供登录用户功能，可后期添上，如有登录功能，则登录者信息将保留在这头像中!'
//		});
//	});
//}

/*为确认框绑定事件*/
doc.app.confirmBoxEvent = function () {
    /*为确认框绑定关闭事件*/
    $(document).delegate('.confirmBox_cancelBtn, .confirmBox_closeBtn', 'click', function () {
        switch ($('.tool_confirmBox').attr('id')) {
            //case 'dep-delete-confirmBox':
            //	$('.icon-trash').removeClass('com-active-icon');
            //	break;
            //case 'job-delete-confirmBox':
            //	$('.icon-trash').removeClass('com-active-icon');
            //	break;
        }
        $('.confirmBox_cover').remove();
    });

    /*为确认框绑定“确认”事件*/
    $(document).delegate('.confirmBox_submitBtn', 'click', function () {
        var confirmBox = $('.tool_confirmBox'),
            id = confirmBox.attr('id');
        switch (id) {
            case 'info_deleteProject':
                doc.info.deleteProject($('.confirmBox_cover').data('projectId'));
                break;
            case 'info_newProject':
                window.location.href = "#";
                //doc.job.delete($('.confirmBox-cover').data('jobId'));
                break;
        }
        $('.confirmBox_cover').remove();
    });
}

/*为提醒框绑定事件*/
doc.app.remindBoxEvent = function () {
    /*为提醒框绑定关闭事件*/
    $(document).delegate('.remindBox_submitBtn,.remindBox_closeBtn', 'click', function () {
        var remindBox = $('.tool_remindBox'),
            id = remindBox.attr('id');
        switch (id) {
            case 'dep-deleteFail-remindBox':
                $('.icon-trash').removeClass('com-active-icon');
                break;
            case 'job-deleteFail-remindBox':
                $('.icon-trash').removeClass('com-active-icon');
                break;
        }
        $('.remindBox_cover').remove();
    });
}


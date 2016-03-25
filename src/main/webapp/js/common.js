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
		return $(' <div class="doc_cover confirmBox_cover">\
			           <article id=' + setting.id + ' class="doc_confirmBox">\
			               <header>\
			                   <h2>' + setting.title + '</h2>\
			                   <i class="icon-remove confirmBox_closeBtn"></i>\
			               </header>\
			               <section>\
			                   <i class="icon-question-sign doc_icon_Remind"></i>\
			                   <p>' + setting.confirm + '</p>\
		                   </section>\
		                   <footer>\
		                       <a class="doc_btn doc_btn_Blue confirmBox_submitBtn">确定</a>\
			                   <a class="doc_btn confirmBox_cancelBtn">取消</a>\
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
		return $(' <div class="doc_cover remindBox_cover">\
			           <article id=' + setting.id + ' class="doc_remindBox">\
			               <header>\
			                   <h2>' + setting.title + '</h2>\
			                   <i class="icon-remove remindBox_closeBtn"></i>\
			               </header>\
			               <section>\
			                   <i class="icon-info-sign doc_icon_Remind"></i>\
			                   <p>' + setting.remind + '</p>\
		                   </section>\
		                   <footer>\
		                       <a class="doc_btn doc_btn_Blue remindBox_submitBtn">确定</a>\
			               </footer>\
			           </article>\
			      </div>').appendTo($('body'));
	},
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
			arr[i] = '<option value="' + data[i].id + '">' + data[i].account + '-' + data[i].name + '</option>';
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
			obj[arr1[0]] = decodeURI(arr1[1]);
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
				callbackForSuccess(obj.msg);
			} else {
				callbackForFail(obj.msg);
			}
		}
	}
}

/*--------------------------------项目中的公共运用函数------------------------------*/
doc.app = {} //公共的实现方法

void function () {
	var navLeft = $('#docNav').offset().left;

	$(window).on('resize', function () {
		navLeft = $('#docNav').offset().left;
	});

	$(window).on('scroll', function () {
		var top = $(this).scrollTop();
		if (top >= 104) {
			$('#docNav').css({
				"position": "fixed",
				"top": "40px",
				"left": navLeft + "px"
			});
		} else {
			$('#docNav').css({
				"position": "absolute",
				"top": "54px",
				"left": "-160px"
			});
		}
	});

}();


/*为确认框绑定事件*/
doc.app.confirmBoxEvent = function () {
	/*为确认框绑定关闭事件*/
	$(document).delegate('.confirmBox_cancelBtn, .confirmBox_closeBtn', 'click', function () {
		switch ($('.doc_confirmBox').attr('id')) {
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
		var confirmBox = $('.doc_confirmBox'),
			id = confirmBox.attr('id');
		switch (id) {
			case 'docList_deleteDoc':
				doc.docInfo.deleteDoc($('.confirmBox_cover').data('docId'));
				break;
		}
		$('.confirmBox_cover').remove();
	});
}

/*为提醒框绑定事件*/
doc.app.remindBoxEvent = function () {
	/*为提醒框绑定关闭事件*/
	$(document).delegate('.remindBox_submitBtn,.remindBox_closeBtn', 'click', function () {
		var remindBox = $('.doc_remindBox'),
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



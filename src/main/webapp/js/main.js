/**
 * Created by Administrator on 2016/3/4 0004.
 */
/*------------------------------稿件中心模块-----------------------------*/
doc.docInfo = {} //稿件中心命名空间

/*为项目列表的dataTable绑定事件*/
doc.docInfo.dataTableEvent = function () {

	$('#docList_table_searchBtn').on('click', search);
	$(document).on('keyup', function (ev) {
		if (ev.keyCode == '13') {
			search();
		}
	});

	function search() {
		var fromTime = $('#docList_fromTime').val(),
			toTime = $('#docList_toTime').val();
		if (!fromTime && !toTime || fromTime && toTime) {
			if (toTime < fromTime) {
				$.remindBox({
					remind: "请确保结束时间大于开始时间"
				});
			} else {
				doc.docInfo.dataTable.fnDraw(true);
			}
		} else {
			$.remindBox({
				remind: "不能只填写开始时间或结束时间"
			});
		}
	}

	doc.docInfo.dataTable.delegate('.icon-trash', 'click', function () {
		var id = $(this).data('id');
		$.confirmBox({
			id: "docList_deleteDoc",
			title: "删除稿件",
			confirm: "确定要删除该稿件吗？"
		}).data('docId', id);
	});
}

//删除稿件
doc.docInfo.deleteDoc = function (id) {
	doc.tool.getJSON({
		url: "/deleteDoc/" + id,
		success: function () {
			doc.docInfo.dataTable.fnDraw(true);
		},
		error: {
			remind: "删除稿件出错"
		}
	});
}

doc.docInfo.sendDoc = function () {
	var originData = []
	var selectData = [];
	var docId = '';
	doc.docInfo.dataTable.delegate('.icon-signout', 'click', function () {
		docId = $(this).data('id');
		doc.tool.getJSON({
			url: "/admin/reviewerList",
			success: function (res) {
				$('#docInfo_sendDocBox_cover').show();
				var p = 0;
				while (p < res.data.length) {
					originData.push(res.data[p++]);
				}
				selectData = res.data;
				var optionHtml = getSelectedContent(res.data);
				$('.docInfo_sendDocBox_right').append('<div class="docInfo_sendDocBox_item"> <select class="doc_select docInfo_sendDocBox_item_select">' + optionHtml.join('') + ' </select> <i class="doc_icon icon-plus-sign"></i>');
			},
			error: {
				remind: "获取审稿员数据失败"
			}
		});
	});

	$('.doc_header_closeBtn,.docInfo_sendDocBox_cancelBtn').on('click', function () {
		$('#docInfo_sendDocBox_cover').hide().find('.docInfo_sendDocBox_right').html('');
	});

	$('.docInfo_sendDocBox_submitBtn').on('click', function () {
		var reviewerid = [];
		$('.docInfo_sendDocBox_item_select').each(function (i, value) {
			if ($(this).val()) {
				reviewerid.push($(this).val());
			}
		});
		doc.tool.getJSON({
			url: "/admin/distribute",
			data: {'docid': docId, "reviewerid": reviewerid},
			success: function (res) {
				$('#docInfo_sendDocBox_cover').hide().find('.docInfo_sendDocBox_right').html('');
				doc.docInfo.dataTable.fnDraw(true);
				$.remindBox({
					remind: "成功派发"
				});
			},
			error: {
				remind: "派发审稿员失败"
			}
		});
	});

	$('.docInfo_sendDocBox_right').delegate('.icon-minus-sign', 'click', function () {
		var value = $(this).siblings('.doc_select').val();
		var optionHtml = addSelectData(value);
		$(this).parent().siblings().find('.doc_select').append(optionHtml).end().end().remove();
	});

	$('.docInfo_sendDocBox_right').delegate('.icon-plus-sign', 'click', function () {
		$(this).removeClass('icon-plus-sign').addClass('icon-minus-sign').siblings('.doc_remind_Red').remove();
		var item = $('<div class="docInfo_sendDocBox_item"></div>').append('<select class="doc_select docInfo_sendDocBox_item_select">' + getSelectedContent(deleteSelectData()).join('') + '</select>');
		item.append(' <i class="doc_icon icon-plus-sign"></i>').appendTo($('.docInfo_sendDocBox_right'));
	});

	function addSelectData(value) {
		var optionHtml = '';
		for (var k = 0, len = originData.length; k < len; k++) {
			if (originData[k].id == value) {
				selectData.push(originData[k]);
				optionHtml = '<option value="' + originData[k].id + '">' + originData[k].account + '-' + originData[k].name + '</option>';
				break;
			}
		}
		return optionHtml;
	}

	function deleteSelectData() {
		var arr = [];
		$('.docInfo_sendDocBox_item_select').each(function (i, elem) {
			var value = $(elem).val();
			if (value) {
				arr.push($(elem).val());
			}
		});
		for (var i = 0; i < selectData.length; i++) {
			for (var j = 0; j < arr.length; j++) {
				if (selectData[i].id == arr[j]) {
					selectData.splice(i, 1);
				}
			}
		}
		return selectData;
	}

	function getSelectedContent(data) {
		var optionHtml = doc.tool.getOptionsOfSelect(data);
		return optionHtml;
	}

	var option = '';
	$('.docInfo_sendDocBox_right').delegate('.doc_select', 'focus', function () {
		if ($(this).val()) {
			option = $(this).find('option:selected').clone();
		}
	});
	$('.docInfo_sendDocBox_right').delegate('.doc_select', 'change', function () {
		var value = $(this).val();
		if (value) {
			$(this).parent().siblings().find('.doc_select').find('option').each(function (i, elem) {
				if (elem.value == value) {
					$(elem).remove();
				}
			}).end().append(option);
			$(this).blur();
		}
	});
};

//发送投稿
doc.docInfo.submitDoc = function () {

	$('#submitDoc_updateBtn').on('change', function () {
		var fileSize = $(this)[0].files[0].size;
		var fileSuffix = $(this).val().split('.').pop();
		if (fileSuffix != "pdf") {
			$(this).get(0).setCustomValidity("文件的格式不符合");
		} else if (fileSize > 100 * 1024 * 1024) {
			$(this).get(0).setCustomValidity("文件的大小超过了100M");
		} else {
			$(this).get(0).setCustomValidity("");
		}
	});

	$('#submitDoc_addAuthorBtn').on('click', function () {
		var index = $('.submitDoc_authorBody').size() + 1;
		$('.submitDoc_authorBody:eq(0)').clone(true).
		find('.submitDoc_authorTable_title').html('<h3>第' + index + '作者</h3><a href="javascript:;">删除</a>').end().find('input').val('').each(function (i, value) {
			value.name = 'authorDomainList[' + (index - 1 ) + '].' + value.name.split('.').pop();
			// console.log(value.name);
		}).end().
		appendTo($('.submitDoc_authorTable'));
	});

	$('.submitDoc_authorTable_title').delegate('a', 'click', function () {
		$(this).parents('.submitDoc_authorBody').remove();
		$('.submitDoc_authorBody').each(function (i) {
			$(this).find('.submitDoc_authorTable_title h3').html('第' + (i + 1) + "作者").end().find('input').each(function (j, value) {
				if (i) {
					value.name = 'authorDomainList[' + i + '].' + value.name.split('.').pop();
				}
			});

		});
	});
};

//修改用户昵称
doc.docInfo.account = function () {
	var newNicknameInput = $('#newNickname'),
		oldNicknameText = $('#oldNickname'),
		btnWrap = $('.docList_account_item_Last').eq(0),
		editNicknameBtn = $('#editNicknameBtn');

	editNicknameBtn.on('click', function () {
		$(this).hide();
		oldNicknameText.hide();
		newNicknameInput.show();
		btnWrap.show();
	});

	$('#cancelBtn').on('click', function () {
		btnWrap.hide();
		newNicknameInput.hide();
		oldNicknameText.show();
		editNicknameBtn.show();
	});

	$('#submitBtn').on('click', function () {
		var nickname = $('#newNickname').val();
		if (!nickname) {
			$('#remind').show();
		} else {
			$('#remind').hide();
			doc.tool.getJSON({
				url: "data.json",
				data: {'nickname': nickname},
				success: function () {
					btnWrap.hide();
					newNicknameInput.hide();
					oldNicknameText.html(nickname).show();
					editNicknameBtn.show();
				},
				error: {
					"remind": "修改昵称失败！"
				}
			});
		}
	});

};

/*------------------------------用户（登录注册）模块-----------------------------*/
doc.user = {} //用户命名空间

doc.user.login = function () {
	$('#forgivePasswordBtn').on('click', function () {
		$('.user_container').addClass('turnLeft');
	});

	$('#registerBtn').on('click', function () {
		$('.user_container').addClass('turnRight');
	});
}

doc.user.register = function () {
	$('#newPassword2').on('change', function () {
		if ($(this).val() != $('#newPassword1').val()) {
			$(this).get(0).setCustomValidity("两次输入的密码不匹配");
		} else {
			$(this).get(0).setCustomValidity("");
		}
	});
};

/*------------------------------系统消息模块-----------------------------*/
doc.message = {} //用户命名空间











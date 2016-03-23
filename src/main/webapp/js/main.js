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
		url: "data.json",
		data: {'id': id},
		success: function () {
			doc.docInfo.dataTable.fnDraw(true);
		},
		error: {
			remind: "删除稿件出错"
		}
	});
}

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
			console.log(value.name);
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
}


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











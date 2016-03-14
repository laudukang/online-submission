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
                alert("请确保结束时间大于开始时间");
            } else {
                doc.docInfo.dataTable.fnDraw(true);
            }
        } else {
            alert("不能只填写开始时间或结束时间");
        }
    }

    doc.docInfo.dataTable.delegate('.icon-trash', 'click', function () {
        var id = $(this).data('id');
        $.confirmBox({
            id: "docList_deleteDoc",
            title: "删除稿件",
            confirm: "确定要删除该稿件吗？"
        }).data('projectId', id);
    });

}

//删除项目
doc.docInfo.deleteProject = function (id) {
    doc.tool.getJSON({
        url: "data.json",
        data: {'id': id},
        success: function () {
            doc.docList.dataTable.fnDraw(true);
        },
        error: {
            remind: "删除部门出错"
        }
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
}













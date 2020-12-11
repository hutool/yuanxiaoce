$(function () {

    /**
     * 复选框全选/全不选
     */
    $("#checkAll").click(function () {
        $(":checkbox[name='ids']").prop("checked", this.checked); // this指当前选择的这个元素的JS对象
    });
});

/**
 * 批量审核
 */
function checkDoneComments() {
    var ids = new Array();

    $(":checked[name=ids]:checked").each(function (i) {
        ids[i] = $(this).prop("id")
    })

    if(ids.length < 1){
        alert("请先选择一条数据哦~");
        return;
    }

    swal({
        title: "确认弹框",
        text: "确认审核通过吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/comments/checkDone",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            alert("审核成功");
                            location.reload();
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    );
}

/**
 * 批量删除
 */
function deleteComments() {
    var ids = new Array();

    $(":checked[name=ids]:checked").each(function (i) {
        ids[i] = $(this).prop("id")
    })

    if(ids.length < 1){
        alert("请先选择一条数据哦~");
        return;
    }

    swal({
        title: "确认弹框",
        text: "确认删除这些评论吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/comments/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            alert("删除成功");
                            location.reload();
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    );
}


function reply() {

    var ids = new Array();

    $(":checked[name=ids]:checked").each(function (i) {
        ids[i] = $(this).prop("id")
    })

    if(ids.length < 1){
        alert("请先选择一条数据哦~");
        return;
    }
    if (ids.length > 1){
        alert("只能选择一条评论回复哦~");
        return;
    }

    $.get(
        "/admin/comments/info/"+ids[0],
        function (data) {
            if(data.data.commentStatus == 0) {
                swal("请审核后，再进行回复!", {
                    icon: "warning",
                });
            }else {
                $("#replyBody").val('');
                $('#replyModal').modal('show');
                $('#commentId').val(ids[0]);
            }
        }
    )


}

//绑定modal上的保存按钮
$('#saveButton').click(function () {
    var replyBody = $("#replyBody").val();
    if (!validCN_ENString2_100(replyBody)) {
        swal("请输入符合规范的回复信息!", {
            icon: "warning",
        });
        return;
    } else {
        var url = '/admin/comments/reply';
        var id = $('#commentId').val();
        var params = {"commentId": id, "replyBody": replyBody}
        $.ajax({
            type: 'POST',//方法类型
            url: url,
            data: params,
            success: function (result) {
                if (result.resultCode == 200) {
                    $('#replyModal').modal('hide');
                    alert("回复成功");
                    location.reload();
                }
                else {
                    $('#replyModal').modal('hide');
                    swal(result.message, {
                        icon: "error",
                    });
                }
                ;
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });
    }
});

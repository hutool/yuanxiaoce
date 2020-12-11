$(function () {

    /**
     * 复选框全选/全不选
     */
    $("#checkAll").click(function () {
        $(":checkbox[name='ids']").prop("checked", this.checked); // this指当前选择的这个元素的JS对象
    });
});

function linkAdd() {
    reset();
    $('.modal-title').html('友链添加');
    $('#linkModal').modal('show');
}

//绑定modal上的保存按钮
$('#saveButton').click(function () {
    var linkId = $("#linkId").val();
    var linkName = $("#linkName").val();
    var linkUrl = $("#linkUrl").val();
    var linkDescription = $("#linkDescription").val();
    var linkRank = $("#linkRank").val();
    if (!validCN_ENString2_18(linkName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的名称！");
        return;
    }
    if (!isURL(linkUrl)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的网址！");
        return;
    }
    if (!validCN_ENString2_100(linkDescription)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的描述！");
        return;
    }
    if (isNull(linkRank) || linkRank < 0) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的排序值！");
        return;
    }
    var params = $("#linkForm").serialize();
    var url = '/admin/links/save';
    //有id，则更新
    if (linkId != '' && linkId > 0) {
        url = '/admin/links/update';
    }
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: params,
        success: function (result) {
            if (result.resultCode == 200) {
                $('#linkModal').modal('hide');
                alert("保存成功");
                location.reload();
            } else {
                $('#linkModal').modal('hide');
                swal("保存失败", {
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

});

function linkEdit() {

    var array = new Array();

    //依次遍历别选中的复选框，选中的执行函数体
    $(":checkbox[name='ids']:checked").each(function (i) {
        array[i] = $(this).prop("id");  //将选中的复选框的id属性的值保存到数组中
    })

    if (array.length > 1) {
        alert("只能选择一条数据修改哦！");
        return;
    }
    if (array.length == 0) {
        alert("请先选择一条数据！");
        return;
    }

    //请求数据，将数据回显到modal中
    $.get(
        "/admin/links/info/" + array[0],
        function (r) {
            if (r.resultCode == 200 && r.data != null) {
                //填充数据至modal
                $("#linkName").val(r.data.linkName);
                $("#linkUrl").val(r.data.linkUrl);
                $("#linkDescription").val(r.data.linkDescription);
                $("#linkRank").val(r.data.linkRank);
                //根据原linkType值设置select选择器为选中状态
                if (r.data.linkType == 1) {
                    $("#linkType option:eq(1)").prop("selected", 'selected');
                }
                if (r.data.linkType == 2) {
                    $("#linkType option:eq(2)").prop("selected", 'selected');
                }
            }
        });
    $('.modal-title').html('友链修改');
    $('#linkModal').modal('show');
    $("#linkId").val(array[0]);
}

function deleteLink() {

    var array = new Array();

    //依次遍历别选中的复选框，选中的执行函数体
    $(":checked[name='ids']:checked").each(function (i) {
        array[i] = $(this).prop("id");  //将选中的复选框的id属性的值保存到数组中
    });

    if (array.length < 1) {
        alert("请先选择一条数据哦~");
        return;
    }

    swal({
        title: "确认弹框",
        text: "确认要删除数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/links/delete",
                    contentType: "application/json",
                    data: JSON.stringify(array),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("删除成功", {
                                icon: "success",
                            });
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

function reset() {
    $("#linkName").val('');
    $("#linkUrl").val('');
    $("#linkDescription").val('');
    $("#linkRank").val(0);
    $('#edit-error-msg').css("display", "none");
    $("#linkType option:first").prop("selected", 'selected');
}
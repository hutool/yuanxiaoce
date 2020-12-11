function tagAdd() {
    var tagName = $("#tagName").val();
    if (!validCN_ENString2_18(tagName)) {
        swal("标签名称不规范", {
            icon: "error"
        });
    } else {
        var url = '/admin/tags/save?tagName=' + tagName;
        $.ajax({
            type: 'POST',//方法类型
            url: url,
            success: function (result) {
                if (result.resultCode == 200) {
                    alert("保存成功");
                    location.reload();  //重新加载当前页面
                }
                else {
                    $("#tagName").val('');
                    swal(result.message, {
                        icon: "error"
                    });
                }
            },
            error: function () {
                swal("操作失败", {
                    icon: "error"
                });
            }
        });
    }
}

function deleteTag(tagId) {
    if (tagId != null) {
        var url = '/admin/tags/delete/' + tagId;
        if (confirm("确定要删除吗？")){
            $.ajax({
                type: 'GET',
                url: url,
                success: function (result) {
                    if(result.resultCode == 200){
                        alert("删除成功！");
                        location.reload();
                    }else {
                        alert(result.message);
                    }
                },
                error: function () {
                    alert("删除失败！");
                }
            })
        }
    }
}

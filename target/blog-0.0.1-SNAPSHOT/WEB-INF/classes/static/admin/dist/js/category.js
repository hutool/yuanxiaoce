//新增按钮
$("#saveCategory").click(function () {
    $(".modal-title").html("添加分类");
    $("#categoryModal").modal('show');
})

//修改按钮
function updateCategory(id){

    //修改之前，需要根据ID向后端获取数据，回显到模态框中
    $.get(
        "/admin/categories/info/"+id,
        function (data) {
            if(data.resultCode == 200 && data.data != null){
                //填充数据至modal中
                $('#categoryName').val(data.data.categoryName);
            }
        }
    )
    $('.modal-title').html("修改分类");
    $("#categoryModal").modal('show');
    //将id添加至modal中的隐藏域中, 用于接下来的提交修改
    $('#categoryId').val(id);
}

//绑定modal上的保存按钮
$('#saveButton').click(function () {
    var categoryName = $("#categoryName").val();
    if (!validCN_ENString2_18(categoryName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的分类名称！");
    } else {
        var params = $("#categoryForm").serialize();
        var url = '/admin/categories/save';
        var categoryId = $('#categoryId').val();
        if (categoryId != ''){
            url = '/admin/categories/update/' + categoryId;
        }
        $.ajax({
            type: 'POST',
            url: url,
            data: params,
            success: function (result) {
                if (result.resultCode == 200) {
                    alert("保存成功");
                } else {
                    alert(result.message);
                }
                //如果保存成功，则向后端发送请求，重新加载页面
                location.replace("/admin/categories");
            },
            error: function () {
                alert("操作失败");
                $("#categoryModal").modal('hidden');
            }
        });
    }
});

function deleteCategory(categoryId) {
    var url = '/admin/categories/delete/' + categoryId;
    if (categoryId != null) {
        var b = confirm("确定要删除吗？");
        if (b){
            $.ajax({
                type: 'GET',
                url: url,
                success: function (result) {
                    if (result.resultCode == 200) {
                        alert("删除成功！");
                    }
                    location.replace("/admin/categories");
                }
            })
        }

    } else {
        alert("删除失败！");
    }
}
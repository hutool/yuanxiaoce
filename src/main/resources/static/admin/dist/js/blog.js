$(function () {

    /**
     * 复选框全选/全不选
     */
    $("#checkAll").click(function () {
        $(":checkbox[name='ids']").prop("checked", this.checked); // this指当前选择的这个元素的JS对象
    });
});

/**
 * 搜索功能
 */
function search() {
    var url = "/admin/blogs/search"
    //刷新id=table的标签体所有内容-----load(url, data, function)  load函数是POST请求
    $("#table").load(url, {
        keyword: $('#keyword').val()
    });
}

//翻页按钮
function page(pageNum) {
    var keyword = $("#keyword").val();
    var url = "/admin/blogs/search"
    $("#table").load(url, {
        pageNum: pageNum,
        keyword: keyword
    });
}


function addBlog() {
    window.location.href = "/admin/blogs/edit";
}

//修改博客
function editBlog() {

    var array = new Array();

    //依次遍历别选中的复选框，选中的执行函数体
    $(":checkbox[name='ids']:checked").each(function (i) {
        array[i] =$(this).prop("id");  //将选中的复选框的id属性的值保存到数组中
    })

    if(array.length > 1){
        alert("只能选择一条数据修改哦！");
        return;
    }
    if(array.length == 0){
        alert("请先选择一条数据！");
        return;
    }

    window.location.href = "/admin/blogs/edit/" + array[0];
}

function deleteBlog() {

    var array = new Array();

    //依次遍历别选中的复选框，选中的执行函数体
    $(":checkbox[name='ids']:checked").each(function (i) {
        array[i] =$(this).prop("id");  //将选中的复选框的id属性的值保存到数组中
    })

    if(array.length == 0){
        alert("请先选择一条数据！");
        return;
    }

    swal({
        text: "确认要删除选中的数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/blogs/delete",
                    contentType: "application/json",
                    data: JSON.stringify(array),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("删除成功", {
                                icon: "success",
                            });
                            location.reload();  //重新加载当前页面
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
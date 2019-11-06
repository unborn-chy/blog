$(function () {
    toastr.options.positionClass = 'toast-top-center';
    var getCategoryId = getQueryString("categoryid");
    var isEdit = getCategoryId != '' ? true : false;
    var initUrl = '/blog/manage/getcategorparent';
    var getAllCategoryUrl = '/blog/manage/categories';
    var addCategoryUrl = '/blog/manage/category';
    var updateCategoryUrl = '/blog/manage/category/' + getCategoryId;

    var getCategoryUrl = '/blog/manage/category/' + getCategoryId;
    console.log("getCategoryId" + getCategoryId);
    console.log("isEdit" + isEdit);
    getCategoryAndParentId();

    function getCategoryAndParentId() {
        $.ajax({
            url: initUrl,
            type: 'get',
            dataType: 'json',
            headers: {
                accessToken: getAdminToken()
            },
            success: function (data) {
                if (data.code == 0) {
                    var parentHtml = `<option value="0">无父节点</option>`;
                    var categoryParentList = data.data.categoryParentList;
                    categoryParentList.map(function (item, index) {
                        parentHtml += `<option value="${item.categoryId}">${item.categoryName}</option>`;
                    });
                    $('#cate-pid').html(parentHtml);
                }
            }
        });
        $.ajax({
            url: getAllCategoryUrl,
            type: 'get',
            dataType: 'json',
            headers: {
                accessToken: getAdminToken()
            },
            success: function (data) {
                if (data.code == 0) {
                    // var categoryListHtml = ``;
                    // var categoryList = data.data.categoryList;
                    // categoryList.map(function (item, index) {
                    //     categoryListHtml += `<tr>`
                    //         + `<td>${item.parentId.categoryId == 0 ? item.categoryName : '——' + item.categoryName}</td>`
                    //         + `<td>`
                    //         + `<a class="btn btn-primary btn-xs get-category" href="/blog/admin/category?categoryid=${item.categoryId}">修改</a>`
                    //         + `<a class="btn btn-danger btn-xs delete-category" data-category-id="${item.categoryId}" style="margin-left: 5px">永久删除</a>`
                    //         + `</td>`
                    //         + `</tr>`;
                    // });
                    // $('#cate-tbody').html(categoryListHtml);
                    // $('.all-category').html(`所有分类目录(${data.data.count})`)

                    var categoryParentHtml = ``;
                    var categoryParent = data.data.categoryParent;
                    var categoryList = data.data.categoryList;
                    categoryParent.map(function (item, index) {
                        categoryParentHtml += `<tr>`
                            + `<td>${item.categoryName}</td>`
                            + `<td>`
                            + `<a class="btn btn-primary btn-xs get-category" href="/blog/admin/category?categoryid=${item.categoryId}">修改</a>`
                            + `<a class="btn btn-danger btn-xs delete-category" data-category-id="${item.categoryId}" style="margin-left: 5px">永久删除</a>`
                            + `</td>`
                            + `</tr>`
                            + loadCateList(item.categoryId,categoryList);
                    });

                    function loadCateList(parentId,categoryList){
                        var categoryListHtml = ``;
                        categoryList.map(function (item, index) {
                            if(item.parentId.categoryId==parentId){
                                categoryListHtml += `<tr>`
                                    + `<td>${"&emsp;&emsp;---" + item.categoryName}</td>`
                                    + `<td>`
                                    + `<a class="btn btn-primary btn-xs get-category" href="/blog/admin/category?categoryid=${item.categoryId}">修改</a>`
                                    + `<a class="btn btn-danger btn-xs delete-category" data-category-id="${item.categoryId}" style="margin-left: 5px">永久删除</a>`
                                    + `</td>`
                                    + `</tr>`;
                            }
                        });
                        return categoryListHtml;
                    }

                    $('#cate-tbody').html(categoryParentHtml);
                    $('.all-category').html(`所有分类目录(${data.data.count})`)
                }
            }
        });
    }



    //添加 修改
    $('#btn-category').click(function () {
        var categoryName = $('#cate-name').val();
        if (categoryName == null || categoryName == '' || categoryName.trim().length==0) {
            toastr.warning("类别名称不能为空或者空格");
            return;
        }
        var parentId = $('#cate-pid').val();
        console.log("categoryName:" + categoryName + "parentId:" + parentId);
        $.ajax({
            url: (isEdit == true) ? updateCategoryUrl : addCategoryUrl,
            type: (isEdit == true) ? 'put' : 'post',
            async: false,
            data: {
                categoryName: categoryName,
                parentId: parentId
            },
            headers: {
                accessToken: getAdminToken()
            },
            success: function (data) {
                if (data.code == 0) {
                    toastr.success(data.msg);
                    //重新加载
                    getCategoryAndParentId();
                    $('#cate-name').val("");
                } else {
                    toastr.error(data.msg);
                }
            }
        });
    });


    //回显 判断是否有?categoryid参数
    if (isEdit) {
        $.ajax({
            url: getCategoryUrl,
            type: 'get',
            dataType: 'json',
            headers: {
                accessToken: getAdminToken()
            },
            success: function (data) {
                var parentHtml = `<option value="0">无父节点</option>`;
                if (data.code == 0) {
                    $('#cate-name').val(data.data.category.categoryName);

                    var categoryParentList = data.data.categoryParentList;
                    categoryParentList.map(function (item, index) {
                        parentHtml += `<option value="${item.categoryId}">${item.categoryName}</option>`;
                    });
                    $('#cate-pid').html(parentHtml);
                    $("#cate-pid").find(`option[value= ${data.data.category.parentId.categoryId}]`).attr("selected", true);
                    $('#btn-category').html('确认修改');
                }
            }
        })
    }

    //删除
    $('#cate-tbody').on('click', '.delete-category', function (e) {
        var categoryId = e.target.dataset.categoryId;
        var deleteUrl = '/blog/manage/category/' + categoryId;
        console.log("categoryId:::" + categoryId);
        //弹出确认框
        $('.common-confirm-delete').modal('show');
        $('.delete-true').click(function () {
            $.ajax({
                url: deleteUrl,
                type: 'delete',
                headers: {
                    accessToken: getAdminToken()
                },
                success: function (data) {
                    if (data.code == 0) {
                        toastr.success(data.msg);
                        //从新加载
                        getCategoryAndParentId();
                    } else {
                        toastr.error(data.msg);
                    }
                }
            });
            $('.common-confirm-delete').modal('hide');
        })
    })
});
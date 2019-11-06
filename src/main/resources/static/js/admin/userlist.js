$(function () {
    toastr.options.positionClass = 'toast-top-center';
    var initUrl = '/blog/manage/users';
    var GetPageIndex = getQueryString("pageIndex");
    var pageIndex = (((GetPageIndex != '') ? GetPageIndex : 1) < 1) ? 1 : ((GetPageIndex != '') ? GetPageIndex : 1);
    var pageSize = 10;

    getUserList();
    function getUserList() {
        var getUserListUrl = initUrl + '?pageIndex=' + pageIndex + '&pageSize=' + pageSize;
        $.ajax({
            url: getUserListUrl,
            type: 'get',
            dataType: 'json',
            headers: {
                accessToken: getAdminToken()
            },
            success: function (data) {
                if (data.code == 0 && data.data != null) {
                    var userListHtml = "";
                    var userList = data.data.userList;
                    userList.map(function (item, index) {
                        userListHtml += `<tr>`
                            + `<td>${item.userId}</td>`
                            + `<td>${item.username}</td>`
                            + `<td>${item.userEmail}</td>`
                            + `<td>${(item.userType == 0) ? '<p style="color: red">管理员</p>' : '普通用户'}</td>`
                            + `<td>${(new Date(item.createTime)).Format("yyyy-MM-dd hh:mm")}</td>`
                            + `<td>`
                            + `<a type="button"  class="btn  btn-primary btn-xs btn-update-type" data-user-id="${item.userId}"  style="margin-left: 4px">设置为管理员</a>`
                            + `<a type="button" class="btn  btn-danger btn-xs btn-delete-btn" data-user-id="${item.userId}" style="margin-left: 4px">删除</a>`
                            + `</td>`
                            + `</tr>`;
                    });
                    $('#userlist-tbody').html(userListHtml);
                    //总记录数
                    var count = data.data.count;
                    //计算总页数
                    var pageCount = Math.ceil(count / pageSize);
                    var pageNum = `第${pageIndex}/${pageCount}页`;
                    $('#page-num').html(pageNum);
                    //页码导航
                    //首页
                    var homePage = '/blog/admin/userlist';
                    //上一页
                    var previousPage = '/blog/admin/userlist?pageIndex=' + ((parseInt(pageIndex) - 1) < 1 ? 1 : (parseInt(pageIndex) - 1));
                    //下一页
                    var nextPage = '/blog/admin/userlist?pageIndex=' + ((parseInt(pageIndex) + 1) > pageCount ? pageCount : (parseInt(pageIndex) + 1));
                    //末页
                    var lastPage = '/blog/admin/userlist?&pageIndex=' + pageCount;
                    $('#page-home').attr("href", homePage);
                 //   $('#page-home').removeClass("disabled");
                    $('#page-previous').attr("href", previousPage);
                   // $('#page-previous').removeClass("disabled");
                    $('#page-next').attr("href", nextPage);
                   // $('#page-next').removeClass("disabled");
                    $('#page-last').attr("href", lastPage);
                   // $('#page-last').removeClass("disabled");
                    console.log("pageCount:" + pageCount);
                    // $('#page-nva').html(pageNavHtml);
                } else {
                    toastr.error(data.msg);
                }
            }
        })
    }

    $('#userlist-tbody').on('click', '.btn-delete-btn', function (e) {
        var userId = e.target.getAttribute("data-user-id");
        console.log("userId:"+ userId);
        var deleteUrl = '/blog/manage/userinfo/' + userId;
        //弹出确认框
        $('.common-confirm-delete').modal('show');
        $('.delete-true').click(function () {
            $.ajax({
                url: deleteUrl,
                type: 'DELETE',
                cache: false,
                headers:{
                    accessToken:getAdminToken()
                },
                success: function (data) {
                    if (data.code == 0) {
                        toastr.success(data.msg);
                        getUserList();
                    }else {
                        toastr.error(data.msg);
                    }
                }
            });
            $('.common-confirm-delete').modal('hide');
        })
    });

    $('#userlist-tbody').on('click', '.btn-update-type', function (e) {
        var userId = e.target.getAttribute("data-user-id");
        console.log("userId:"+ userId);
        var updateUserTypeUrl = '/blog/manage/userinfo/' + userId;
            $.ajax({
                url: updateUserTypeUrl,
                type: 'put',
                cache: false,
                headers:{
                    accessToken:getAdminToken()
                },
                success: function (data) {
                    if (data.code == 0) {
                        toastr.success(data.msg);
                        getUserList();
                    }else {
                        toastr.error(data.msg);
                    }
                }
            });
    });
});
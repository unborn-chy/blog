$(function () {
    toastr.options.positionClass = 'toast-top-center';
    var GetPageIndex = getQueryString("pageIndex");
    //获取pageIndex 如果pageIndex不是空串 那就是获取到的值，否则为1，如果pageIndex<1(负数之类的)，那pageIndex就唯一，否则就是1   防止状态栏上页码为负数
    var pageIndex = (((GetPageIndex != '') ? GetPageIndex : 1) < 1) ? 1 : ((GetPageIndex != '') ? GetPageIndex : 1);
    var pageSize = 10;
    var status = (getQueryString("status") != '') ? getQueryString("status") : 0;
    console.log("status:" + status);
    var getArticleListUrl = '/blog/manage/articles';
    //获取状态为0的
    if (status == 0) {
        //getArticleFaliList();
        getArticlePassList();
    } else {
        //getArticlePassList();
        getArticleFaliList();
    }

    function getArticlePassList() {
        var initUrl = getArticleListUrl + '?status=0&pageIndex=' + pageIndex + '&pageSize=' + pageSize;
        $.ajax({
            url: initUrl,
            type:'get',
            dataType: 'json',
            headers:{
                accessToken:getAdminToken()
            },
            success:function (data) {
                if (data.code == 0 && data.data != null) {
                    var articleHtml = "";
                    var articleList = data.data.articleList;
                    articleList.map(function (item, index) {
                        articleHtml += `<tr>`
                            + `<td>${item.articleTitle}</td>`
                            + `<td>${item.userInfo.username}</td>`
                            + `<td>${item.category.categoryName}</td>`
                            + `<td>${item.articleTags}</td>`
                            + `<td>${item.articleLikeCount}</td>`
                            + `<td>${item.articleViews}</td>`
                            + `<td>${(new Date(item.createTime)).Format("yyyy-MM-dd hh:mm")}</td>`
                            + `<td>`
                            + `<a type="button" href="/blog/article/${item.articleId}" target="_blank" class="btn  btn-vk btn-xs" style="margin-left: 4px">查看</a>`
                            + `<a type="button"  href="/blog/admin/article/${item.articleId}" class="btn  btn-primary btn-xs" style="margin-left: 4px">编辑</a>`
                            + `<a type="button" class="btn  btn-danger btn-xs btn-delete-btn" data-article-id="${item.articleId}" style="margin-left: 4px">删除</a>`
                            + `</td>`
                            + `</tr>`;
                    });
                    $('#article-tbody').html(articleHtml);
                    //总记录数
                    var count = data.data.count;
                    $('#count-status-0').html(`(${count})`);

                    var otherCount = data.data.otherCount;
                    $('#count-status-1').html(`(${otherCount})`);

                    //计算总页数
                    var pageCount = Math.ceil(count / pageSize);
                    var pageNum = `第${pageIndex}/${pageCount}页`;
                    $('#page-num').html(pageNum);
                    //页码导航
                    //首页
                    var homePage = '/blog/admin/articles?status=' + status;
                    //上一页
                    var previousPage = '/blog/admin/articles?status=' + status + '&pageIndex=' + ((parseInt(pageIndex) - 1) < 1 ? 1 : (parseInt(pageIndex) - 1));
                    //下一页
                    var nextPage = '/blog/admin/articles?status=' + status + '&pageIndex=' + ((parseInt(pageIndex) + 1) > pageCount ? pageCount : (parseInt(pageIndex) + 1));
                    //末页
                    var lastPage = '/blog/admin/articles?status=' + status + '&pageIndex=' + pageCount;

                    $('#page-home').attr("href", homePage);
                    $('#page-home').removeClass("disabled");
                    $('#page-previous').attr("href", previousPage);
                    $('#page-previous').removeClass("disabled");
                    $('#page-next').attr("href", nextPage);
                    $('#page-next').removeClass("disabled");
                    $('#page-last').attr("href", lastPage);
                    $('#page-last').removeClass("disabled");
                    console.log("pageCount:" + pageCount);
                    // $('#page-nva').html(pageNavHtml);
                }
            }
        })
    }

    function getArticleFaliList() {
        var initUrl = getArticleListUrl + '?status=1&pageIndex=' + pageIndex + '&pageSize=' + pageSize;
        $.ajax({
            url: initUrl,
            type:'get',
            dataType: 'json',
            headers:{
                accessToken:getAdminToken()
            },
            success:function (data) {
                if (data.code == 0 && data.data != null) {
                    // var
                    var articleHtml = "";
                    var articleList = data.data.articleList;

                    var userType = data.data.userType;
                    var passHtml = "";
                    var passHtmlText1 = "";
                    var passHtmlText2 = "";

                    if (userType == 0) {
                        passHtmlText1 = `<a type="button" class="btn btn-success btn-xs btn-pass-btn" `;
                        passHtmlText2 = ` style="margin-left: 4px">通过</a>`;
                    }

                    console.log("passHtml外面:" + passHtml);
                    articleList.map(function (item, index) {
                        articleHtml += '<tr>'
                            + `<td>${item.articleTitle}</td>`
                            + `<td>${item.userInfo.username}</td>`
                            + `<td>${item.category.categoryName}</td>`
                            + `<td>${item.articleTags}</td>`
                            + `<td>${item.articleLikeCount}</td>`
                            + `<td>${item.articleViews}</td>`
                            + `<td>${(new Date(item.createTime)).Format("yyyy-MM-dd hh:mm")}</td>`
                            + '<td>'
                            + `<a type="button" class="btn btn-primary btn-xs" href="/blog/admin/article/${item.articleId}" style="margin-left: 4px">编辑</a>`
                            + `${(userType == 0) ? passHtmlText1 + 'data-article-id=' + item.articleId + passHtmlText2 : passHtml}`
                            + `<a type="button" class="btn btn-danger btn-xs btn-delete-btn" data-article-id="${item.articleId}"style="margin-left: 4px">丢弃</a>`
                            + `</td>`
                            + `</tr>`;
                    });
                    $('#article-tbody').html(articleHtml);
                    //总记录数
                    var count = data.data.count;
                    $('#count-status-1').html(`(${count})`);

                    var otherCount = data.data.otherCount;
                    $('#count-status-0').html(`(${otherCount})`);

                    //计算总页数
                    var pageCount = Math.ceil(count / pageSize);

                    var pageNum = `第${pageIndex}/${pageCount}页`;
                    $('#page-num').html(pageNum);
                    //页码导航
                    //首页
                    var homePage = '/blog/admin/articles?status=' + status;
                    //上一页
                    var previousPage = '/blog/admin/articles?status=' + status + '&pageIndex=' + ((parseInt(pageIndex) - 1) < 1 ? 1 : (parseInt(pageIndex) - 1));
                    //下一页
                    var nextPage = '/blog/admin/articles?status=' + status + '&pageIndex=' + ((parseInt(pageIndex) + 1) > pageCount ? pageCount : (parseInt(pageIndex) + 1));
                    //末页
                    var lastPage = '/blog/admin/articles?status=' + status + '&pageIndex=' + pageCount;

                    $('#page-home').attr("href", homePage);
                    $('#page-home').removeClass("disabled");
                    $('#page-previous').attr("href", previousPage);
                    $('#page-previous').removeClass("disabled");
                    $('#page-next').attr("href", nextPage);
                    $('#page-next').removeClass("disabled");
                    $('#page-last').attr("href", lastPage);
                    $('#page-last').removeClass("disabled");
                    console.log("pageCount:" + pageCount);
                    // $('#page-nva').html(pageNavHtml);
                }
            }
        })
    }

    //设置删除

    $('#article-tbody').on('click', '.btn-delete-btn', function (e) {
        var articleId = e.target.dataset.articleId;
        var deleteUrl = '/blog/manage/article/' + articleId;

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
                        setTimeout("location.reload()", "1000");
                    }else {
                        toastr.error(data.msg);
                    }
                }
            });
            $('.common-confirm-delete').modal('hide');
        })
    });


    //设置通过
    $('#article-tbody').on('click', '.btn-pass-btn', function (e) {
        var articleId = e.target.dataset.articleId;
        var passUrl = '/blog/manage/articles/' + articleId;
        console.log("pass", articleId);
        $.ajax({
            url: passUrl,
            type: 'PUT',
            cache: false,
            headers:{
                accessToken:getAdminToken()
            },
            success: function (data) {
                if (data.code == 0) {
                    toastr.success("通过成功");
                    setTimeout("location.reload()", "1000")

                }else {
                    toastr.error("通过失败");
                }
            }
        })
    })

});
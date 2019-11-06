$(function () {
    toastr.options.positionClass = 'toast-top-center';
    var getPageIndex = getQueryString("pageIndex");
    var pageIndex = (((getPageIndex != '') ? getPageIndex : 1) < 1) ? 1 : ((getPageIndex != '') ? getPageIndex : 1);
    var pageSize = 5;
    var getCommentListUrl = '/blog/manage/comments?pageIndex=' + pageIndex + '&pageSize=' + pageSize;
    //获取getCommentList
    getCommentList();
    function getCommentList() {
        $.ajax({
            url:getCommentListUrl,
            type:'get',
            dataType:'json',
            headers:{
                accessToken:getAdminToken()
            },
            success:function (data) {
                if (data.code == 0) {

                    var commentList = data.data.commentList;
                    var commentHtml = "";
                    commentList.map(function (item, index) {
                        commentHtml += `<tr>`
                            + `<td>${item.userInfoFrom.username}</td>`
                            + `<td>${item.commentContent}</td>`
                            + `<td><a href="/blog/article/${item.article.articleId}">${item.article.articleTitle}</a></td>`
                            + `<td>${(new Date(item.createTime)).Format("yyyy-MM-dd hh:mm")}</td>`
                            + `<td>`
                            + `<a type="button" class="btn  btn-danger btn-xs btn-delete-comment" data-comment-id="${item.commentId}">删除</a>`
                            + `</td>`
                            + `</tr>`;

                    });
                    $('#comment-list-tbody').html(commentHtml);

                    //总记录数
                    var count = data.data.count;

                    $('#comment-count').append(`<span>(${count})</span>`);
                    //计算总页数
                    var pageCount = Math.ceil(count / pageSize);

                    var pageNum = `第${pageIndex}/${pageCount}页`;
                    $('#page-num').html(pageNum);

                    //页码导航
                    //首页
                    var homePage = '/blog/admin/comment';
                    //上一页
                    var previousPage = '/blog/admin/comment?pageIndex=' + ((parseInt(pageIndex) - 1) < 1 ? 1 : (parseInt(pageIndex) - 1));
                    //下一页
                    var nextPage = '/blog/admin/comment?pageIndex=' + ((parseInt(pageIndex) + 1) > pageCount ? pageCount : (parseInt(pageIndex) + 1));
                    //末页
                    var lastPage = '/blog/admin/comment?pageIndex=' + pageCount;

                    $('#page-home').attr("href", homePage);
                    $('#page-home').removeClass("disabled");
                    $('#page-previous').attr("href", previousPage);
                    $('#page-previous').removeClass("disabled");
                    $('#page-next').attr("href", nextPage);
                    $('#page-next').removeClass("disabled");
                    $('#page-last').attr("href", lastPage);
                    $('#page-last').removeClass("disabled");
                    console.log("pageCount:" + pageCount);

                }
            }
        })
    }

    $('#comment-list-tbody').on('click','.btn-delete-comment',function (e) {
        var commentId = e.target.getAttribute("data-comment-id");
        var deleteUrl = '/blog/manage/comment/' + commentId ;
        console.log("commentId:" + commentId);

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
                        getCommentList();
                    } else {
                        toastr.error(data.msg);
                    }
                }
            });
            $('.common-confirm-delete').modal('hide');
        })
    })
    //
});
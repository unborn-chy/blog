$(function () {
    toastr.options.positionClass = 'toast-top-center';
    var articleId = getUrlParameter();
    // console.log("articleId:" + articleId);
    var getArticleUrl = '/blog/frontend/article/' + articleId;
    var getAllCommentUrl = '/blog/frontend/comment/' + articleId;
    //添加评论
    var addCommentUrl = '/blog/frontend/comment/' + articleId;
    $.getJSON(getArticleUrl, function (data) {
        if (data.code == 0) {
            var article = data.data.article;
            //标题
            document.title = article.articleTitle;
            $('.entry-title').html(article.articleTitle);
            //文章
            $('.single-content').html(article.articleContent);
            //分类
            var articleCategoryName = article.category.categoryName;
            crumbs(articleCategoryName);
            var articleCategoryHtml = `<a href="/blog/category/${articleCategoryName}" rel="category tag">${articleCategoryName}</a>`;
            $('.single-cat').append(articleCategoryHtml);
            //标签
            var tags = article.articleTags;
            var tagArr = tags.split(",");
            for (var i = 0; i < tagArr.length; i++) {
                var tagHtml = `<li> <a href="/blog/tag/${tagArr[i]}" rel="tag" style="background:#FFFF00">${tagArr[i]}</a></li>`;
                $('.article-tags').append(tagHtml);
            }
            tagsColor();
            //点赞
            $('.count').html(article.articleLikeCount);
            //浏览量

            $('.views').append(`${article.articleViews} views`);
            //作者信息
            var userInfoHtml = `<img alt="" src="${article.userInfo.userImg}" class="avatar avatar-64 photo" height="64" width="64">`
                + `<ul class="spostinfo">`
                + `<li><strong>版权声明：</strong>`
                + `本站原创文章，于 ${(new Date(article.createTime)).Format("yyyy年MM月dd日")}，由<b><a href="#" rel="author">${article.userInfo.username}</a></b> 发表。`
                + `</li>`
                + `<li><strong>最后编辑时间：</strong>`
                + `${(new Date(article.lastEditTime)).Format("yyyy年MM月dd日")}`
                + `</li>`
                + `</ul>`;
            $('.authorbio').html(userInfoHtml);
        } else {
            toastr.error(data.msg);

        }
    });

    getAllComment();

    //所有评论
    function getAllComment() {
        $.getJSON(getAllCommentUrl, function (data) {
            if (data.code == 0) {
                var commentFromList = data.data.commentFromList;
                var commentToList = data.data.commentToList;

                $('.comments-title').html(`目前评论：${data.data.commentCount}`)
                var commentIco = `<a href="javascript:void(0)"  rel="external nofollow">`
                                        +`<i class="fa fa-comment-o"></i> ${data.data.commentCount} </a>`;

                $('.comment').html(commentIco);
                var fromHtml = '';
                commentFromList.map(function (item, index) {
                    fromHtml += `<li class="comment even thread-even parent">`
                        + `<div class="comment-body">`
                        + `<div class="comment-author vcard">`
                        + `<img class="avatar" src="${item.userInfoFrom.userImg}" alt="avatar">`
                        + `<strong>`
                        + `<a href="javascript:void(0)" class="native-list-one-head-name" target="_blank">${item.userInfoFrom.username}</a>`
                        + `</strong>`
                        + `<span>`
                        + `<span class="ua-info" style="display: none;">`
                        + `<span style="display: none;"></span>`
                        + `</span> <br>`
                        + `<span class="comment-aux">`
                        + `<span class="reply">`
                        + `<a class="native-list-one-footer-reback" data-user-name="${item.userInfoFrom.username}"  data-comment-id="${item.commentId}"href="javascript:void(0)">回复</a></span>${(new Date(item.createTime)).Format("yyyy-MM-dd hh:mm:ss")}`
                        + `</span>`
                        + `</span>`
                        + `</div>`
                        + `<p>${item.commentContent}</p>`
                        + `</div>`
                        + children(item.commentId, item.userInfoFrom.username, commentToList);
                    +`</li>`
                });
                $('#comments-list').html(fromHtml);

                function children(commentId, username, commentToList) {
                    var childrenHtml = '';
                    commentToList.map(function (item, index) {
                        if (item.parentId == commentId) {
                            childrenHtml = `<ul class="comment-list children">`
                                + `<li class="comment even thread-even parent">`
                                + `<div class="comment-body">`
                                + `<div class="comment-author vcard">`
                                + `<img class="avatar" src="${item.userInfoFrom.userImg}"alt="avatar">`
                                + `<strong>`
                                + `<a href="javascript:void(0)" rel="external nofollow" class="native-list-one-head-name" target="_blank">${item.userInfoFrom.username}</a> `
                                + `</strong>`
                                + `<span class="comment-meta commentmetadata native-comment-ua"><span class="ua-info">`
                                + `<br>`
                                + `<span class="comment-aux">`
                                + `<span class="reply">`
                                + `</span>${(new Date(item.createTime)).Format("yyyy-MM-dd hh:mm:ss")}</span> </span></div>`
                                + `<p><a href="">@${username}：</a>${item.commentContent}</p>`
                                + `</div>`
                                + `</li>`
                                + `</ul>`;
                        }
                    });
                    return childrenHtml;
                }
            }
        });
    }

    $(`#comments-list`).on('click', '.native-list-one-footer-reback', function (e) {
        $('#submit').unbind();
        $('#commentContent').val('');
        $('#commentContent').focus();
        var username = e.target.getAttribute('data-user-name');
        var commentId = e.target.getAttribute('data-comment-id');
        $('#commentContent').attr('placeholder','@'+username);
        submitComment(username,commentId);
    });
    submitComment();


    function submitComment(username,commentId) {
        $('#submit').click(function () {
            var content = $('.comment-input-content').val();
            if (content == '' || content == null || content.trim().length == 0) {
                toastr.warning("内容不能为空");
                return;
            }
            console.log("content2:" + content);
            $.ajax({
                url:addCommentUrl,
                type: 'post',
                data:{
                    content:content,
                    username,username,
                    commentId,commentId
                },
                headers: {
                    accessToken: getAdminToken()
                },
                success:function (data) {
                    if(data.code==0){
                        toastr.success(data.msg);
                        getAllComment();
                    }else {
                        toastr.error(data.msg);
                    }
                }
            })
        });
    }


    function tagsColor() {
        // 标签背景
        box_width = $(".single-tag").width();
        len = $(".single-tag ul li a").length - 1;
        $(".single-tag ul li a").each(function (i) {
            var let = new Array('c3010a', '31ac76', 'ea4563', '31a6a0', '8e7daa', '4fad7b', 'f99f13', 'f85200', '666666');
            var random1 = Math.floor(Math.random() * 9) + 0;
            var num = Math.floor(Math.random() * 6 + 9);
            $(this).attr('style', 'background:#' + let[random1] + '');
            if ($(this).next().length > 0) {
                last = $(this).next().position().left;
            }
        });
    }

    //碎片导航
    function crumbs(categoryName) {
        var crumbsHtml =
            `<a class="crumbs" href="/blog/index"><i class="fa fa-home"></i>首页</a>`
            + `<i class="fa fa-angle-right"></i>`
            + `<a class="crumbs" href="/blog/category/${categoryName}">${categoryName}</a>`
            + `<i class="fa fa-angle-right"></i>` + '正文';

        $('.breadcrumb').html(crumbsHtml);
    }

    //点赞
    $('.favorite').click(function () {
        $.ajax({
            url: getArticleUrl,
            type: 'put',
            headers: {
                accessToken: getAdminToken()
            },
            success: function (data) {
                if (data.code == 0) {
                    $('.count').html(data.data.articleLikeCount);
                } else {
                    toastr.warning(data.msg);
                }
            }
        })
    })
});
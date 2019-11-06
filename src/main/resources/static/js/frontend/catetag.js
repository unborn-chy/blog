$(function () {
    toastr.options.positionClass = 'toast-top-center';
    //点击搜索弹出搜索框
    var getCategorySidebar = '/blog/frontend/categorysidebar';
    var keywordUrl = '/blog/frontend/keyword';

    $.getJSON(getCategorySidebar, function (data) {
        if (data.code == 0) {
            //类别
            var categoryListParent = data.data.categoryListParent;
            var categoryListParentHtml = "";
            var categoryList = data.data.categoryList;
            var categoryListHtml = '<ul class="sub-menu">';
            categoryListParent.map(function (item, index) {
                categoryListParentHtml += `<li  class="menu-item menu-item-type-custom menu-item-object-custom">`
                    + `<a href="/blog/category/${item.categoryName}">`
                    + `<span class="font-text">${item.categoryName}</span>`
                    + `&emsp;<i class="fa-caret-down fa"></i>`
                    + `</a>`
                    + getCategory(item.categoryId)
                    + `<li>`
            });

            $('#menu-mainmenu').html(categoryListParentHtml);

            function getCategory(parentId) {
                categoryListHtml = '<ul class="sub-menu">';
                for (var i = 0; i < categoryList.length; i++) {
                    if (categoryList[i].parentId.categoryId == parentId) {
                        categoryListHtml += `<li id="" class ="menu-item menu-item-type-custom menu-item-object-custom">`
                            + `<a href="/blog/category/${categoryList[i].categoryName}">`
                            + `<span class="font-text">` + categoryList[i].categoryName + `</span>`
                            + `</a>`
                    }
                }
                return categoryListHtml + '</ul>'
            }
            //    标签
            var tagsList = data.data.tagsList;
            var tagsListHtml = '';
            tagsList.map(function (item, index) {
                tagsListHtml += `<a href="/blog/tag/${item.tagName}" class="tag-cloud-link " style="font-size: 14px;">${item.tagName}</a>`
            });
            $('.tagcloud').html(tagsListHtml);

            $('.articleCount').html(`文章 ${data.data.articleCount}`)
            //网站概述
            var dateBegin = new Date("2019-08-15");
            var dateEnd = new Date();
            var parseDate = dateEnd.getTime() - dateBegin.getTime();
            var days = Math.floor(parseDate / (24 * 3600 * 1000));

            var webDescHtml = `<li><i class="fa fa-file-o"></i>文章总数：${data.data.articleCount} 篇 </li>`
                    +`<li><i class="fa fa-folder-o"></i>分类数量：${data.data.categoryCount} 个</li>`
                    +`<li><i class="fa fa-tags"></i> 标签总数：${data.data.tagsCount} 个</li>`
                    +`<li><i class="fa fa-clock-o"></i> 运行时间：${days+1} 天</li>`
                    +`<li><i class="fa fa-eye"></i>浏览总量：${data.data.viewsCount} 次</li>`
                    +`<li><i class="fa fa-pencil-square-o"></i> 最后更新：<span style="color:#2F889A">${(new Date(data.data.lastEditTime)).Format('yyyy年MM月dd日')}</span></li>`
                    +`<span style="color:red;">加油！不要因为走的太远，而忘了当初为什么出发。</span>`

            $('.site-profile').html(webDescHtml);

            //最近评论 10
            var commentList = data.data.commentList;
            var commentListHtml = '';
            commentList.map(function (item,index) {
                commentListHtml += `<li>`
                    +`<a href="/blog/article/${item.article.articleId}" title="" rel="external nofollow">`
                    +`<img src="${item.userInfoFrom.userImg}"alt="avatar" class="avatar " height="128" width="128">`
                            +`<span class="comment_author"><strong>${item.userInfoFrom.username}</strong></span>`
                            +`${item.commentContent}</a>`
                    +`</li>`
            });
            $('.recently-comment').html(commentListHtml)


        }
    });

    //获取搜索框点击事件

    $('#searchsubmit').click(function () {
        var keyword = $('#keyword').val();
        if (keyword == null || keyword == '' || keyword.trim().length == 0) {
            toastr.warning('内容不能为空');
            return;
        }
       // console.log("keyword:" + keyword);
        $.ajax({
            url: keywordUrl,
            type: 'post',
            data: {
                keyword: keyword
            },
            success: function (data) {
                if (data.code == 0) {
                    var articleList = data.data.articleList;
                    var articleHtml = '';
                    articleList.map(function (item, index) {
                        articleHtml += `<article class="wow fadeInUp post type-post status-publish format-standard sticky hentry rwz" data-wow-delay="0.3s">`
                            + `<figure class="thumbnail">`
                            + `<span class="load">`
                            + `<a  href="/blog/article/${item.articleId}" target="_blank" >`
                            + `<img src="${item.articleImg}" />`
                            + `</a>`
                            + `</span>`
                            + `<span class="cat">`
                            + `<a href="/blog/category/${item.category.categoryName}" target="_blank">${item.category.categoryName}</a>`
                            + `</span>`
                            + `</figure>`
                            + `<header class="entry-header">`
                            + `<h2 class="entry-title">`
                            + `<a href="/blog/article/${item.articleId}" target="_blank"> ${item.articleTitle}</a>`
                            + `</h2>`
                            + `</header><!-- .entry-header -->`
                            + `<div class="entry-content">`
                            + '<div class="archive-content">' + subArticleContent(item.articleContent) + '</div>'
                            + `<span class="title-l"></span>`
                            + `<span class="new-icon"></span>`
                            + `<span class="entry-meta">`
                            + '<br/><br/>'
                            + `<span class="date">${(new Date(item.createTime)).Format("yyyy年MM月dd日")}&emsp;</span>`
                            + `<span class="views"><i class="fa fa-eye"></i> ${item.articleViews} views</span>`
                            + `</span>`
                            + `<div class="clear"></div>`
                            + `</div><!-- .entry-content -->`
                            + `<span class="entry-more">`
                            + `<a href="/blog/article/${item.articleId}" target="_blank">阅读全文</a>`
                            + `</span>`
                            + `</article>`
                    });
                    $('#main').html(articleHtml);
                    $(".nav-search").click();

                    var crumbsHtml = `<a class="crumbs" href="#"><i class="fa fa-home"></i>首页</a>`
                        + `<i class="fa fa-angle-right"></i>`
                        + `搜索&nbsp;${keyword}&nbsp;找到&nbsp; ${data.data.articleCount}&nbsp;个与之相关的文章`
                    $('.breadcrumb').html(crumbsHtml);
                    $('.navigation').remove()
                } else {
                    toastr.info(data.msg);
                }
            }
        })
    });

    //判断用户是否已经登录
    // if(getAdminToken()!=null && getAdminToken()!='' ) {
    var getUser = '/blog/manage/getuserinfo';
    $.ajax({
        url: getUser,
        type: 'get',
        dataType: 'json',
        headers: {
            accessToken: getAdminToken()
        },
        success: function (data) {
            if (data.code == 0) {
                var userInfo = data.data.userInfo;

                var userInfoHtml = `<div class="user-box">`
                    + `<div class="user-my">`
                    + `<img alt="${userInfo.username}" src="${userInfo.userImg}" class="avatar avatar-10 photo"style="width: 16px;height: 16px;">`
                    + `<a href="javascript:void(0)">${userInfo.username}，您好！</a>`
                    + `</div>`
                    + `<div class="user-info" style="display: none;">`
                    + `<div class="arrow-up"></div>`
                    + `<div class="user-info-min">`
                    + `<h3>${userInfo.username}</h3>`
                    + `<div class="usericon">`
                    + `<img alt="${userInfo.username}" src="${userInfo.userImg}" class="avatar  photo"
                            style="width: 96px; height: 96px;">`
                    + `</div>`
                    + `<div class="userinfo" >`
                    + `<p>`
                    + `<a href="/blog/admin/userinfo" target="_blank">个人资料</a>`
                    + `<a href="/blog/admin/dashboard" target="_blank">管理站点</a>`
                    + `<a href="javascript:void(0)"  id="user-logout">登出</a>`
                    + `</p>`
                    + `<div class="clear"></div>`
                    + `</div>`
                    + `</div>`
                    + `</div>`
                    + `</div>`;
                $('#user-profile').html(userInfoHtml);
            }
        }
    });


    $('#user-profile').mouseover(function(){
        $('.user-info').css('display','block');
    });
    $('#user-profile').mouseout(function(){
        $('.user-info').css('display','none');
    });

    $('.mobile-login').click(function () {
        if(getAdminToken()==null){
            window.location.href = '/blog/admin/login';
        }else {
            window.location.href = '/blog/admin/dashboard';
        }
    });



    $('.top-nav').on('click','#user-logout',function () {
        document.cookie="token=" + null;
        saveAdminToken("");
        window.location.href = '/blog/admin/login';
    })


});


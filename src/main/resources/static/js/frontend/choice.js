$(function () {
    toastr.options.positionClass = 'toast-top-center';
    var getPageIndex = getQueryString("page");
    var pageIndex = (((getPageIndex != '') ? getPageIndex : 1) < 1) ? 1 : ((getPageIndex != '') ? getPageIndex : 1);
    var pageSize = 8;
    var tagOrCategory = subTsgOrCat();
    console.log("tagOrCategory:" + tagOrCategory);
    var son = getUrlParameter();

    console.log("son:" + son);
    var getTagCateUrl = '';
    if (tagOrCategory == 'tag') {
        getTagCateUrl = '/blog/frontend/tag/' + son + '?pageIndex=' + pageIndex + '&pageSize=' + pageSize;
    } else {
        getTagCateUrl = '/blog/frontend/category/' + son + '?pageIndex=' + pageIndex + '&pageSize=' + pageSize;
    }
    console.log("getTagCateUrl:" + getTagCateUrl);
    $.getJSON(getTagCateUrl, function (data) {
        if (data.code == 0) {
            //文章
            var articleList = data.data.articleList;
            var articleHtml = '';
            articleList.map(function (item, index) {
                articleHtml += `<article class="wow fadeInUp post type-post status-publish format-standard sticky hentry rwz" data-wow-delay="0.3s">`
                    + `<figure class="thumbnail">`
                    + `<span class="load">`
                    + `<a href="/blog/article/${item.articleId}"  target="_blank">`
                    + `<img src="${item.articleImg}" />`
                    + `</a>`
                    + `</span>`
                    + `<span class="cat">`
                    + `<a href="/blog/category/${item.category.categoryName}"  target="_blank">${item.category.categoryName}</a>`
                    + `</span>`
                    + `</figure>`
                    + `<header class="entry-header">`
                    + `<h2 class="entry-title">`
                    + `<a href="/blog/article/${item.articleId}"  target="_blank"> ${item.articleTitle}</a>`
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
                    + `<a href="/blog/article/${item.articleId}"  target="_blank">阅读全文</a>`
                    + `</span>`
                    + `</article>`
            });
            $('#main').html(articleHtml);
            document.title = `小咸鱼 | ${son}`;
            crumbs();
            //总记录数
            var recordCount = data.data.articleCount;
            //总页数
            var pageCount = Math.ceil(recordCount / pageSize);

            if (pageIndex != 1) {
                var indexPageHtml =
                    `<a class="page-numbers" id="page-previous" href="/blog/${tagOrCategory}/${son}?page=${(pageIndex - 1)}">`
                    + `<i class="fa fa-angle-left"></i>`
                    + `</a>`
                    + `<a class="page-numbers" id="page-home" href="/blog/${tagOrCategory}/${son}?page=1">首页</a>`;
                $('.nav-links').append(indexPageHtml);
            }
            if (pageCount < 5) {
                var begin = 1;
                var end = pageCount;
            } else {
                var begin = (pageIndex - 3);
                var end = (pageIndex + 1);
                //头溢出
                if (begin < 1) {
                    begin = 1;
                    end = 5;
                }
                //尾溢出
                if (end > pageCount) {
                    begin = pageCount - 4;
                    end = pageCount;
                }
            }
            console.log("begin:" + begin);
            console.log("end:" + end);
            for (var i = begin; i <= end; i++) {
                if (i == pageIndex) {
                    $('.nav-links').append(`<span class="page-numbers current">${i}</span>`);
                } else {
                    $('.nav-links').append(`<a class="page-numbers"  href="/blog/${tagOrCategory}/${son}?page=${i}">${i}</a>`);
                }
            }
            if (pageIndex != pageCount) {
                var lastPageHtml =
                    `<a class="page-numbers" href="/blog/${tagOrCategory}/${son}?page=${pageCount}">尾页</a>`
                    + `<a class="page-numbers"href="/blog/${tagOrCategory}/${son}?page=${(parseInt(pageIndex) + 1)}">`
                    + `<i class="fa fa-angle-right"></i>`
                    + `</a>`;
                $('.nav-links').append(lastPageHtml);
            }
        } else {
            toastr.info(data.msg);
        }
    });

    function crumbs() {
        var crumbsHtml =
            `<a class="crumbs" href="/blog/index"><i class="fa fa-home"></i>首页</a>`
            + `<i class="fa fa-angle-right"></i>`
            + `<a class="crumbs" href="/blog/${tagOrCategory}/${son}">${son}</a>`
            + `<i class="fa fa-angle-right"></i>` + '正文'

        $('.breadcrumb').html(crumbsHtml);
    }


});
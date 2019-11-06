$(function () {
    var getPageIndex = getQueryString("page");
    var pageIndex = (((getPageIndex != '') ? getPageIndex : 1) < 1) ? 1 : ((getPageIndex != '') ? getPageIndex : 1);
    console.log("pageIndex:" + pageIndex);
    var pageSize = 8;
    var getAllInfoUrl = '/blog/frontend/getallinfo?pageIndex=' + pageIndex + '&pageSize=' + pageSize;


    $.getJSON(getAllInfoUrl, function (data) {
        if (data.code == 0) {
            //公告
            var adviceListHtml = '';
            var adviceList = data.data.adviceList;
            adviceList.map(function (item,index) {
                adviceListHtml += `<li class="scrolltext-title">`
                    +`<p rel="bookmark">${item.adviceContent}</p>`
                    +`</li>`
            });

            $('.all-advice-ul').html(adviceListHtml);
            //轮播图
            var headlineHtml = '<div carousel-item>';
            var headLineList = data.data.headLineList;
            headLineList.map(function (item, index) {
                headlineHtml += `<div>`
                    + `<p class="slider-caption">${item.lineName}</p>`
                    + `<a href="${item.lineLink}">`
                    + `<img src="${item.lineImg}" width="100%" height="100%">`
                    + `</a>`
                    + `</div>`
            });
            $('#headline').html(headlineHtml + '</div>');
            loadHeadline();
            //文章
            var articleList = data.data.articleList;
            var articleHtml = '';
            articleList.map(function (item, index) {
                articleHtml += `<article class="wow fadeInUp post type-post status-publish format-standard sticky hentry rwz" data-wow-delay="0.3s">`
                    + `<figure class="thumbnail">`
                    + `<span class="load">`
                    + `<a href="/blog/article/${item.articleId}" target="_blank">`
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
            $('#main').append(articleHtml);

            //我要显示5页
            // 1 2 [3] 4 5
            //总记录数
            var recordCount = data.data.articleCount;
            //总页数
            var pageCount = Math.ceil(recordCount / pageSize);
            if (pageIndex != 1) {
                var indexPageHtml =
                    `<a class="page-numbers" id="page-previous" href="/blog/index?page=${(pageIndex - 1)}">`
                    + `<i class="fa fa-angle-left"></i>`
                    + `</a>`
                    + `<a class="page-numbers" id="page-home" href="/blog/index?page=1">首页</a>`;
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
                    $('.nav-links').append(`<a class="page-numbers"  href="/blog/index?page=${i}">${i}</a>`);
                }
            }

            if (pageIndex != pageCount) {
                var lastPageHtml =
                    `<a class="page-numbers" href="/blog/index?page=${pageCount}">尾页</a>`
                    + `<a class="page-numbers"href="/blog/index?page=${(parseInt(pageIndex) + 1)}">`
                    + `<i class="fa fa-angle-right"></i>`
                    + `</a>`;
                $('.nav-links').append(lastPageHtml);
            }
        }
    });


    //实例化轮播图 需要在完成遍历之后
    function loadHeadline() {
        layui.use('carousel', function () {
            var carousel = layui.carousel;
            //建造实例
            carousel.render({
                elem: '#headline'
                , width: '100%' //设置容器宽度
                , height: '330px'
                , arrow: 'hover' //不始终显示箭头
            });
        });
    }


    $("#scrolldiv").textSlider({ line: 1, speed: 300, timer: 6000 });
});
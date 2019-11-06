$(function () {
    toastr.options.positionClass = 'toast-top-center';
    var getCategorySidebar = '/blog/frontend/categorysidebar';

    var getAllInfoUrl = '/blog/frontend/getallinfo?pageIndex=1&pageSize=5';
    $.getJSON(getCategorySidebar, function (data) {
        if (data.code == 0) {
            var articleCountHtml = `<h3>${data.data.articleCount}</h3><p>文章</p>`;
            $('.article-count').html(articleCountHtml);
            var commentCountHtml = `<h3>${data.data.commentCount}</h3><p>评论</p>`;
            $('.comment-count').html(commentCountHtml);
            var viewsCountHtml =`<h3>${data.data.viewsCount}</h3><p>浏览量</p>`;
            $('.views-count').html(viewsCountHtml);


            var dateBegin = new Date("2019-08-15");
            var dateEnd = new Date();
            var parseDate = dateEnd.getTime() - dateBegin.getTime();
            var days = Math.floor(parseDate / (24 * 3600 * 1000));

            var fountDaysHtml =`<h3>${days+1}</h3><p>成立天数</p>`;

            $('.found-days').html(fountDaysHtml);

            //最近评论 5
            var commentList = data.data.commentList;
            var commentListHtml = '';

            commentList.map(function (item,index) {
                if(index<5){
                    commentListHtml += ` <tr>`
                        +`<td>${item.userInfoFrom.username}</td>`
                        +'<td>' + subCommentContent(item.commentContent)+ '</td>'
                        +`<td><span class="label bg-green">已发布</span></td>`
                        +`<td>${(new Date(data.data.lastEditTime)).Format('yyyy-MM-dd hh:mm:ss')}</td>`
                        +`</tr>`
                }
            });
            $('.new-comment').html(commentListHtml)
        }
    });



    $.getJSON(getAllInfoUrl, function (data) {
        if (data.code == 0) {
            //文章
            var articleList = data.data.articleList;
            var articleHtml = '';
            articleList.map(function (item, index) {
                articleHtml += `<tr>`
                                    +`<td><a target="_blank" href="/blog/article/${item.articleId}">${item.articleTitle}</a></td>`
                                    +`<td class="text-center"><span class="label bg-green">已发布</span></td>`
                                    +`<td>${(new Date(item.createTime)).Format("yyyy年MM月dd日")}</td>`
                               +`</tr>`
            });
            $('.new-article').append(articleHtml);
            }
    });
});
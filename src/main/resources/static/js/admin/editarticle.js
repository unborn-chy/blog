$(function () {
    var imgSize = 4 * 1024 * 1024;
    //提示信息
    toastr.options.positionClass = 'toast-top-center';
    //编辑器
    var E = window.wangEditor;
    var editor = new E('#tool', '#edit');// 两个参数也可以传入 elem 对象，class 选择器
    //开启debug模式
    // editor.customConfig.debug = true;
    // 关闭粘贴内容中的样式
    editor.customConfig.pasteFilterStyle = true;
    // 忽略粘贴内容中的图片
    editor.customConfig.pasteIgnoreImg = true;

    // 上传图片到服务器
    editor.customConfig.uploadFileName = 'myFile'; //设置文件上传的参数名称
    editor.customConfig.uploadImgServer = '/blog/upload'; //设置上传文件的服务器路径

    editor.customConfig.uploadImgMaxSize = imgSize; // 将图片大小限制为 4M


    editor.customConfig.customAlert = function (info) {
        // info 是需要提示的内容
        toastr.error("图片最大为4M")
    }
    //自定义上传图片事件
    editor.customConfig.uploadImgHooks = {
        customInsert: function (insertImg, result, editor) {
            if (result.code == 0) {
                var url = result.data;
                insertImg(url)
            } else {
                toastr.error(result.msg)
            }
        }
    };
    editor.create();

    $('.js-example-basic-multiple').select2({
        placeholder: '请选择标签',
    });

    //选择图片 并显示
    var thumbnail = document.getElementById("thumbnail");
    var selectImg = document.getElementById("thumbnail-img");
    thumbnail.addEventListener("change", function () {
        if ($('#thumbnail')[0].files[0].size > imgSize) {
            toastr.warning("图片最大为4M");
            return;
        }
        var reader = new FileReader();
        reader.readAsDataURL(thumbnail.files[0]);//发起异步请求
        reader.onload = function () {
            //读取完成后，将结果赋值给img的src
            selectImg.src = this.result;
        }
    });


    var parameter = getUrlParameter();
    console.log("parameter:" + parameter);
    var initUrl = '/blog/manage/getcategorytagslist';
    //回显文章 修改文章
    var articleInfoUrl = '/blog/manage/article/' + parameter;


    //回显类别，标签
   getArticleInfo();

    //回显文章
    function getArticleInfo() {
            $.ajax({
                url: articleInfoUrl,
                type:'get',
                dataType: 'json',
                headers:{
                    accessToken:getAdminToken()
                },
                success:function (data) {
                    if (data.code == 0) {
                        var article = data.data.article;
                        $('#article-title').val(article.articleTitle);
                        editor.txt.html(article.articleContent);
                        var categoryHtml = '';
                        var tagsHtml = '';
                        data.data.categoryList.map(function (item, index) {
                            categoryHtml += `<option value=${item.categoryId}>${item.categoryName}</option>`
                        });
                        data.data.tagsList.map(function (item, index) {
                            tagsHtml += `<option value=${item.tagName}>${item.tagName}</option>`
                        })
                        console.log("data.data.article.category.categoryId", data.data.article.category.categoryId)
                        $('#category-select').html(categoryHtml);
                        $('#tags-select').html(tagsHtml);

                        $("#category-select").find(`option[value= ${data.data.article.category.categoryId}]`).attr("selected", true);

                        //选择标签
                        var tagsSel = data.data.article.articleTags;
                        var tagsArr = tagsSel.split(",");

                        for (var i = 0; i < tagsArr.length; i++) {
                            $("#tags-select").find(`option[value= ${tagsArr[i]} ]`).attr("selected", true);
                        }
                        //图片
                        $('#thumbnail-img').attr('src',article.articleImg);
                    }else {
                        toastr.error(data.msg);
                        //然后从定性到文章列表
                        // window.location.href = "https://www.bilibili.com/";
                    }
                }

        })
    }


    $('#btn-submit').click(function () {
        var article = {};
        // //标题
       // 文章ID
        article.articleId = parameter;
        article.articleTitle = $('#article-title').val();
        console.log("articleTitle" + article.articleTitle);
        if (article.articleTitle == null || article.articleTitle=="") {
            toastr.warning("文章标题不能为空");
            console.log("article.articleTitle123456:" + article.articleTitle)
            return;
        }
        //内容 Html
        article.articleContent = editor.txt.html();
        console.log("articleContent" + article.articleContent);
        // if (article.articleContent == null) {
        //     toastr.warning("文章内容不能为空");
        //     return;
        // }
        //类别 id
        article.category = {};
        article.category.categoryId = $('#category-select').val();
        if (article.category.categoryId == null) {
            toastr.warning("类别不能为空");
            return;
        }
        //标签
        var articleTags = new Array()
        for (var i = 0; i < $('.select2-selection__choice').length; i++) {
            var optionVal = $('.select2-selection__choice')[i].getAttribute('title');
            articleTags.push(optionVal);
        }
        // article.articleTags = arrayObj;

        if (articleTags.length <= 0) {
            toastr.warning("标签不能为空");
            return;
        }
        // //缩略图
        var articleImg = $('#thumbnail')[0].files[0];

        if (articleImg != null) {
            if (articleImg.size > imgSize) {
                toastr.warning("缩略图最大为4M");
                return;
            }
        }

        console.log("----articleImg----", articleImg);
        //console.log("----articleImg.size----", articleImg.size);
        var formDate = new FormData();
        formDate.append('articleStr', JSON.stringify(article));
        formDate.append('articleTags', articleTags);
        formDate.append('articleImg', articleImg);
        console.log("formDate", formDate);

        $.ajax({
            url: articleInfoUrl,
            type: 'PUT',
            data: formDate,
            contentType: false,
            processData: false,
            cache: false,
            headers:{
                accessToken:getAdminToken()
            },
            success: function (data) {
                if (data.code == 0) {
                    toastr.info(data.msg);
                } else {
                    toastr.warning(data.msg);
                }
            }
        });
    })
});
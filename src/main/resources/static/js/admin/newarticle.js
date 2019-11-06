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
    };
    //自定义上传图片事件
    editor.customConfig.uploadImgHooks = {
        customInsert: function (insertImg, result, editor) {
            if (result.code == 0) {
                var url = result.data;
                insertImg(url);
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
            //console.log(this.result)
        }
    });

    //回显类别，标签
    var initUrl = '/blog/manage/getcategorytagslist';
    //添加文章
    var addArticleUrl = '/blog/manage/article';
    //回显类别，标签
    getCategoryTags();

    //显示分类，标签
    function getCategoryTags() {
            $.ajax({
                url: initUrl,
                type:'get',
                dataType: 'json',
                headers:{
                    accessToken:getAdminToken()
                },
                success:function (data) {
                    if (data.code == 0) {
                        var categoryHtml = '';
                        var tagsHtml = '';
                        data.data.categoryList.map(function (item, index) {
                            categoryHtml += `<option value=${item.categoryId}>${item.categoryName}</option>`
                        });
                        data.data.tagsList.map(function (item, index) {
                            tagsHtml += `<option value=${item.tagName}>${item.tagName}</option>`
                        });
                        $('#category-select').html(categoryHtml);
                        $('#tags-select').html(tagsHtml);
                    }
                }

        });
    };

    $('#btn-submit').click(function () {

        var article = {};
        // //标题
        article.articleTitle = $('#article-title').val();
        console.log("articleTitle" + article.articleTitle);
        if (article.articleTitle == null || article.articleTitle == "") {
            toastr.warning("文章标题不能为空");
            return;
        }
        //内容 Html
        article.articleContent = editor.txt.html();
        console.log("articleContent" + article.articleContent);
        if (article.articleContent == null) {
            toastr.warning("文章内容不能为空");
            return;
        }
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
        if (articleTags.length <= 0) {
            toastr.warning("标签不能为空");
            return;
        }
        //缩略图
        var articleImg = $('#thumbnail')[0].files[0];

        if (articleImg == null) {
            toastr.warning("缩略图不能为空");
            return;
        }else {
            if (articleImg.size > imgSize) {
                toastr.warning("缩略图最大为4M");
                return;
            }
        }
        var formData = new FormData();
        formData.append('articleStr', JSON.stringify(article));
        formData.append('articleTags', articleTags);
        formData.append('articleImg', articleImg);
        $.ajax({
            url: addArticleUrl,
            type: 'POST',
            data: formData,
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
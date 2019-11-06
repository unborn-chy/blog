$(function () {
    toastr.options.positionClass = 'toast-top-center';
    var initUrl = '/blog/manage/tags';

    var tagId = getQueryString("tagid");
    console.log("tagId :" + tagId);
    var isEdit = tagId != '' ? true : false;
    var surebtn = `<button type="button" class="btn btn-primary btn-sm" id="btn-tag">${isEdit?'确认修改':'确认添加'}</button>`;
    getTagsList();

    function getTagsList() {
        $.ajax({
            url: initUrl,
            type: 'get',
            dataType: 'json',
            headers: {
                accessToken: getAdminToken()
            },
            success: function (data) {
                if (data.code == 0) {
                    var tagsHtml = "";
                    var tagsList = data.data.tagsList;
                    tagsList.map(function (item, index) {
                        tagsHtml += `<li>`
                            + `<a data-pjax="true" class="btn btn-default btn-sm" href="/blog/admin/tag?tagid=${item.tagId}">${item.tagName}</a>`
                            + `</li>`
                    });
                    $('.tags').html(tagsHtml);
                    var count = data.data.count;
                    // $('.all-tags').html(`所有标签(${count})`);
                    $('.all-tags').html(`所有标签(${count})`);
                }
            }
        });

        $('.box-footer').html(surebtn);
    }

    //添加修改
    $('#btn-tag').click(function () {
       var  addTagsUrl = '/blog/manage/tag';
       var updateUrl = '/blog/manage/tag/' + tagId;
        var tagName = $('#tagName').val();
        if (tagName == null || tagName == '') {
            toastr.error("名称不能为空");
            return;
        }
        $.ajax({
            url:(isEdit==true)?updateUrl:addTagsUrl,
            type: (isEdit==true)?'put':'post',
            data: {
                tagName: tagName
            },
            headers: {
                accessToken: getAdminToken()
            },
            success:function (data) {
                if (data.code==0){
                    toastr.success(data.msg);
                    getTagsList();
                } else {
                    toastr.error(data.msg);
                }
            }
        })
    });


    //回显
    if(isEdit){
        var rebtn  = `<a href="/blog/admin/tag" class="btn btn-info btn-sm " style="margin-left: 7px;">返回添加</a>`;
       var delebtn =  `<a class="btn btn-danger btn-sm pull-right btn-delete">永久删除 </a>`;

        var getTagUrl = '/blog/manage/tag/' + tagId;
        $.ajax({
            url: getTagUrl,
            type: 'get',
            dataType: 'json',
            headers: {
                accessToken: getAdminToken()
            },
            success: function (data) {
                if (data.code == 0) {
                    $('#tagName').val(data.data.tag.tagName);
                    $('.box-footer').append(rebtn + delebtn)
                } else {
                    toastr.error(data.msg);
                }
            }
        })
    }
    //删除
    // $('.btn-delete').click(function () {
    $('.box-footer').on('click','.btn-delete',function (e) {
        var deleteUrl = '/blog/manage/tag/' + tagId;
        console.log("deleteUrl:::"+ deleteUrl);
        //弹出确认框
        $('.common-confirm-delete').modal('show');
        $('.delete-true').click(function () {
            $.ajax({
                url:deleteUrl,
                type:'delete',
                headers: {
                    accessToken: getAdminToken()
                },
                success:function (data) {
                    if(data.code==0){
                        toastr.success(data.msg);
                        //重新加载
                        setTimeout('window.location.href= "/blog/admin/tag"',"1000");
                    }else {
                        toastr.error(data.msg);
                        setTimeout('window.location.href= "/blog/admin/tag"',"1000");
                    }
                }
            });
            $('.common-confirm-delete').modal('hide');
        })
    })
});
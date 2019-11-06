$(function () {
    toastr.options.positionClass = 'toast-top-center';
    var imgSize = 4 * 1024 * 1024;
    var getHeadLineListUrl = '/blog/manage/headlines';
    var addHeadLineUrl = '/blog/manage/headline';
    var lineId = getQueryString("lineid");
    var updateHeadlineUrl = '/blog/manage/headline/' + lineId;
   // console.log("lineId :" + lineId);
    var isEdit = lineId != '' ? true : false;

    $(".headline-img-select").on("change", function (e) {
        var e = e || window.event;
        //获取 文件 个数 取消的时候使用
        var files = e.target.files;
        if (files.length > 0) {
            // 获取文件名 并显示文件名
            var fileName = files[0].name;
            $(".headline-img-input").val(fileName);
        } else {
            //清空文件名
            $(".headline-img-input").val("");
        }
    });


    //获取headLineList
    getHeadLIneList();
    function getHeadLIneList() {
        $.ajax({
            url:getHeadLineListUrl,
            type:'get',
            dataType:'json',
            headers:{
                accessToken:getAdminToken()
            },
            success:function (data) {
                if (data.code == 0) {
                    var getHeadLineHtml = "";
                    data.data.headLineList.map(function (item, index) {
                        getHeadLineHtml += `<tr>`
                            + `<td>${item.lineName}</td>`
                            + `<td>${item.lineLink}</td>`
                            + `<td><img src="${item.lineImg}" width="200px" height="200px"></td>`
                            + `<td>${(new Date(item.createTime)).Format("yyyy-MM-hh")}</td>`
                            + `<td>`
                            + `<a type="button" class="btn  btn-primary btn-xs"  href="/blog/admin/headline?lineid=${item.lineId}" >编辑</a>`
                            + `<a type="button" class="btn  btn-danger btn-xs btn-delete-headline" data-line-id="${item.lineId}">删除</a>`
                            + `</td>`
                            + `</tr>`;

                    });
                    $('#get-head-line').html(getHeadLineHtml);
                }
            }
        })
    }

    //返回主页
    //添加 修改
    $('#modal-preserve').click(function () {
        var lineName = $('#head-line-name').val();
        if (lineName == null || lineName == ""||lineName.trim().length==0) {
            toastr.warning('名称不能为空');
            return;
        }
        var lineLink = $('#head-line-link').val();
        if (lineLink == null || lineLink == ""||lineLink.trim().length==0) {
            toastr.warning('链接不能为空');
            return;
        }
        //图片 headline-img-input
        var lineImgText = $('#headline-img-input').val();
        if (lineImgText == null || lineImgText == ""||lineImgText.trim().length==0) {
            toastr.warning('图片不能为空');
            return;
        }
        console.log("lineImgText" + lineImgText);

        var lineImg = $('#head-line-file-img')[0].files[0];

        if (lineImg == null) {
            lineImg = "";
        } else {
            if (lineImg.size > imgSize) {
                toastr.warning("图片最大为4M");
                return;
            }
        }
        //得到文件后缀名称
        var suffix = lineImgText.substring(lineImgText.lastIndexOf('.') + 1);
        console.log("suffix::" + suffix.length + suffix);
        if (suffix != "png" && suffix != "jpg" && suffix != "gif") {
            toastr.warning("图片格式只能是 png,jpg,gif");
            return;
        }

        var formData = new FormData();
        formData.append('lineName', lineName);
        formData.append('lineLink', lineLink);
        formData.append('lineImgText', lineImgText);
        formData.append('lineImg', lineImg);

        $.ajax({
            url: (isEdit==true)?updateHeadlineUrl:addHeadLineUrl,
            type: (isEdit==true)?'put':'post',
            cache: false,
            processData: false,
            contentType: false,
            data: formData,
            headers:{
                accessToken:getAdminToken()
            },
            success: function (data) {
                if (data.code == 0) {
                    toastr.success(data.msg);
                    (isEdit==true)? setTimeout("window.location.href = '/blog/admin/headline'", "1000"):getHeadLIneList();
                } else {
                    toastr.error(data.msg);
                }
            }
        });
        $('#add-update-modal').modal('hide');
        $('#head-line-name').val('');
        $('#head-line-link').val('');
        $('#headline-img-input').val('');
    });

    $('#modal-off').click(function () {
        $('#add-update-modal').modal('hide');
        $('#head-line-name').val('');
        $('#head-line-link').val('');
        $('#headline-img-input').val('');
    });

    //

    //回显
    if(isEdit){
        var getHeadLineUrl = '/blog/manage/headline/' + lineId;
        $.ajax({
            url:getHeadLineUrl,
            type:'get',
            dataType:'json',
            headers:{
                accessToken:getAdminToken()
            },
            success:function (data) {
                if (data.code == 0) {
                    $('#head-line-name').val(data.data.headLine.lineName);
                    $('#head-line-link').val(data.data.headLine.lineLink);
                    $('#headline-img-input').val(data.data.headLine.lineImg);
                    $('#add-update-modal').modal('show');
                } else {
                    toastr.error(data.msg);
                }
            }

        });
    }
    //删除

    $('#get-head-line').on('click','.btn-delete-headline',function (e) {
        var lineId = e.target.dataset.lineId;
        var deleteHeadlineUrl = '/blog/manage/headline/' + lineId;
        console.log("lineId delete :" +lineId);
        $('.common-confirm-delete').modal('show');
        $('.delete-true').click(function () {
            $.ajax({
                url:deleteHeadlineUrl,
                type:'delete',
                headers:{
                    accessToken:getAdminToken()
                },
                success:function (data) {
                    if(data.code==0){
                        toastr.success(data.msg);
                        getHeadLIneList();
                    }else {
                        toastr.error(data.msg);
                    }
                }
            });
            $('.common-confirm-delete').modal('hide');
        })
    })
});
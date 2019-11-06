$(function () {
    toastr.options.positionClass = 'toast-top-center';
    var adviceId = getQueryString("adviceid");
    var isEdit = adviceId != '' ? true : false;
    var getAllAdviceUrl = '/blog/manage/advices';
    var addAdviceUrl = '/blog/manage/advice';
    var getAdviceUrl = '/blog/manage/advice/' + adviceId;
    var updateAdviceUrl = '/blog/manage/advice/' + adviceId;
    getAllAdvice();

    function getAllAdvice() {
        $.ajax({
            url: getAllAdviceUrl,
            type: 'get',
            dataType: 'json',
            headers: {
                accessToken: getAdminToken()
            },
            success: function (data) {
               if(data.code ==0){
                   var adviceList = data.data;
                   var adviceListHtml='';
                   adviceList.map(function (item,index) {
                       adviceListHtml +=`<tr>`
                                    +`<td>${item.adviceContent}</td>`
                                    +`<td>`
                                       +`<a data-pjax="true" href="/blog/admin/advice?adviceid=${item.adviceId}" class="btn btn-primary btn-xs " >修改</a>`
                                        +`<button class="btn btn-danger btn-xs delete-advice" data-advice-id="${item.adviceId}"  style="margin-left: 5px">永久删除</button>`
                                    +`</td>`
                                +`</tr>`
                   })
                   $('#advice-tbody').html(adviceListHtml);
               }
            }
        })

    }

    $('#btn-advice').click(function () {
        var adviceContent = $('#advice-content').val();
        console.log("adviceContent:"  +adviceContent)
        if (adviceContent == null || adviceContent == '' || adviceContent.trim().length==0) {
            toastr.warning("公告不能为空或者空格");
            return;
        }
        $.ajax({
            url: (isEdit == true) ? updateAdviceUrl : addAdviceUrl,
            type: (isEdit == true) ? 'put' : 'post',
            async: false,
            data: {
                adviceContent: adviceContent,
            },
            headers: {
                accessToken: getAdminToken()
            },
            success: function (data) {
                if (data.code == 0) {
                    toastr.success(data.msg);
                    //重新加载
                    getAllAdvice();
                    $('#advice-content').val('');
                } else {
                    toastr.error(data.msg);
                }
            }
        });

    });

    //回显
    if (isEdit) {
        $.ajax({
            url: getAdviceUrl,
            type: 'get',
            dataType: 'json',
            headers: {
                accessToken: getAdminToken()
            },
            success: function (data) {
                if (data.code == 0) {
                   $('#advice-content').val(data.data.adviceContent);
                   $('.advice-title').html("修改公告")
                   $('#btn-advice').html("确认修改")
                }
            }
        })
    }

    //删除
    $('#advice-tbody').on('click', '.delete-advice', function (e) {
        var adviceId = e.target.getAttribute("data-advice-id");
        var deleteAdviceUrl = '/blog/manage/advice/' + adviceId;
        //弹出确认框
        $('.common-confirm-delete').modal('show');
        $('.delete-true').click(function () {
            $.ajax({
                url: deleteAdviceUrl,
                type: 'delete',
                headers: {
                    accessToken: getAdminToken()
                },
                success: function (data) {
                    if (data.code == 0) {
                        toastr.success(data.msg);
                        //从新加载
                        getAllAdvice();
                    } else {
                        toastr.error(data.msg);
                    }
                }
            });
            $('.common-confirm-delete').modal('hide');
        })
    })
});
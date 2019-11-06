$(function () {
    toastr.options.positionClass = 'toast-top-center';
    var getUserUrl = '/blog/manage/getuserinfo';
    $.ajax({
        url: getUserUrl,
        type: 'get',
        dataType: 'json',
        headers: {
            accessToken: getAdminToken()
        },
        success: function (data) {
            if (data.code == 0) {
                var userInfo = data.data.userInfo;
                $('.user-info-name').html(userInfo.username);
                $('.img-circle').attr('src',userInfo.userImg);
            } else {
                toastr.error(data.msg);
            }
        }
    });


    //退出
    $('.user-exit').click(function () {
        document.cookie="token=" + null;
        saveAdminToken("");
        window.location.href = '/blog/admin/login';
    })
});
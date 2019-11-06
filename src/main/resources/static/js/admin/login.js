$(function () {
    toastr.options.positionClass = 'toast-top-center';
    var userRegisterUrl = '/blog/manage/register';
    var userLoginUrl = '/blog/manage/login';
    var findPasswordUrl = '/blog/manage/findpassword';

    $('input').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%' // optional
    });


    //注册
    $('#user-register').click(function () {
        var username = $('#user-name').val();
        if (username == null || username == '' || username.trim().length == 0) {
            toastr.warning("用户名不能为空或者空格");
            return;
        }
        var userEmail = $('#user-email').val();
        if (userEmail == null || userEmail == '' || userEmail.trim().length == 0) {
            toastr.warning("邮箱不能为空");
            return;
        }
        var isEmaile = checkEmail(userEmail);
        console.log("isEmaile:" + checkEmail(userEmail))
        if (!isEmaile) {
            toastr.warning("邮箱格式不正确");
            return;
        }
        var password = $('#user-password').val();
        if (password == null || password == '' || password.trim().length == 0) {
            toastr.warning("密码不能为空或者空格")
            return;
        }

        $.ajax({
            url: userRegisterUrl,
            type: 'post',
            async:false,
            data:{
                username:username,
                userEmail:userEmail,
                password:password
            },
            success:function (data) {
                if(data.code==0){
                    toastr.success(data.msg);
                    setTimeout("window.location.href='/blog/admin/login'", "2000");
                }else {
                    toastr.error(data.msg)
                }
            }
            }
        )
    });

    console.log("getUsername():"+getUsername());
    if(getUsername()!=null && getPassword()!=null){
        $('#user-name-email').val(getUsername());
        $('#user-password-login').val(getPassword());
    }



    //登录
    $('#user-login').click(function () {
        var usernameOrEmail = $('#user-name-email').val();
        if (usernameOrEmail == null || usernameOrEmail == '' || usernameOrEmail.trim().length == 0) {
            toastr.warning("用户不能为空或者空格");
            return;
        }
        var password = $('#user-password-login').val();
        if (password == null || password == '' || password.trim().length == 0) {
            toastr.warning("密码不能为空或者空格")
            return;
        }

        $.ajax({
                url: userLoginUrl,
                type: 'post',
                async:false,
                data:{
                    usernameOrEmail:usernameOrEmail,
                    password:password
                },
                success:function (data) {
                    if(data.code==0){
                        toastr.success(data.msg);
                        document.cookie="token=" + data.data;
                        saveAdminToken(data.data);
                        console.log("token:" + data.data);
                        //保存账号密码
                        if($('#remember-me').is(':checked')){
                            saveUsername(usernameOrEmail);
                            savePassword(password);
                        }
                        window.location.href='/blog/index';

                    }else {
                        toastr.error(data.msg);
                    }
                }
            }
        )
    });

    //找回密码
    $('#find-user-psw').click(function () {
        var username = $('#user-name').val();
        if (username == null || username == '' || username.trim().length == 0) {
            toastr.warning("用户名不能为空或者空格");
            return;
        }
        var userEmail = $('#user-email').val();
        if (userEmail == null || userEmail == '' || userEmail.trim().length == 0) {
            toastr.warning("邮箱不能为空");
            return;
        }
        var isEmaile = checkEmail(userEmail);
        console.log("isEmaile:" + checkEmail(userEmail))
        if (!isEmaile) {
            toastr.warning("邮箱格式不正确");
            return;
        }


        $.ajax({
                url: findPasswordUrl,
                type: 'post',
                data:{
                    username:username,
                    userEmail:userEmail,
                },
                success:function (data) {
                    if(data.code==0){
                        toastr.success(data.msg);
                        //setTimeout("window.location.href='/blog/admin/login'", "2000");

                    }else {
                        toastr.error(data.msg)
                    }
                }
            }
        )
    });

});
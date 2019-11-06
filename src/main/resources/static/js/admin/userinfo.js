$(function () {
    toastr.options.positionClass = 'toast-top-center';
    var imgSize = 4 * 1024 * 1024;
    var getUserInfo = '/blog/manage/getuserinfo';
    var updateInfoUrl = '/blog/manage/updateuserinfo';
    var updatePaw = '/blog/manage/updatepsw';

    $.ajax({
        url: getUserInfo,
        type: 'get',
        dataType: 'json',
        headers: {
            accessToken: getAdminToken()
        },
        success: function (data) {
            if (data.code == 0) {
                var userInfo = data.data.userInfo;
                (userInfo.userType == 0) ? $('#user-type').html('万恶管理员') : $('#user-type').html('投稿者');
                $('#user-name').val(userInfo.username);
                $('#user-email').val(userInfo.userEmail);
                $('#user-img-input').val(userInfo.userImg);
                $('#user-explain').val(userInfo.userExplain);
            } else {
                toastr.error(data.msg);
            }
        }
    });

    //修改信息
    $('#user-preserve').click(function () {
        var username = $('#user-name').val();
        if(username==null || username==''||username.trim().length==0){
            toastr.warning("用户名称不能为空");
            return;
        }
        var userEmail = $('#user-email').val();
        if(userEmail==null || userEmail==''||userEmail.trim().length==0){
            toastr.warning("邮箱不能为空");
            return;
        }
       var isEmaile = checkEmail(userEmail);

        console.log("isEmaile:" + checkEmail(userEmail))
        if(!isEmaile){
            toastr.warning("邮箱格式不正确");
            return;
        }

        //图片 user-img-input
        var userImgText = $('#user-img-input').val();
        if (userImgText == null || userImgText == ""||userImgText.trim().length==0) {
            toastr.warning('图片不能为空');
            return;
        }
        console.log("lineImgText" + userImgText);

        var userImgInfo = $('#user-img-file')[0].files[0];

        if (userImgInfo == null) {
            userImgInfo = "";
        } else {
            if (userImgInfo.size > imgSize) {
                toastr.warning("图片最大为4M");
                return;
            }
        }
        var userExplain = $('#user-explain').val();
        var formData = new FormData();

        formData.append("username",username);
        formData.append("userEmail",userEmail);
        formData.append("userImgText",userImgText);
        formData.append("userImgInfo",userImgInfo);
        formData.append("userExplain",userExplain);
        $.ajax({
            url:updateInfoUrl,
            type:'put',
            cache: false,
            processData: false,
            contentType: false,
            data: formData,
            headers: {
                accessToken: getAdminToken()
            },
            success:function (data) {
                if(data.code==0){
                    toastr.success(data.msg);
                    setTimeout("window.location.href='/blog/admin/login'", "1500");
                }else {
                    toastr.error(data.msg)
                }
            }
        });
    });

    //修改密码

    $('#update-psw-btn').click(function () {
        //原密码
        var lodPassword = $('#old-password').val();
        var newPassword = $('#new-password').val();
        if(newPassword==null||newPassword ==''||newPassword.trim().length ==0){
            toastr.warning("新密码不能为空或者空格")
            return;
        }
        var reNewPassword = $('#re-new-password').val();
        if(reNewPassword==null||reNewPassword ==''||reNewPassword.trim().length ==0){
            toastr.warning("新密码不能为空或者空格");
            return;
        }
        if(newPassword!=reNewPassword){
            toastr.warning("两次新密码不相同");
            return;
        }

      $.ajax({
          url:updatePaw,
          type:'put',
          data: {
              lodPassword:lodPassword,
              newPassword:newPassword
          },
          headers: {
              accessToken: getAdminToken()
          },
          success:function (data) {
              if(data.code==0){
                  toastr.success(data.msg)
                  setTimeout("window.location.href='/blog/admin/login'", "1500");
              }else {
                  toastr.error(data.msg)
              }
          }
      });
        console.log("lodPassword" + lodPassword);
    });

    $(".user-img-select").on("change", function (e) {
        var e = e || window.event;
        //获取 文件 个数 取消的时候使用
        var files = e.target.files;
        if (files.length > 0) {
            // 获取文件名 并显示文件名
            var fileName = files[0].name;
            $(".user-img-input").val(fileName);
        } else {
            //清空文件名
            $(".user-img-input").val("");
        }
    });



});
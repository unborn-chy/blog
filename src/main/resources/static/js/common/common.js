function getUrlParameter() {
    var winUrl = decodeURI(window.location.href);
    var start = winUrl.lastIndexOf("/");
    var end = winUrl.indexOf("?");
    //console.log("end:" + end);
    if(end<=0){
        var result = winUrl.substring(start + 1);
        //console.log("resultIf:" + result);
        return result;
    }else {
        var result = winUrl.slice(start+1, end);
        //console.log("resultElse:" + result);
        return result;
    }


}

function isRealNum(val) {
    // isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除
    if (val === "" || val == null) {
        return false;
    }
    if (!isNaN(val)) {
        return true;
    } else {
        return false;
    }
}

function getQueryString(name) {
    //匹配
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
    return '';
}


//格式化时间
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

//检查邮箱格式
function checkEmail(email) {
    var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
    if (reg.test(email)) {
        return true;
    } else {
        return false;
    }
}

function getAdminToken() {
    return localStorage.getItem("accessToken")
}

function saveAdminToken(token) {
    localStorage.setItem("accessToken", token)
}

function saveUsername(username) {
    localStorage.setItem("username", username)
}
function savePassword(password) {
    localStorage.setItem("password", password)
}
function getUsername() {
    return localStorage.getItem("username")
}
function getPassword() {
    return localStorage.getItem("password")
}



//截取文章简介
function subArticleContent(articleContent) {
    var result = articleContent.slice(0, 80);
    return result+"..."
}

//截取评论简介
function subCommentContent(commentContent) {
    var result = commentContent.slice(0, 14);
    return result+"..."
}
//截取tag 或者category

function subTsgOrCat() {
     var winUrl = window.location.href;
    var start = winUrl.indexOf("/blog") + 6;
    var end = winUrl.lastIndexOf("/");
    var result = winUrl.slice(start, end);
    return result;
}
$(document).ready(function () {
// 搜索
    $(".nav-search").click(function () {
        $("#search-main").fadeToggle(300);
    });

// 菜单
    $(".nav-mobile").click(function () {
        $("#mobile-nav").slideToggle(500);
    });

// 引用
    $(".backs").click(function () {
        $(".track").slideToggle("slow");
        return false;
    });

// 分享
    if (/iphone|ipod|ipad|ipad|mobile/i.test(navigator.userAgent.toLowerCase())) {
        $('.share-sd').click(function () {
            $('#share').animate({
                    opacity: 'toggle',
                    top: '-80px'
                },
                500).animate({
                    top: '-60px'
                },
                'fast');
            return false;
        });
    } else {
        $(".share-sd").mouseover(function () {
            $(this).children("#share").show();
        });
        $(".share-sd").mouseout(function () {
            $(this).children("#share").hide();
        });
    }

// 关闭
    $('.shut-error').click(function () {
        $('.user_error').animate({
                opacity: 'toggle'
            },
            100);
        return false;
    });

// 文字展开
    $(".show-more span").click(function (e) {
        $(this).html(["<i class='fa fa-plus-square'></i>展开", "<i class='fa fa-minus-square'></i>折叠"][this.hutia ^= 1]);
        $(this.parentNode.parentNode).next().slideToggle();
        e.preventDefault();
    });

// 滚屏
    $('.scroll-h').click(function () {
        $('html,body').animate({
                scrollTop: '0px'
            },
            800);
    });
    $('.scroll-c').click(function () {
        $('html,body').animate({
                scrollTop: $('.scroll-comments').offset().top
            },
            800);
    });
    $('.scroll-b').click(function () {
        $('html,body').animate({
                scrollTop: $('.site-info').offset().top
            },
            800);
    });


    // 文字滚动
    (function ($) {
        $.fn.textSlider = function (settings) {
            settings = jQuery.extend({
                    speed: "normal",
                    line: 2,
                    timer: 1000
                },
                settings);
            return this.each(function () {
                $.fn.textSlider.scllor($(this), settings)
            })
        };
        $.fn.textSlider.scllor = function ($this, settings) {
            var ul = $("ul:eq(0)", $this);
            var timerID;
            var li = ul.children();
            var _btnUp = $(".up:eq(0)", $this);
            var _btnDown = $(".down:eq(0)", $this);
            var liHight = $(li[0]).height();
            var upHeight = 0 - settings.line * liHight;
            var scrollUp = function () {
                _btnUp.unbind("click", scrollUp);
                ul.animate({
                        marginTop: upHeight
                    },
                    settings.speed,
                    function () {
                        for (i = 0; i < settings.line; i++) {
                            ul.find("li:first").appendTo(ul)
                        }
                        ul.css({
                            marginTop: 0
                        });
                        _btnUp.bind("click", scrollUp)
                    })
            };
            var scrollDown = function () {
                _btnDown.unbind("click", scrollDown);
                ul.css({
                    marginTop: upHeight
                });
                for (i = 0; i < settings.line; i++) {
                    ul.find("li:last").prependTo(ul)
                }
                ul.animate({
                        marginTop: 0
                    },
                    settings.speed,
                    function () {
                        _btnDown.bind("click", scrollDown)
                    })
            };
            var autoPlay = function () {
                timerID = window.setInterval(scrollUp, settings.timer)
            };
            var autoStop = function () {
                window.clearInterval(timerID)
            };
            ul.hover(autoStop, autoPlay).mouseout();
            _btnUp.css("cursor", "pointer").click(scrollUp);
            _btnUp.hover(autoStop, autoPlay);
            _btnDown.css("cursor", "pointer").click(scrollDown);
            _btnDown.hover(autoStop, autoPlay)
        }
    })(jQuery);

// 去边线
    $(".message-widget li:last, .message-page li:last, .hot_commend li:last, .search-page li:last, .my-comment li:last, .message-tab li:last").css("border", "none");

// 表情
    $('.emoji').click(function () {
        $('.emoji-box').animate({
                opacity: 'toggle',
                left: '50px'
            },
            1000).animate({
                left: '10px'
            },
            'fast');
        return false;
    });


// 字号
    $("#fontsize").click(function () {
        var _this = $(this);
        var _t = $(".single-content");
        var _c = _this.attr("class");
        if (_c == "size_s") {
            _this.removeClass("size_s").addClass("size_l");
            _this.text("A+");
            _t.removeClass("fontsmall").addClass("fontlarge");
        } else {
            _this.removeClass("size_l").addClass("size_s");
            _this.text("A-");
            _t.removeClass("fontlarge").addClass("fontsmall");
        }
        ;
    });

// 目录
    if (document.body.clientWidth > 1024) {
        $(function () {
            $(window).scroll(function () {
                if ($("#log-box").html() != undefined) {
                    var h = $("#title-2").offset().top;
                    if ($(this).scrollTop() > h && $(this).scrollTop() < h + 50) {
                        $("#log-box").show();
                    }
                    var h = $("#title-1").offset().top;
                    if ($(this).scrollTop() > h && $(this).scrollTop() < h + 50) {
                        $("#log-box").hide();
                        $("#log-box").show();
                    }
                }
            });
        })
    }

    $(".log-button, .log-close").click(function () {
        $("#log-box").fadeToggle(300);
    });

    if ($("#log-box").length > 0) {
        $(".log").removeClass("log-no");
    }
    $('.log-prompt').show().delay(5000).fadeOut();




// 标签背景
//     box_width = $(".single-tag").width();
//     len = $(".single-tag ul li a").length - 1;
//     $(".single-tag ul li a").each(function (i) {
//         var let = new Array('c3010a', '31ac76', 'ea4563', '31a6a0', '8e7daa', '4fad7b', 'f99f13', 'f85200', '666666');
//         var random1 = Math.floor(Math.random() * 9) + 0;
//         var num = Math.floor(Math.random() * 6 + 9);
//         $(this).attr('style', 'background:#' + let[random1] + '');
//         if ($(this).next().length > 0) {
//             last = $(this).next().position().left;
//         }
//     });

// 按钮clear
    $(".single-content").find(".down-line:last").css({clear: "both"});

// tab
    var $li = $('.zm-tabs-nav span');
    var $ul = $('.zm-tabs-container ul');
    $li.mouseover(function () {
        var $this = $(this);
        var $t = $this.index();
        $li.removeClass();
        $this.addClass('current');
        $ul.css('display', 'none');
        $ul.eq($t).css('display', 'block');
    })

// 结束
});

// 隐藏侧边
function pr() {
    var R = document.getElementById("sidebar");
    var L = document.getElementById("primary");
    if (R.className == "sidebar") {
        R.className = "sidebar-hide";
        L.className = "";
    } else {
        R.className = "sidebar";
        L.className = "primary";
    }
}









with (document) 0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];

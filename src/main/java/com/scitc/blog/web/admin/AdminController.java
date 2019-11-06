package com.scitc.blog.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AdminController {
    //写文章
    @GetMapping("/admin/article")
    public String newArticle(){
        return "admin/newarticle";
    }
    //修改文章
    @GetMapping("/admin/article/{articleId}")
    public String editArticle(@PathVariable("articleId") Integer articleId){
        return "admin/editarticle";
    }
    //文章列表
    @GetMapping("/admin/articles")
    public String articles(){
        return "admin/articlelist";
    }

    //公告
    @GetMapping("/admin/advice")
    public String advice(){
        return "admin/advice";
    }

    //评论
    @GetMapping("/admin/comment")
    public String comment(){
        return "admin/comment";
    }

    //分类
    @GetMapping("/admin/category")
    public String category(){
        return "admin/category";
    }
    //标签
    @GetMapping("/admin/tag")
    public String tag(){
        return "admin/tag";
    }
    //头条
    @GetMapping("/admin/headline")
    public String headLine(){
        return "admin/headline";
    }
    //用户列表
    @GetMapping("/admin/users")
    public String userList(){
        return "admin/userlist";
    }
    //用户列表
    @GetMapping("/admin/userinfo")
    public String userInfo(){
        return "admin/userinfo";
    }
    //仪表盘
    @GetMapping("/admin/dashboard")
    public String dashBoard(){
        return "admin/dashboard";
    }
    //登录
    @GetMapping("/admin/login")
    public String login(){
        return "admin/login";
    }
    //注册
    @GetMapping("/admin/register")
    public String register(){
        return "admin/register";
    }
    //找回密码
    @GetMapping("/admin/findpsw")
    public String findPaw(){
        return "admin/findpsw";
    }

    //博客主界面
    @GetMapping("/index")
    public String index(){
        return "frontend/index";
    }

    //标签时
    @GetMapping("/tag/{tag}")
    public String tagChoice(){
        return "frontend/choice";
    }

    //点击类别
    @GetMapping("/category/{category}")
    public String categoryChoice(){
        return "frontend/choice";
    }
    //文章详情
    @GetMapping("/article/{articleid}")
    public String article(){
        return "frontend/article";
    }

}

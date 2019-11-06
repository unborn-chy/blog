package com.scitc.blog.web.admin;

import com.scitc.blog.dto.ImageHolder;
import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.dto.Result;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.model.Article;
import com.scitc.blog.model.UserInfo;
import com.scitc.blog.service.ArticleService;
import com.scitc.blog.service.UserInfoService;
import com.scitc.blog.utils.CommonUtil;
import com.scitc.blog.utils.JwtUtils;
import com.scitc.blog.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.scitc.blog.enums.OperationEnums.NO_LOGIN;

@RestController
@RequestMapping("/manage")
public class UserInfoController {
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private UserInfoService userInfoService;

    //getList
    @GetMapping("/users")
    public Result<Map<String, Object>> getUserList(@RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize, HttpServletRequest request) {
        UserInfo currentUser = CommonUtil.getUserInfo(request);
        if (currentUser.getUserType() != 0) {
            return ResultUtils.error(OperationEnums.NO_AUTHORITY);
        }
        Map<String, Object> map = new HashMap<>();
        OperationExecution op = userInfoService.getUserInfoList(pageIndex, pageSize);
        map.put("userList", op.getDataList());
        map.put("count", op.getCount());
        return ResultUtils.success(OperationEnums.SUCCESS, map);
    }


    @GetMapping("/getuserinfo")
    public Result<Map<String, Object>> getUserInfo(HttpServletRequest request) {
        log.info("-----------------------------userInfo  ------------------------");
        UserInfo userInfo = CommonUtil.getUserInfo(request);
        if (userInfo == null) {
            return ResultUtils.error(OperationEnums.NO_LOGIN);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", userInfo);
        return ResultUtils.success(OperationEnums.SUCCESS, map);
    }

    @PutMapping("/updateuserinfo")
    public Result updateUserInfo(@Valid UserInfo userInfo, BindingResult bindingResult,
                                 @RequestParam(value = "userImgInfo", required = false) MultipartFile userImgInfo,
                                 @RequestParam("userImgText") String userImgText,
                                 HttpServletRequest request) {
        log.info("进来了");
        if (bindingResult.hasErrors()) {
            return ResultUtils.error(bindingResult.getFieldError().getDefaultMessage());
        }
        UserInfo currentUserInfo = CommonUtil.getUserInfo(request);
        userInfo.setUserId(currentUserInfo.getUserId());

        log.info("userInfo:{}", userInfo);
        //判断图片
        MultipartFile userImg = userImgInfo;
        log.info("userImgText：{}", userImgText);
        ImageHolder imageHolder = null;
        if (userImg != null && !"".equals(userImg)) {
            try {
                imageHolder = new ImageHolder(userImgInfo.getOriginalFilename(), userImgInfo.getInputStream());
            } catch (IOException e) {
                throw new BlogException("图片转换失败" + e.getMessage());
            }
        } else {
            imageHolder = new ImageHolder(userImgText, null);
        }
        OperationExecution oe = userInfoService.updateUserInfo(userInfo, imageHolder);
        return ResultUtils.success(oe.getStateInfo());
    }

    //更新密码
    @PutMapping("/updatepsw")
    public Result updatePsw(@RequestParam("lodPassword") String lodPassword, @RequestParam("newPassword") String newPassword,
                            HttpServletRequest request) {
        log.info("进来了");
        log.info("lodPassword:" + lodPassword + "newPassword: " + newPassword);
        UserInfo currentUserInfo = CommonUtil.getUserInfo(request);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(currentUserInfo.getUserId());
        userInfo.setPassword(lodPassword);
        OperationExecution oe = userInfoService.updateUserPsw(userInfo, newPassword);
        return ResultUtils.success(oe.getStateInfo());

    }

    //注册
    @PostMapping("/register")
    public Result register(@Valid UserInfo userInfo, BindingResult bindingResult, HttpServletRequest request) {
        log.info("进来了");
        if (bindingResult.hasErrors()) {
            return ResultUtils.error(bindingResult.getFieldError().getDefaultMessage());
        }
        log.info("userInfo:{}", userInfo);
        OperationExecution oe = userInfoService.registerUserInfo(userInfo);
        return ResultUtils.success(oe.getStateInfo());
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestParam("usernameOrEmail") String usernameOrEmail, @RequestParam("password") String password, HttpServletRequest request) {
        OperationExecution oe = userInfoService.login(usernameOrEmail, password);
        UserInfo userInfo = (UserInfo) oe.getData();
        request.getSession().setAttribute("userInfo", userInfo);
        String token = JwtUtils.sign(userInfo);
        return ResultUtils.success(OperationEnums.LOGIN_SUCCESS, token);
    }

    //找回密码
    @PostMapping("/findpassword")
    public Result findPassword(@RequestParam("username") String username, @RequestParam("userEmail") String userEmail) {
        OperationExecution oe = userInfoService.findByPassword(username, userEmail);
        return ResultUtils.success(oe.getStateInfo());
    }

    //删除用户   先查询用户下的所有 文章，在删除用户
    @DeleteMapping("/userinfo/{userId}")
    public Result deleteUser(@PathVariable("userId") Integer userId, HttpServletRequest request) {
        UserInfo currentUser = CommonUtil.getUserInfo(request);
        if (currentUser.getUserType() != 0) {
            return ResultUtils.error(OperationEnums.NO_AUTHORITY);
        }
        userInfoService.deleteUserInfo(userId);
        return ResultUtils.success(OperationEnums.DELETE_SUCCESS);
    }

    //设置用户为管理员
    @PutMapping("/userinfo/{userId}")
    public Result updateUserType(@PathVariable("userId") Integer userId, HttpServletRequest request) {
        UserInfo currentUser = CommonUtil.getUserInfo(request);
        if (currentUser.getUserType() != 0) {
            return ResultUtils.error(OperationEnums.NO_AUTHORITY);
        }
        userInfoService.updateUserType(userId);
        return ResultUtils.success(OperationEnums.UPDATE_SUCCESS);
    }
}

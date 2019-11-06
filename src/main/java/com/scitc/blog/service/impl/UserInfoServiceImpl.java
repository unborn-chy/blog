package com.scitc.blog.service.impl;

import com.scitc.blog.dto.ImageHolder;
import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.mapper.ArticleMapper;
import com.scitc.blog.mapper.UserInfoMapper;
import com.scitc.blog.model.Article;
import com.scitc.blog.model.UserInfo;
import com.scitc.blog.service.UserInfoService;
import com.scitc.blog.utils.CommonUtil;
import com.scitc.blog.utils.EmailUtils;
import com.scitc.blog.utils.ImageUtil;
import com.scitc.blog.utils.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.List;


@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger log = LoggerFactory.getLogger(UserInfoService.class);
    @Value(value = "${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private EmailUtils emailUtils;

    @Override
    @Transactional
    public OperationExecution registerUserInfo(UserInfo userInfo) throws BlogException {
        //注册用户 传过来应该有 用户名，邮箱，密码
        if (userInfo.getUsername().trim().isEmpty() || userInfo.getUsername().trim().length() == 0 || userInfo.getPassword().trim().isEmpty() || userInfo.getPassword().trim().length() == 0) {
            throw new BlogException(OperationEnums.NAME_PSW_NULL);
        }
        //判断用户存在
        UserInfo tempUserInfo = userInfoMapper.queryUserInfo(null, userInfo.getUsername(), null);
        if (tempUserInfo != null) {
            throw new BlogException(OperationEnums.USERNAME__EXIST);
        }
        //邮箱存在
        tempUserInfo = userInfoMapper.queryUserInfo(null, null, userInfo.getUserEmail());
        if (tempUserInfo != null) {
            throw new BlogException(OperationEnums.EMAIL_EXIST);
        }
        //注册用户
        userInfo.setUserType(1);
        //设置默认头像
        File fileImg = new File("classpath:/img/user-head-img.jpg");

        String userImg = contextPath + "/img/" + fileImg.getName();
        userInfo.setUserImg(userImg);
        userInfo.setCreateTime(new Date());
        int effectedNum = userInfoMapper.insertUserInfo(userInfo);
        if (effectedNum <= 0) {
            throw new BlogException(OperationEnums.REGISTER_ERROR);
        }
        return new OperationExecution<UserInfo>(OperationEnums.SUCCESS, userInfo);
    }

    @Override
    @Transactional
    public OperationExecution updateUserInfo(UserInfo userInfo, ImageHolder imageHolder) throws BlogException {
        // 更新用户信息 传过来应该有 userId
        if (userInfo.getUserId() == null) {
            throw new BlogException(OperationEnums.NULL_ID);
        }
        //1.不修改账号，邮箱
        if (userInfo.getUsername().trim().isEmpty() || userInfo.getUsername().trim().length() == 0 || userInfo.getUserEmail().trim().isEmpty() || userInfo.getUserEmail().trim().length() == 0) {
            throw new BlogException(OperationEnums.NAME_PSW_NULL);
        }
        //

        /**
         * 2. 判断邮箱账号
         *    1..用传入的userId去查询完整的用户信息
         *    2..判断用户信息是否有改变（因为是判断 对象 中的值是否相等 所有用equals（equals：只判断值是否相等），如果用 == （ ==：判断值和地址值是否相等） ）
         *                              没改变就向下执行，
         *                              有改变  进入if: 1.判断改变后的值是否与其他已有的值重复，只要能拿到数据，说明就重复
         *                                              2.不重复，那就存入进入，如果存入失败就说明邮箱重复了（数据库设置的唯一属性)
         */


        UserInfo tempUserInfoById = userInfoMapper.queryUserInfo(userInfo.getUserId(), null, null);
        log.info("tempUserInfoById:{}", tempUserInfoById);
        log.info("tempUserInfoById.getUsername():{}", tempUserInfoById.getUsername());
        log.info("userInfo.getUsername():{}", userInfo.getUsername());

        if (!tempUserInfoById.getUsername().equals(userInfo.getUsername())) {
            UserInfo tempUserInfo = userInfoMapper.queryUserInfo(null, userInfo.getUsername(), null);
            if (tempUserInfo != null) {

                log.info("用户：tempUserInfo{}", tempUserInfo);
                throw new BlogException(OperationEnums.USERNAME__EXIST);
            } else {
                try {
                    userInfoMapper.updateUserInfo(userInfo);
                } catch (Exception e) {
                    throw new BlogException(OperationEnums.EMAIL_EXIST);
                }
            }

        }
        //只改邮箱
        log.info("邮箱:tempUserInfoById.getUserEmail():{}", tempUserInfoById.getUserEmail());
        log.info("邮箱:userInfo.getUserEmail():{}", userInfo.getUserEmail());
        if (!tempUserInfoById.getUserEmail().equals(userInfo.getUserEmail())) {
            UserInfo tempUserInfo = userInfoMapper.queryUserInfo(null, null, userInfo.getUserEmail());
            log.info("邮箱tempUserInfo：{}", tempUserInfo);
            if (tempUserInfo != null && tempUserInfo.getUserId() != userInfo.getUserId()) {
                throw new BlogException(OperationEnums.EMAIL_EXIST);

            } else {
                try {
                    userInfoMapper.updateUserInfo(userInfo);
                } catch (Exception e) {
                    throw new BlogException(OperationEnums.USERNAME__EXIST);
                }
            }
        }
        //更新图片
        int effectedNum = 0;
        if (imageHolder.getImage() != null && imageHolder.getImageName() != null) {
            //处理图片
            try {
                AddUserInfoImg(userInfo, imageHolder);
            } catch (Exception e) {
                throw new BlogException(OperationEnums.ADDIMAGE_ERROR);
            }
            effectedNum = userInfoMapper.updateUserInfo(userInfo);
        } else {
            userInfo.setUserImg(imageHolder.getImageName());
            effectedNum = userInfoMapper.updateUserInfo(userInfo);
        }

        if (effectedNum <= 0) {
            throw new BlogException(OperationEnums.UPDATE_ERROR);
        } else {
            return new OperationExecution(OperationEnums.SUCCESS);
        }
    }

@Override
@Transactional
public OperationExecution updateUserPsw(UserInfo userInfo, String newPassword) throws BlogException {

    //先查询
    //用户不能为空
    UserInfo tempUserInfo = userInfoMapper.queryUserInfo(userInfo.getUserId(), null, null);
    if (tempUserInfo == null) {
        throw new BlogException(OperationEnums.NULL_INFO);
    }
    // 密码不能错误
    if (!tempUserInfo.getPassword().equals(userInfo.getPassword())) {
        throw new BlogException(OperationEnums.PSW_ERROR);
    } else {
        // 新密码不能为空或者空串
        if (newPassword == null || newPassword.trim().isEmpty() || newPassword.trim().length() == 0) {
            throw new BlogException(OperationEnums.PSW_IS_NULL);
        }
        userInfo.setPassword(newPassword);
        int effectedNum = userInfoMapper.updateUserInfo(userInfo);
        if (effectedNum <= 0) {
            throw new BlogException(OperationEnums.PSW_UPDATE_ERROR);
        } else {
            return new OperationExecution(OperationEnums.UPDATE_SUCCESS);
        }
    }
}

    @Override
    @Transactional
    public OperationExecution deleteUserInfo(Integer userId) throws BlogException {
        try {
            UserInfo tempUser = new UserInfo();
            tempUser.setUserId(userId);
            Article article = new Article();
            article.setUserInfo(tempUser);

            List<Article> articleList = articleMapper.queryArticleList(article, 0, 10000);

            for(int i=0;i<articleList.size();i++){
                articleMapper.deleteArticleById(articleList.get(i).getArticleId());
            }
            userInfoMapper.deleteUserInfo(userId);
        }catch (Exception e){
            throw new BlogException(OperationEnums.DELETE_ERROR);
        }
       return new OperationExecution();
    }

@Override
public OperationExecution login(String nameOrEmail, String password) throws BlogException {
    //可以用 用户名，邮箱登录
    //1.用户名
    UserInfo tempUserInfo = userInfoMapper.queryUserInfo(null, nameOrEmail, null);
    if (tempUserInfo != null) {
        if (tempUserInfo.getPassword().equals(password)) {
            return new OperationExecution(OperationEnums.LOGIN_SUCCESS, tempUserInfo);
        } else {
            throw new BlogException(OperationEnums.LOGIN_ERROR);
        }
    }
    //邮箱登录
    tempUserInfo = userInfoMapper.queryUserInfo(null, null, nameOrEmail);
    if (tempUserInfo != null) {
        if (tempUserInfo.getPassword().equals(password)) {
            return new OperationExecution(OperationEnums.LOGIN_SUCCESS, tempUserInfo);
        } else {
            throw new BlogException(OperationEnums.LOGIN_ERROR);
        }
    } else {
        throw new BlogException(OperationEnums.LOGIN_ERROR);
    }

}

    @Override
    public OperationExecution getUserInfoList(int pageIndex, int pageSize) throws BlogException {
        int rowIndex = CommonUtil.getRowIndex(pageIndex, pageSize);
        List<UserInfo> userInfoList = userInfoMapper.queryUserInfoList(rowIndex, pageSize);
        int count = userInfoMapper.queryUserInfoCount();
        OperationExecution op = new OperationExecution();
        op.setDataList(userInfoList);
        op.setCount(count);
        return op;
    }

@Override
@Transactional
public OperationExecution findByPassword(String username, String userEmail) throws BlogException {
    //找回密码  从新设置新密码给用户 密码发邮件给用户
    UserInfo userInfo = userInfoMapper.queryUserInfo(null, username, userEmail);
    if (userInfo != null) {
        log.info("改密码前tempUserInfo：{}", userInfo);
        String newPassword = CommonUtil.getStringRandom(12);
        //更改查出来的用户
        userInfo.setPassword(newPassword);
        userInfoMapper.updateUserInfo(userInfo);
        boolean sendFlag = emailUtils.sendEmail(userEmail, newPassword);
        if (sendFlag) {
            return new OperationExecution(OperationEnums.EMAIL_SEND_SUCCESS);
        } else {
            throw new BlogException(OperationEnums.EMAIL_SEND_ERROR);
        }
    } else {
        throw new BlogException(OperationEnums.USERNAME_EMAIL_ERROR);
    }
}

    @Override
    public UserInfo findByUserInfo(Integer userId,String username,String userEmail) throws BlogException {
        return userInfoMapper.queryUserInfo(userId,username,userEmail);
    }

    @Override
    public int updateUserType(Integer userId) throws BlogException {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserType(0);
        int effectedNum = userInfoMapper.updateUserInfo(userInfo);
        if(effectedNum<=0){
            throw new  BlogException(OperationEnums.UPDATE_AUTHORITY);
        }
        return effectedNum;
    }


    private void AddUserInfoImg(UserInfo userInfo, ImageHolder imageHolder) {
        String dest = PathUtil.getImagePath(userInfo.getUserId());
        log.info("UserInfoServiceImpl  dest :{}", dest);
        String relativeAddr = ImageUtil.addImgAddr(dest, imageHolder);
        userInfo.setUserImg(contextPath + relativeAddr);
        log.info("存入数据库 setUserImg", contextPath + relativeAddr);
    }
}

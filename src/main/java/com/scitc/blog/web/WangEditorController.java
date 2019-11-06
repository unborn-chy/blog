package com.scitc.blog.web;

import com.scitc.blog.dto.ImageHolder;
import com.scitc.blog.dto.Result;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.model.UserInfo;
import com.scitc.blog.utils.CommonUtil;
import com.scitc.blog.utils.ImageUtil;
import com.scitc.blog.utils.PathUtil;
import com.scitc.blog.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@Controller
public class WangEditorController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

@RequestMapping("/upload")
@ResponseBody
public Result upload(@RequestParam("myFile") MultipartFile multipartFile, HttpServletRequest request) {

    log.info("--------------------------------");
    log.info("我是上传图片");
    String url = null;
    //获取项目路径
    try {
        //   从session获取用户
//            UserInfo userInfo = new UserInfo();
//            userInfo.setUserId(1);
//            request.getSession().setAttribute("userInfo", userInfo);
        //获取
        UserInfo currentUser = (UserInfo) request.getSession().getAttribute("userInfo");
        log.info("URL233:" + request.getRequestURI());
        //添加图片
        ImageHolder imageHolder = new ImageHolder(multipartFile.getOriginalFilename(), multipartFile.getInputStream());

        log.info("imageHolder.getimage:{}", imageHolder.getImage());
        String fileName = addImgFileName(currentUser.getUserId(), imageHolder);
        // 返回图片访问路径  http://ip:端口/
//            url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + fileName;
        url = request.getContextPath() + fileName;
        log.info("url:" + url);
    } catch (Exception e) {
        ResultUtils.error(OperationEnums.ADDIMAGE_ERROR);
    }
    return ResultUtils.success(OperationEnums.SUCCESS,url);
}


    public String addImgFileName(Integer userId, ImageHolder imageHolder) {
        String dest = PathUtil.getImagePath(userId);
        log.info("dest:{}", dest);
        String relativeAddr = ImageUtil.addImgAddr(dest, imageHolder);
        return relativeAddr;
    }

}

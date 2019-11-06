package com.scitc.blog.web.admin;

import com.auth0.jwt.JWT;
import com.scitc.blog.dto.ImageHolder;
import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.dto.Result;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.exception.BlogException;
import com.scitc.blog.model.HeadLine;
import com.scitc.blog.model.UserInfo;
import com.scitc.blog.service.HeadLineService;
import com.scitc.blog.service.impl.ArticleServiceImpl;
import com.scitc.blog.utils.CommonUtil;
import com.scitc.blog.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/manage")
public class HeadLineController {
    private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private HeadLineService headLineService;
//添加
@PostMapping("/headline")
public Result addHeadLine(@RequestParam(value = "lineName") String lineName, @RequestParam(value = "lineLink") String lineLink,
                          @RequestParam(value = "lineImgText") String lineImgText,
                          @RequestParam(value = "lineImg",required = false )MultipartFile lineImg,
                          HttpServletRequest request){

    log.info("进来了------------");
    UserInfo currentUser = CommonUtil.getUserInfo(request);
    if (currentUser.getUserType() != 0) {
        return ResultUtils.error(OperationEnums.NO_AUTHORITY);
    }
    HeadLine headLine = new HeadLine();
    headLine.setLineName(lineName);
    headLine.setLineLink(lineLink);

    //判断图片
    MultipartFile getHeadLineImg = lineImg;

    log.info("lineImgText：{}",lineImgText);
    ImageHolder imageHolder=null;
    if(getHeadLineImg!=null && !"".equals(getHeadLineImg)){
        try {
            imageHolder = new ImageHolder(lineImg.getOriginalFilename(),lineImg.getInputStream());
        } catch (IOException e) {
            throw new BlogException("图片转换失败" + e.getMessage());
        }
    }else {
        imageHolder = new ImageHolder(lineImgText,null);
    }
    OperationExecution oe = headLineService.addHeadLine(headLine,imageHolder,currentUser.getUserId());
        return ResultUtils.success(oe.getStateInfo());
}

    @GetMapping("/headlines")
    public Result<Map<String,Object>> getHeadLineList(HttpServletRequest request){
        log.info("打印进来了---------------");

        Map<String,Object> map = new HashMap<>();
        OperationExecution oe = headLineService.getHeadLineList();
        map.put("headLineList",oe.getDataList());
        map.put("count",oe.getCount());
        return ResultUtils.success(OperationEnums.SUCCESS,map);
    }



    @GetMapping("/headline/{lineId}")
    public Result<Map<String,Object>> getHeadLineList(@PathVariable("lineId") Integer lineId,HttpServletRequest request){
        UserInfo currentUser = CommonUtil.getUserInfo(request);
        if (currentUser.getUserType() != 0) {
            return ResultUtils.error(OperationEnums.NO_AUTHORITY);
        }
        Map<String,Object> map = new HashMap<>();
        OperationExecution oe = headLineService.getHeadLine(lineId);
        map.put("headLine",oe.getData());
        return ResultUtils.success(OperationEnums.SUCCESS,map);
    }

    @PutMapping("/headline/{lineId}")
    public Result<Map<String,Object>> updateHeadLine(@PathVariable("lineId") Integer lineId,@RequestParam(value = "lineName") String lineName,
                                                     @RequestParam(value = "lineLink") String lineLink,
                                                     @RequestParam(value = "lineImgText") String lineImgText,
                                                     @RequestParam(value = "lineImg",required = false )MultipartFile lineImg,
                                                     HttpServletRequest request){
        log.info("进来了------------");
        UserInfo currentUser = CommonUtil.getUserInfo(request);
        if (currentUser.getUserType() != 0) {
            return ResultUtils.error(OperationEnums.NO_AUTHORITY);
        }
        HeadLine headLine = new HeadLine();
        headLine.setLineId(lineId);
        headLine.setLineName(lineName);
        headLine.setLineLink(lineLink);

        //判断图片
        MultipartFile getHeadLineImg = lineImg;

        log.info("lineImgText：{}",lineImgText);
        ImageHolder imageHolder=null;
        if(getHeadLineImg!=null && !"".equals(getHeadLineImg)){
            try {
                imageHolder = new ImageHolder(lineImg.getOriginalFilename(),lineImg.getInputStream());
            } catch (IOException e) {
                throw new BlogException("图片转换失败" + e.getMessage());
            }
        }else {
            imageHolder = new ImageHolder(lineImgText,null);
        }
        OperationExecution oe = headLineService.updateHeadline(headLine,imageHolder,currentUser.getUserId());
            return ResultUtils.success(oe.getStateInfo());
    }

@DeleteMapping("/headline/{lineId}")
public Result deleteHeadLine(@PathVariable("lineId") Integer lineId,HttpServletRequest request){
    UserInfo currentUser = CommonUtil.getUserInfo(request);
    if (currentUser.getUserType() != 0) {
        return ResultUtils.error(OperationEnums.NO_AUTHORITY);
    }
    OperationExecution oe = headLineService.deleteHeadLine(lineId);
        return  ResultUtils.success(oe.getStateInfo());

}
}

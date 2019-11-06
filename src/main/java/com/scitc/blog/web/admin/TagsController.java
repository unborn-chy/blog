package com.scitc.blog.web.admin;

import com.scitc.blog.dto.OperationExecution;
import com.scitc.blog.dto.Result;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.model.Tags;
import com.scitc.blog.model.UserInfo;
import com.scitc.blog.service.TagsService;
import com.scitc.blog.utils.CommonUtil;
import com.scitc.blog.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/manage")
public class TagsController {
    private static final Logger logger = LoggerFactory.getLogger(TagsController.class);
    @Autowired
    private TagsService tagsService;

    @GetMapping("/tags")
    public Result<Map<String, Object>> tags() {
        Map<String, Object> map = new HashMap<>();
        OperationExecution oe = tagsService.getTagsList();
        map.put("tagsList", oe.getDataList());
        map.put("count", oe.getCount());
        return ResultUtils.success(OperationEnums.SUCCESS, map);
    }

    @PostMapping("/tag")
    public Result addTag(@RequestParam("tagName") String tagName, HttpServletRequest request) {
        UserInfo currentUser = CommonUtil.getUserInfo(request);
        if (currentUser.getUserType() != 0) {
            return ResultUtils.error(OperationEnums.NO_AUTHORITY);
        }
        Tags tags = new Tags();
        tags.setTagName(tagName);
        OperationExecution oe = tagsService.insertTags(tags);
        return ResultUtils.success(oe.getStateInfo());
    }

    @GetMapping("/tag/{tagId}")
    public Result<Map<String, Object>> getTagsById(@PathVariable("tagId") Integer tagId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        OperationExecution oe = tagsService.getTagsById(tagId);
        map.put("tag", oe.getData());
        return ResultUtils.success(OperationEnums.SUCCESS, map);
    }


    @PutMapping("/tag/{tagId}")
    public Result updateTagsById(@PathVariable("tagId") Integer tagId, @RequestParam("tagName") String tagName, HttpServletRequest request) {
        UserInfo currentUser = CommonUtil.getUserInfo(request);
        if (currentUser.getUserType() != 0) {
            return ResultUtils.error(OperationEnums.NO_AUTHORITY);
        }
        Tags tags = new Tags();
        tags.setTagId(tagId);
        tags.setTagName(tagName);
        OperationExecution oe = tagsService.updateTags(tags);
        return ResultUtils.success(oe.getStateInfo());
    }

    @DeleteMapping("/tag/{tagId}")
    public Result deleteTags(@PathVariable("tagId") Integer tagId, HttpServletRequest request) {
        UserInfo currentUser = CommonUtil.getUserInfo(request);
        if (currentUser.getUserType() != 0) {
            return ResultUtils.error(OperationEnums.NO_AUTHORITY);
        }
        OperationExecution oe = tagsService.deleteTags(tagId);
        return ResultUtils.success(oe.getStateInfo());
    }
}

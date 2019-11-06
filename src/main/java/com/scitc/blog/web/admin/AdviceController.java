package com.scitc.blog.web.admin;

import com.scitc.blog.dto.Result;
import com.scitc.blog.enums.OperationEnums;
import com.scitc.blog.model.Advice;
import com.scitc.blog.model.UserInfo;
import com.scitc.blog.service.AdviceService;
import com.scitc.blog.utils.CommonUtil;
import com.scitc.blog.utils.ResultUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/manage")
public class AdviceController {
    @Autowired
    private AdviceService adviceService;

@PostMapping("/advice")
public Result addAdvice(@Param("adviceContent") String adviceContent, HttpServletRequest  request){

    UserInfo currentUser = CommonUtil.getUserInfo(request);
    if (currentUser.getUserType() != 0) {
        return ResultUtils.error(OperationEnums.NO_AUTHORITY);
    }
    Advice advice = new Advice();
    advice.setAdviceContent(adviceContent);
    adviceService.addAdvice(advice);
    return ResultUtils.success(OperationEnums.INNER_SUCCESS);
}

    @PutMapping("/advice/{adviceId}")
    public Result updateAdvice(@PathVariable("adviceId") Integer adviceId,
                            @Param("adviceContent") String adviceContent,
                            HttpServletRequest  request){
        UserInfo currentUser = CommonUtil.getUserInfo(request);
        if (currentUser.getUserType() != 0) {
            return ResultUtils.error(OperationEnums.NO_AUTHORITY);
        }
        Advice advice = new Advice();
        advice.setAdviceId(adviceId);
        advice.setAdviceContent(adviceContent);

        adviceService.updateAdvice(advice);
        return ResultUtils.success(OperationEnums.UPDATE_SUCCESS);
    }

@DeleteMapping("/advice/{adviceId}")
public Result deleteAdvice(@PathVariable("adviceId") Integer adviceId,
                        HttpServletRequest  request){
    UserInfo currentUser = CommonUtil.getUserInfo(request);
    if (currentUser.getUserType() != 0) {
        return ResultUtils.error(OperationEnums.NO_AUTHORITY);
    }
    adviceService.deleteAdvice(adviceId);
    return ResultUtils.success(OperationEnums.DELETE_SUCCESS);
}



    @GetMapping("/advice/{adviceId}")
    public Result getAdviceById(@PathVariable("adviceId") Integer adviceId,
                            HttpServletRequest  request){
        Advice advice = adviceService.getAdviceById(adviceId);
        return ResultUtils.success(OperationEnums.SUCCESS,advice);
    }


@GetMapping("/advices")
public Result Advices(){
    List<Advice> adviceList = adviceService.getAdviceList();
    return ResultUtils.success(OperationEnums.SUCCESS,adviceList);
}




}

package com.github.shanbei.shanbeiuser.controller;

import com.github.shanbei.shanbeiuser.common.BaseResponse;
import com.github.shanbei.shanbeiuser.common.ResultUtils;
import com.github.shanbei.shanbeiuser.model.domain.SysPost;
import com.github.shanbei.shanbeiuser.service.SysPostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/post")
public class SysPostController {

    @Resource
    private SysPostService sysPostService;

    @GetMapping("/search")
    public BaseResponse<List<SysPost>> selectAll() {
        List<SysPost> result = sysPostService.selectAll();
        return ResultUtils.success(result);
    }
}

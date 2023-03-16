package com.github.shanbei.shanbeiuser.controller;

import com.github.shanbei.shanbeiuser.common.BaseResponse;
import com.github.shanbei.shanbeiuser.common.ResultUtils;
import com.github.shanbei.shanbeiuser.model.domain.SysPost;
import com.github.shanbei.shanbeiuser.model.domain.dto.PostSearchRequest;
import com.github.shanbei.shanbeiuser.service.SysPostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/post")
public class SysPostController {

    @Resource
    private SysPostService sysPostService;

    /**
     * 查询所有部门
     *
     * @return 返回所有部门的列表
     */
    @GetMapping("/searchAll")
    public BaseResponse<List<SysPost>> searchAll() {
        List<SysPost> result = sysPostService.selectAll();
        return ResultUtils.success(result);
    }

    /**
     * 根据岗位名称查询岗位信息
     *
     * @param postSearchRequest 岗位搜索请求
     * @return 返回岗位信息
     */
    @GetMapping("/searchName")
    public BaseResponse<SysPost> searchName(@RequestBody PostSearchRequest postSearchRequest) {
        String postName = postSearchRequest.getPostName();
        System.out.println(postName);
        SysPost result = sysPostService.selectPostByName(postName);
        return ResultUtils.success(result);
    }
}

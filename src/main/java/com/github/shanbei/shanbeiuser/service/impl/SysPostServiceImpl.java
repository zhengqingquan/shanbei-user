package com.github.shanbei.shanbeiuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.shanbei.shanbeiuser.model.domain.SysPost;
import com.github.shanbei.shanbeiuser.model.domain.User;
import com.github.shanbei.shanbeiuser.service.SysPostService;
import com.github.shanbei.shanbeiuser.mapper.SysPostMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 96400
* @description 针对表【sys_post(岗位信息表)】的数据库操作Service实现
* @createDate 2023-03-09 16:27:32
*/
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost>
    implements SysPostService{

    @Resource
    private SysPostMapper sysPostMapper;

    @Override
    public List<SysPost> selectAll() {
        QueryWrapper<SysPost> queryWrapper = new QueryWrapper<>();
        sysPostMapper.selectList(queryWrapper);
        return null;
    }

    @Override
    public boolean addPost(SysPost post) {
        return false;
    }

    @Override
    public boolean removePost(Long postId) {
        return false;
    }

    @Override
    public SysPost selectPostById(Long postId) {
        return null;
    }
}




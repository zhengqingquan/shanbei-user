package com.github.shanbei.shanbeiuser.service;

import com.github.shanbei.shanbeiuser.model.domain.SysPost;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 96400
 * @description 针对表【sys_post(岗位信息表)】的数据库操作Service
 * @createDate 2023-03-09 16:27:32
 */
public interface SysPostService extends IService<SysPost> {

    /**
     * 查询所有岗位
     *
     * @return 所有岗位的列表
     */
    List<SysPost> selectAll();

    /**
     * 新增岗位
     *
     * @param post 岗位对象
     * @return 是否成功
     */
    boolean addPost(SysPost post);

    /**
     * 通过岗位ID删除岗位
     *
     * @param postId 岗位ID
     * @return 是否成功
     */
    boolean removePost(Long postId);

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 岗位对象
     */
    SysPost selectPostById(Long postId);

}

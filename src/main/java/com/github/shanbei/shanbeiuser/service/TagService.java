package com.github.shanbei.shanbeiuser.service;

import com.github.shanbei.shanbeiuser.model.domain.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 96400
* @description 针对表【tag(标签表)】的数据库操作Service
* @createDate 2023-03-10 20:35:36
*/
public interface TagService extends IService<Tag> {

    /**
     * 添加单个标签
     *
     * @return
     */
    boolean addTag();

    /**
     * 添加多个标签
     *
     * @param tagList
     * @return
     */
    boolean addTag(List<Tag> tagList);

    /**
     * 删除单个标签
     *
     * @return
     */
    boolean removeTag();
}

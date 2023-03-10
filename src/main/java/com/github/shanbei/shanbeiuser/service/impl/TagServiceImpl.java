package com.github.shanbei.shanbeiuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.shanbei.shanbeiuser.model.domain.Tag;
import com.github.shanbei.shanbeiuser.service.TagService;
import com.github.shanbei.shanbeiuser.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author 96400
* @description 针对表【tag(标签表)】的数据库操作Service实现
* @createDate 2023-03-10 20:35:36
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}





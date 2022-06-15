package org.snbo.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.snbo.eduservice.bean.EduCourseDescription;
import org.snbo.eduservice.mapper.EduCourseDescriptionMapper;
import org.snbo.eduservice.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author snbo
 * @since 2022-03-26
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduCourseDescription> wrapper3 = new QueryWrapper<>();
        wrapper3.eq("id",courseId);
        baseMapper.delete(wrapper3);
    }
}

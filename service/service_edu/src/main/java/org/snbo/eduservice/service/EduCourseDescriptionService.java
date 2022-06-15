package org.snbo.eduservice.service;

import org.snbo.eduservice.bean.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author snbo
 * @since 2022-03-26
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {
    /**
     * description: 根据课程 id 逻辑删除课程的描述信息
     *
     * @param courseId 课程 id
     * @author sunbo
     * @date 2022/5/27 17:36
     */
    void removeByCourseId(String courseId);
}

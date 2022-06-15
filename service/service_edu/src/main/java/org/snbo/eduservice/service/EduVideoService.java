package org.snbo.eduservice.service;

import org.snbo.eduservice.bean.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author snbo
 * @since 2022-03-26
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * description: 根据课程 id 删除课程的小节信息
     *
     * @param courseId 课程 id
     * @author sunbo
     * @date 2022/5/28 16:02
     */
    void removeByCourseId(String courseId);

    /**
     * description: 根据视频 id 删除视频信息,包括数据库信息和视频点播平台信息
     *
     * @param id 视频 id
     * @author sunbo
     * @date 2022/5/30 13:26
     */
    void removeVideoInfoById(String id);
}

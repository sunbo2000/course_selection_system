package org.snbo.eduservice.service;

import org.snbo.eduservice.bean.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import org.snbo.eduservice.bean.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author snbo
 * @since 2022-03-26
 */
public interface EduChapterService extends IService<EduChapter> {
    /**
     * description: 根据课程 id 获取课程的章节信息与小节信息
     *
     * @param courseId 课程 id
     * @return {@link List<ChapterVo>}
     * @author sunbo
     * @date 2022/5/27 17:01
     */
    List<ChapterVo> getChapterAndVideo(String courseId);

    /**
     * description: 根据章节 id 逻辑删除章节信息
     *
     * @param id 章节 id
     * @author sunbo
     * @date 2022/5/27 17:10
     */
    void deleteChapter(String id);

    /**
     * description: 根据课程 id 逻辑删除课程信息
     *
     * @param courseId 课程 id
     * @author sunbo
     * @date 2022/5/27 17:10
     */
    void removeByCourseId(String courseId);
}

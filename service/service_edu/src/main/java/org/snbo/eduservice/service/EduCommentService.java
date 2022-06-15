package org.snbo.eduservice.service;

import org.snbo.eduservice.bean.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author snbo
 * @since 2022-04-15
 */
public interface EduCommentService extends IService<EduComment> {
    /**
     * description: 根据课程 id,分页查询课程的评论信息
     *
     * @param courseId 课程 id
     * @param current  分页查询时的页码
     * @param size     分页查询时候每页显示条数
     * @return {@link Map<String,Object>} 返回 Map 集合; value1 为包含 size 个评论实例的集合; value2 为评论的总条数
     * @author sunbo
     * @date 2022/5/27 17:17
     */
    Map<String, Object> getCommentsByCourseId(String courseId, Long current, Long size);

    /**
     * description: 从 http 请求头中获取到用户信息, 将用户信息加入评论信息,然后将评论信息存储到数据库中
     *
     * @param comment 评论信息实例
     * @param request http请求
     * @author sunbo
     * @date 2022/5/27 17:32
     */

    void saveComment(EduComment comment, HttpServletRequest request);

    /**
     * description: 根据父级评论 id 分页获取该评论下的子回复
     *
     * @param partId  评论父级部分 id
     * @param current 当前页
     * @param size    每页记录数
     * @return {@link Map<String,Object>} 返回 Map 集合,包含评论集合(List)
     * @author sunbo
     * @date 2022/5/30 12:13
     */
    Map<String, Object> getReplayPage(String partId, Long current, Long size);
}

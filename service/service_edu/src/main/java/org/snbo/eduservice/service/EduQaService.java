package org.snbo.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.snbo.eduservice.bean.EduQa;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author snbo
 * @since 2022-04-28
 */
public interface EduQaService extends IService<EduQa> {

    /**
     * description: 根据搜索条件获取 length 条话题记录,根据点赞数进行排序
     *
     * @param length  获取条数
     * @param context 搜索条件
     * @return {@link Map<String,Object>} 返回 Map 集合,value1 为话题列表(List), value2 为话题总记录数
     * @author sunbo
     * @date 2022/5/28 11:37
     */
    Map<String, Object> getTopic(String length, String context);

    /**
     * description: 根据评论父项 id,分页查询该父项话题下的评论
     *
     * @param partId  评论父项 id
     * @param current 当前页
     * @param size    每页记录数
     * @return {@link Map<String,Object>} 返回 Map 集合,value1 为评论集合, value2 为评论总记录数
     * @author sunbo
     * @date 2022/5/28 14:05
     */
    Map<String, Object> getTopicPage(String partId, Long current, Long size);

    /**
     * description: 根据子评论父项 id,分页查询该父项评论下的子评论
     *
     * @param partId 子评论父项 id
     * @param page   分页组件
     * @return {@link Map<String,Object>} 返回 Map 集合,value1 为回复集合(List),value2 为评论总记录数,
     * value3 为boolean 代表是否有下一项,value4 为 boolean 代表是否有上一项, value5 为总页数
     * @author sunbo
     * @date 2022/5/28 13:51
     */
    Map<String, Object> getCommentPage(String partId, Page<EduQa> page);

}

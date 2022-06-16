package org.snbo.eduservice.service;

import org.snbo.commonutils.vo.CourseOrderVo;
import org.snbo.eduservice.bean.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;

import org.snbo.eduservice.bean.vo.CourseInfoVo;

import org.snbo.eduservice.bean.vo.CourseQuery;


import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author snbo
 * @since 2022-03-26
 */
public interface EduCourseService extends IService<EduCourse> {
    /**
     * description: 添加课程信息到数据库中
     *
     * @param courseInfoVo 课程信息 vo
     * @return {@link String} 添加课程信息成功后返回课程的 id
     * @author sunbo
     * @date 2022/5/27 17:38
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * description: 根据课程 id 获取课程信息
     *
     * @param courseId 课程 id
     * @return {@link CourseInfoVo}
     * @author sunbo
     * @date 2022/5/27 17:40
     */
    CourseInfoVo getCourseInfoById(String courseId);

    /**
     * description: 根据课程 id 更新课程信息
     *
     * @param courseInfo 课程信息对应的 java 实例
     * @author sunbo
     * @date 2022/5/27 17:42
     */
    void updateCourseInfo(CourseInfoVo courseInfo);


    /**
     * description: 根据课程 id 获取课程信息,在查询课程订单信息时使用
     *
     * @param courseId 课程 id
     * @return {@link CourseOrderVo}
     * @author sunbo
     * @date 2022/5/28 11:33
     */
    CourseOrderVo getOrderCourseInfo(String courseId);

    /**
     * description: 根据日期查询该日期下新增的课程数
     *
     * @param day 日期
     * @return {@link Integer} 课程数
     * @author sunbo
     * @date 2022/5/28 11:36
     */
    Integer getCourseCount(String day);


    /**
     * description: 根据查询条件分页查询课程信息
     *
     * @param current     当前页
     * @param size        每页记录数
     * @param courseQuery 查询条件
     * @return {@link Map<String,Object>} 返回 Map 集合,包含课程信息集合(List)和总记录数(long)
     * @author sunbo
     * @date 2022/5/30 12:26
     */
    Map<String, Object> getCourseInfoPage(Long current, Long size, CourseQuery courseQuery);
}

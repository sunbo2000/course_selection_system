package org.snbo.eduservice.mapper;

import org.snbo.commonutils.vo.CourseOrderVo;
import org.snbo.eduservice.bean.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author snbo
 * @since 2022-03-26
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {


    /**
     * description: 根据课程 id 获取课程信息, 订单管理服务 edu-order 需要获取课程信息时需要远程调用当前模块,当前模块调用方法
     * 获取课程信息
     *
     * @param courseId 课程 id
     * @return {@link CourseOrderVo}
     * @author sunbo
     * @date 2022/5/27 16:45
     */
    CourseOrderVo getOrderCourseInfo(String courseId);

    /**
     * description: 根据日期参数 day 查询数据库,获取该天新增的课程数
     *
     * @param day 日期,天
     * @return {@link Integer}
     * @author sunbo
     * @date 2022/5/27 16:58
     */
    Integer getCourseCount(String day);
}

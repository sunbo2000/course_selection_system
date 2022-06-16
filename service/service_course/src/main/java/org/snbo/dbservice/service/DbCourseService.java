package org.snbo.dbservice.service;

import org.snbo.dbservice.bean.DbCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import org.snbo.dbservice.bean.vo.CourseQuery;
import org.snbo.dbservice.bean.vo.CourseVo;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author snbo
 * @since 2022-06-16
 */
public interface DbCourseService extends IService<DbCourse> {


    /**
     * description: 根据条件分页查询
     *
     * @param current     当前页
     * @param size        每页记录数
     * @param courseQuery 查询条件
     * @return {@link Map<String, Object>}
     * @author sunbo
     * @date 2022/6/16 9:59
     */
    Map<String, Object> getCoursePage(Long current, Long size, CourseQuery courseQuery);

    /**
     * description: 添加课程信息
     *
     * @param courseVo 课程信息
     * @return {@link String} 返回课程 id
     * @author sunbo
     * @date 2022/6/16 12:33
     */
    String saveCourse(CourseVo courseVo);

    /**
     * description: 更新课程信息
     *
     * @return {@link String}
     * @author sunbo
     * @date 2022/6/16 12:45
     */
    String updateCourse(CourseVo courseVo);

    /**
     * description: 根据id删除课程信息
     *
     * @param id
     * @author sunbo
     * @date 2022/6/16 12:51
     */
    void deleteCourse(String id);
}

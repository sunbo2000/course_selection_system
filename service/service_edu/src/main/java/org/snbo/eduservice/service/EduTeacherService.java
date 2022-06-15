package org.snbo.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.snbo.eduservice.bean.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import org.snbo.eduservice.bean.vo.TeacherQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author snbo
 * @since 2022-03-17
 */
public interface EduTeacherService extends IService<EduTeacher> {
    /**
     * description: 根据 sort 排序,获取前八条讲师信息,用作前台首页显示
     *
     * @return {@link List<EduTeacher>}
     * @author sunbo
     * @date 2022/5/28 15:54
     */
    List<EduTeacher> listIndexInfo();

    /**
     * description: 分页查询讲师信息
     *
     * @param pageTeacher 分页组件
     * @return {@link Map<String,Object>} 返回 Map 集合,包含讲师集合和记录总条数等信息
     * @author sunbo
     * @date 2022/5/28 15:54
     */
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);

    /**
     * description: 根据讲师 id 获取讲师信息,包括讲师个人信息和讲师的课程信息
     *
     * @param teacherId 讲师 id
     * @return {@link Map<String,Object>} 返回 Map 集合,包括讲师信息和课程集合
     * @author sunbo
     * @date 2022/5/28 15:54
     */
    Map<String, Object> getTeacherInfoById(String teacherId);

    /**
     * description: 分页获取讲师信息
     *
     * @param current 当前页
     * @param size    每页记录数
     * @return {@link Map<String,Object>} 返回 Map 集合,包含讲师集合(List)和总记录数(long)
     * @author sunbo
     * @date 2022/5/30 13:10
     */
    Map<String, Object> getTeacherInfoPage(Long current, Long size);

    /**
     * description: 根据查询条件分页查询获取讲师信息
     *
     * @param current      当前页
     * @param size         每页记录数
     * @param teacherQuery 查询条件
     * @return {@link Map<String,Object>} 返回 Map 集合,包含讲师集合(List)和总记录数(long)
     * @author sunbo
     * @date 2022/5/30 13:17
     */
    Map<String, Object> getTeacherInfoPageByQuery(Long current, Long size, TeacherQuery teacherQuery);
}

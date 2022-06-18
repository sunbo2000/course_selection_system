package org.snbo.dbservice.service;

import org.snbo.dbservice.bean.DbStudent;
import com.baomidou.mybatisplus.extension.service.IService;
import org.snbo.dbservice.bean.vo.StudentUpdateInfo;
import org.snbo.dbservice.bean.vo.StudentQuery;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author snbo
 * @since 2022-06-17
 */
public interface DbStudentService extends IService<DbStudent> {

    /**
     * description: 分页查询学生信息
     *
     * @param current      但你当前页
     * @param size         每页条数
     * @param studentQuery 查询条件
     * @return {@link Map< String, Object>}
     * @author sunbo
     * @date 2022/6/17 10:50
     */
    Map<String, Object> getStudentPage(Long current, Long size, StudentQuery studentQuery);

    /**
     * description: 生成学生信息
     *
     * @author sunbo
     * @date 2022/6/17 14:52
     */
    void importStudents();

    /**
     * description: 更新学生信息
     *
     * @param studentUpdateInfo 学生信息
     * @author sunbo
     * @date 2022/6/17 17:39
     */
    void updateStudent(StudentUpdateInfo studentUpdateInfo);

    /**
     * description: 选课
     *
     * @param request  请求头
     * @param courseId 课程 id
     * @return {@link boolean}
     * @author sunbo
     * @date 2022/6/18 11:41
     */
    boolean selectCurriculum(HttpServletRequest request, String courseId);

    /**
     * description: 退课
     *
     * @param request  请求头
     * @param courseId 课程id
     * @return {@link boolean}
     * @author sunbo
     * @date 2022/6/18 12:35
     */
    boolean dropout(HttpServletRequest request, String courseId);

    /**
     * description: 查看学生可选课程
     *
     * @param request   请求头
     * @param subjectId 课程分类
     * @return {@link Map<String, Object>}
     * @author sunbo
     * @date 2022/6/18 12:53
     */
    Map<String, Object> getCurriculum(HttpServletRequest request,  String subjectId);
}

package org.snbo.dbservice.mapper;

import org.apache.ibatis.annotations.Param;
import org.snbo.dbservice.bean.DbCourse;
import org.snbo.dbservice.bean.DbStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author snbo
 * @since 2022-06-17
 */
public interface DbStudentMapper extends BaseMapper<DbStudent> {
    /**
     * description: 获取学生的课程信息
     *
     * @return {@link List<DbCourse>}
     * @author sunbo
     * @date 2022/6/18 13:00
     */
    List<DbCourse> getStudentCourseList(@Param("studentId") String studentId, @Param("subjectId") String subjectId);
}

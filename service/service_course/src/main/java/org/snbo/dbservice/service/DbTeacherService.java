package org.snbo.dbservice.service;

import org.snbo.dbservice.bean.DbTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import org.snbo.dbservice.bean.vo.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author snbo
 * @since 2022-06-16
 */
public interface DbTeacherService extends IService<DbTeacher> {

    /**
     * description: 按条件分页查询教师信息
     *
     * @param current      当前页
     * @param size         每页记录数
     * @param teacherQuery 查询条件
     * @return {@link Map< String, Object>}
     * @author sunbo
     * @date 2022/6/16 18:11
     */
    Map<String, Object> getTeacherPage(Long current, Long size, TeacherQuery teacherQuery);
}

package org.snbo.dbservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.snbo.commonutils.ResultCode;
import org.snbo.dbservice.bean.DbCourse;
import org.snbo.dbservice.bean.DbCourseDescription;
import org.snbo.dbservice.bean.vo.CourseQuery;
import org.snbo.dbservice.bean.vo.CourseVo;
import org.snbo.dbservice.mapper.DbCourseMapper;
import org.snbo.dbservice.service.DbCourseDescriptionService;
import org.snbo.dbservice.service.DbCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.snbo.servicebase.exceptionhandler.MoguException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author snbo
 * @since 2022-06-16
 */
@Service
public class DbCourseServiceImpl extends ServiceImpl<DbCourseMapper, DbCourse> implements DbCourseService {

    private final DbCourseDescriptionService courseDescriptionService;

    @Autowired
    public DbCourseServiceImpl(DbCourseDescriptionService courseDescriptionService) {
        this.courseDescriptionService = courseDescriptionService;
    }

    @Override
    public Map<String, Object> getCoursePage(Long current, Long size, CourseQuery courseQuery) {

        Page<DbCourse> page = new Page<>(current, size);

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        QueryWrapper<DbCourse> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id", subjectParentId);

        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id", subjectId);
        }

        wrapper.orderByDesc("gmt_modified");

        baseMapper.selectPage(page, wrapper);

        Map<String, Object> map = new HashMap<>(2);
        map.put("courseList", page.getRecords());
        map.put("total", page.getTotal());

        return map;
    }

    @Override
    public String saveCourse(CourseVo courseVo) {
        DbCourse course = new DbCourse();
        BeanUtils.copyProperties(courseVo, course);

        if (baseMapper.insert(course) <= 0) {
            throw new MoguException(ResultCode.ERROR, "添加课程信息失败");
        }
        String courseId = course.getId();

        DbCourseDescription dbCourseDescription = new DbCourseDescription();
        BeanUtils.copyProperties(courseVo, dbCourseDescription);
        dbCourseDescription.setId(courseId);
        courseDescriptionService.save(dbCourseDescription);
        return courseId;
    }

    @Override
    public String updateCourse(CourseVo courseVo) {
        DbCourse course = baseMapper.selectById(courseVo.getId());
        BeanUtils.copyProperties(courseVo, course);
        if (baseMapper.updateById(course) <= 0) {
            throw new MoguException(20001, "修改课程信息失败");
        }
        DbCourseDescription courseDescription = new DbCourseDescription();
        BeanUtils.copyProperties(courseVo, courseDescription);

        if (!courseDescriptionService.updateById(courseDescription)) {
            throw new MoguException(ResultCode.ERROR, "修改课程描述信息失败");
        }

        return course.getId();
    }

    @Override
    public void deleteCourse(String id) {
        boolean b = courseDescriptionService.removeById(id);
        int i = baseMapper.deleteById(id);
        if (!b && i <= 0) {
            throw new MoguException(ResultCode.ERROR, "删除课程失败");
        }
    }
}

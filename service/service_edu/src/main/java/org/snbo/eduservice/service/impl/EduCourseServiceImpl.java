package org.snbo.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.snbo.commonutils.ResultCode;
import org.snbo.commonutils.vo.CourseOrderVo;
import org.snbo.eduservice.bean.EduCourse;
import org.snbo.eduservice.bean.EduCourseDescription;
import org.snbo.eduservice.bean.vo.CourseInfoVo;
import org.snbo.eduservice.bean.vo.CourseQuery;
import org.snbo.eduservice.mapper.EduCourseMapper;
import org.snbo.eduservice.service.EduCourseDescriptionService;
import org.snbo.eduservice.service.EduCourseService;
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
 * 课程 服务实现类
 * </p>
 *
 * @author snbo
 * @since 2022-03-26
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    private final EduCourseDescriptionService descriptionService;


    public static final String STR = "1";

    @Autowired
    public EduCourseServiceImpl(EduCourseDescriptionService descriptionService) {
        this.descriptionService = descriptionService;

    }


    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        //把接收的info信息里的基本信息给eduCourse保存到edu_course表
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        boolean save = this.save(eduCourse);
        if (!save) {
            throw new MoguException(20001, "添加课程失败");
        }

        String cid = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        //把接收的info信息里的简介信息给eduCourseDescription保存到edu_course_description表
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        eduCourseDescription.setId(cid);
        descriptionService.save(eduCourseDescription);

        return cid;
    }

    /**
     * 根据id获取课程信息
     */
    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {
        CourseInfoVo courseInfo = new CourseInfoVo();
        //获取基本信息
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse, courseInfo);
        //获取简介信息
        EduCourseDescription description = descriptionService.getById(courseId);
        courseInfo.setDescription(description.getDescription());
        return courseInfo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfo) {
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfo, course);
        boolean b = this.updateById(course);
        if (!b) {
            throw new MoguException(20001, "修改课程信息失败");
        }
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfo, courseDescription);
        boolean b1 = descriptionService.updateById(courseDescription);
        if (!b1) {
            throw new MoguException(ResultCode.ERROR, "修改课程描述信息失败");
        }
    }


  /*  @Override
    public void removeAllInfo(String courseId) {
        //删小节
        videoService.removeByCourseId(courseId);
        //删章节
        chapterService.removeByCourseId(courseId);
        //删描述
        descriptionService.removeByCourseId(courseId);
        //删课程
        baseMapper.deleteById(courseId);
    }*/




    @Override
    public CourseOrderVo getOrderCourseInfo(String courseId) {
        CourseOrderVo orderCourseInfo = baseMapper.getOrderCourseInfo(courseId);
        if (orderCourseInfo == null) {
            throw new MoguException(20001, "获取课程信息失败");
        }
        return orderCourseInfo;
    }

    @Override
    public Integer getCourseCount(String day) {
        return baseMapper.getCourseCount(day);
    }


    @Override
    public Map<String, Object> getCourseInfoPage(Long current, Long size, CourseQuery courseQuery) {

        Page<EduCourse> page = new Page<>(current, size);

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

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
        map.put("total", page.getTotal());
        map.put("courses", page.getRecords());

        return map;
    }
}

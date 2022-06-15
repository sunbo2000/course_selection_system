package org.snbo.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.snbo.eduservice.bean.EduCourse;
import org.snbo.eduservice.bean.EduTeacher;
import org.snbo.eduservice.bean.vo.TeacherQuery;
import org.snbo.eduservice.mapper.EduTeacherMapper;
import org.snbo.eduservice.service.EduCourseService;
import org.snbo.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author snbo
 * @since 2022-03-17
 */

@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    private final EduCourseService courseService;

    @Autowired
    public EduTeacherServiceImpl(EduCourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    @Cacheable(value = "teachers1", key = "'indexInfo1'")
    public List<EduTeacher> listIndexInfo() {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        wrapper.last("limit 4");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        baseMapper.selectPage(pageTeacher, wrapper);
        List<EduTeacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();

        boolean hasNext = pageTeacher.hasNext();
        boolean hasPrevious = pageTeacher.hasPrevious();

        Map<String, Object> map = new HashMap<>(16);
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public Map<String, Object> getTeacherInfoById(String teacherId) {
        EduTeacher eduTeacher = baseMapper.selectById(teacherId);

        //查询讲师的课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);

        HashMap<String, Object> map = new HashMap<>(16);
        map.put("teacher", eduTeacher);
        map.put("courses", courseList);

        return map;
    }

    @Override
    public Map<String, Object> getTeacherInfoPage(Long current, Long size) {
        //创建分页对象
        Page<EduTeacher> teacherPage = new Page<>(current, size);
        // 会把所有的数据返回到 teacherPage 里面, 包括记录数, 表内信息, 是否有下一行等
        baseMapper.selectPage(teacherPage, null);
        //获取总记录数
        long total = teacherPage.getTotal();
        //获取数据集合
        List<EduTeacher> list = teacherPage.getRecords();

        Map<String, Object> map = new HashMap<>(2);
        map.put("total", total);
        map.put("teachers", list);

        return map;
    }

    @Override
    public Map<String, Object> getTeacherInfoPageByQuery(Long current, Long size, TeacherQuery teacherQuery) {
        //page对像
        Page<EduTeacher> pageCondition = new Page<>(current, size);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询,条件判断代替动态 sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //条件判断
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        //大于等于
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        //小于等于
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        //排序
        wrapper.orderByDesc("gmt_modified");

        baseMapper.selectPage(pageCondition, wrapper);
        //获取总记录数
        long total = pageCondition.getTotal();
        //获取数据集合
        List<EduTeacher> list = pageCondition.getRecords();

        Map<String, Object> map = new HashMap<>(2);
        map.put("total", total);
        map.put("teachers", list);

        return map;
    }
}
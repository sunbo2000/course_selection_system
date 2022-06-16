package org.snbo.eduservice.controller;


import io.swagger.annotations.ApiOperation;
import org.snbo.commonutils.R;
import org.snbo.commonutils.vo.CourseOrderVo;
import org.snbo.eduservice.bean.EduCourse;
import org.snbo.eduservice.bean.vo.CourseInfoVo;
import org.snbo.eduservice.bean.vo.CourseQuery;
import org.snbo.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author snbo
 * @since 2022-03-26
 */
@RestController
@RequestMapping("/eduService/course")
public class EduCourseController {

    private final EduCourseService courseService;

    @Autowired
    public EduCourseController(EduCourseService courseService) {
        this.courseService = courseService;
    }


    @PostMapping("/{current}/{size}")
    @ApiOperation(value = "(查)多条件分页查询")
    public R getPageCourseInfo(@PathVariable Long current, @PathVariable Long size
            , @RequestBody(required = false) CourseQuery courseQuery) {
        Map<String, Object> map = courseService.getCourseInfoPage(current, size, courseQuery);
        return R.ok().data(map);
    }

    @PostMapping
    @ApiOperation(value = "(增)添加课程信息")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "(查)根据课程id查询课程信息")
    public R getCourseInfo(@PathVariable String id) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfoById(id);
        return R.ok().data("courseInfo", courseInfoVo);
    }

    @PutMapping("/courseInfo")
    @ApiOperation(value = "(改)根据id修改课程信息和简介")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfo) {
        courseService.updateCourseInfo(courseInfo);
        return R.ok();
    }


    @PutMapping
    @ApiOperation(value = "(改)更改课程状态为已发布")
    public R updateStatus(@RequestBody EduCourse course) {
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.ok();
    }

    @GetMapping("/getOrderCourseInfo/{courseId}")
    public CourseOrderVo getOrderCourse(@PathVariable String courseId) {
        return courseService.getOrderCourseInfo(courseId);

    }

    @GetMapping("/courseCount/{day}")
    public Integer getCourseCount(@PathVariable String day) {
        return courseService.getCourseCount(day);
    }
}


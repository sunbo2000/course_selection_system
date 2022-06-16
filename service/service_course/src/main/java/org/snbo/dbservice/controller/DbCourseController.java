package org.snbo.dbservice.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.snbo.commonutils.R;
import org.snbo.dbservice.bean.vo.CourseQuery;
import org.snbo.dbservice.bean.vo.CourseVo;
import org.snbo.dbservice.service.DbCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author snbo
 * @since 2022-06-16
 */
@RestController
@RequestMapping("/db/course")
public class DbCourseController {

    private final DbCourseService courseService;

    @Autowired
    public DbCourseController(DbCourseService courseService) {
        this.courseService = courseService;
    }

    @ApiOperation("post 请求 ,按条件分页查询课程信息,三个参数分别是当前页码,每页条数,查询条件")
    @PostMapping("/{current}/{size}")
    public R getCoursePage(@ApiParam(name = "current", value = "当前页码,路径参数") @PathVariable Long current,
                           @ApiParam(name = "size", value = "每页的条数,路径参数") @PathVariable Long size,
                           @ApiParam(name = "courseQuery", value = "查询条件类,请求体") @RequestBody(required = false) CourseQuery courseQuery) {
        Map<String, Object> map = courseService.getCoursePage(current, size, courseQuery);
        return R.ok().data(map);
    }

    @ApiOperation("post 请求 ,添加课程信息")
    @PostMapping
    public R saveCourse(@ApiParam(name = "courseVo", value = "课程实体类") @RequestBody CourseVo courseVo) {
        String courseId = courseService.saveCourse(courseVo);
        return R.ok().data("courseId", courseId);
    }

    @ApiOperation("put请求,修改课程信息")
    @PutMapping
    public R updateCourse(@ApiParam(name = "courseVo", value = "课程实体类") @RequestBody CourseVo courseVo) {
        String courseId = courseService.updateCourse(courseVo);
        return R.ok().data("courseId", courseId);
    }

    @ApiOperation("delete 请求 ,根据id删除课程信息")
    @DeleteMapping("/{id}")
    public R deleteCourse(@ApiParam(name = "id", value = "课程id") @PathVariable String id) {
        courseService.deleteCourse(id);
        return R.ok();
    }
}


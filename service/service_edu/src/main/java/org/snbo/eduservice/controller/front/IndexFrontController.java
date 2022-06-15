package org.snbo.eduservice.controller.front;

import io.swagger.annotations.ApiOperation;
import org.snbo.commonutils.R;
import org.snbo.eduservice.bean.EduCourse;
import org.snbo.eduservice.bean.EduTeacher;
import org.snbo.eduservice.service.EduCourseService;
import org.snbo.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author sunbo
 * @create 2022-04-03-16:19
 */

@RestController
@RequestMapping("/eduService/front/index")
public class IndexFrontController {

    private final EduTeacherService teacherService;
    private final EduCourseService courseService;

    @Autowired
    public IndexFrontController(EduTeacherService teacherService, EduCourseService courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
    }


    @GetMapping
    @ApiOperation(value = "(查)查询首页课程和名师信息")
    public R index(){
        //前八条课程记录
        List<EduCourse> courses = courseService.listIndexInfo();
        //前四条讲师记录
        List<EduTeacher> teachers = teacherService.listIndexInfo();
        return R.ok().data("courses",courses).data("teachers",teachers);
    }
}

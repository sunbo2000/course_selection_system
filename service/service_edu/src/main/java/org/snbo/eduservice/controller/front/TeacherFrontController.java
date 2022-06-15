package org.snbo.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.snbo.commonutils.R;
import org.snbo.eduservice.bean.EduTeacher;
import org.snbo.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author sunbo
 * @create 2022-04-10-15:29
 */

@RestController
@RequestMapping("/eduService/frontTeacher")
public class TeacherFrontController {

    private final EduTeacherService teacherService;

    @Autowired
    public TeacherFrontController(EduTeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/{current}/{size}")
    public R getTeacherFrontList(@PathVariable Long current, @PathVariable Long size) {
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        Map<String, Object> map = teacherService.getTeacherFrontList(pageTeacher);
        return R.ok().data(map);
    }

    @GetMapping("/{teacherId}")
    public R getFrontTeacherInfoById(@PathVariable String teacherId) {
        //查询讲师基本信息,简介,课程信息
        Map<String, Object> map = teacherService.getTeacherInfoById(teacherId);
        return R.ok().data(map);
    }
}

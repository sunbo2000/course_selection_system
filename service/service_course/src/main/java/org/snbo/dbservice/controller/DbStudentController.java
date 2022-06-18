package org.snbo.dbservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.snbo.commonutils.R;
import org.snbo.dbservice.bean.vo.StudentUpdateInfo;
import org.snbo.dbservice.bean.vo.StudentQuery;
import org.snbo.dbservice.service.DbStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author snbo
 * @since 2022-06-17
 */
@RestController
@RequestMapping("/db/student")
public class DbStudentController {
    private final DbStudentService studentService;


    @Autowired
    public DbStudentController(DbStudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/{current}/{size}")
    public R getStudentPage(@PathVariable Long current, @PathVariable Long size, @RequestBody StudentQuery studentQuery) {
        Map<String, Object> map = studentService.getStudentPage(current, size, studentQuery);
        return R.ok().data(map);
    }

    @PostMapping()
    public R saveStudent() {
        studentService.importStudents();
        return R.ok();
    }

    @PutMapping
    public R updateStudent(@RequestBody StudentUpdateInfo studentUpdateInfo) {
        studentService.updateStudent(studentUpdateInfo);
        return R.ok();
    }

    @GetMapping("/curriculum/{subjectId}")
    public R getCoursePage(HttpServletRequest request,
                           @PathVariable String subjectId) {
        Map<String, Object> map = studentService.getCurriculum(request,subjectId);
        return R.ok().data(map);
    }

    @GetMapping("/{courseId}")
    public R selectCurriculum(HttpServletRequest request, @PathVariable String courseId) {
        boolean b = studentService.selectCurriculum(request, courseId);
        return R.ok();
    }

    @DeleteMapping("/{courseId}")
    public R dropout(HttpServletRequest request, @PathVariable String courseId) {
        boolean b = studentService.dropout(request, courseId);
        return R.ok();
    }
}


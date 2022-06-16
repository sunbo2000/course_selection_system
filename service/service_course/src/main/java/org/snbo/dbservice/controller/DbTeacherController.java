package org.snbo.dbservice.controller;


import io.swagger.annotations.ApiOperation;
import org.snbo.commonutils.R;
import org.snbo.dbservice.bean.DbTeacher;
import org.snbo.dbservice.bean.vo.TeacherQuery;
import org.snbo.dbservice.service.DbTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author snbo
 * @since 2022-06-16
 */
@RestController
@RequestMapping("/db/teacher")
public class DbTeacherController {
    private final DbTeacherService teacherService;

    @Autowired
    public DbTeacherController(DbTeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @ApiOperation("查询所有教师信息")
    @GetMapping
    public R getAllTeacher() {
        List<DbTeacher> list = teacherService.list(null);
        return R.ok().data("teacherList", list);
    }

    @ApiOperation("(查)多条件组合分页查询")
    @PostMapping("/{current}/{size}")
    public R pageCondition(@PathVariable Long current, @PathVariable Long size,
                           @RequestBody(required = false) TeacherQuery teacherQuery) {
        Map<String, Object> map = teacherService.getTeacherPage(current, size, teacherQuery);
        return R.ok().data(map);
    }


    @ApiOperation("添加讲师信息")
    @PostMapping
    public R saveTeacher(@RequestBody DbTeacher teacher) {
        boolean save = teacherService.save(teacher);
        if (save) {
            return R.ok().message("添加成功");
        } else {
            return R.error().message("添加教师信息失败");
        }
    }

    @ApiOperation("更新教师信息")
    @PutMapping
    public R updateTeacher(@RequestBody DbTeacher teacher) {
        boolean b = teacherService.updateById(teacher);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation("删除教师信息")
    @DeleteMapping("/{id}")
    public R deleteTeacher(@PathVariable String id) {
        boolean b = teacherService.removeById(id);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}


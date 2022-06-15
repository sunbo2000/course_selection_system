package org.snbo.eduservice.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.snbo.commonutils.R;
import org.snbo.eduservice.bean.EduTeacher;
import org.snbo.eduservice.bean.vo.TeacherQuery;
import org.snbo.eduservice.service.EduTeacherService;
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
 * @since 2022-03-17
 */

@Api(value = "讲师管理")
@RestController
@RequestMapping("/eduService/teachers")
public class EduTeacherController {

    private final EduTeacherService teacherService;

    @Autowired
    public EduTeacherController(EduTeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @ApiOperation(value = "(查)查询所有讲师信息")
    @GetMapping
    public R findAll() {
        //调用list方法查询
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("teachers", list);
    }

    @ApiOperation(value = "(删)逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师id") @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation("(查)分页查询讲师信息")
    @GetMapping("page/{current}/{size}")
    public R pageList(@PathVariable Long current, @PathVariable Long size) {
        Map<String, Object> map = teacherService.getTeacherInfoPage(current, size);
        return R.ok().data(map);
    }

    @ApiOperation("(查)多条件组合分页查询")
    @PostMapping("/pageCondition/{current}/{size}")
    public R pageCondition(@PathVariable Long current, @PathVariable Long size,
                           @RequestBody(required = false) TeacherQuery teacherQuery) {
        Map<String, Object> map = teacherService.getTeacherInfoPageByQuery(current, size, teacherQuery);
        return R.ok().data(map);
    }


    @ApiOperation("(增)添加讲师信息")
    @PostMapping
    public R saveTeacher(@RequestBody EduTeacher teacher) {
        boolean flag = teacherService.save(teacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //修改功能


    @ApiOperation("(查)根据id查询讲师信息")
    @GetMapping("/{id}")
    public R getTeacherById(@PathVariable Long id) {
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    @ApiOperation("(改)根据id修改讲师信息")
    @PutMapping
    public R update(@RequestBody EduTeacher teacher) {
        boolean flag = teacherService.updateById(teacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}


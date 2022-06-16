package org.snbo.dbservice.controller;


import io.swagger.annotations.ApiOperation;
import org.snbo.commonutils.R;
import org.snbo.dbservice.bean.vo.SubjectType;
import org.snbo.dbservice.service.DbSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author snbo
 * @since 2022-06-16
 */
@RestController
@RequestMapping("/db/subject")
public class DbSubjectController {

    private final DbSubjectService subjectService;

    @Autowired
    public DbSubjectController(DbSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * 添加课程分类
     * 获取上传过来的文件,把文件内容读取出来
     */
    @PostMapping
    @ApiOperation(value = "(增)根据excel表格添加课程分类数据")
    public R addSubject(MultipartFile file){
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    /**
     * 获取课程分类数据
     */
    @GetMapping
    @ApiOperation(value = "(查)查询所有课程分类数据")
    public R getAll(){
        List<SubjectType> list = subjectService.getAllSubject();
        return R.ok().data("subjects",list);
    }
}


package org.snbo.eduservice.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.snbo.commonutils.R;
import org.snbo.eduservice.bean.subject.SubjectType;
import org.snbo.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author snbo
 * @since 2022-03-23
 */
@Api(value = "根据Excel添加课程分类")
@RestController
@RequestMapping("/eduService/subjects")
public class EduSubjectController {

    private final EduSubjectService subjectService;

    @Autowired
    public EduSubjectController(EduSubjectService subjectService) {
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


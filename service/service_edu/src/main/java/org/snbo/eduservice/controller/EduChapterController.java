package org.snbo.eduservice.controller;


import io.swagger.annotations.ApiOperation;
import org.snbo.commonutils.R;
import org.snbo.eduservice.bean.EduChapter;
import org.snbo.eduservice.bean.chapter.ChapterVo;
import org.snbo.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author snbo
 * @since 2022-03-26
 */
@RestController
@RequestMapping("/eduService/chapter")
public class EduChapterController {
    private final EduChapterService chapterService;

    @Autowired
    public EduChapterController(EduChapterService chapterService) {
        this.chapterService = chapterService;
    }


    @GetMapping("/getChapAndVideo/{courseId}")
    @ApiOperation(value = "(查)根据id查询章节和小节信息")
    public R getChapterAndVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterAndVideo(courseId);
        return R.ok().data("chapterAndVideo", list);
    }

    @PostMapping
    @ApiOperation(value = "(增)增加章节数据")
    public R addChapter(@RequestBody EduChapter chapter) {
        chapterService.save(chapter);
        return R.ok();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "(查)根据id查询章节信息")
    public R getById(@PathVariable String id) {
        EduChapter chapter = chapterService.getById(id);
        return R.ok().data("chapter", chapter);
    }

    @PutMapping
    @ApiOperation(value = "(改)根据id更改章节信息")
    public R updateChapter(@RequestBody EduChapter chapter) {
        chapterService.updateById(chapter);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "(删)根据id删除章节")
    public R deleteChapter(@PathVariable String id) {
        chapterService.deleteChapter(id);
        return R.ok();
    }
}


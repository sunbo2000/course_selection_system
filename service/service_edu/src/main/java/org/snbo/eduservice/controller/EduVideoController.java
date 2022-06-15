package org.snbo.eduservice.controller;


import io.swagger.annotations.ApiOperation;
import org.snbo.commonutils.R;
import org.snbo.eduservice.bean.EduVideo;
import org.snbo.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author snbo
 * @since 2022-03-26
 */
@RestController
@RequestMapping("/eduService/video")
public class EduVideoController {
    private final EduVideoService videoService;

    @Autowired
    public EduVideoController(EduVideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    @ApiOperation(value = "(增)添加小节信息")
    public R saveVideo(@RequestBody EduVideo video) {
        videoService.save(video);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "(删)删除小节信息")
    public R deleteVideo(@PathVariable String id) {
        videoService.removeVideoInfoById(id);
        return R.ok();
    }

    @PutMapping
    @ApiOperation(value = "(改)更改小节信息")
    public R updateVideo(@RequestBody EduVideo video) {
        videoService.updateById(video);
        return R.ok();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "(查)根据id查询小节")
    public R getById(@PathVariable String id) {
        EduVideo video = videoService.getById(id);
        return R.ok().data("video", video);
    }
}


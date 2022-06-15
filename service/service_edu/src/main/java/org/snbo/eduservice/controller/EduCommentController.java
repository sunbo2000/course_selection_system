package org.snbo.eduservice.controller;


import io.swagger.annotations.ApiOperation;
import org.snbo.commonutils.R;
import org.snbo.eduservice.bean.EduComment;
import org.snbo.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author snbo
 * @since 2022-04-15
 */
@RestController
@RequestMapping("/eduService/comment")
public class EduCommentController {

    private final EduCommentService commentService;

    @Autowired
    public EduCommentController(EduCommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "添加用户评论信息")
    @PostMapping
    public R saveComment(@RequestBody EduComment comment, HttpServletRequest request) {
        commentService.saveComment(comment, request);
        return R.ok();
    }


    @GetMapping("/{courseId}/{current}/{size}")
    @ApiOperation(value = "根据课程id查询课程评论")
    public R getCourseComment(@PathVariable String courseId, @PathVariable Long current
            , @PathVariable Long size) {
        Map<String, Object> map = commentService.getCommentsByCourseId(courseId, current, size);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据评论块id获取该评论下回复的分页查询")
    @GetMapping("/getReplayPage/{partId}/{current}/{size}")
    public R getReplayPage(@PathVariable String partId, @PathVariable Long current, @PathVariable Long size) {
        Map<String, Object> map = commentService.getReplayPage(partId, current, size);
        return R.ok().data(map);
    }
}


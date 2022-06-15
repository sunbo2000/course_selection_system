package org.snbo.eduservice.bean.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunbo
 * @create 2022-03-27-21:04
 */
@Data
public class ChapterVo {
    @ApiModelProperty(value = "章节 id")
    private String id;
    @ApiModelProperty(value = "章节标题")
    private String title;
    @ApiModelProperty(value = "章节包含的小节")
    private List<VideoVo> children = new ArrayList<>();
}

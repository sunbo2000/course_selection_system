package org.snbo.eduservice.bean.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunbo
 * @create 2022-03-27-21:04
 */
@Data
public class VideoVo {
    @ApiModelProperty(value = "小节 id")
    private String id;
    @ApiModelProperty(value = "小节标题")
    private String title;
    @ApiModelProperty(value = "是否可以试听：1收费 0免费")
    private Integer isFree;
    @ApiModelProperty(value = "云端视频资源")
    private String videoSourceId;
}

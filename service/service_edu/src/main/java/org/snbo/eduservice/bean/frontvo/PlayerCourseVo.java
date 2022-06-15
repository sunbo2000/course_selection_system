package org.snbo.eduservice.bean.frontvo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.Date;

/**
 * @author sunbo
 * @create 2022-04-13-13:23
 */
@Data
public class PlayerCourseVo {

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
}

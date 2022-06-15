package org.snbo.eduservice.bean.qa;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author sunbo
 * @create 2022-05-01-20:43
 */
@Data
public class Comment {
    @ApiModelProperty(value = "话题id")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "问答内容")
    private String content;

    @ApiModelProperty(value = "点赞人数")
    private Integer good;

    @ApiModelProperty(value = "问答所属的话题id")
    private String partId;

    @ApiModelProperty(value = "回复对象的昵称")
    private String toName;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    private Boolean showGood;

}

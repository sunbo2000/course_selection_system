package org.snbo.eduservice.bean.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author sunbo
 * @create 2022-04-15-20:49
 */

@Data
public class ReplyPart {

    @ApiModelProperty(value = "评论ID")
    private String id;

    @ApiModelProperty(value = "回复的用户昵称")
    private String replay;

    @ApiModelProperty(value = "回复所属的块id, 回复的块id为父评论id")
    private String partId;

    @ApiModelProperty(value = "会员id")
    private String memberId;

    @ApiModelProperty(value = "会员昵称")
    private String nickname;

    @ApiModelProperty(value = "会员头像")
    private String avatar;

    @ApiModelProperty(value = "评论时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "评论内容")
    private String content;
}

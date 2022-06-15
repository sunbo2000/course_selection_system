package org.snbo.eduservice.bean.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author sunbo
 * @create 2022-04-15-20:43
 */
@Data
public class CommentPart {

    @ApiModelProperty(value = "评论ID")
    private String id;

    @ApiModelProperty(value = "回复所属的块id, 评论的块id为0,回复的块id为父评论id")
    private String partId;

    @ApiModelProperty(value = "会员id")
    private String memberId;

    @ApiModelProperty(value = "会员昵称")
    private String nickname;

    @ApiModelProperty(value = "会员头像")
    private String avatar;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "回复的条数")
    private Long total;

    @ApiModelProperty(value = "评论块下的回复")
    private List<ReplyPart> replyList;

    @ApiModelProperty(value = "是否显示回复分页条")
    private Boolean showPage;

}

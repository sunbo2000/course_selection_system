package org.snbo.eduservice.bean.qa;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;

import java.util.Date;
import java.util.List;

/**
 * @author sunbo
 * @create 2022-04-28-21:29
 */
@Data
public class Topic {

    @ApiModelProperty(value = "评论ID")
    private String id;

    @ApiModelProperty(value = "回复所属的块id, 评论的块id为0,回复的块id为父评论id")
    private String partId;

    @ApiModelProperty(value = "会员id")
    private String userId;

    @ApiModelProperty(value = "会员昵称")
    private String nickname;

    @ApiModelProperty(value = "会员头像")
    private String avatar;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论图片")
    private List<String> pictureList;

    @ApiModelProperty(value = "点赞数")
    private int good;

    @ApiModelProperty(value = "评论时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "回复的条数")
    private Integer total;

    @ApiModelProperty(value = "是否有下一页")
    private Boolean next;

    @ApiModelProperty(value = "是否有上一页")
    private Boolean previous;

    @ApiModelProperty(value = "当前页")
    private Integer current;

    @ApiModelProperty(value = "是否已经点赞")
    private Boolean showGood;

    @ApiModelProperty(value = "总页数")
    private Integer pages;

    @ApiModelProperty(value = "评论块下的回复")
    private List<Comment> commentList;

    @ApiModelProperty(value = "是否显示回复分页条")
    private Boolean showPage;

}

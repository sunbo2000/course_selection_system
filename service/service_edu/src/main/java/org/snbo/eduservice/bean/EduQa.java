package org.snbo.eduservice.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author snbo
 * @since 2022-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "EduQa对象", description = "话题实体类")
public class EduQa implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "话题id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "问答内容")
    private String content;

    @ApiModelProperty(value = "评论图片")
    private String picture;

    @ApiModelProperty(value = "点赞人数")
    private Integer good;

    @ApiModelProperty(value = "问答所属的话题id")
    private String partId;

    @ApiModelProperty(value = "回复对象的昵称")
    private String toName;

    @ApiModelProperty(value = "逻辑删除1(true)")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}

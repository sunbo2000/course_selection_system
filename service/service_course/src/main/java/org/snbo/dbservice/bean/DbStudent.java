package org.snbo.dbservice.bean;

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
 * @since 2022-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "DbStudent对象", description = "")
public class DbStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学生id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "学生学号")
    private String studentNumber;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "性别0:男,1:女")
    private Integer gender;

    @ApiModelProperty(value = "学生姓名")
    private String name;

    @ApiModelProperty(value = "学生专业id")
    private String majorId;

    @ApiModelProperty(value = "年级")
    private Integer grade;

    @ApiModelProperty(value = "学分限制")
    private Integer creditLimit;

    @ApiModelProperty(value = "学分")
    private Integer credit;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}

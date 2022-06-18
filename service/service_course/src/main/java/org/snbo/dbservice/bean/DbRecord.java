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
 * @since 2022-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "DbRecord对象", description = "")
public class DbRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "信息id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "年级")

    private Integer grade19;
    @ApiModelProperty(value = "年级")

    private Integer grade20;
    @ApiModelProperty(value = "年级")

    private Integer grade21;
    @ApiModelProperty(value = "年级")
    private Integer grade22;

    @ApiModelProperty(value = "开启时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "实际结束时间")
    private Date actualEndTime;

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

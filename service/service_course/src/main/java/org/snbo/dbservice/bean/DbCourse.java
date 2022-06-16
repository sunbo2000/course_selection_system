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
 * @since 2022-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DbCourse对象", description="")
public class DbCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "课程老师id")
    private String teacherId;

    @ApiModelProperty(value = "课程分类id")
    private String subjectId;

    @ApiModelProperty(value = "课程分类父级id")
    private String subjectParentId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "选课限制人数")
    private Integer limited;

    @ApiModelProperty(value = "已经选课人数")
    private Integer enroll;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程学分")
    private Integer credit;

    @ApiModelProperty(value = "课程是否可选,0:不可选,1:可选")
    private Integer status;

    @ApiModelProperty(value = "逻辑删除,0:未删除,1:已删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}

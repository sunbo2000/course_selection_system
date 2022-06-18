package org.snbo.dbservice.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author sunbo
 * @date 2022-06-16-12:27
 */
@Data
public class CourseVo {
    @ApiModelProperty(value = "课程id")
    private String id;

    @ApiModelProperty(value = "课程老师id")
    private String teacherId;

    @ApiModelProperty(value = "课程分类父级id")
    private String subjectParentId;

    @ApiModelProperty(value = "课程分类id")
    private String subjectId;

    @ApiModelProperty(value = "课程简介")
    private String description;

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
}

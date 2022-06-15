package org.snbo.eduservice.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author sunbo
 * @create 2022-03-30-13:11
 */
@Data
@ApiModel(value = "Course查询对象", description = "课程查询对象封装")
public class CourseQuery {

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;

    @ApiModelProperty(value = "二级类别id")
    private String subjectId;
}
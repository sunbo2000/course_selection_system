package org.snbo.dbservice.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunbo
 * @date 2022-06-16-9:54
 */
@Data
public class CourseQuery {

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程老师id")
    private String teacherId;

    @ApiModelProperty(value = "课程分类父级id")
    private String subjectParentId;

    @ApiModelProperty(value = "课程分类id")
    private String subjectId;

}

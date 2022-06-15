package org.snbo.eduservice.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author sunbo
 * @create 2022-03-29-19:26
 */
@Data
public class CoursePublishInfo {

    @ApiModelProperty(value = "课程id")
    private String id;
    @ApiModelProperty(value = "课程名称")
    private String title;
    @ApiModelProperty(value = "课程课时")
    private Integer lessonNum;
    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;
    @ApiModelProperty(value = "课程封面")
    private String cover;
    @ApiModelProperty(value = "课程简介")
    private String description;
    @ApiModelProperty(value = "讲师名")
    private String teacherName;
    @ApiModelProperty(value = "一级分类")
    private String oneSubject;
    @ApiModelProperty(value = "二级分类")
    private String twoSubject;
}

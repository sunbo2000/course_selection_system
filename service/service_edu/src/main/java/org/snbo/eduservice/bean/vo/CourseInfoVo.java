package org.snbo.eduservice.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

 /**
 *  * 为什么需要这个类?
 *  * 因为前端以json格式发送数据,后端用对象去接收,前端的基本信息和简介是一起发送的,但是后端两者是分开的
 *  * 用基本信息的类去接收信息接收不到简介信息,所以需要创建一个VO(value object)专门来接收前端信息
 *
 * @author sunbo
 * @create 2022-03-26-16:35
 */

@Data
public class CourseInfoVo {

    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "课程专业ID")
    private String subjectId;

    @ApiModelProperty(value = "课程专业父级ID")
    private String subjectParentId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程简介")
    private String description;
}

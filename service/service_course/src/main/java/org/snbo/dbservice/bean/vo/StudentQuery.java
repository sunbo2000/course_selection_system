package org.snbo.dbservice.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunbo
 * @date 2022-06-17-1:14
 */
@Data
public class StudentQuery {
    @ApiModelProperty(value = "学生学号")
    private String studentNumber;

    @ApiModelProperty(value = "学生姓名")
    private String name;

    @ApiModelProperty(value = "学生专业")
    private String major;

    @ApiModelProperty(value = "年级")
    private Integer grade;
}

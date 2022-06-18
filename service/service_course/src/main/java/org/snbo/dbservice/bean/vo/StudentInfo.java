package org.snbo.dbservice.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunbo
 * @date 2022-06-17-19:46
 */

@Data
public class StudentInfo {
    @ApiModelProperty(value = "学生id")
    private String id;

    @ApiModelProperty(value = "学生学号")
    private String studentNumber;

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
}

package org.snbo.dbservice.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunbo
 * @date 2022-06-16-15:56
 */

@Data
public class TeacherQuery {
    @ApiModelProperty(value = "教师所在学院")
    private String college;

    @ApiModelProperty(value = "教师姓名")
    private String name;
}

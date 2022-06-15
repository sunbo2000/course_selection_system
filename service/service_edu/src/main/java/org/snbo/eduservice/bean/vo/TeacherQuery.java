package org.snbo.eduservice.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunbo
 * @create 2022-03-18-10:31
 */
@Data
public class TeacherQuery {

    @ApiModelProperty("条件查询,教师名称")
    private String name;

    @ApiModelProperty("条件查询,教师头衔,1 高级讲师 2 首席讲师")
    private Integer level;

    @ApiModelProperty(value = "条件查询,开始时间",example = "2022-03-19 12:30:30")
    private String begin;

    @ApiModelProperty(value = "条件查询,结束时间",example = "2022-03-19 12:30:30")
    private String end;
}

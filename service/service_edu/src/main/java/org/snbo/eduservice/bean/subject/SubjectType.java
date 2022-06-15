package org.snbo.eduservice.bean.subject;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 前端需要特定格式数据返回
 *
 * @author sunbo
 * @create 2022-03-25-22:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectType {
    @ApiModelProperty(value = "课程分类 id")
    private String id;
    @ApiModelProperty(value = "分类标题")
    private String title;
    @ApiModelProperty(value = "当分类是一级分类时,作为子分类")
    List<SubjectType> children;
}

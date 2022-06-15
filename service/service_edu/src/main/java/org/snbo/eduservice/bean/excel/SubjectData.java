package org.snbo.eduservice.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * excel 表格的第一行是作为表头的,即下面的一级分类和二级分类
 * 从第二行开始读取数据
 *
 * @author sunbo
 * @create 2022-03-23-21:43
 */

@Data
public class SubjectData {

    @ExcelProperty(value = "一级分类", index = 0)
    private String oneSubjectName;
    @ExcelProperty(value = "二级分类", index = 1)
    private String twoSubjectName;

}

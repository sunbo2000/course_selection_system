package org.snbo.dbservice.service;

import org.snbo.dbservice.bean.DbSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.snbo.dbservice.bean.vo.SubjectType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author snbo
 * @since 2022-06-16
 */
public interface DbSubjectService extends IService<DbSubject> {

    /**
     * description: 保存 Excel 文件内的课程分类数据到数据库中
     *
     * @param file Excel 文件
     * @param subjectService 分类业务类
     * @author sunbo
     * @date 2022/5/28 14:11
     */
    void saveSubject(MultipartFile file, DbSubjectService subjectService);

    /**
     * description: 获取所有课程分类数据
     *
     * @return {@link List <SubjectType>}
     * @author sunbo
     * @date 2022/5/28 14:16
     */
    List<SubjectType> getAllSubject();

}

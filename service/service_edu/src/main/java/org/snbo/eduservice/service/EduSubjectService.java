package org.snbo.eduservice.service;

import org.snbo.eduservice.bean.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.snbo.eduservice.bean.subject.SubjectType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author snbo
 * @since 2022-03-23
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * description: 保存 Excel 文件内的课程分类数据到数据库中
     *
     * @param file Excel 文件
     * @param subjectService 分类业务类
     * @author sunbo
     * @date 2022/5/28 14:11
     */
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    /**
     * description: 获取所有课程分类数据
     *
     * @return {@link List<SubjectType>}
     * @author sunbo
     * @date 2022/5/28 14:16
     */
    List<SubjectType> getAllSubject();
}

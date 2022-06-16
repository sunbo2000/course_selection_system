package org.snbo.dbservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.snbo.dbservice.bean.DbSubject;
import org.snbo.dbservice.bean.excel.SubjectData;
import org.snbo.dbservice.bean.vo.SubjectType;
import org.snbo.dbservice.listener.SubjectExcelListener;
import org.snbo.dbservice.mapper.DbSubjectMapper;
import org.snbo.dbservice.service.DbSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author snbo
 * @since 2022-06-16
 */
@Service
public class DbSubjectServiceImpl extends ServiceImpl<DbSubjectMapper, DbSubject> implements DbSubjectService {
    @Override
    public void saveSubject(MultipartFile file, DbSubjectService subjectService) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<SubjectType> getAllSubject() {
        //分类集合
        List<SubjectType> list = new ArrayList<>();
        //查找一级分类
        QueryWrapper<DbSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        //效果相同
        List<DbSubject> list1 = baseMapper.selectList(wrapper);
        //查找二级分类
        QueryWrapper<DbSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.ne("parent_id", "0");
        List<DbSubject> list2 = this.list(wrapper1);

        for (DbSubject sub1 : list1) {
            //创建一级分类
            SubjectType subjectType = new SubjectType();
            //设置一级分类
            subjectType.setId(sub1.getId());
            subjectType.setTitle(sub1.getTitle());
            //二级分类子集合
            List<SubjectType> children = new ArrayList<>();
            subjectType.setChildren(children);
            //添加一级分类到集合中
            list.add(subjectType);
            String pid = sub1.getId();
            for (DbSubject sub2 : list2) {
                if (pid.equals(sub2.getParentId())) {
                    //创建二级分类
                    SubjectType subjectType2 = new SubjectType();
                    //设置二级分类
                    BeanUtils.copyProperties(sub2, subjectType2);
                    //添加二级分类到集合中
                    children.add(subjectType2);
                }
            }
        }
        return list;
    }
}

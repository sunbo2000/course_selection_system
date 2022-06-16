package org.snbo.dbservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import org.snbo.dbservice.bean.DbSubject;
import org.snbo.dbservice.bean.excel.SubjectData;
import org.snbo.dbservice.service.DbSubjectService;
import org.snbo.servicebase.exceptionhandler.MoguException;

/**
 * @author sunbo
 * @create 2022-03-23-22:21
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //监听器不能交给 spring 管理,因为它比 spring 加载的早(监听器>过滤器>servlet>spring,不能给它注入其他对象
    //不能实现数据库操作
    //既然不能注入,那就给它通过有参构造传进来

    public DbSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(DbSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * 读取Excel,一行一行读取
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new MoguException(20001, "文件数据为空");
        }
        //一行一行读取,每次都有两个值,第一个值一级分类,第二个值二级分类
        //判断一级分类不能重复
        String oneName = subjectData.getOneSubjectName();
        DbSubject oneSubject = existOneSubject(oneName);
        //没有一级分类
        if (oneSubject == null) {
            oneSubject = new DbSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(oneSubject);
        }

        //判断二级分类不能重复
        String pid = oneSubject.getId();
        String twoName = subjectData.getTwoSubjectName();
        DbSubject twoSubject = existTwoSubject(twoName, pid);
        //没有一级分类
        if (twoSubject == null) {
            twoSubject = new DbSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(twoName);
            subjectService.save(twoSubject);
        }
    }

    /**
     * 根据名字查找一级分类
     */
    private DbSubject existOneSubject(String name) {
        QueryWrapper<DbSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        return subjectService.getOne(wrapper);
    }

    /**
     * 根据名字和父 id 查找二级分类
     */
    private DbSubject existTwoSubject(String name, String pid) {
        QueryWrapper<DbSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        return subjectService.getOne(wrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

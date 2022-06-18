package org.snbo.dbservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.snbo.dbservice.bean.DbTeacher;
import org.snbo.dbservice.bean.vo.TeacherQuery;
import org.snbo.dbservice.mapper.DbTeacherMapper;
import org.snbo.dbservice.service.DbTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author snbo
 * @since 2022-06-16
 */
@Service
public class DbTeacherServiceImpl extends ServiceImpl<DbTeacherMapper, DbTeacher> implements DbTeacherService {

    @Override
    public Map<String, Object> getTeacherPage(Long current, Long size, TeacherQuery teacherQuery) {
        Page<DbTeacher> page = new Page<>(current, size);
        QueryWrapper<DbTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        String collegeId = teacherQuery.getCollege();

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(collegeId)) {
            wrapper.eq("college_id", collegeId);
        }

        wrapper.orderByDesc("gmt_modified");

        baseMapper.selectPage(page, wrapper);

        Map<String, Object> map = new HashMap<>(2);
        map.put("teacherList", page.getRecords());
        map.put("total", page.getTotal());

        return map;
    }
}

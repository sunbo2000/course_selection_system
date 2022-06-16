package org.snbo.dbservice.service.impl;

import org.snbo.dbservice.bean.DbTeacher;
import org.snbo.dbservice.mapper.DbTeacherMapper;
import org.snbo.dbservice.service.DbTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}

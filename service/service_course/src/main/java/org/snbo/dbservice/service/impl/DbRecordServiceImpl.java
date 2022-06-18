package org.snbo.dbservice.service.impl;

import org.snbo.dbservice.bean.DbRecord;
import org.snbo.dbservice.mapper.DbRecordMapper;
import org.snbo.dbservice.service.DbRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author snbo
 * @since 2022-06-18
 */
@Service
public class DbRecordServiceImpl extends ServiceImpl<DbRecordMapper, DbRecord> implements DbRecordService {

}

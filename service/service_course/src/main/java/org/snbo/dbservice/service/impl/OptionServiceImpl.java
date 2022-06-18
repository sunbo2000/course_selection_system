package org.snbo.dbservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.snbo.commonutils.ResultCode;
import org.snbo.dbservice.bean.DbRecord;
import org.snbo.dbservice.bean.DbStudent;
import org.snbo.dbservice.bean.vo.OptionVo;
import org.snbo.dbservice.service.DbRecordService;
import org.snbo.dbservice.service.OptionService;
import org.snbo.dbservice.utils.OptionUtils;
import org.snbo.servicebase.exceptionhandler.MoguException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author sunbo
 * @date 2022-06-17-23:01
 */
@Service
public class OptionServiceImpl implements OptionService {

    private final RedisTemplate<String, String> redisTemplate;
    private final DbRecordService recordService;

    @Autowired
    public OptionServiceImpl(RedisTemplate<String, String> redisTemplate, DbRecordService recordService) {
        this.redisTemplate = redisTemplate;
        this.recordService = recordService;
    }

    @Override
    public void startOption(OptionVo option) {
        long timeDelay = 0L;
        if (option.getStart() != null) {
            timeDelay = option.getStart().getTime() - System.currentTimeMillis();
            if (timeDelay < 0) {
                start(option);
                return;
            }
        }
        //保存记录
        DbRecord record = new DbRecord();
        if (option.getGrade19()) {
            record.setGrade19(1);
        }
        if (option.getGrade20()) {
            record.setGrade20(1);
        }
        if (option.getGrade21()) {
            record.setGrade21(1);
        }
        if (option.getGrade22()) {
            record.setGrade22(1);
        }
        record.setStartTime(option.getStart());
        record.setEndTime(option.getEnd());


        recordService.save(record);

        //延时执行
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                start(option);
            }
        }, timeDelay);
    }


    public void start(OptionVo optionVo) {
        if (optionVo.getGrade19()) {
            redisTemplate.opsForValue().set(OptionUtils.getGRADE19(), "2019", optionVo.getEnd().getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
        if (optionVo.getGrade20()) {
            redisTemplate.opsForValue().set(OptionUtils.getGRADE20(), "2020", optionVo.getEnd().getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
        if (optionVo.getGrade21()) {
            redisTemplate.opsForValue().set(OptionUtils.getGRADE21(), "2021", optionVo.getEnd().getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
        if (optionVo.getGrade22()) {
            redisTemplate.opsForValue().set(OptionUtils.getGRADE22(), "2022", optionVo.getEnd().getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void endTask(String id) {
        DbRecord record = recordService.getById(id);
        if (record.getEndTime().getTime() < System.currentTimeMillis()) {
            throw new MoguException(ResultCode.ERROR, "任务已结束");
        }

        List<String> list = new ArrayList<>(4);
        if (record.getGrade19() == 1) {
            list.add(OptionUtils.getGRADE19());
        }
        if (record.getGrade20() == 1) {
            list.add(OptionUtils.getGRADE20());
        }
        if (record.getGrade21() == 1) {
            list.add(OptionUtils.getGRADE21());
        }
        if (record.getGrade22() == 1) {
            list.add(OptionUtils.getGRADE22());
        }

        redisTemplate.delete(list);

        record.setActualEndTime(new Date());
        recordService.updateById(record);
    }

    @Override
    public Map<String, Object> getPage(Long current, Long size) {
        Page<DbRecord> page = new Page<>(current, size);
        QueryWrapper<DbRecord> wrapper = new QueryWrapper<>();

        wrapper.orderByDesc("gmt_create");

        recordService.page(page, wrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("recordList", page.getRecords());
        map.put("total", page.getTotal());

        return map;
    }

}

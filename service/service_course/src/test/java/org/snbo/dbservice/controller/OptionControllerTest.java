package org.snbo.dbservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.snbo.dbservice.bean.vo.OptionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sunbo
 * @date 2022-06-18-10:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class OptionControllerTest {

    @Autowired
    private OptionController optionController;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void addScheme() throws ParseException {
        OptionVo optionVo = new OptionVo();
        optionVo.setGrade19(true);
        optionVo.setGrade22(true);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date parse = simpleDateFormat.parse("2022-06-18 10:15:00");
        System.out.println(parse);
        optionVo.setStart(parse);
        Date parse1 = simpleDateFormat.parse("2022-06-18 10:19:00");
        optionVo.setEnd(parse1);

        optionController.addScheme(optionVo);
    }

    @Test
    void endTask() {
        System.out.println(redisTemplate.opsForValue().get("2019"));
        System.out.println(redisTemplate.opsForValue().get("2020"));
        System.out.println(redisTemplate.opsForValue().get("2021"));
        System.out.println(redisTemplate.opsForValue().get("2022"));

    }
}
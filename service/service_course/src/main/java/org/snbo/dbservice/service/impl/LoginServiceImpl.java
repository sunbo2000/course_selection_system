package org.snbo.dbservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.snbo.commonutils.JwtUtils;
import org.snbo.commonutils.ResultCode;
import org.snbo.dbservice.bean.DbStudent;
import org.snbo.dbservice.bean.vo.LoginVo;
import org.snbo.dbservice.bean.vo.StudentInfo;
import org.snbo.dbservice.service.DbStudentService;
import org.snbo.dbservice.service.LoginService;
import org.snbo.servicebase.exceptionhandler.MoguException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sunbo
 * @date 2022-06-17-19:24
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final RedisTemplate<String, String> redisTemplate;
    private final DbStudentService studentService;

    @Autowired
    public LoginServiceImpl(RedisTemplate<String, String> redisTemplate, DbStudentService studentService) {
        this.redisTemplate = redisTemplate;
        this.studentService = studentService;
    }

    @Override
    public String login(LoginVo loginVo) {
        String studentNumber = loginVo.getUserId();
        String password = loginVo.getPassword();

        if (StringUtils.isEmpty(studentNumber) || StringUtils.isEmpty(password)) {
            throw new MoguException(ResultCode.ERROR, "登录失败");
        }

        //非空再继续
        QueryWrapper<DbStudent> wrapper = new QueryWrapper<>();
        wrapper.eq("student_number", studentNumber);
        DbStudent student = studentService.getOne(wrapper);

        if (student == null) {
            throw new MoguException(ResultCode.ERROR, "账号或密码错误");
        }

        //判断是不是选课时间
        String isTime = redisTemplate.opsForValue().get(studentNumber.substring(0, 4));
        if (StringUtils.isEmpty(isTime)) {
            throw new MoguException(ResultCode.ERROR, "当前不是选课时间");
        }

        //全通过返回token
        return JwtUtils.getJwtToken(student.getId(), student.getName());
    }

    @Override
    public StudentInfo getLoginInfo(HttpServletRequest request) {
        String studentId = JwtUtils.getMemberIdByJwtToken(request);
        if (!StringUtils.isEmpty(studentId)) {
            throw new MoguException(ResultCode.ERROR, "未登录");
        }

        DbStudent byId = studentService.getById(studentId);
        StudentInfo studentInfo = new StudentInfo();
        BeanUtils.copyProperties(byId, studentInfo);

        return studentInfo;
    }
}

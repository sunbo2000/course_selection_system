package org.snbo.dbservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.snbo.commonutils.JwtUtils;
import org.snbo.commonutils.MD5Utils;
import org.snbo.commonutils.ResultCode;
import org.snbo.dbservice.bean.DbCourse;
import org.snbo.dbservice.bean.DbStudent;
import org.snbo.dbservice.bean.StudentCourse;
import org.snbo.dbservice.bean.vo.StudentUpdateInfo;
import org.snbo.dbservice.bean.vo.StudentQuery;
import org.snbo.dbservice.bean.vo.SubjectType;
import org.snbo.dbservice.mapper.DbStudentMapper;
import org.snbo.dbservice.service.DbCourseService;
import org.snbo.dbservice.service.DbStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.snbo.dbservice.service.DbSubjectService;
import org.snbo.dbservice.service.StudentCourseService;
import org.snbo.servicebase.exceptionhandler.MoguException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author snbo
 * @since 2022-06-17
 */
@Service
@Transactional
public class DbStudentServiceImpl extends ServiceImpl<DbStudentMapper, DbStudent> implements DbStudentService {
    private final DbSubjectService subjectService;

    private final DbCourseService courseService;
    private final StudentCourseService studentCourseService;

    private final String[] Surname = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许",
            "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎",
            "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷",
            "罗", "毕", "郝", "邬", "安", "常", "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄", "和",
            "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒",
            "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闵", "席", "季"};
    private final String[] girlName = {"梦琪", "忆柳", "之桃", "慕青", "问兰", "尔岚", "元香", "初夏",
            "沛菡", "傲珊", "曼文", "乐菱", "痴珊", "恨玉", "惜文", "香寒", "新柔", "语蓉",
            "海安", "夜蓉", "涵柏", "水桃", "醉蓝", "春儿", "语琴", "从彤", "傲晴", "语兰",
            "又菱", "碧彤", "元霜", "怜梦", "紫寒", "妙彤", "曼易", "南莲", "紫翠", "雨寒",
            "若南", "寻真", "晓亦", "向珊", "慕灵", "以蕊", "寻雁", "映易", "雪柳", "孤岚",
            "笑霜", "海云", "凝天", "沛珊", "寒云", "冰旋", "宛儿", "绿真", "盼儿", "晓霜",
            "碧凡", "夏菡", "曼香", "若烟", "半梦", "雅绿", "冰蓝", "灵槐", "平安", "书翠",
            "翠风", "香巧", "代云", "梦曼", "幼翠", "友巧", "听寒", "梦柏", "醉易", "访旋",
            "亦玉", "凌萱", "访卉", "怀亦", "笑蓝", "春翠", "靖柏", "夜蕾", "冰夏", "梦松",
            "书雪", "乐枫", "念薇", "靖雁", "寻春", "恨山", "从寒", "忆香", "觅波", "静曼",
            "易烟", "如萱"};
    private final String[] boyName = {"晓博", "文博", "宇文", "辰锟", "智晖", "宜春", "和雅", "明杰", "华采", "兴安",
            "文柏", "君之", "晗日", "懿轩", "睿德", "昊嘉", "宏伟", "嘉慕", "元德", "康德", "阳泽", "昆颉", "良骏", "理群",
            "佑运", "永元", "高懿", "良策", "英华", "周成", "嘉纳", "曦之", "兴旺", "飞翮", "玉轩", "俊楚", "鹏池", "朋兴",
            "意智", "阳舒", "运盛", "勇男", "心水", "文山", "嘉许", "涵润", "敏学", "建同", "伟茂", "泰然", "建安", "铭晨",
            "涵涤", "骞北", "璞瑜", "康泰", "光熙", "建义", "凯泽", "阳伯", "锐逸", "宏邈", "良工", "浦和", "智刚", "哲茂",
            "鹏运", "雅昶", "经国", "海阳", "嘉茂", "德义", "飞沉", "英耀", "宏远", "弘量", "和悦", "鸿文", "烨磊", "和宜",};

    @Autowired
    public DbStudentServiceImpl(DbSubjectService subjectService, StudentCourseService studentCourseService, DbCourseService courseService) {
        this.subjectService = subjectService;
        this.studentCourseService = studentCourseService;
        this.courseService = courseService;
    }


    @Override
    public Map<String, Object> getStudentPage(Long current, Long size, StudentQuery studentQuery) {
        Page<DbStudent> page = new Page<>(current, size);

        String name = studentQuery.getName();
        String studentNumber = studentQuery.getStudentNumber();
        Integer grade = studentQuery.getGrade();
        String major = studentQuery.getMajor();

        QueryWrapper<DbStudent> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(studentNumber)) {
            wrapper.eq("student_number", studentNumber);
        }
        if (!StringUtils.isEmpty(grade)) {
            wrapper.eq("grade", grade);
        }
        if (!StringUtils.isEmpty(major)) {
            wrapper.eq("major", major);
        }

        wrapper.orderByAsc("grade", "major", "student_number");

        baseMapper.selectPage(page, wrapper);

        Map<String, Object> map = new HashMap<>(2);
        map.put("studentList", page.getRecords());
        map.put("total", page.getTotal());

        return map;
    }

    @Override
    public void importStudents() {
        int num = 50;
        List<DbStudent> list = new ArrayList<>();

        List<SubjectType> types = subjectService.getAllSubject();
        while (num-- > 0) {
            list.add(createStudent(types));
        }

        //统一添加学号
        //获取该年级该专业最大学号
        QueryWrapper<DbStudent> wrapper = new QueryWrapper<>();
        wrapper.select("grade,major_id AS majorId,MAX(student_number) AS number");
        wrapper.groupBy("grade", "major_id ");
        List<Map<String, Object>> result = baseMapper.selectMaps(wrapper);
        if (result == null) {
            result = new ArrayList<>();
        }

        Iterator<DbStudent> iterator = list.iterator();
        List<Map<String, Object>> myList = new ArrayList<>();
        while (iterator.hasNext()) {
            DbStudent student = iterator.next();
            int number = 0;
            for (Map<String, Object> map : result) {
                if (student.getGrade().equals(Integer.valueOf(map.get("grade").toString()))
                        && student.getMajorId().equals(map.get("majorId"))) {
                    number = Integer.parseInt(map.get("number").toString()) % 1000 + 1;

                    //设置学号
                    String sid = student.getGrade() +
                            student.getMajorId().substring(16) + number;
                    student.setStudentNumber(sid);
                    //设置密码
                    student.setPassword(student.getStudentNumber());
                    map.put("number", number);
                }
            }
            if (number == 0) {
                for (Map<String, Object> map : myList) {
                    if (student.getGrade().equals(Integer.valueOf(map.get("grade").toString()))
                            && student.getMajorId().equals(map.get("majorId"))) {
                        number = Integer.parseInt(map.get("number").toString()) % 1000 + 1;
                        //设置学号
                        String sid = student.getGrade() +
                                student.getMajorId().substring(16) + number;
                        student.setStudentNumber(String.valueOf(sid));
                        //设置密码
                        student.setPassword(student.getStudentNumber());
                        map.put("number", number);
                    }
                }

                if (number == 0) {
                    number = 100;
                    //设置学号
                    String sid = student.getGrade() +
                            student.getMajorId().substring(16) + number;
                    student.setStudentNumber(sid);
                    //设置密码
                    student.setPassword(student.getStudentNumber());
                    Map<String, Object> map = new HashMap<>();
                    map.put("grade", student.getGrade());
                    map.put("majorId", student.getMajorId());
                    map.put("number", student.getStudentNumber());
                    myList.add(map);
                }

            }
        }

        this.saveBatch(list);
    }

    private DbStudent createStudent(List<SubjectType> types) {

        DbStudent student = new DbStudent();
        Random random = new Random();
        String firstname = Surname[random.nextInt(Surname.length)];
        if (random.nextInt(2) == 0) {
            student.setGender(0);
            student.setName(firstname + boyName[random.nextInt(boyName.length)]);
        } else {
            student.setGender(1);
            student.setName(firstname + girlName[random.nextInt(girlName.length)]);
        }

        //设置年级
        student.setGrade(2019 + random.nextInt(4));

        //设置专业
        student.setMajorId(types.get(random.nextInt(types.size())).getId());

        //设置默认头像
        if (student.getGender() == 0) {
            student.setAvatar("https://edu-mogu.oss-cn-chengdu.aliyuncs.com/%E5%AD%A6%E7%94%9F%20%281%29.png");
        } else {
            student.setAvatar("https://edu-mogu.oss-cn-chengdu.aliyuncs.com/%E5%AD%A6%E7%94%9F%E5%A6%B9.png");
        }

        return student;
    }

    @Override
    public void updateStudent(StudentUpdateInfo studentUpdateInfo) {
        if (!StringUtils.isEmpty(studentUpdateInfo.getId())) {
            throw new MoguException(ResultCode.ERROR, "错误信息");
        }
        DbStudent student = baseMapper.selectById(studentUpdateInfo.getId());
        if (!StringUtils.isEmpty(studentUpdateInfo.getOldPassword())) {
            if (MD5Utils.encrypt(studentUpdateInfo.getOldPassword()).equals(student.getPassword())) {
                student.setPassword(MD5Utils.encrypt(studentUpdateInfo.getPassword()));
            }
        }
        if (!StringUtils.isEmpty(studentUpdateInfo.getAvatar())) {
            student.setAvatar(studentUpdateInfo.getAvatar());
        }
        baseMapper.updateById(student);
    }

    @Override
    public boolean selectCurriculum(HttpServletRequest request, String courseId) {
        String studentId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(studentId)) {
            throw new MoguException(ResultCode.ERROR, "未登录");
        }

        DbStudent student = baseMapper.selectById(studentId);
        DbCourse course = courseService.getById(courseId);

        Integer credit = student.getCredit();
        Integer creditLimit = student.getCreditLimit();

        if ((credit + course.getCredit()) > creditLimit) {
            throw new MoguException(ResultCode.ERROR, "超出学分限制");
        }

        if (course.getEnroll().equals(course.getLimited())) {
            throw new MoguException(ResultCode.ERROR, "选课人数已满");
        }

        //课程注册人数加一
        course.setEnroll(course.getEnroll() + 1);
        courseService.updateById(course);
        //学生学分加上课程学分
        student.setCredit(credit + course.getCredit());
        baseMapper.updateById(student);
        //添加关系表
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(studentId);
        studentCourse.setCourseId(courseId);

        return studentCourseService.save(studentCourse);
    }

    @Override
    public boolean dropout(HttpServletRequest request, String courseId) {
        String studentId = JwtUtils.getMemberIdByJwtToken(request);
        DbStudent student = baseMapper.selectById(studentId);
        DbCourse course = courseService.getById(courseId);

        course.setEnroll(course.getEnroll() - 1);
        student.setCredit(student.getCredit() - course.getCredit());

        boolean b = courseService.updateById(course);
        if (!b) {
            throw new MoguException(ResultCode.ERROR, "退课失败,请重试");
        }

        int i = baseMapper.updateById(student);
        if (i <= 0) {
            throw new MoguException(ResultCode.ERROR, "退课失败,请重试");
        }

        QueryWrapper<StudentCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", studentId);
        wrapper.eq("course_id", courseId);

        return studentCourseService.remove(wrapper);
    }

    @Override
    public Map<String, Object> getCurriculum(HttpServletRequest request, String subjectId) {
        String studentId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(studentId)) {
            throw new MoguException(ResultCode.ERROR, "未登录");
        }
        List<DbCourse> courseList = baseMapper.getStudentCourseList(studentId, subjectId);
        Map<String, Object> map = new HashMap<>();
        map.put("courseList", courseList);
        return map;
    }
}

package org.snbo.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.snbo.eduservice.bean.EduQa;
import org.snbo.eduservice.bean.qa.Comment;
import org.snbo.eduservice.bean.qa.Topic;
import org.snbo.eduservice.mapper.EduQaMapper;
import org.snbo.eduservice.service.EduQaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author snbo
 * @since 2022-04-28
 */
@Service
public class EduQaServiceImpl extends ServiceImpl<EduQaMapper, EduQa> implements EduQaService {


    @Override
    public Map<String, Object> getTopic(String length, String context) {
        List<Topic> topicList = new ArrayList<>();
        QueryWrapper<EduQa> wrapper = new QueryWrapper<>();
        wrapper.eq("part_id", "0");
        Integer count = baseMapper.selectCount(wrapper);
        if (!StringUtils.isEmpty(context)) {
            wrapper.like("content", context);
        }
        wrapper.orderByDesc("good");
        wrapper.last("limit " + length);

        List<EduQa> eduQaList = baseMapper.selectList(wrapper);
        for (EduQa eduQa : eduQaList) {
            Topic topic = new Topic();
            BeanUtils.copyProperties(eduQa, topic);
            if (!StringUtils.isEmpty(eduQa.getPicture())) {
                topic.setPictureList(Arrays.asList(eduQa.getPicture().split(",")));
            }
            QueryWrapper<EduQa> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("part_id", topic.getId());
            //查询回复条数
            topic.setTotal(baseMapper.selectCount(wrapper1));

            topicList.add(topic);
        }

        Map<String, Object> map = new HashMap<>(16);
        map.put("topicList", topicList);
        map.put("total", count);
        return map;
    }

    @Override
    public Map<String, Object> getTopicPage(String partId, Long current, Long size) {
        List<Topic> topicList = new ArrayList<>();
        Page<EduQa> page = new Page<>(current, size);
        QueryWrapper<EduQa> wrapper = new QueryWrapper<>();
        wrapper.eq("part_id", partId);
        wrapper.orderByAsc("gmt_create");
        baseMapper.selectPage(page, wrapper);

        //把主题放进第一位
        if (current == 1) {
            //楼层话题
            EduQa qa = baseMapper.selectById(partId);
            Topic topic = new Topic();
            BeanUtils.copyProperties(qa, topic);
            if (!StringUtils.isEmpty(qa.getPicture())) {
                topic.setPictureList(Arrays.asList(qa.getPicture().split(",")));
            }
            topicList.add(topic);
        }
        List<EduQa> eduQaList = page.getRecords();
        Long total = page.getTotal();
        for (EduQa eduQa : eduQaList) {
            Topic topic = new Topic();
            if (!StringUtils.isEmpty(eduQa.getPicture())) {
                topic.setPictureList(Arrays.asList(eduQa.getPicture().split(",")));
            }
            BeanUtils.copyProperties(eduQa, topic);

            QueryWrapper<EduQa> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("part_id", topic.getId());
            //查询回复条数
            topic.setTotal(baseMapper.selectCount(wrapper1));

            //先看三条
            wrapper1.orderByAsc("gmt_create");
            wrapper1.last("limit 3");
            List<EduQa> eduQaList1 = baseMapper.selectList(wrapper1);
            ArrayList<Comment> commentList = new ArrayList<>();
            for (EduQa qa : eduQaList1) {
                Comment comment = new Comment();
                BeanUtils.copyProperties(qa, comment);
                commentList.add(comment);
            }

            topic.setCommentList(commentList);
            topicList.add(topic);
        }

        Map<String, Object> map = new HashMap<>(16);
        map.put("topicList", topicList);
        map.put("total", total);
        return map;
    }


    @Override
    public Map<String, Object> getCommentPage(String partId, Page<EduQa> page) {
        QueryWrapper<EduQa> wrapper = new QueryWrapper<>();
        wrapper.eq("part_id", partId);
        wrapper.orderByAsc("gmt_create");
        baseMapper.selectPage(page, wrapper);

        List<EduQa> eduQaList = page.getRecords();
        ArrayList<Comment> commentList = new ArrayList<>();
        for (EduQa qa : eduQaList) {
            Comment comment = new Comment();
            BeanUtils.copyProperties(qa, comment);
            commentList.add(comment);
        }


        Map<String, Object> map = new HashMap<>(16);
        map.put("commentList", commentList);
        map.put("total", page.getTotal());
        map.put("next", page.hasNext());
        map.put("previous", page.hasPrevious());
        map.put("current", page.getCurrent());
        map.put("pages", page.getPages());
        return map;
    }
}

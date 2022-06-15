package org.snbo.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.snbo.commonutils.R;
import org.snbo.commonutils.ResultCode;
import org.snbo.eduservice.bean.EduVideo;
import org.snbo.eduservice.feignclient.VodClient;
import org.snbo.eduservice.mapper.EduVideoMapper;
import org.snbo.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.snbo.servicebase.exceptionhandler.MoguException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author snbo
 * @since 2022-03-26
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {


    private final VodClient client;

    @Autowired
    public EduVideoServiceImpl(VodClient client) {
        this.client = client;
    }

    @Override
    public void removeByCourseId(String courseId) {
        //根据视频id删除阿里云视频
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.select("video_source_id");
        wrapper.eq("course_id", courseId);
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper);
        //
        List<String> videoIds = new ArrayList<>();
        for (EduVideo video : eduVideos) {
            String videoId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoId)) {
                videoIds.add(videoId);
            }
        }
        if (!videoIds.isEmpty()) {
            //调用另一个模块删除视频的接口
            R result = client.deleteVideoByIds(videoIds);
            if (result.getCode().equals(ResultCode.ERROR)) {
                throw new MoguException(20001, result.getMessage() + ",熔断器...");
            }
        }
        //删除小节
        QueryWrapper<EduVideo> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id", courseId);
        baseMapper.delete(wrapper1);

    }

    @Override
    public void removeVideoInfoById(String id) {
        //先删除视频点播平台视频再删除数据库信息
        //根据小节id得到视频id
        String videoSourceId = baseMapper.selectById(id).getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            R result = client.deleteVideo(videoSourceId);
            if (result.getCode().equals(ResultCode.ERROR)) {
                throw new MoguException(20001, result.getMessage() + " 熔断器...");
            }
        }
        int byId = baseMapper.deleteById(id);
        if (byId <= 0) {
            throw new MoguException(ResultCode.ERROR, "删除小节信息失败");
        }
    }
}

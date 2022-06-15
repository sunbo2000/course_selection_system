package org.snbo.eduservice.feignclient;

import org.snbo.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 远程调用 edu-vod 服务,对课程视频进行操作
 *
 * @author sunbo
 * @create 2022-04-01-21:48
 */
@Component
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    /**
     * description: 根据视频 id 删除视频,分别从视频点播平台和数据库中删除
     *
     * @param id 视频 id
     * @return {@link R}
     * @author sunbo
     * @date 2022/5/27 16:26
     */
    @DeleteMapping("/eduVod/video/{id}")
    R deleteVideo(@PathVariable("id") String id);

    /**
     * description: 根据 id 集合删除多个视频,分别从视频点播平台和数据库中删除
     *
     * @param videoIdList 一个课程所有的视频 id 组成的集合
     * @return {@link R}
     * @author sunbo
     * @date 2022/5/27 16:29
     */
    @DeleteMapping("/eduVod/video")
    R deleteVideoByIds(@RequestParam("videoIdList") List<String> videoIdList);
}

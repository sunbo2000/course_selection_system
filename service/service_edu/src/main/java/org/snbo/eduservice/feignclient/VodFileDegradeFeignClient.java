package org.snbo.eduservice.feignclient;

import org.snbo.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 熔断器,当远程调用服务出错时会调用此熔断器
 *
 * @author sunbo
 * @create 2022-04-02-15:19
 */

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R deleteVideo(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteVideoByIds(List<String> videoIdList) {
        return R.error().message("删除多个视频出错");
    }
}

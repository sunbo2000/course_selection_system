package org.snbo.eduservice.feignclient;

import org.snbo.commonutils.vo.UCenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 远程调用 service-uCenter 服务
 *
 * @author sunbo
 * @create 2022-04-15-21:46
 */
@Component
@FeignClient(name = "service-uCenter")
public interface UCenterClient {
    /**
     * description: 根据用户 id 获取用户信息
     *
     * @param id 用户 id
     * @return {@link UCenterMember}
     * @author sunbo
     * @date 2022/5/27 16:24
     */
    @GetMapping("/eduCenter/member/{id}")
    UCenterMember getMemberInfoById(@PathVariable("id") String id);
}

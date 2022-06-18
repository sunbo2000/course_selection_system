package org.snbo.dbservice.service;

import org.snbo.dbservice.bean.vo.OptionVo;

import java.util.Map;

/**
 * @author sunbo
 * @date 2022-06-17-23:01
 */
public interface OptionService {

    /**
     * description: 开启选课系统
     *
     * @param option 启动信息
     * @author sunbo
     * @date 2022/6/17 23:04
     */
    void startOption(OptionVo option);

    /**
     * description: 关闭某个系统
     *
     * @param id 任务id
     * @author sunbo
     * @date 2022/6/18 9:34
     */
    void endTask(String id);

    /**
     * description: 分页查询数据
     *
     * @param current 当前页
     * @param size    记录数
     * @return {@link Map< String, Object>}
     * @author sunbo
     * @date 2022/6/18 11:24
     */
    Map<String, Object> getPage(Long current, Long size);
}

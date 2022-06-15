package org.snbo.ucenterservice.mapper;

import org.apache.ibatis.annotations.Param;
import org.snbo.ucenterservice.bean.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author snbo
 * @since 2022-04-06
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    /**
     * description: 根据日期查询当天的注册人数
     *
     * @param day 日期(yyyy-MM-dd)
     * @return {@link Integer}
     * @author sunbo
     * @date 2022/5/28 17:56
     */
    Integer countRegister(@Param("day") String day);
}

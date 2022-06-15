package org.snbo.commonutils.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunbo
 * @create 2022-04-15-22:00
 */
@Data
public class UCenterMember {
    @ApiModelProperty(value = "会员id")
    private String id;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "手机号")
    private String mobile;
}

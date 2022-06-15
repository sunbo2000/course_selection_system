package org.snbo.ucenterservice.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunbo
 * @create 2022-05-10-0:48
 */
@Data
public class AccountInfo {

    @ApiModelProperty(value = "会员id")
    private String id;

    @ApiModelProperty(value = "微信openid")
    private String openid;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "是否有密码")
    private Boolean psd;
}

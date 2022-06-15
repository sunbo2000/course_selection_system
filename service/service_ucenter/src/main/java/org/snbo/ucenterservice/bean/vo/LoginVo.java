package org.snbo.ucenterservice.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunbo
 * @create 2022-04-06-17:20
 */
@Data
@ApiModel(value="登录对象", description="登录对象")
public class LoginVo {
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "密码")
    private String password;
}

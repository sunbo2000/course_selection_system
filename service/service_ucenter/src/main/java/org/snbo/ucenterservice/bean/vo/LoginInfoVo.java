package org.snbo.ucenterservice.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunbo
 * @create 2022-04-06-20:25
 */
@Data
public class LoginInfoVo {
    @ApiModelProperty(value = "会员id")
    private String id;

    @ApiModelProperty(value = "微信openid")
    private String openid;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别 1 女，2 男")
    private Integer sex;

    @ApiModelProperty(value = "出生日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private java.sql.Date birth;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户签名")
    private String sign;
}

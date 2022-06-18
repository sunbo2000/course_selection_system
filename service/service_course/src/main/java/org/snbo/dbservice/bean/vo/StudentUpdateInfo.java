package org.snbo.dbservice.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunbo
 * @date 2022-06-17-17:34
 */

@Data
public class StudentUpdateInfo {

    private String id;

    private String oldPassword;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像")
    private String avatar;
}

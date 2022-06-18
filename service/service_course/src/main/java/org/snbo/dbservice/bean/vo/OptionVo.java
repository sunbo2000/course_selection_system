package org.snbo.dbservice.bean.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author sunbo
 * @date 2022-06-17-22:41
 */

@Data
public class OptionVo {
    private Boolean grade19;
    private Boolean grade20;
    private Boolean grade21;
    private Boolean grade22;
    private Date start;
    private Date end;
}

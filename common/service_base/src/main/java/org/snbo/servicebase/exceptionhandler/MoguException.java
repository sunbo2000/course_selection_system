package org.snbo.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sunbo
 * @create 2022-03-18-14:56
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoguException extends RuntimeException {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private String msg;


    /**
     * 自行重写 toString 方法, lombok 重写的方法输出的是 meg ,而我们这里需要 message
     */
    @Override
    public String toString() {
        return "MoguException{" +
                "message=" + msg +
                ", code=" + code +
                '}';
    }
}

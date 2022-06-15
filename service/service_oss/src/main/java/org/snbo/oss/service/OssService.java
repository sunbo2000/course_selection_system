package org.snbo.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author sunbo
 * @create 2022-03-22-22:41
 */
public interface OssService {
    /**
     * description: 上传图片到阿里云 oss 服务里
     *
     * @param file 图片文件
     * @return {@link String}
     * @author sunbo
     * @date 2022/5/28 17:25
     */
    String uploadFileAvatar(MultipartFile file);
}

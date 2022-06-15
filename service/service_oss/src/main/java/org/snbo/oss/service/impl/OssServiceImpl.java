package org.snbo.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.snbo.oss.service.OssService;
import org.snbo.oss.utils.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author sunbo
 * @create 2022-03-22-22:42
 */

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        //创建Oss实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {

            //传入 桶名称, 上传到 oss 的文件路径(出去oss自己的)/文件名称(由于没有文件路径,直接文件名称了), 输入流
            String filename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String datePath = new DateTime().toString("yyyy/MM/dd");
            filename = datePath + "/" + uuid + filename;
            //获取文件输入流
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(bucketName, filename, inputStream);
            return "https://" + bucketName + "." + endpoint + "/" + filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}


package org.snbo.oss.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.snbo.commonutils.R;
import org.snbo.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author sunbo
 * @create 2022-03-22-22:41
 */

@Api("阿里 oss")
@RestController
@RequestMapping("/eduOss/fileOss")
public class OssController {

    private final OssService ossService;

    @Autowired
    public OssController(OssService ossService) {
        this.ossService = ossService;
    }

    /**
     * 上传头像
     */
    @ApiModelProperty("上传图像")
    @PostMapping
    public R uploadOssFile(MultipartFile file ){
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}

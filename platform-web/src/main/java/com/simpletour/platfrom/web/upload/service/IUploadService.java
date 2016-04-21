package com.simpletour.platfrom.web.upload.service;

import com.simpletour.platfrom.web.upload.domain.UpYun;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Mario on 2016/4/18.
 */
public interface IUploadService {

    /**
     * @param file
     * @return
     */
    String generateFileName(MultipartFile file) throws IOException;

    /**
     * 上传图片
     *
     * @param filePath 文件路径
     * @param file     文件
     * @param auto     是否自动创建父级目录(最多10级)
     * @return
     */
    UpYun upload(String filePath, MultipartFile file, boolean auto) throws IOException;
}

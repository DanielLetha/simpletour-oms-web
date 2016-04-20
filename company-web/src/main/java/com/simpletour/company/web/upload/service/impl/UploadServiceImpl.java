package com.simpletour.company.web.upload.service.impl;

import com.simpletour.commons.util.Hashs;
import com.simpletour.company.web.upload.config.UpYunConfig;
import com.simpletour.company.web.upload.domain.UpYun;
import com.simpletour.company.web.upload.service.IUploadService;
import com.simpletour.company.web.upload.util.UpYunUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Mario on 2016/4/18.
 */
@Service
public class UploadServiceImpl implements IUploadService {

    private static final Logger logger = Logger.getLogger(UploadServiceImpl.class);

    @Override
    public String generateFileName(MultipartFile file) throws IOException {
        if (file.getInputStream() == null)
            throw new IOException("文件不存在");
        return Hashs.MD5(file.getOriginalFilename() + System.currentTimeMillis()) + "." + file.getOriginalFilename().substring(file.getOriginalFilename()
                .lastIndexOf('.') + 1);
    }

    @Override
    public UpYun upload(String filePath, MultipartFile file, boolean auto) throws IOException {
        try {
            UpYun upYun = new UpYun(UpYunConfig.imagebucketName, UpYunConfig.imageUserName, UpYunUtil.md5(UpYunConfig.imagePassword), UpYunUtil.md5(file.getInputStream()));
            writeFile(upYun, filePath, file, auto);

            String uri = filePath;
            upYun.setSaveHost(UpYunConfig.imageSaveHost);
            upYun.setSaveName(filePath.substring(filePath.lastIndexOf(UpYunConfig.SEPARATOR) + 1));
            upYun.setSavePath(uri.substring(0, filePath.lastIndexOf(UpYunConfig.SEPARATOR)));
            return upYun;

        } catch (IOException e) {
            throw e;
        }
    }

    private boolean writeFile(UpYun upYun, String filePath, MultipartFile multipartFile, boolean auto) throws IOException {
        if (!(multipartFile.getInputStream() == null || upYun == null))
            return writeFile(upYun, filePath, multipartFile.getInputStream(), auto, null);
        throw new IOException();
    }

    private boolean writeFile(UpYun upYun, String filePath, InputStream inputStream, boolean auto, Map<String, String> params) throws IOException {
        filePath = formatPath(filePath, upYun.getBucketName());

        OutputStream os = null;
        HttpURLConnection conn = null;

        try {
            // 获取链接
            URL url = new URL("http://" + upYun.getApiDomain() + filePath);
            conn = (HttpURLConnection) url.openConnection();

            // 设置必要参数
            conn.setConnectTimeout(upYun.getTimeout());
            conn.setRequestMethod(UpYunConfig.METHOD_PUT);
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            // 设置时间
            conn.setRequestProperty(UpYunConfig.DATE, UpYunUtil.getGMTDate());
            // 设置签名
            conn.setRequestProperty(UpYunConfig.AUTHORIZATION,
                    UpYunUtil.sign(conn, filePath, inputStream.available(), upYun.getPassword(), upYun.getUserName()));

            // 设置文件的 MD5 参数
            if (!(upYun.getContentMD5() == null || upYun.getContentMD5().isEmpty())) {
                conn.setRequestProperty(UpYunConfig.CONTENT_MD5, upYun.getContentMD5());
                upYun.setContentMD5(null);
            }

            // 设置文件的访问密钥
            if (!(upYun.getFileSecret() == null || upYun.getFileSecret().isEmpty())) {
                conn.setRequestProperty(UpYunConfig.CONTENT_SECRET, upYun.getFileSecret());
                upYun.setFileSecret(null);
            }

            // 是否自动创建父级目录
            if (auto) {
                conn.setRequestProperty(UpYunConfig.MKDIR, "true");
            }

            // 设置额外的参数，如图片缩略图等
            if (params != null && !params.isEmpty()) {

                for (Map.Entry<String, String> param : params.entrySet()) {
                    conn.setRequestProperty(param.getKey(), param.getValue());
                }
            }

            // 创建链接
            conn.connect();

            os = conn.getOutputStream();
            byte[] data = new byte[4096];
            int temp = 0;

            // 上传文件内容
            while ((temp = inputStream.read(data)) != -1) {
                os.write(data, 0, temp);
            }

            // 获取返回的信息
            getText(upYun, conn, false);
            // 上传成功
            return true;

        } catch (IOException e) {
            if (upYun.isDebug())
                e.printStackTrace();

            // 上传失败
            return false;

        } finally {

            if (os != null) {
                os.close();
                os = null;
            }
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
    }


    /**
     * 格式化路径参数，去除前后的空格并确保以"/"开头，最后添加"/空间名"
     * <p>
     * 最终构成的格式："/空间名/文件路径"
     *
     * @param path       目录路径或文件路径
     * @param bucketName 空间名
     * @return 格式化后的路径
     */
    private String formatPath(String path, String bucketName) {
        if (!(path == null || path.isEmpty())) {
            // 去除前后的空格
            path = path.trim();

            // 确保路径以"/"开头
            if (!path.startsWith(UpYunConfig.SEPARATOR)) {
                return UpYunConfig.SEPARATOR + bucketName + UpYunConfig.SEPARATOR + path;
            }
        }
        return UpYunConfig.SEPARATOR + bucketName + path;
    }

    /**
     * 获得连接请求的返回数据
     *
     * @param upYun
     * @param conn
     * @return 字符串
     */
    private String getText(UpYun upYun, HttpURLConnection conn, boolean isHeadMethod)
            throws IOException {

        StringBuilder text = new StringBuilder();
        upYun.setFileType(null);

        InputStream is = null;
        InputStreamReader sr = null;
        BufferedReader br = null;

        int code = conn.getResponseCode();

        try {
            is = code >= 400 ? conn.getErrorStream() : conn.getInputStream();

            if (!isHeadMethod) {
                sr = new InputStreamReader(is);
                br = new BufferedReader(sr);

                char[] chars = new char[4096];
                int length = 0;

                while ((length = br.read(chars)) != -1) {
                    text.append(chars, 0, length);
                }
            }
            if (200 == code && conn.getHeaderField(UpYunConfig.X_UPYUN_WIDTH) != null) {
                upYun.setPicWidth(conn.getHeaderField(UpYunConfig.X_UPYUN_WIDTH));
                upYun.setPicHeight(conn.getHeaderField(UpYunConfig.X_UPYUN_HEIGHT));
                upYun.setPicFrames(conn.getHeaderField(UpYunConfig.X_UPYUN_FRAMES));
                upYun.setFileType(conn.getHeaderField(UpYunConfig.X_UPYUN_FILE_TYPE));
            } else {
                upYun.setPicWidth(conn.getHeaderField(null));
                upYun.setPicHeight(conn.getHeaderField(null));
                upYun.setPicFrames(conn.getHeaderField(null));
                upYun.setFileType(conn.getHeaderField(null));
            }

            if (200 == code && conn.getHeaderField(UpYunConfig.X_UPYUN_FILE_TYPE) != null) {
                upYun.setFileType(conn.getHeaderField(UpYunConfig.X_UPYUN_FILE_TYPE));
                upYun.setFileSize(conn.getHeaderField(UpYunConfig.X_UPYUN_FILE_SIZE));
                upYun.setFileDate(conn.getHeaderField(UpYunConfig.X_UPYUN_FILE_DATE));

            } else {
                upYun.setFileType(null);
                upYun.setFileSize(null);
                upYun.setFileDate(null);
            }
        } finally {
            if (br != null) {
                br.close();
                br = null;
            }
            if (sr != null) {
                sr.close();
                sr = null;
            }
            if (is != null) {
                is.close();
                is = null;
            }
        }

        if (isHeadMethod) {
            if (code >= 400)
                return null;
            return "";
        }

        if (code >= 400)
            throw new IOException(text.toString());

        return text.toString();
    }

}

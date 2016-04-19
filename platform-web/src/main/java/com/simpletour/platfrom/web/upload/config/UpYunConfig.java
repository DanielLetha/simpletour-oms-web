package com.simpletour.platfrom.web.upload.config;

/**
 * Created by Mario on 2016/4/18.
 */
public class UpYunConfig {

    public static final String imageSaveHost = "http://images.simpletour.com";

    public static final String imagebucketName = "simpletour-image";

    public static final String imageUserName = "admin";

    public static final String imagePassword = "T96ATRmirYd5";

    /**
     * 路径的分割符
     */
    public static final String SEPARATOR = "/";

    /**
     * 默认的编码格式
     */
    public static final String UTF8 = "UTF-8";

    /**
     * SKD版本号
     */
    public static final String VERSION = "2.0";

    public static final String AUTHORIZATION = "Authorization";
    public static final String DATE = "Date";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_MD5 = "Content-MD5";
    public static final String CONTENT_SECRET = "Content-Secret";
    public static final String MKDIR = "mkdir";

    public static final String X_UPYUN_WIDTH = "x-upyun-width";
    public static final String X_UPYUN_HEIGHT = "x-upyun-height";
    public static final String X_UPYUN_FRAMES = "x-upyun-frames";
    public static final String X_UPYUN_FILE_TYPE = "x-upyun-file-type";
    public static final String X_UPYUN_FILE_SIZE = "x-upyun-file-size";
    public static final String X_UPYUN_FILE_DATE = "x-upyun-file-date";

    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    /**
     * 根据网络条件自动选择接入点:v0.api.upyun.com
     */
    public static final String ED_AUTO = "v0.api.upyun.com";
    /**
     * 电信接入点:v1.api.upyun.com
     */
    public static final String ED_TELECOM = "v1.api.upyun.com";
    /**
     * 联通网通接入点:v2.api.upyun.com
     */
    public static final String ED_CNC = "v2.api.upyun.com";
    /**
     * 移动铁通接入点:v3.api.upyun.com
     */
    public static final String ED_CTT = "v3.api.upyun.com";
}

package com.simpletour.platfrom.web.upload.domain;

import com.simpletour.platfrom.web.upload.config.UpYunConfig;

/**
 * Created by Mario on 2016/4/18.
 */
public class UpYun {
    // 默认不开启debug模式
    private boolean debug = false;
    // 默认的超时时间：30秒
    private int timeout = 30 * 1000;
    // 默认为自动识别接入点
    private String apiDomain = UpYunConfig.ED_AUTO;
    // 待上传文件的 Content-MD5 值
    private String contentMD5 = null;
    // 待上传文件的"访问密钥"
    private String fileSecret = null;
    // 空间名
    private String bucketName = null;
    // 操作员名
    private String userName = null;
    // 操作员密码
    private String password = null;

    // 图片信息的参数
    private String picWidth = null;
    private String picHeight = null;
    private String picFrames = null;
    private String picType = null;

    // 文件信息的参数
    private String fileType = null;
    private String fileSize = null;
    private String fileDate = null;


    //文件存储的信息
    private String saveHost = null;
    private String savePath = null;
    private String saveName = null;

    public UpYun() {
    }

    /**
     * 初始化 UpYun 存储接口
     *
     * @param bucketName 空间名称
     * @param userName   操作员名称
     * @param password   密码
     */
    public UpYun(String bucketName, String userName, String password, String contentMD5) {
        this.bucketName = bucketName;
        this.userName = userName;
        this.password = password;
        this.contentMD5 = contentMD5;
    }

    /**
     * 查看当前是否是debug模式
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * 设置是否开启debug模式
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * 查看当前的超时时间
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * 设置连接超时时间，默认为30秒
     *
     * @param timeout 秒数，60即为一分钟超时
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout * 1000;
    }

    /**
     * 查看当前的域名接入点
     */
    public String getApiDomain() {
        return apiDomain;
    }

    /**
     * 切换 API 接口的域名接入点
     * <p>
     * 可选参数：<br>
     * 1) UpYun.ED_AUTO(v0.api.upyun.com)：默认，根据网络条件自动选择接入点 <br>
     * 2) UpYun.ED_TELECOM(v1.api.upyun.com)：电信接入点<br>
     * 3) UpYun.ED_CNC(v2.api.upyun.com)：联通网通接入点<br>
     * 4) UpYun.ED_CTT(v3.api.upyun.com)：移动铁通接入点
     *
     * @param apiDomain 域名接入点
     */
    public void setApiDomain(String apiDomain) {
        this.apiDomain = apiDomain;
    }

    public String getContentMD5() {
        return contentMD5;
    }

    /**
     * 设置待上传文件的 Content-MD5 值
     * <p>
     * 如果又拍云服务端收到的文件MD5值与用户设置的不一致，将回报 406 Not Acceptable 错误
     *
     * @param contentMD5 文件 MD5 校验后的内容
     */
    public void setContentMD5(String contentMD5) {
        this.contentMD5 = contentMD5;
    }

    public String getFileSecret() {
        return fileSecret;
    }

    /**
     * 设置待上传文件的"访问密钥"
     * <p>
     * 注意：<br>
     * 仅支持图片空！设置密钥后，无法根据原文件URL直接访问，需带 URL 后面加上 （缩略图间隔标志符+密钥） 进行访问
     * <p>
     * 举例:<br>
     * 如果缩略图间隔标志符为"!"，密钥为"bac"，上传文件路径为"/folder/test.jpg"，<br>
     * 那么该图片的对外访问地址为：http://空间域名 /folder/test.jpg!bac
     *
     * @param fileSecret 密钥字符串
     */
    public void setFileSecret(String fileSecret) {
        this.fileSecret = fileSecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(String picWidth) {
        this.picWidth = picWidth;
    }

    public String getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(String picHeight) {
        this.picHeight = picHeight;
    }

    public String getPicFrames() {
        return picFrames;
    }

    public void setPicFrames(String picFrames) {
        this.picFrames = picFrames;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileDate() {
        return fileDate;
    }

    public void setFileDate(String fileDate) {
        this.fileDate = fileDate;
    }

    public String getSaveHost() {
        return saveHost;
    }

    public void setSaveHost(String saveHost) {
        this.saveHost = saveHost;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }
}

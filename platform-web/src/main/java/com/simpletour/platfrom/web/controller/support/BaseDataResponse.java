package com.simpletour.platfrom.web.controller.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;

import java.io.Serializable;

/**
 * Created by zt on 15-11-19.
 */
public class BaseDataResponse implements Serializable, Cloneable {

    static {
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
    }
    private static final long serialVersionUID = 2820474567085491843L;

    @JSONField(ordinal = 1, name = "code")
    private final int code;
    @JSONField(ordinal = 5, name = "data")
    protected Object data;
    @JSONField(ordinal = 2, name = "msg")
    private String msg;
    @JSONField(ordinal = 3, name = "detail")
    private String detail;
    @JSONField(ordinal = 4, name = "jumpUrl")
    private String jumpUrl;
    @JSONField(ordinal = 7, name = "html")
    private String html;
    @JSONField(serialize = false)
    private MessageSource messageSource;

    private BaseDataResponse(int code) {
        this.code = code;
    }

    /**
     * 成功
     *
     * @return
     */
    public static final BaseDataResponse ok() {
        return new BaseDataResponse(0);
    }

    @Deprecated
    public static final BaseDataResponse ok(MessageSource source) {
        BaseDataResponse response = new BaseDataResponse(0);
        response.setMessageSource(source);
        return response;
    }

    /**
     * 失败
     *
     * @return
     */
    public static final BaseDataResponse fail() {
        return new BaseDataResponse(1);
    }

    @Deprecated
    public static final BaseDataResponse fail(MessageSource source) {
        BaseDataResponse response = new BaseDataResponse(1);
        response.setMessageSource(source);
        return response;
    }

    public static final BaseDataResponse fail(int code) {
        return new BaseDataResponse(code);
    }

    /**
     * 无数据
     *
     * @return
     */
    public static final BaseDataResponse noData() {
        return new BaseDataResponse(2);
    }

    @Deprecated
    public static final BaseDataResponse noData(MessageSource source) {
        BaseDataResponse response = new BaseDataResponse(2);
        response.setMessageSource(source);
        return response;
    }

    /**
     * 　表单验证失败
     *
     * @return
     */
    public static final BaseDataResponse validationFail() {
        return new BaseDataResponse(100);
    }

    @Deprecated
    public static final BaseDataResponse validationFail(MessageSource source) {
        BaseDataResponse response = new BaseDataResponse(100);
        response.setMessageSource(source);
        return response;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public String getDetail() {
        return detail;
    }

    public String getHtml() {
        return html;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * response message
     *
     * @param msg
     * @return
     */
    public BaseDataResponse msg(String msg) {
        if (this.getMessageSource() != null && msg.contains("."))
            this.msg = messageSource.getMessage(msg, null, null);
        else
            this.msg = msg;
        return this;
    }

    public BaseDataResponse detail(String detail) {
        if (this.getMessageSource() != null && detail.contains("."))
            this.detail = messageSource.getMessage(detail, null, null);
        else
            this.detail = detail;
        return this;
    }

    public BaseDataResponse detail(Exception e) {
        if (this.getMessageSource() != null && e.getMessage().contains("."))
            this.detail = messageSource.getMessage(e.getMessage(), null, null);
        else
            this.detail = e.getMessage();
        return this;
    }

    public BaseDataResponse jumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
        return this;
    }

    public BaseDataResponse html(String html) {
        this.html = html;
        return this;
    }

    /**
     * response data
     *
     * @param data
     * @return
     */
//    public BaseDataResponse data(Object data) {
//        throw new UnsupportedOperationException("unsupported operation");
//    }

    public BaseDataResponse data(Object data) {
        this.data=data;
        return this;
    }

    /**
     * response data
     *
     * @param bindingResult
     * @return
     */
    public BaseDataResponse tips(BindingResult bindingResult) {
        throw new UnsupportedOperationException("unsupported operation");
    }

    public BaseDataResponse action(BaseAction action, Boolean jump) {
        if (action.getTitle() != null && !action.getTitle().isEmpty())
            this.msg(action.getTitle());
        if (action.getMsg() != null && !action.getMsg().isEmpty())
            this.detail(action.getMsg());
        if (jump && action.getJumpUrl() != null)
            this.jumpUrl(action.getJumpUrl());
        return this;
    }

    public BaseDataResponse customAction(BaseAction action){
        this.msg(action.getTitle());
        this.detail(action.getMsg());
        if(action.getJumpUrl()!=null){
            this.jumpUrl(action.getJumpUrl());
        }
        return this;
    }

}
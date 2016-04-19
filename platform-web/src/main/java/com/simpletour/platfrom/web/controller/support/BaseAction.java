package com.simpletour.platfrom.web.controller.support;

/**
 * Created by zt on 2015-11-19.
 */
public class BaseAction {

    //标题
    private String title;
    //消息
    private String msg;
    //跳转地址
    private String jumpUrl;

    public BaseAction(String title, String msg, String jumpUrl) {
        this.title = title;
        this.msg = msg;
        this.jumpUrl = jumpUrl;
    }

    //统一
    public static BaseAction OBJECT_NOTFOUND() {
        return new BaseAction("资源不存在", "您请求的资源不存在，请核对后再次尝试", null);
    }

    public static BaseAction OBJECT_DELETED() {
        return new BaseAction("资源已被删除", "您请求的资源已删除，请核对后再次尝试", null);
    }

    public static BaseAction ADD_SUCCESS(String domain, String name) {
        return ADD_SUCCESS(domain, name, null);
    }

    public static BaseAction ADD_SUCCESS(String domain, String name, String url) {
        String msg = "添加成功";
        if (name != null && !name.isEmpty()) {
            msg = "添加'" + name + "'成功";
        }
        return new BaseAction(domain + "添加成功", msg, url);
    }

    public static BaseAction ADD_FAIL(String domain) {
        return ADD_FAIL(domain, null, null);
    }

    public static BaseAction ADD_FAIL(String domain, String name) {
        return ADD_FAIL(domain, name, null);
    }

    public static BaseAction ADD_FAIL(String domain, String name, String url) {
        String msg = "添加失败";
        if (name != null && !name.isEmpty()) {
            msg = "添加'" + name + "'失败";
        }
        return new BaseAction(domain + "添加失败", msg, url);
    }

    public static BaseAction EDIT_SUCCESS(String domain, String name) {
        return EDIT_SUCCESS(domain, name, null);
    }

    public static BaseAction EDIT_SUCCESS(String domain, String name, String url) {
        String msg = "编辑成功";
        if (name != null && !name.isEmpty()) {
            msg = "编辑'" + name + "'成功";
        }
        return new BaseAction(domain + "编辑成功", msg, url);
    }

    public static BaseAction EDIT_FAIL(String domain) {
        return EDIT_FAIL(domain, null, null);
    }

    public static BaseAction EDIT_FAIL(String domain, String name) {
        return EDIT_FAIL(domain, name, null);
    }

    public static BaseAction EDIT_FAIL(String domain, String name, String url) {
        String msg = "编辑失败";
        if (name != null && !name.isEmpty()) {
            msg = "编辑'" + name + "'失败";
        }
        return new BaseAction(domain + "编辑失败", msg, url);
    }

    public static BaseAction DEL_SUCCESS(String domain, String name) {
        return DEL_SUCCESS(domain, name, null);
    }

    public static BaseAction DEL_SUCCESS(String domain, String name, String url) {
        String msg = "删除成功";
        if (name != null && !name.isEmpty()) {
            msg = "删除'" + name + "'成功";
        }
        return new BaseAction(domain + "删除成功", msg, url);
    }

    public static BaseAction DEL_FAIL(String domain, String name) {
        return DEL_FAIL(domain, name, null);
    }

    public static BaseAction DEL_FAIL(String domain) {
        return DEL_FAIL(domain, null, null);
    }

    public static BaseAction DEL_FAIL(String domain, String name, String url) {
        String msg = "删除失败";
        if (name != null && !name.isEmpty()) {
            msg = "删除'" + name + "'失败";
        }
        return new BaseAction(domain + "删除失败", msg, url);
    }

    public static BaseAction LIST_FAIL(String domain) {
        return new BaseAction(domain + "查询失败", "请检查查询条件", null);
    }


    public static BaseAction SEAT_CHOOSE_FAIL() {
        return new BaseAction("选座失败", "选座失败", null);
    }

    public static BaseAction SEAT_CHOOSE_SUCCESS() {
        return new BaseAction("选座成功", "选座成功", null);
    }

    public static BaseAction ROLE_PRIVILEGE_SUCCCESS(String name) {
        return new BaseAction("角色授权成功", "您已经成功为角色'" + name + "'授权", "/system/role");
    }

    public static BaseAction ROLE_PRIVILEGE_FAIL() {
        return new BaseAction("角色授权失败", "角色授权失败", null);
    }

    public static BaseAction PERMISSION_PRIVILEGE_SUCCCESS(String name) {
        return new BaseAction("权限关联角色成功", "您已经成功为权限'" + name + "'关联角色", "/system/permission");
    }

    public static BaseAction PERMISSION_PRIVILEGE_FAIL() {
        return new BaseAction("权限关联角色失败", "权限关联角色失败", null);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }
}


package com.simpletour.company.web.form.upload;


import com.simpletour.company.web.upload.domain.UpYun;

/**
 * Created by Mario on 2016/4/18.
 */
public class UploadForm {
    private String saveHost;
    private String savePath;
    private String saveName;

    public UploadForm() {
    }

    public UploadForm(UpYun yun) {
        this.saveHost = yun.getSaveHost();
        this.savePath = yun.getSavePath();
        this.saveName = yun.getSaveName();
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

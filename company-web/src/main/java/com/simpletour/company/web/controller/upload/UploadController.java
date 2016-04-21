package com.simpletour.company.web.controller.upload;


import com.simpletour.company.web.controller.support.BaseDataResponse;
import com.simpletour.company.web.form.upload.UploadForm;
import com.simpletour.company.web.upload.domain.UpYun;
import com.simpletour.company.web.upload.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Mario on 2016/4/18.
 */
@Controller
public class UploadController {
    @Autowired
    IUploadService uploadService;

    @ResponseBody
    @RequestMapping(value = "/simpletour/images/{table:(?:company_employee)}", method = RequestMethod.POST)
    public BaseDataResponse uploadImage(@RequestParam(value = "file", required = false) MultipartFile file, @PathVariable String table) {
        UpYun yun = null;
        try {
            yun = uploadService.upload("/simpletour/images/" + table + "/" + uploadService.generateFileName(file), file, true);
        } catch (IOException e) {
            return BaseDataResponse.fail().msg(e.getMessage());
        }
        return BaseDataResponse.ok().data(new UploadForm(yun));
    }
}

package com.simpletour.company.web.controller.system;

import com.simpletour.company.web.controller.support.BaseController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Mario on 2016/4/11.
 */
@Controller
@RequestMapping("/system/employee/")
public class EmployeeController extends BaseController {

    private static final Logger logger = Logger.getLogger(EmployeeController.class);

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        this.setPageTitle(model, "添加人员信息");
        this.enableGoBack(model);
//        Empl
        return null;
    }


}

package com.simpletour.company.web.controller.sale;

import com.simpletour.company.web.controller.support.BaseDataResponse;
import com.simpletour.company.web.form.sale.AgreementProductPriceBatchForm;
import com.simpletour.company.web.form.sale.AgreementProductPriceForm;
import org.apache.poi.ss.formula.functions.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @Brief :  ${用途}
 * @Author: liangfei/liangfei@simpletour.com
 * @Date :  2016/4/22 10:50
 * @Since ： ${VERSION}
 * @Remark: ${Remark}
 */
@RequestMapping("/sale/productPrice")
@Controller
public class AgreementProductPriceController {


    @RequestMapping(value = {"", "list"})
    public String list(Model model) {
        //TODO 编写查询相关代码
        return "/sale/productPrice/list";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public BaseDataResponse add(@Valid AgreementProductPriceForm form, BindingResult result, Model model) {
        //// TODO: 2016/4/22编写添加价格相关代码 
        return null;
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public BaseDataResponse edit(@Valid AgreementProductPriceForm form, BindingResult result, Model model) {
        // TODO: 2016/4/22 编写编辑相关代码 
        return null;
    }

    @RequestMapping(value = "batchEdit", method = RequestMethod.POST)
    public BaseDataResponse batchEdit(@Valid AgreementProductPriceBatchForm form, BindingResult result, Model model) {
        // TODO: 2016/4/22 编写批量编辑相关代码 
        return null;
    }


}

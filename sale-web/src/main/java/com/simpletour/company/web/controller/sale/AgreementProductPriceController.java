package com.simpletour.company.web.controller.sale;

import com.simpletour.biz.sale.bo.AgreementPriceBo;
import com.simpletour.company.web.controller.support.BaseDataResponse;
import com.simpletour.company.web.enums.Option;
import com.simpletour.company.web.form.sale.AgreementProductPriceBatchForm;
import com.simpletour.company.web.form.sale.AgreementProductPriceForm;
import com.simpletour.company.web.query.sale.AgreementProductPriceQuery;
import com.simpletour.domain.sale.AgreementProduct;
import com.simpletour.domain.sale.AgreementProductPrice;
import com.simpletour.service.sale.IAgreementProductPriceService;
import com.simpletour.service.sale.IAgreementProductService;
import org.apache.poi.ss.formula.functions.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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


    @Autowired
    private IAgreementProductPriceService priceService;
    @Autowired
    private IAgreementProductService productService;


    @RequestMapping(value = {"", "list"}, method = RequestMethod.POST)
    public BaseDataResponse list(@Valid AgreementProductPriceQuery query, Model model) {
        List<AgreementPriceBo> agreementProductPriceList = priceService.getAgreementProductPriceList(query.asQuery());
        List<AgreementProductPriceForm> agreementProductPriceForms = agreementProductPriceList.stream().map(p -> new AgreementProductPriceForm(p)).collect(Collectors.toList());

        if (agreementProductPriceList.isEmpty() || agreementProductPriceList.size() == 0) {
            return BaseDataResponse.noData();
        }
        return BaseDataResponse.ok().data(agreementProductPriceForms);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public BaseDataResponse add(@Valid AgreementProductPriceForm form, BindingResult result, Model model) {

        AgreementPriceBo agreementProductPrice = form.as();
        Optional<AgreementProduct> optional = productService.getAgreementProductById(form.getAgreementProductId());
        if (optional.isPresent()) {
            agreementProductPrice.setAgreementProduct(optional.get());
        }
        Optional<AgreementPriceBo> priceOptional = priceService.addAgreementProductPrice(agreementProductPrice);
        if (!priceOptional.isPresent()) {
            return BaseDataResponse.fail();
        }
        return BaseDataResponse.ok();
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public BaseDataResponse edit(@Valid AgreementProductPriceForm form, BindingResult result, Model model) {
        AgreementPriceBo agreementProductPrice = form.as();
        Optional<AgreementProduct> optional = productService.getAgreementProductById(form.getAgreementProductId());
        if (optional.isPresent()) {
            agreementProductPrice.setAgreementProduct(optional.get());
        }
        Optional<AgreementPriceBo> priceOptional = priceService.updateAgreementProductPrice(agreementProductPrice);
        if (!priceOptional.isPresent()) {
            return BaseDataResponse.fail();
        }
        return BaseDataResponse.ok();
    }

    @RequestMapping(value = "batchEdit", method = RequestMethod.POST)
    public BaseDataResponse batchEdit(@Valid AgreementProductPriceBatchForm form, BindingResult result, Model model) {
        //获取选择的天数
        if (!form.getEndDate().after(form.getStartDate())) {
            return BaseDataResponse.fail().msg("日期不对");
        }
        int[] days = new int[7];
        if (form.isSunday()) {
            days[0] = 1;
        }
        if (form.isMonday()) {
            days[1] = 2;
        }
        if (form.isTuesday()) {
            days[2] = 3;
        }
        if (form.isWednesday()) {
            days[3] = 4;
        }
        if (form.isThursday()) {
            days[4] = 5;
        }
        if (form.isFriday()) {
            days[5] = 6;
        }
        if (form.isSaturday()) {
            days[6] = 7;
        }
        List<Date> dates = getSelectedDate(form.getStartDate(),form.getEndDate(),days);
        Optional<AgreementProduct> productOp =  productService.getAgreementProductById(form.getAgreementProductId());
        if(!productOp.isPresent()){
            return BaseDataResponse.fail();
        }
        List<AgreementPriceBo> agreementPriceBoList =new ArrayList<>();
        for(Date date:dates){
            AgreementPriceBo agreementPriceBo= form.as();
            agreementPriceBo.setAgreementProduct(productOp.get());
            agreementPriceBo.setDate(date);
            agreementPriceBoList.add(agreementPriceBo);
        }
        priceService.batchInsert(agreementPriceBoList);
        return BaseDataResponse.ok();
    }

    private  List<Date> getSelectedDate(Date startDate, Date endDate, int[] days) {
        List<Date> dateList = new ArrayList<>();
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(startDate);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(endDate);
        while (calStart.before(calEnd)) {
            for (int i = 0; i < days.length; i++) {
                if (calStart.get(Calendar.DAY_OF_WEEK) == days[i]) {
                    dateList.add(calStart.getTime());
                }
            }

            calStart.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dateList;
    }



}

package com.simpletour.company.web.controller.sale;

import com.simpletour.biz.sale.bo.AgreementPriceBo;
import com.simpletour.commons.data.dao.query.ConditionOrderByQuery;
import com.simpletour.commons.data.dao.query.condition.AndConditionSet;
import com.simpletour.commons.data.dao.query.condition.Condition;
import com.simpletour.company.web.controller.support.BaseController;
import com.simpletour.company.web.controller.support.BaseDataResponse;
import com.simpletour.company.web.enums.FormModeType;
import com.simpletour.company.web.form.sale.AgreementProductPriceBatchForm;
import com.simpletour.company.web.form.sale.AgreementProductPriceForm;
import com.simpletour.company.web.query.sale.AgreementProductPriceQuery;
import com.simpletour.domain.sale.AgreementProduct;
import com.simpletour.service.sale.IAgreementProductPriceService;
import com.simpletour.service.sale.IAgreementProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
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
public class AgreementProductPriceController extends BaseController {

    @Autowired
    private IAgreementProductPriceService priceService;

    @Autowired
    private IAgreementProductService productService;


    @RequestMapping(value = "list/{agreementProductId}", method = RequestMethod.GET)
    public String list(@PathVariable Long agreementProductId, Model model) {
        Optional<AgreementProduct> agreementProductOptional = productService.getAgreementProductById(agreementProductId);
        this.setPageTitle(model, agreementProductOptional.get().getProduct().getName()+"产品价格日历");
        this.enableGoBack(model);
        ConditionOrderByQuery orderByQuery = new ConditionOrderByQuery();
        AndConditionSet conditionSet = new AndConditionSet();
        conditionSet.addCondition("agreementProduct",agreementProductOptional.get());
        conditionSet.addCondition("date", getFistDateOfMonth(), Condition.MatchType.greaterOrEqual);
        conditionSet.addCondition("date", getLastDateOfMonth(), Condition.MatchType.lessOrEqual);
        orderByQuery.setCondition(conditionSet);

        List<AgreementPriceBo> agreementProductPriceList = priceService.getAgreementProductPriceListByQuery(orderByQuery);
        List<AgreementProductPriceForm> agreementProductPriceForms = agreementProductPriceList.stream().map(p -> new AgreementProductPriceForm(p)).collect(Collectors.toList());

        model.addAttribute("productPriceList",agreementProductPriceForms);
        return "/sale/productPrice/list";
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public BaseDataResponse list(@Valid AgreementProductPriceQuery query, Model model) {
        Optional<AgreementProduct> agreementProductOptional = productService.getAgreementProductById(query.getAgreementProductId());
        ConditionOrderByQuery orderByQuery = new ConditionOrderByQuery();
        AndConditionSet conditionSet = new AndConditionSet();
        conditionSet.addCondition("agreementProduct",agreementProductOptional.get());
        conditionSet.addCondition("date", query.getStartDate(), Condition.MatchType.greaterOrEqual);
        conditionSet.addCondition("date", query.getEndDate(), Condition.MatchType.lessOrEqual);
        orderByQuery.setCondition(conditionSet);
        List<AgreementPriceBo> agreementProductPriceList = priceService.getAgreementProductPriceListByQuery(orderByQuery);
        List<AgreementProductPriceForm> agreementProductPriceForms = agreementProductPriceList.stream().map(p -> new AgreementProductPriceForm(p)).collect(Collectors.toList());

        if (agreementProductPriceList.isEmpty() || agreementProductPriceList.size() == 0) {
            return BaseDataResponse.noData();
        }
        return BaseDataResponse.ok().data(agreementProductPriceForms);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public BaseDataResponse add(@Valid AgreementProductPriceForm form, BindingResult result, Model model) {
        form.setMode(FormModeType.ADD.getValue());
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
        form.setMode(FormModeType.ADD.getValue());
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
        List<Date> dates = getSelectedDate(form.getStartDate(), form.getEndDate(), days);
        Optional<AgreementProduct> productOp = productService.getAgreementProductById(form.getAgreementProductId());
        if (!productOp.isPresent()) {
            return BaseDataResponse.fail();
        }
        List<AgreementPriceBo> agreementPriceBoList = new ArrayList<>();
        for (Date date : dates) {
            AgreementPriceBo agreementPriceBo = form.as();
            agreementPriceBo.setAgreementProduct(productOp.get());
            agreementPriceBo.setDate(date);
            agreementPriceBoList.add(agreementPriceBo);
        }
        priceService.batchInsert(agreementPriceBoList);
        return BaseDataResponse.ok();
    }

    private List<Date> getSelectedDate(Date startDate, Date endDate, int[] days) {
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

    private Date getFistDateOfMonth(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,0);
        c.set(Calendar.DAY_OF_MONTH,1);
        return c.getTime();
    }

    private Date getLastDateOfMonth(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,0);
        c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }


}

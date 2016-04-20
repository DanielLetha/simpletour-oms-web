package com.simpletour.company.web.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sczly on 2015/6/3.
 */
public enum FormModeType implements Options {
    ADD("添加","add"),UPDATE("更新","update"),DEL("删除","del"), ;

    private String name, value;

    private FormModeType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }


    @Override
    public List<Option> as() {
        return FormModeType.as(this,FormModeType.values());
    }

    /**
     *
     * @param selected
     * @return
     */
    public static List<Option> as(FormModeType selected, FormModeType... types){
        List<Option> options = new ArrayList<>();
        for(FormModeType type : types){
            if(type!= selected){
                options.add(new Option(type.getName(),type.getValue()));
            }else {
                options.add(new Option(type.getName(),type.getValue(),true));
            }
        }
        return options;
    }

    /**
     *
     * @param value
     * @return
     */
    public static FormModeType of(String value){
        for(FormModeType type : FormModeType.values()){
            if(type.getValue().equals(value))return type;
        }
        return null;
    }
}

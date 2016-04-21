package com.simpletour.company.web.util;

import com.simpletour.company.web.enums.Option;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Brief : 针对枚举类型的下拉列表的工具类
 * Author: Hawk
 * Date  : 2015/11/26
 */
public class OptionsUtil {

    /**
     * 获取原始的枚举类型列表
     * @param selected 选中的类型
     * @param types    枚举类
     * @return
     */
    public static List<Option> defaultEnumTypes(String selected, Class types) {
        List<Option> options = new ArrayList<>();
        if (isEnumSatisfy(types)) {
            for (Object o : types.getEnumConstants()) {
                        Enum type = (Enum)o;
                        String value = type.name();
                        Method method = getRemarkMethod(o);
                        if (method != null) {
                            String name = getRemarkValue(method, o);
                    if (value.equals(selected)) {
                        options.add(new Option(name, value, true));
                    } else {
                        options.add(new Option(name, value));
                    }
                }
            }
            return options;
        }
        return options;
    }

    /**
     * 获取原始的枚举类型列表
     * @param selected 选中的类型
     * @param types    枚举类
     * @return
     */
    public static List<Option> defaultEnumTypes(Enum selected, Class types) {
        return defaultEnumTypes(selected.name(), types);
    }

    /**
     * 后台的List页面时，经常会有一个全部的选项，此方法为了避免重新生成一个新的枚举类
     * @param allName  全部选项的名字
     * @param allValue 全部选项的Value
     * @param selected 选中那一项
     * @param types
     * @return
     */
    public static List<Option> addAllToEnumTypes(String allName, String allValue, String selected, Class types) {
        List<Option> options = defaultEnumTypes(selected, types);
        List<Option> newOptions = new ArrayList<>();
        Option allOption = new Option(allName, allValue);
        newOptions.add(allOption);
        newOptions.addAll(options);
        Optional<Option> option = newOptions.stream().filter(o -> o.isSelected()).findFirst();
        if (!option.isPresent()) {
            allOption.setSelected(true);
        }
        return newOptions;
    }

    /**
     * 当后台的List页面传过来Type参数时，判断是否为全部选项
     * @param value 页面传过来的数据
     * @param types 枚举类型
     * @return
     */
    public static boolean isAllOption(String value, Class types) {
        if (isEnumSatisfy(types)) {
            Object[] objects = types.getEnumConstants();
            for (Object o : objects) {
                Enum type = (Enum)o;
                if (type.name().equals(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断是否为枚举类型,并且是否拥有getRemark方法
     * @param types
     * @return
     */
    private static boolean isEnumSatisfy(Class types) {
        if (!types.isEnum()) return false;
        Object[] objects = types.getEnumConstants();
        if (objects.length > 0) {
            Object o = objects[0];
            Method[] methods = o.getClass().getDeclaredMethods();
            for (Method m : methods) {
                if ("getRemark".equals(m.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 调用getRemark方法，获取Value
     * @param method getRemark的Method
     * @param o      枚举类
     * @return
     */
    private static String getRemarkValue(Method method, Object o) {
        try {
            return (String)method.invoke(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取getRemark的method
     * @param o 枚举对象
     * @return
     */
    private static Method getRemarkMethod(Object o) {
        Enum type = (Enum)o;
        Optional<Method> method = Arrays.asList(type.getClass().getDeclaredMethods())
                .stream()
                .filter(c -> "getRemark".equals(c.getName()))
                .findFirst();
        if (method.isPresent()) {
            return method.get();
        }
        return null;
    }

    /**
     * 获取Boolean类型的筛选框，
     * @param trueValue 当为true时显示的值。
     * @param falseValue 当为false时，筛选框中显示的值
     * @param choice 当前选择的值
     * @return
     */
    public static List<Option> getBooleanOption(String trueValue,String falseValue,Boolean choice){
        List<Option> options = new ArrayList<>();
        options.add(new Option(trueValue,"true"));
        options.add(new Option(falseValue,"false"));
        if(choice!=null&&choice.equals(Boolean.FALSE)){
            options.get(1).setSelected(true);
        }else{
            options.get(0).setSelected(true);
        }
        return options;
    }

    /**
     * 获取Boolean类型筛选框，当为空时筛选所有情况。
     * @param nullValue 当值为空时显示的值
     * @param trueValue 当值为true时，显示的值
     * @param falseValue 当值为false时，显示的值
     * @param choice 当前选择的值
     * @return
     */
    public static List<Option> getBooleanOption(String nullValue,String trueValue,String falseValue,Boolean choice) {
        List<Option> options=new ArrayList<>();
        options.add(new Option(nullValue,""));
        options.add(new Option(trueValue,"true"));
        options.add(new Option(falseValue,"false"));
        if(choice==null){
            options.get(0).setSelected(true);
        }else if (choice.equals(Boolean.TRUE)) {
            options.get(1).setSelected(true);
        } else {
            options.get(2).setSelected(true);
        }
        return options;
    }

//    public static JSONArray getNodeTypes(List<NodeType> nodeTypes){
//        List<SimpleNodeType> types = new ArrayList<>();
//        nodeTypes.stream().forEach(t ->
//                types.add(new SimpleNodeType(t)));
//        return JSON.parseArray(JSON.toJSONString(types));
//    }
//
//
//    public static void main(String[] args) {
////        System.out.println(addAllToEnumTypes("全部", "all", Catering.Type.other.name(), Catering.Type.class));
////        System.out.print(isAllOption("all", Catering.Type.class));
//
//        System.out.println(defaultEnumTypes(Entertainment.Type.activity, Entertainment.Type.class));
//    }
}

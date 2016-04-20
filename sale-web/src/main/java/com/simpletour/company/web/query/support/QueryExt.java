package com.simpletour.company.web.query.support;

import com.alibaba.fastjson.annotation.JSONField;
import com.simpletour.commons.data.dao.IBaseDao;
import com.simpletour.commons.data.dao.query.ConditionOrderByQuery;
import com.simpletour.commons.data.dao.query.condition.AndConditionSet;
import com.simpletour.commons.data.dao.query.condition.Condition;
import com.simpletour.commons.data.dao.query.condition.ConditionSet;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 * User: XuHui
 * Date: 2016/2/23
 * Time: 11:52
 */
public class QueryExt<T extends Object> implements Serializable {
    @JSONField(ordinal = Integer.MAX_VALUE - 1)
    private int index;

    @JSONField(ordinal = Integer.MAX_VALUE)
    private int size;

    @JSONField(ordinal = Integer.MAX_VALUE)
    private int totalCount;

    public QueryExt() {
        this(1, 10);
    }

    public QueryExt(int index, int size) {
        if (index < 1 || size < 1) throw new IllegalArgumentException("index or size must be natural number");
        this.index = index;
        this.size = size;
    }

    public QueryExt(int index, int size, int totalCount) {
        this(index, size);
        setTotalCount(totalCount);
    }

    /**
     * @return
     */
    public int offset() {
        return (index - 1) * size;
    }

    /**
     * @return
     */
    public int limit() {
        return size;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public final static String getSearchStr(String str) {
        if (str == null || str.equals(""))
            return null;
        StringBuffer sb = new StringBuffer();
        String[] strings = str.split(" ");
        if (strings.length <= 0)
            return "";
        for (int i = 0; i < strings.length; i++) {
            if (!strings[i].equals("")) {
                sb.append(strings[i]);
                sb.append("%");
            }
        }
        if (sb.lastIndexOf("%") == sb.length() - 1)
            sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    public Map<String, Object> asMap(boolean canLogic) {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                QueryWord queryWord = field.getAnnotation(QueryWord.class);
                try {
                    setKeyValue(map, queryWord, field);
                } catch (IllegalAccessException e) {
                }
            }
        }
        if (canLogic)
            map.put("del", false);
        return map;
    }

    public ConditionSet asConditison() {
        AndConditionSet conditionSet = new AndConditionSet();
        Field[] fields = this.getClass().getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                QueryWord queryWord = field.getAnnotation(QueryWord.class);
                try {
                    setKeyValue(conditionSet, queryWord, field);
                } catch (IllegalAccessException | InstantiationException ignored) {
                }
            }
        }
        return conditionSet;
    }


    public <T extends ConditionOrderByQuery> T asQuery(Class<T> clazz) {
        T res;
        try {
            res = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        res.setCondition(asConditison());
        res.setPageSize(size);
        res.setPageIndex(index);
        setSortByField(res);
        return res;
    }

    public void setSortByField(ConditionOrderByQuery res) {
//        res.addSortByField("id", IBaseDao.SortBy.DESC);
    }

    public Map<String, Object> asMap() {
        return asMap(false);
    }

    private void setKeyValue(ConditionSet conditionSet, QueryWord key, Field value) throws IllegalAccessException, InstantiationException {
        if (conditionSet == null || key == null || value == null)
            return;
        value.setAccessible(true);
        if (!key.nullable() && (value.get(this) == null || "".equals(value.get(this)))) {
            return;
        }
        String keyStr;
        if (key.value() == null || key.value().isEmpty()) {
            keyStr = value.getName();
        } else {
            keyStr = key.value();
        }
        if (key.matchType().equals(Condition.MatchType.like)) {
            if (!("".equals(value.get(this)))) {
                conditionSet.addCondition(keyStr, getSearchStr((String) value.get(this)), key.matchType());
            }
        } else if (key.matchType().equals(Condition.MatchType.lessOrEqual) && value.getType().isAssignableFrom(Date.class)) {
            Date date = (Date) value.get(this);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);
            date = calendar.getTime();
            conditionSet.addCondition(keyStr, date, key.matchType());
        } else {
            if (key.enumType().equals(Enum.class)) {
                conditionSet.addCondition(keyStr, value.get(this), key.matchType());
            } else {
                if (!"".equals(value.get(this)))
                    conditionSet.addCondition(keyStr, Enum.valueOf(key.enumType(), (String) value.get(this)), key.matchType());
            }
        }
    }

    private void setKeyValue(Map<String, Object> map, QueryWord key, Field value) throws IllegalAccessException {
        if (map == null || key == null || value == null)
            return;
        value.setAccessible(true);
        String keyStr;
        if (key.value() == null || key.value().isEmpty()) {
            keyStr = value.getName();
        } else {
            keyStr = key.value();
        }
        if (value.getClass().isAssignableFrom(String.class)) {
            if (key.matchType().equals(Condition.MatchType.like)) {
                map.put(keyStr, getSearchStr((String) value.get(this)));
            } else {
                map.put(keyStr, (String) value.get(this));
            }
        } else {
            map.put(keyStr, value.get(this));
        }
    }
}

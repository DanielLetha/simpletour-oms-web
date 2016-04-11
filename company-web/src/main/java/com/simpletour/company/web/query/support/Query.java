package com.simpletour.company.web.query.support;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by igotti on 14-11-5.
 */
public class Query {

    @JSONField(ordinal = Integer.MAX_VALUE - 1)
    private int index;

    @JSONField(ordinal = Integer.MAX_VALUE)
    private int size;

    public Query() {
        this(1, 10);
    }

    public Query(int index, int size) {
        if (index < 1 || size < 1) throw new IllegalArgumentException("index or size must be natural number");
        this.index = index;
        this.size = size;
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

    public Map<String, Object> asMapWithDel() {
        Map<String, Object> map = new HashMap<>();
        map.put("del", false);
        return map;
    }
}

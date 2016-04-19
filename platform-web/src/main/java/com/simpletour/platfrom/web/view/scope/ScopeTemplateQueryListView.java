package com.simpletour.platfrom.web.view.scope;

import com.simpletour.domain.company.ScopeTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/11.
 * Remark:
 */
public class ScopeTemplateQueryListView {

    //权限范围的集合
    private List<ScopeTemplateElementQueryView> scopeQueryList = new ArrayList<>();

    public ScopeTemplateQueryListView(List<ScopeTemplate> scopeTemplates) {
        if (scopeTemplates != null && !scopeTemplates.isEmpty()){
            scopeTemplates.stream().forEach(scopeTemplate -> scopeQueryList.add(new ScopeTemplateElementQueryView(scopeTemplate)));
        }
    }

    public List<ScopeTemplateElementQueryView> getScopeQueryList() {
        return scopeQueryList;
    }

    public void setScopeQueryList(List<ScopeTemplateElementQueryView> scopeQueryList) {
        this.scopeQueryList = scopeQueryList;
    }
}

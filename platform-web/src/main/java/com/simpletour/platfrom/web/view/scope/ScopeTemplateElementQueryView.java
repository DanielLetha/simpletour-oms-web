package com.simpletour.platfrom.web.view.scope;

import com.simpletour.domain.company.Module;
import com.simpletour.domain.company.Permission;
import com.simpletour.domain.company.ScopeTemplate;
import com.simpletour.platfrom.web.view.module.ModuleQueryElementView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/11.
 * Remark:
 */
public class ScopeTemplateElementQueryView {

    //权限范围id
    private Long scopeId;

    //权限范围名称
    private String scopeName;

    //模块的集合
    private List<ModuleQueryElementView> moduleQueryList = new ArrayList<>();

    public ScopeTemplateElementQueryView(ScopeTemplate scopeTemplate) {
        if (scopeTemplate != null){
            this.scopeId = scopeTemplate.getId();
            this.scopeName = scopeTemplate.getName();
            //构造模块的集合
            if (scopeTemplate.getPermissions() != null && !scopeTemplate.getPermissions().isEmpty()){
                Map<Module, List<Permission>> moduleMap = scopeTemplate.getPermissions().stream().collect(Collectors.groupingBy(Permission::getModule));
                for(Map.Entry<Module,List<Permission>> entry : moduleMap.entrySet()){
                    moduleQueryList.add(new ModuleQueryElementView(entry.getKey()));
                }
            }
        }
    }

    public Long getScopeId() {
        return scopeId;
    }

    public void setScopeId(Long scopeId) {
        this.scopeId = scopeId;
    }

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    public List<ModuleQueryElementView> getModuleQueryList() {
        return moduleQueryList;
    }

    public void setModuleQueryList(List<ModuleQueryElementView> moduleQueryList) {
        this.moduleQueryList = moduleQueryList;
    }
}

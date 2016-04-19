package com.simpletour.platfrom.web.view.module;

import com.simpletour.domain.company.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/9.
 * Remark:
 */
public class ModuleQueryListView {

    private List<ModuleQueryElementView> list = new ArrayList<>();

    public ModuleQueryListView(List<Module> modules) {
        if (modules != null && !modules.isEmpty()){
           modules.stream().forEach(module -> list.add(new ModuleQueryElementView(module)));
        }
    }

    public List<ModuleQueryElementView> getList() {
        return list;
    }

    public void setList(List<ModuleQueryElementView> list) {
        this.list = list;
    }
}

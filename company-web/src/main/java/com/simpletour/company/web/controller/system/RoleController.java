package com.simpletour.company.web.controller.system;

import com.simpletour.company.web.controller.support.BaseController;
import com.simpletour.company.web.query.system.RoleQuery;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by XuHui on 2015/12/3.
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {
    private static final String DOMAIN = "角色";
    private static final String LIST_URL = "/system/role/list";

//    @Autowired
//    private IRoleService roleService;
//
//    @Autowired
//    private IPermissionService permissionService;

    @RequestMapping(value = {"", "list"})
    @RequiresPermissions(value = {"role_list"})
    public String list(RoleQuery query, Model model) {
        this.setPageTitle(model, "角色列表");
//        DomainPage<Role> page = roleService.queryRolesPages(Query.getSearchStr(query.getName()),
//                Query.getSearchStr(query.getCode()), query.getIndex(), query.getSize());
        model.addAttribute("query", query);
//        model.addAttribute("page", page);
//        model.addAttribute("pageHelper", new PageHelper(page));
        return "/system/role/list";
    }
}

package com.simpletour.platfrom.web.view.company;

import com.simpletour.domain.company.Company;
import com.simpletour.platfrom.web.query.company.CompanyQuery;
import com.simpletour.platfrom.web.view.BaseElementView;

/**
 * Author:  wangLin
 * Mail  :  wl@simpletour.com
 * Date  :  2016/4/5.
 * Remark:
 */
public class CompanyElementView extends BaseElementView<Company> {

    //公司编号
    private String no;

    //名称
    private String companyName;

    //地址
    private String address;

    /**
     * 供呢个名称
     *  初始化或未使用功能名称搜索时，列表字段展示“......”，当使用功能名称搜索时，展示搜索的功能名称；
     */
    private String permissionName;

    //对接人姓名
    private String buttManName;

    public CompanyElementView(Company company,CompanyQuery query) {
        if (company != null && query != null){
            this.no = String.valueOf(company.getId());
            this.companyName = company.getName();
            this.address = company.getAddress();
            this.buttManName = company.getContacts();
            if (query.getPermissionName() == null || "".equals(query.getPermissionName())){
                this.permissionName = "......";
            } else {
                //只有底层代码不出错,公司权限集合中肯定包含作为查询条件的功能名称
                this.permissionName = query.getPermissionName();
            }
        }
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getButtManName() {
        return buttManName;
    }

    public void setButtManName(String buttManName) {
        this.buttManName = buttManName;
    }
}

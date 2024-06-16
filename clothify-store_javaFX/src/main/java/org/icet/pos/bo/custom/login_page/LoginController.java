package org.icet.pos.bo.custom.login_page;

import org.icet.pos.bo.SuperBo;
import org.icet.pos.model.EmployeeModel;

import java.util.List;

public interface LoginController extends SuperBo {
    public List<EmployeeModel> getEmployeeDetail();
}

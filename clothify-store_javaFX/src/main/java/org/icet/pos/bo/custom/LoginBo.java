package org.icet.pos.bo.custom;

import org.icet.pos.bo.SuperBo;
import org.icet.pos.model.EmployeeModel;

import java.util.List;

public interface LoginBo extends SuperBo {
     List<EmployeeModel> getEmployeeDetail();
}

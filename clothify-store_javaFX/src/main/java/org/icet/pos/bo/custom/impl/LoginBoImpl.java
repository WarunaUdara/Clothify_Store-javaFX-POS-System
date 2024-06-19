package org.icet.pos.bo.custom.impl;


import org.icet.pos.bo.custom.LoginBo;
import org.icet.pos.dao.factory.DaoFactory;
import org.icet.pos.dao.custom.impl.EmployeeDaoImpl;
import org.icet.pos.entity.EmployeeEntity;
import org.icet.pos.model.EmployeeModel;
import org.icet.pos.dao.factory.DaoType;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class LoginBoImpl implements LoginBo {
    EmployeeDaoImpl dao= DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);

    @Override
    public List<EmployeeModel> getEmployeeDetail(){
        persist();
        List<EmployeeEntity> empList = dao.findAll();
        List<EmployeeModel> employeeModelList=new ArrayList<>();
        empList.forEach(employeeEntity -> {
            ModelMapper mapper=new ModelMapper();
            EmployeeModel employeeModel = mapper.map(employeeEntity, EmployeeModel.class);
            employeeModelList.add(employeeModel);
        });
        return employeeModelList;


    }
    public void persist(){

    }
}

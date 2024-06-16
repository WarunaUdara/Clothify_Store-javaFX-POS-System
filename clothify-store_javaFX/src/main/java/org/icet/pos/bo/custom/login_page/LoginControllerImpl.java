package org.icet.pos.bo.custom.login_page;


import org.icet.pos.dao.DaoFactory;
import org.icet.pos.dao.custom.login_page.LoginDaoImpl;
import org.icet.pos.entity.EmployeeEntity;
import org.icet.pos.model.EmployeeModel;
import org.icet.pos.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class LoginControllerImpl implements LoginController {
    LoginDaoImpl dao= DaoFactory.getInstance().getDao(DaoType.LOGIN);

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

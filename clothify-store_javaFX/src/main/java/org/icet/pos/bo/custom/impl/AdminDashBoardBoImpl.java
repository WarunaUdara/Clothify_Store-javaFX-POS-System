package org.icet.pos.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.icet.pos.bo.custom.AdminDashBoardBo;
import org.icet.pos.dao.custom.EmployeeDao;
import org.icet.pos.dao.factory.DaoFactory;
import org.icet.pos.entity.EmployeeEntity;
import org.icet.pos.dao.factory.DaoType;
import org.icet.pos.model.EmployeeModel;
import org.modelmapper.ModelMapper;


public class AdminDashBoardBoImpl implements AdminDashBoardBo {
    private EmployeeDao employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);
    private ModelMapper mapper=new ModelMapper();


    @Getter
    private  Integer totalEmployees=0;

    @Override
    public String getNewEmployeeId() {
        String id = "E0000";
        for (EmployeeEntity employee : employeeDao.findAll()) {
            id = String.valueOf(employee.getId());
            totalEmployees++;
        }

        return generateNewEmployeeId(id);
    }


    public String generateNewEmployeeId(String empId) {
        if (empId.equals("null")) {
            return "E0001";
        }
        int x = Integer.parseInt(empId.substring(1)) + 1; // Increment ID
        return String.format("E%04d", x); // Format with leading zeros
    }


    public void persist(EmployeeModel employee) {

        employeeDao.persist(mapper.map(employee, EmployeeEntity.class));
    }

    public ObservableList<EmployeeModel> getAllEmployees() {
        ObservableList<EmployeeModel> list= FXCollections.observableArrayList();
        for (EmployeeEntity employee : employeeDao.findAll()) {
            EmployeeModel employeeModel = mapper.map(employee, EmployeeModel.class);
            list.add(employeeModel);
        }
        return list;
    }
}

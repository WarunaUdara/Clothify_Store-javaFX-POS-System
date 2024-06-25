package org.icet.pos.bo.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.icet.pos.bo.custom.ManageCustomerBo;
import org.icet.pos.dao.custom.CustomerDao;
import org.icet.pos.dao.factory.DaoFactory;
import org.icet.pos.dao.factory.DaoType;
import org.icet.pos.entity.CustomerEntity;
import org.icet.pos.model.CustomerModel;
import org.modelmapper.ModelMapper;

public class ManageCustomerBoImpl implements ManageCustomerBo {


    private CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    private ModelMapper mapper = new ModelMapper();

    @Getter
    private Integer totalCustomers = 0;

    @Override
    public String getNewCustomerId() {
        String id = "E0000";
        for (CustomerEntity customer : customerDao.findAll()) {
            id = String.valueOf(customer.getId());
            totalCustomers++;
        }
        return generateNewCustomerId(id);
    }

    public String generateNewCustomerId(String empId) {
        if (empId.equals("null")) {
            return "C0001";
        }
        int x = Integer.parseInt(empId.substring(1)) + 1; // Increment ID
        return String.format("C%04d", x); // Format with leading zeros
    }

    public void persist(CustomerModel customerModel) {
        customerDao.persist(mapper.map(customerModel, CustomerEntity.class));
    }

    public ObservableList<CustomerModel> getAllCustomers() {
        ObservableList<CustomerModel> list = FXCollections.observableArrayList();
        for (CustomerEntity customer : customerDao.findAll()) {
            CustomerModel customerModel = mapper.map(customer, CustomerModel.class);
            list.add(customerModel);
        }
        return list;
    }

    public CustomerModel findCustomerById(String id) {
        CustomerEntity customerEntity = customerDao.findById(id);
        if (customerEntity != null) {
            return mapper.map(customerEntity, CustomerModel.class);
        }
        return null; // or throw an exception if the employee is not found
    }
    public void removeCustomerById(String id) {
        customerDao.deleteById(id);
    }
    public void updateCustomer(CustomerModel customer) {
        customerDao.update(mapper.map(customer, CustomerEntity.class));
    }
}



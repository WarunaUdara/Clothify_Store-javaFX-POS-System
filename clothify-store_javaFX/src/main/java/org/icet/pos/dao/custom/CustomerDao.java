package org.icet.pos.dao.custom;

import org.icet.pos.dao.CrudDao;
import org.icet.pos.entity.CustomerEntity;

import java.util.List;

public interface CustomerDao extends CrudDao<CustomerEntity, String> {

    void persist(CustomerEntity entity);

    CustomerEntity findById(String id);

    List<CustomerEntity> findAll();

    void update(CustomerEntity entity);

    void delete(CustomerEntity entity);
}

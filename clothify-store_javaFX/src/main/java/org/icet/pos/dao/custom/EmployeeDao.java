package org.icet.pos.dao.custom;

import org.icet.pos.dao.CrudDao;
import org.icet.pos.entity.EmployeeEntity;

import java.util.List;

public interface EmployeeDao extends CrudDao<EmployeeEntity, String> {
    void persist(EmployeeEntity entity);

    EmployeeEntity findById(String id);

    List<EmployeeEntity> findAll();

    void update(EmployeeEntity entity);

    void delete(EmployeeEntity entity);
}

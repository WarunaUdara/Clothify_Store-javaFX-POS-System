package org.icet.pos.dao;

import org.icet.pos.model.EmployeeModel;

import java.util.List;

public interface CrudDao<T, ID> extends SuperDao {

        void persist(T entity);
        T findById(ID id);
        List<T> findAll();
        void update(T entity);
        void delete(T entity);


}

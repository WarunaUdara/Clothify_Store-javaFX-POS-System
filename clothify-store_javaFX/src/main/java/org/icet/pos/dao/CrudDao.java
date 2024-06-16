package org.icet.pos.dao;

import org.icet.pos.model.EmployeeModel;

import java.util.List;

public interface CrudDao<T> extends SuperDao {
    void persist(T entity);
    T findById(Integer id);
    List<T> findAll();
    void update(T entity);
    void delete(T entity);

}

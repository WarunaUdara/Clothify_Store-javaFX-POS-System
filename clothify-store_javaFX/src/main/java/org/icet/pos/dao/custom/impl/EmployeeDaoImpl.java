package org.icet.pos.dao.custom.impl;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.icet.pos.dao.custom.EmployeeDao;
import org.icet.pos.entity.EmployeeEntity;
import org.icet.pos.util.HibernateUtil;

import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public void persist(EmployeeEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public EmployeeEntity findById(String id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        EmployeeEntity entity = session.get(EmployeeEntity.class, id);
        session.getTransaction().commit();
        session.close();
        return entity;
    }

    @Override
    public List<EmployeeEntity> findAll() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        String hql = "FROM employee"; // HQL to select all records from EmployeeEntity table
        Query query = session.createQuery(hql, EmployeeEntity.class);
        List<EmployeeEntity> employeeEntities = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return employeeEntities;
    }

    @Override
    public void update(EmployeeEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }


    @Override
    public void delete(EmployeeEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    public void updatePassword(String email, String newPassword) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        String hql = "UPDATE employee SET password = :password WHERE email = :email";
        Query query = session.createQuery(hql);
        query.setParameter("password", newPassword);
        query.setParameter("email", email);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
    @Override
    public void deleteById(String id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        EmployeeEntity entity = session.get(EmployeeEntity.class, id);
        if (entity != null) {
            session.delete(entity);
        }
        session.getTransaction().commit();
        session.close();
    }


}

package org.icet.pos.dao.custom.impl;

import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.icet.pos.dao.custom.CustomerDao;
import org.icet.pos.entity.CustomerEntity;
import org.icet.pos.util.HibernateUtil;

import java.util.List;

@Slf4j
public class CustomerDaoImpl implements CustomerDao {

    @Override
    public void persist(CustomerEntity entity) {
        try {
            Session session = HibernateUtil.getSession();
            session.getTransaction().begin();
            session.persist(entity);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            log.error("Exception: ", e);
        }
    }

    @Override
    public CustomerEntity findById(String id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        CustomerEntity entity = session.get(CustomerEntity.class, id);
        session.getTransaction().commit();
        session.close();
        return entity;
    }

    @Override
    public List<CustomerEntity> findAll() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        String hql = "FROM customer"; // HQL to select all records from CustomerEntity table
        Query query = session.createQuery(hql, CustomerEntity.class);
        List<CustomerEntity> customerEntities = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return customerEntities;
    }

    @Override
    public void update(CustomerEntity entity) {
        try {
            Session session = HibernateUtil.getSession();
            session.getTransaction().begin();
            session.update(entity);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            log.error("Exception: ", e);
        }
    }

    @Override
    public void delete(CustomerEntity entity) {
        try {
            Session session = HibernateUtil.getSession();
            session.getTransaction().begin();
            session.delete(entity);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            log.error("Exception: ", e);
        }
    }
}

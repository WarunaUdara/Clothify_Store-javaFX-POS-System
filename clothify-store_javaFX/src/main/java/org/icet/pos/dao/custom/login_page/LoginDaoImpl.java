package org.icet.pos.dao.custom.login_page;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.icet.pos.entity.EmployeeEntity;
import org.icet.pos.util.HibernateUtil;

import java.util.List;

public class LoginDaoImpl implements LoginDao {


    @Override
    public void persist(EmployeeEntity entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public EmployeeEntity findById(Integer id) {
        return null;
    }

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

    }

    @Override
    public void delete(EmployeeEntity entity) {

    }
}

package org.icet.pos.dao.custom.customer;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.icet.pos.entity.CustomerEntity;
import org.icet.pos.entity.EmployeeEntity;
import org.icet.pos.model.EmployeeModel;
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
            log.error("Exception : ",e);
        }

    }

    @Override
    public CustomerEntity findById(Integer id) {
        return null;
    }

    @Override
    public List<CustomerEntity> findAll() {
        return null;
    }

    @Override
    public void update(CustomerEntity entity) {

    }

    @Override
    public void delete(CustomerEntity entity) {

    }
}

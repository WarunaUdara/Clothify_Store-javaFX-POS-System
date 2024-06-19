package org.icet.pos.dao.factory;

import org.icet.pos.dao.SuperDao;
import org.icet.pos.dao.custom.impl.CustomerDaoImpl;
import org.icet.pos.dao.custom.impl.EmployeeDaoImpl;

public class DaoFactory {
    private static DaoFactory instance;
    private DaoFactory(){}
    public static DaoFactory getInstance() {
        return instance!=null?instance:(instance=new DaoFactory());
    }
    public <T extends SuperDao>T getDao(DaoType daoType){
        switch (daoType){
            case CUSTOMER:return (T)  new CustomerDaoImpl();
            case ITEM:;
            case EMPLOYEE:return (T) new EmployeeDaoImpl();

        }
        return null;

    }
}
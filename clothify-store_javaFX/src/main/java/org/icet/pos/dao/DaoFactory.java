package org.icet.pos.dao;

import org.icet.pos.dao.custom.customer.CustomerDaoImpl;
import org.icet.pos.dao.custom.login_page.LoginDaoImpl;
import org.icet.pos.util.DaoType;

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
            case LOGIN:return (T) new LoginDaoImpl();
        }
        return null;

    }
}
package org.icet.pos.bo.factory;

import org.icet.pos.bo.SuperBo;
import org.icet.pos.bo.custom.impl.AdminDashBoardBoImpl;
import org.icet.pos.bo.custom.impl.CustomerBoImpl;
import org.icet.pos.bo.custom.impl.LoginBoImpl;

public class BoFactory {

    private static BoFactory instance;
    private BoFactory(){}
    public static BoFactory getInstance() {
        return instance!=null?instance:(instance=new BoFactory());
    }
    public <T extends SuperBo>T getBo(BoType boType){
        switch (boType){
            case CUSTOMER: return (T) new CustomerBoImpl();
            case ITEM:return null;
            case LOGIN:return (T)new LoginBoImpl();
            case ADMIN_DASHBOARD:return (T) new AdminDashBoardBoImpl();
        }
        return null;
    }
}
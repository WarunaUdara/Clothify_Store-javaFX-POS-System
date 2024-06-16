package org.icet.pos.bo;

import org.icet.pos.bo.custom.customer.CustomerBoImpl;
import org.icet.pos.bo.custom.login_page.LoginControllerImpl;
import org.icet.pos.controller.LoginFormController;
import org.icet.pos.util.BoType;

public class BoFactory {

    private static BoFactory instance;
    private BoFactory(){}
    public static BoFactory getInstance() {
        return instance!=null?instance:(instance=new BoFactory());
    }
    public <T extends SuperBo>T getBo(BoType boType){
        switch (boType){
            case CUSTOMER: return (T) new CustomerBoImpl();
            case ITEM:
            case LOGIN:return (T)new LoginControllerImpl();
        }
        return null;
    }
}
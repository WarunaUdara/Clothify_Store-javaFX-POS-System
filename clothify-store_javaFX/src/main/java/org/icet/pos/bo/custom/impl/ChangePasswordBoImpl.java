package org.icet.pos.bo.custom.impl;

import org.icet.pos.bo.custom.ChangePasswordBo;
import org.icet.pos.dao.custom.impl.EmployeeDaoImpl;
import org.icet.pos.dao.factory.DaoFactory;
import org.icet.pos.dao.factory.DaoType;

public class ChangePasswordBoImpl implements ChangePasswordBo {
    EmployeeDaoImpl dao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);

    @Override
    public void changePassword(String email, String newPassword) {
        dao.updatePassword(email, newPassword);
    }
}

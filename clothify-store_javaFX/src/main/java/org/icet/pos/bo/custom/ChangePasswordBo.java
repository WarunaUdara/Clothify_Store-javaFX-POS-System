package org.icet.pos.bo.custom;

import org.icet.pos.bo.SuperBo;

public interface ChangePasswordBo extends SuperBo {
    void changePassword(String email, String newPassword);
}

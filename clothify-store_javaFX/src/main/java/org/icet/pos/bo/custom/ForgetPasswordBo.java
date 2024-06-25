package org.icet.pos.bo.custom;

import org.icet.pos.bo.SuperBo;

public interface ForgetPasswordBo extends SuperBo {
    String generateOtp();
}

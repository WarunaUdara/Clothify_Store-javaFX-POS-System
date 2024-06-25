package org.icet.pos.bo.custom.impl;

import org.icet.pos.bo.custom.ForgetPasswordBo;

import java.security.SecureRandom;

public class ForgetPasswordBoImpl implements ForgetPasswordBo {
    private static final SecureRandom random = new SecureRandom();

    @Override
    public String generateOtp() {
        int otp = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        return String.valueOf(otp);
    }

}

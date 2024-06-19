package org.icet.pos.bo.custom;

import org.icet.pos.bo.SuperBo;

public interface AdminDashBoardBo extends SuperBo {
    String getNewEmployeeId();
    String generateNewEmployeeId(String empId);

}

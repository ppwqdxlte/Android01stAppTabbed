package com.laowang.android01stapptabbed.service;

import com.laowang.android01stapptabbed.entity.Result;
import com.laowang.android01stapptabbed.entity.TokenUser;
import com.laowang.android01stapptabbed.util.SQLServerConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Management {

    private Management() {
    }

    private static final Management INSTANCE = new Management();

    public static Management getInstance() {
        return INSTANCE;
    }

    /**
     * modify token user's password
     *
     * @param username token system username
     * @param oldPwd   token system password
     * @param newPwd01 new password
     * @param newPwd02 repeated new password
     * @return result of changing password
     */
    public Result<TokenUser> changePassword(String username, String oldPwd, String newPwd01, String newPwd02) {
        Result<TokenUser> result = Login.getInstance().submit(username, oldPwd);
        if (result.getData() == null) {
            result.setMsg("Wrong username or password, fail to change password!!");
            return result;
        }
        if (newPwd01 == null || newPwd01.trim().length() == 0 || !newPwd01.equals(newPwd02)) {
            result.setData(null);
            result.setMsg("The new password is incorrect,please re-enter!");
            return result;
        }
        Connection connection = SQLServerConnector.getConnectionOfThisProject();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement("UPDATE OperatorList SET Password=? WHERE Op_Name=?");
            ps.setString(1, newPwd01);
            ps.setString(2, username);
            int i = ps.executeUpdate();
            if (i > 0) {
                result.getData().setPassword(newPwd01);
                result.setMsg("Password successfully modified!!!");
            } else {
                result.setData(null);
                result.setMsg("Password modification failed due to unknown reason of database!");
            }
        } catch (SQLException e) {
            result.setData(null);
            result.setMsg(e.getMessage());
            result.setThrowable(e);
        } finally {
            SQLServerConnector.closeAll(connection, ps, rs);
        }
        return result;
    }
}

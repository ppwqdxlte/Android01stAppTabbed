package com.laowang.android01stapptabbed.service;

import com.laowang.android01stapptabbed.entity.Result;
import com.laowang.android01stapptabbed.entity.TokenUser;
import com.laowang.android01stapptabbed.util.SQLServerConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    private Login() {
    }

    private static final Login INSTANCE = new Login();

    public static Login getInstance() {
        return INSTANCE;
    }

    /** Sign in token-browser system
     * @param username Token browser system username
     * @param password Token browser system password
     * @return token system user result
     */
    public Result<TokenUser> submit(String username, String password) {
        Connection connection = SQLServerConnector.getConnectionOfThisProject();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Result<TokenUser> result = new Result<>();
//        String strCommandText = String.format("select * from OperatorList where Op_Name=%s and Password=%s",username,password);
        try {
            ps = connection.prepareStatement("select * from OperatorList where Op_Name=? and Password=?");
            ps.setString(1,username);
            ps.setString(2,password);
            rs = ps.executeQuery();
            if (rs.wasNull()){
                result.setMsg("Wrong username or password!!");
            }
            while (rs.next()){
                TokenUser tokenUser = new TokenUser();
                tokenUser.setUsername(rs.getString(1));
                tokenUser.setPassword(rs.getString(2));
                tokenUser.setLevel(rs.getString(3));
                tokenUser.setDate(rs.getDate(4));
                result.setData(tokenUser);
                result.setMsg("There is indeed such a person.Login succeeded!");
            }
        } catch (SQLException e) {
            result.setMsg(e.getMessage());
            result.setThrowable(e);
        } finally {
            SQLServerConnector.closeAll(connection,ps,rs);
        }
        return result;
    }
    /**
     * checking the user's login status
     * @param username system username
     * @return login status true-sign in false-sign out
     */
    public Boolean checkLoginStatus(String username){
        return false;
    }
}

package com.laowang.android01stapptabbed.util;

import com.laowang.android01stapptabbed.config.SqlServerDatabase;

import java.sql.*;

public class SQLServerConnector {

    /**
     * @param driverName eg. "com.microsoft.sqlserver.jdbc.SQLServerDriver"
     * @param dburl      eg. "jdbc:sqlserver://localhost:1433;DatabaseName = student"
     * @param dbUserID   eg. "sa";// database user-name
     * @param dbUserPwd    eg. "root";// database user's password
     * @return object of Connection
     */
    private static Connection getConnection(String driverName, String dburl, String dbUserID, String dbUserPwd) {
        try {
            Class.forName(driverName);
            System.out.println("Success to load driver!!!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Fail to load driver...");
        }
        Connection dbcon = null;
        try {
            dbcon = DriverManager.getConnection(dburl, dbUserID, dbUserPwd);
            System.out.println("Successfully connect to SQL-Server!!!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed connection...");
        }
        return dbcon;
    }

    public static Connection getConnectionOfThisProject(){
        return getConnection(SqlServerDatabase.DRIVER_NAME,SqlServerDatabase.DB_URL,
                SqlServerDatabase.DB_USER_ID,SqlServerDatabase.DB_USER_PWD);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closePrepareStatement(PreparedStatement ps){
        if (ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet rs){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeAll(Connection conn,PreparedStatement ps,ResultSet rs){
        closeResultSet(rs);
        closePrepareStatement(ps);
        closeConnection(conn);
    }

}

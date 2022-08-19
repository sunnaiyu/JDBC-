package com.jsoft.util;


import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class JDBCUtil {

    public static void close(Connection conn, Statement stmt, ResultSet rs) {

        if(Objects.nonNull(stmt)) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(Objects.nonNull(conn)){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(Objects.nonNull(rs)){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 关闭链接的方法
     */
    public static void close(Connection conn, Statement stmt) {

        if(Objects.nonNull(stmt)) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(Objects.nonNull(conn)){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获取JDBC连接的方法
     * @return
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
        Properties properties = new Properties();
        properties.load(JDBCUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
        // 去jdbc.properties中取出属性名为mysql.url对应的值
        String url = properties.getProperty("mysql.url");
        String driverName = properties.getProperty("mysql.driverName");
        String username = properties.getProperty("mysql.username");
        String password = properties.getProperty("mysql.password");
        Class.forName(driverName);

        return DriverManager.getConnection(url, username, password);
    }

}

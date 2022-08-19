package com.jsoft.morning;

import com.jsoft.util.JDBCUtil;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 Statement的不足：
 1、大量的字符串拼接，代码可读性降低。
 2、sql注入

 SQL注入：BUG
     通过字符串的拼接，可以得到一个恒等的sql语句，可以跳过某些判断。
 */
public class Ch01 {

    public static void main(String[] args) {
        login("zxcvzxcvzxcv","b' or '1' = '1");
    }


    public static void login(String username,String password) {
        Scanner sc = new Scanner(System.in);
        // 1.获取连接
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
//            System.out.println("请输入用户名：");
//            String username = sc.next();
//            System.out.println("请输入密码：");
//            String password = sc.next();

            String sql = "select * from user where username = '"
                    + username + "' and password = '" +  password + "'";
//            StringBuilder strb = new StringBuilder("select * from user where username = ");
//            strb.append("'").append(username).append("'").append(" and password = '")
//                            .append(password).append("'");
//            String sql = strb.toString();
            System.out.println("sql：" + sql);
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                System.out.println("登录成功，欢迎回来：" + username);
            }else {
                System.out.println("账号或密码错误！");
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn,stmt,rs);
        }
    }

}

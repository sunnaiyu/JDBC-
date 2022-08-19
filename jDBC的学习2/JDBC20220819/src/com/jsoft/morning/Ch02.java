package com.jsoft.morning;

import com.jsoft.util.JDBCUtil;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PreparedStatement：预编译（预加载）接口
 * 1、通过conn获取的对象
 * 2、是Statement接口的子接口
 * 3、sql语句中可以传参。用?占位，通过setXXX方法来给?赋值
 * 4、提高性能
 * 5、避免sql注入
 */
public class Ch02 {

    @Test
    public void test03() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "select * from user where username = ? and password = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"aaa");
            pstmt.setString(2,"b' or '1' = '1");

            rs = pstmt.executeQuery();
            if(rs.next()) {
                System.out.println("登录成功...");
            }else {
                System.out.println("账号或密码错误...");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn,pstmt,rs);
        }
    }

    @Test
    public void test02() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "select * from scores where s_id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,7);

            rs = pstmt.executeQuery();
            while(rs.next()) {
                int sId = rs.getInt("s_id");
                int score = rs.getInt("score");
                int cId = rs.getInt("c_id");
                System.out.println("学号：" + sId + "，分数：" + score + "，科目号：" + cId);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn,pstmt,rs);
        }
    }

    @Test
    public void test01() {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = JDBCUtil.getConnection();
            String sql = "update teacher set name = ? where id = ?";
            // 预编译
            pstmt = conn.prepareStatement(sql);
            // 给占位符赋值，根据位置
            pstmt.setString(1,"JJ");
            pstmt.setInt(2,6);

            // 正式执行sql
            int i = pstmt.executeUpdate();
            System.out.println(i);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn,pstmt);
        }

    }
}

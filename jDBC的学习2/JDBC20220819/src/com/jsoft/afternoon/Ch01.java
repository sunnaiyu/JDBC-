package com.jsoft.afternoon;

import com.jsoft.util.JDBCUtil;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;

public class Ch01 {

    @Test
    public void test03() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "select * from user";

            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            // 获取元数据
            /*
                元数据：表格本身的数据
                    表格的列名，结果集的列名
             */
            ResultSetMetaData metaData = rs.getMetaData();
//            System.out.println(metaData.getColumnName(1));
//            System.out.println(metaData.getColumnName(2));
//            System.out.println(metaData.getColumnCount());
//            metaData.get
            for (int i = 1; i <= metaData.getColumnCount() ; i++) {
                metaData.getColumnName(i);
            }


            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                System.out.println("id：" + id + "，username：" + username + "，password：" + password);
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

}

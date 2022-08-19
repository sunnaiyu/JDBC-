package com.jsoft.afternoon;

import com.jsoft.util.JDBCUtil;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
  数据库事务：是数据库的特性
  
  Mysql的数据库引擎
  1.在MySQL中，只有使用了Innodb引擎的数据库才支持事务
  2.事务处理可以用来维护数据的完整性。保证sql语句要么全部执行，
       要么全部不执行。
  3.发生在DML中，增删改。
  
  事务的四大特征ACID
  1、原子性 A。
   一个事务，要么全部完成，要么全部不完成。
  2、一致性 C。
   在事务开始之前和事务结束之后，数据库的完整性没有被破坏。
  3、隔离性 Isolation
   数据库允许多个事务同时对数据进行处理。每个事务之间是相互隔离。
  4、持久性 D
   事务结束以后，对数据的增删改是永久性的。
  
   术语：提交事务，回滚事务（事务回滚）
  
   1、事务一旦提交，就不可能回滚。
   2、当一个连接对象被创建时，默认情况下自动提交事务。
   3、关闭连接时，数据会自动提交事务。
  
   操作事务的步骤：
   1、关闭事务的自动提交
  
   当做出增删改操作，把变化发生在内存中，提交事务，才会真正提交给数据库。
 *
 */
public class Ch02 {

    @Test
    public void test01() {
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;

        try {
            conn = JDBCUtil.getConnection();
            // 关闭事务的自动提交
            // true：开启（默认） false：关闭
            // 开启一个事务
            conn.setAutoCommit(false);
            // 把id为1的账户余额-1000
            String sql1 = "update bank set balance = balance - 1000 where id = 1";
            pstmt1 = conn.prepareStatement(sql1);
            pstmt1.executeUpdate();

            String sql2 = "update bank set balance = balance + 1000 where id = 2";
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.executeUpdate();

            int i = 10 / 0;

            // 提交事务
            conn.commit();

            System.out.println("转账成功...");

        } catch (Exception e) {
            try {
                // 事务回滚
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn,pstmt1);
            JDBCUtil.close(null,pstmt2);
        }
    }

}

package com.jsoft.afternoon.test;

import com.jsoft.util.JDBCUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDao {

    private final Connection conn;
    {
        try {
            conn = JDBCUtil.getConnection();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转账
     */
    public Integer transform(String out,String in,Double balance){
        // 取款之前要先查询
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        double b = 0;

        String sql = "select balance from bank where accountid = ?";

        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,out);

            rs = preparedStatement.executeQuery();
            while(rs.next()) {
                b = rs.getDouble("balance");
            }

            if(b >= balance) {
                // 余额够
                // 执行修改
                conn.setAutoCommit(false);
                sql = "update bank set balance = balance - ? where accountid = ?";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setDouble(1,balance);
                preparedStatement.setString((int)2,out);
                int i = preparedStatement.executeUpdate();

                sql = "update bank set balance = balance + ? where accountid = ?";
                preparedStatement2 = conn.prepareStatement(sql);
                preparedStatement2.setDouble(1,balance);
                preparedStatement2.setString((int)2,in);
                i = preparedStatement2.executeUpdate();

                conn.commit();

                return i;

            }else{
                // 余额不够
                throw new RuntimeException("余额不足，转账失败");
            }

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn,preparedStatement,rs);
            JDBCUtil.close(null,preparedStatement2);
        }
    }

    /**
     * 取款
     */
    public Integer out(String accountid,Double balance) {
        // 取款之前要先查询
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        double b = 0;

        String sql = "select balance from bank where accountid = ?";

        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,accountid);

            rs = preparedStatement.executeQuery();
            while(rs.next()) {
                b = rs.getDouble("balance");
            }

            if(b >= balance) {
                // 余额够
                // 执行修改
                sql = "update bank set balance = balance - ? where accountid = ?";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setDouble(1,balance);
                preparedStatement.setString((int)2,accountid);

                int i = preparedStatement.executeUpdate();
                return i;

            }else{
                // 余额不够
                throw new RuntimeException("余额不足，取款失败");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn,preparedStatement,rs);
        }
    }

    /**
     * 存款
     * @param accountid
     * @param balance
     * @return
     */
    public Integer in(String accountid,Double balance) {
        int i = 0;

        String sql = "update bank set balance = ? where accountid = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDouble(1,balance);
            preparedStatement.setString(2,accountid);

            i = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn,preparedStatement);
        }

        return i;
    }

    /**
     * 开户
     * @param accountid
     * @param balance
     * @return
     */
    public Integer add(String accountid,Double balance) {

        int i = 0;

        String sql = "insert into bank (accountid,balance) values (?,?)";
        PreparedStatement preparedStatement = null;
        try {
             preparedStatement = conn.prepareStatement(sql);
             preparedStatement.setString(1,accountid);
             preparedStatement.setDouble(2,balance);

            i = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn,preparedStatement);
        }

        return i;
    }

}

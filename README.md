# JDBC的学习

Statement的不足：

1. 大量的字符串拼接，代码可读性降低。
2. sql注入

SQL注入：BUG通过字符串的拼接，可以得到一个恒等的sql语句，可以跳过某些判断。如 `login("zxcvzxcvzxcv","b' or '1' = '1");`

## PreparedStatement

预编译（预加载）接口

1. 通过conn获取的对象
2. 是Statement接口的子接口
3. sql语句中可以传参。用?占位，通过setXXX方法来给?赋值
4. 提高性能
5. 避免sql注入

```java
 @Test
    public void test03() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "select * from user where username = ? and password = ?";//使用？占位，后面再给？赋值
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
```

## 数据库事务

数据库事务：是数据库的特性

Mysql的数据库引擎

1. 在MySQL中，只有使用了Innodb引擎的数据库才支持事务
2. 事务处理可以用来维护数据的完整性。保证sql语句要么全部执行，要么全部不执行。

  3. 发生在DML中，增删改。

### 事务的四大特征ACID

1. 原子性 A。
      一个事务，要么全部完成，要么全部不完成。
2. 一致性 C。
      在事务开始之前和事务结束之后，数据库的完整性没有被破坏。
3. 隔离性 Isolation
      数据库允许多个事务同时对数据进行处理。每个事务之间是相互隔离。
4. 持久性 D
      事务结束以后，对数据的增删改是永久性的。

   术语：提交事务，回滚事务（事务回滚）

注意：

1. 事务一旦提交，就不可能回滚。

2. 当一个连接对象被创建时，默认情况下自动提交事务。

3. 关闭连接时，数据会自动提交事务。

操作事务的步骤：

1、关闭事务的自动提交:当做出增删改操作，把变化发生在内存中，提交事务，才会真正提交给数据库。

```java
@Test
    public void test01() {
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        try {
            conn = JDBCUtil.getConnection();//工具类创建连接对象
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
                // 出现异常事务回滚
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

```


package com.jsoft.afternoon.test;


import org.junit.Test;

/**
 * 1、创建一张银行信息表
 *      字段：主键 银行卡号，余额......
 * 2、封装方法，存款，取款，转账，所有的操作最终要数据持久化。
 * 3、查询余额的方法。
 * 4、开户、修改密码。
 */
public class Demo {

    private AccountDao accountDao = new AccountDao();

    @Test
    public void test01() {
//        System.out.println(accountDao.out("1102345678", 2000.00));
        accountDao.transform("1102345678","1209876543",10000.00);

    }

}

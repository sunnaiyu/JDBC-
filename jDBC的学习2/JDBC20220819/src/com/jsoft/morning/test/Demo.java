package com.jsoft.morning.test;

import org.junit.Test;

import java.util.List;

public class Demo {

    /**
     * 需求：查询学生姓名，分数，科目名
     *
     *      利用面向对象的思想
     *
     * 得到一个集合。
     * 所有的查询和拼装集合的操作都在Dao类中去做
     * 我们在Demo这个类中只做测试。
     *
     */

    StudentScoreCourseDao dao = new StudentScoreCourseDao();

    @Test
    public void test01() {
        // 查询学生姓名，成绩，科目名
//        System.out.println(dao.getAll());
//        System.out.println(dao.getById(2));
//        System.out.println(dao.getBySidAndCid(2, 2));
//        System.out.println(dao.getCount(1));
        System.out.println(dao.getOrderScore());
    }

}

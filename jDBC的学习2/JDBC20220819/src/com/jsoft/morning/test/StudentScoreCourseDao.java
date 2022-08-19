package com.jsoft.morning.test;

import com.jsoft.util.JDBCUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentScoreCourseDao {

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
     * 查询的方法
     * 查询学生姓名，分数，科目名
     */
    public List<StudentScoreCourse> getAll() {
        List<StudentScoreCourse> stus = new ArrayList<>(16);
        String sql = "select s.name sname,r.score,c.name cname " +
                "from student s " +
                "left join scores r on s.id = r.s_id " +
                "left join course c on  c.id = r.c_id";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                String sname = rs.getString("sname");
                int score = rs.getInt("score");
                String cname = rs.getString("cname");
                StudentScoreCourse ssc = new StudentScoreCourse(sname,score,cname);
                stus.add(ssc);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn,pstmt,rs);
        }

        return stus;
    }

    /**
     * 查询某一个学生的分数
     */
    public List<StudentScoreCourse> getById(Integer sid) {
        List<StudentScoreCourse> stus = new ArrayList<>(16);
        String sql = "select s.name sname,r.score,c.name cname " +
                "from student s " +
                "left join scores r on s.id = r.s_id " +
                "left join course c on c.id = r.c_id where s_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,sid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                String sname = rs.getString("sname");
                int score = rs.getInt("score");
                String cname = rs.getString("cname");
                StudentScoreCourse ssc = new StudentScoreCourse(sname,score,cname);
                stus.add(ssc);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn,pstmt,rs);
        }

        return stus;
    }


    /**
     * 查询某一个学生的某一科的分数
     */
    public StudentScoreCourse getBySidAndCid(Integer sid,Integer cid) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        StudentScoreCourse ssc = null;
        String sql = "select s.name sname,r.score,c.name cname " +
                "from student s " +
                "left join scores r on s.id = r.s_id " +
                "left join course c on  c.id = r.c_id where s_id = ? and c_id = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,sid);
            pstmt.setInt(2,cid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                String sname = rs.getString("sname");
                int score = rs.getInt("score");
                String cname = rs.getString("cname");
                ssc = new StudentScoreCourse(sname,score,cname);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(conn,pstmt,rs);
        }

        return ssc;
    }

    /**
     * 查询某一个学生的总分
     */
    public Integer getCount(Integer id) {

        Integer score = 0;

        String sql = "select sum(score) score " +
                "from scores  " +
                "GROUP BY s_id HAVING s_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                score = rs.getInt("score");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtil.close(conn,pstmt,rs);
        }

        return score;
    }

    /**
     * 查询某一个学生的平均分
     */

    /**
     * 按照总分排序
     */
    public List<Integer> getOrderScore() {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Integer> scores = new ArrayList<>();

        String sql = "select sum(score) score " +
                "from scores  " +
                "GROUP BY s_id ORDER BY score desc,s_id asc";

        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                int score = rs.getInt("score");
                scores.add(score);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return scores;
    }

}

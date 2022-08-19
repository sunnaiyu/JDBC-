package com.jsoft.morning.test;

public class StudentScoreCourse {

    private String sname;
    private Integer score;
    private String cname;

    public StudentScoreCourse() {
    }

    public StudentScoreCourse(String sname, Integer score, String cname) {
        this.sname = sname;
        this.score = score;
        this.cname = cname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "StudentScoreCourse{" +
                "sname='" + sname + '\'' +
                ", score=" + score +
                ", cname='" + cname + '\'' +
                '}';
    }
}

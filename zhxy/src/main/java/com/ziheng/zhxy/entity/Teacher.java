package com.ziheng.zhxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName teacher
 */
@TableName(value ="teacher")
public class Teacher extends BaseEntity implements Serializable {
    /**
     * 老师id

     */
    @TableId(type = IdType.AUTO)
    private Integer tId;

    /**
     * 老师名称
     */
    private String tName;

    /**
     * 教学科目
     */
    private String course;

    /**
     * 用户id
     */
    private Integer accountId;

    /**
     * 入职时间
     */
    private Date entryTime;

    /**
     * 学历
     */
    private String degree;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 老师id

     */
    public Integer gettId() {
        return tId;
    }

    /**
     * 老师id

     */
    public void settId(Integer tId) {
        this.tId = tId;
    }

    /**
     * 老师名称
     */
    public String gettName() {
        return tName;
    }

    /**
     * 老师名称
     */
    public void settName(String tName) {
        this.tName = tName;
    }

    /**
     * 教学科目
     */
    public String getCourse() {
        return course;
    }

    /**
     * 教学科目
     */
    public void setCourse(String course) {
        this.course = course;
    }

    /**
     * 用户id
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 用户id
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 入职时间
     */
    public Date getEntryTime() {
        return entryTime;
    }

    /**
     * 入职时间
     */
    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    /**
     * 学历
     */
    public String getDegree() {
        return degree;
    }

    /**
     * 学历
     */
    public void setDegree(String degree) {
        this.degree = degree;
    }



    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Teacher other = (Teacher) that;
        return (this.gettId() == null ? other.gettId() == null : this.gettId().equals(other.gettId()))
            && (this.gettName() == null ? other.gettName() == null : this.gettName().equals(other.gettName()))
            && (this.getCourse() == null ? other.getCourse() == null : this.getCourse().equals(other.getCourse()))
            && (this.getAccountId() == null ? other.getAccountId() == null : this.getAccountId().equals(other.getAccountId()))
            && (this.getEntryTime() == null ? other.getEntryTime() == null : this.getEntryTime().equals(other.getEntryTime()))
            && (this.getDegree() == null ? other.getDegree() == null : this.getDegree().equals(other.getDegree()));

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gettId() == null) ? 0 : gettId().hashCode());
        result = prime * result + ((gettName() == null) ? 0 : gettName().hashCode());
        result = prime * result + ((getCourse() == null) ? 0 : getCourse().hashCode());
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getEntryTime() == null) ? 0 : getEntryTime().hashCode());
        result = prime * result + ((getDegree() == null) ? 0 : getDegree().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", tId=").append(tId);
        sb.append(", tName=").append(tName);
        sb.append(", course=").append(course);
        sb.append(", accountId=").append(accountId);
        sb.append(", entryTime=").append(entryTime);
        sb.append(", degree=").append(degree);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
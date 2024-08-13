package com.ziheng.zhxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName student
 */
@TableName(value ="student")
public class Student extends BaseEntity implements Serializable {
    /**
     * 学生id
     */
    @TableId(type = IdType.AUTO)
    private Integer sid;

    /**
     * 学生名称
     */
    private String sName;

    /**
     * 所属用户id
     */
    private Integer userId;

    /**
     * 所属班级id
     */
    private Integer classId;

    /**
     * 所属年级id
     */
    private Integer gradeId;

    /**
     * 入学时间
     */
    private Date enrollmentDate;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 学生id
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * 学生id
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * 学生名称
     */
    public String getsName() {
        return sName;
    }

    /**
     * 学生名称
     */
    public void setsName(String sName) {
        this.sName = sName;
    }

    /**
     * 所属用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 所属用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 所属班级id
     */
    public Integer getClassId() {
        return classId;
    }

    /**
     * 所属班级id
     */
    public void setClassId(Integer clsaaId) {
        this.classId = clsaaId;
    }

    /**
     * 所属年级id
     */
    public Integer getGradeId() {
        return gradeId;
    }

    /**
     * 所属年级id
     */
    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    /**
     * 入学时间
     */
    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    /**
     * 入学时间
     */
    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
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
        Student other = (Student) that;
        return (this.getSid() == null ? other.getSid() == null : this.getSid().equals(other.getSid()))
            && (this.getsName() == null ? other.getsName() == null : this.getsName().equals(other.getsName()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getClassId() == null ? other.getClassId() == null : this.getClassId().equals(other.getClassId()))
            && (this.getGradeId() == null ? other.getGradeId() == null : this.getGradeId().equals(other.getGradeId()))
            && (this.getEnrollmentDate() == null ? other.getEnrollmentDate() == null : this.getEnrollmentDate().equals(other.getEnrollmentDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSid() == null) ? 0 : getSid().hashCode());
        result = prime * result + ((getsName() == null) ? 0 : getsName().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getClassId() == null) ? 0 : getClassId().hashCode());
        result = prime * result + ((getGradeId() == null) ? 0 : getGradeId().hashCode());
        result = prime * result + ((getEnrollmentDate() == null) ? 0 : getEnrollmentDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sid=").append(sid);
        sb.append(", sName=").append(sName);
        sb.append(", userId=").append(userId);
        sb.append(", clsaaId=").append(classId);
        sb.append(", gradeId=").append(gradeId);
        sb.append(", enrollmentDate=").append(enrollmentDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
package com.ziheng.zhxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName t_class
 */
@TableName(value ="t_class")
public class TClass implements Serializable {
    /**
     * 班级id
     */
    @TableId(type = IdType.AUTO)
    private Integer cid;

    /**
     * 年级id 外键
     */
    private Integer gid;

    /**
     * 班主任id 外键
     */
    private Integer headTeacherId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 学期
     */
    private String classTerm;

    /**
     * 日志-创建人
     */
    private String createUser;

    /**
     * 日志-创建时间
     */
    private Date createTime;

    /**
     * 日志-修改人
     */
    private String modifiedUser;

    /**
     * 日志-修改时间
     */
    private Date modifiedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 班级id
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * 班级id
     */
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * 年级id 外键
     */
    public Integer getGid() {
        return gid;
    }

    /**
     * 年级id 外键
     */
    public void setGid(Integer gid) {
        this.gid = gid;
    }

    /**
     * 班主任id 外键
     */
    public Integer getHeadTeacherId() {
        return headTeacherId;
    }

    /**
     * 班主任id 外键
     */
    public void setHeadTeacherId(Integer headTeacherId) {
        this.headTeacherId = headTeacherId;
    }

    /**
     * 班级名称
     */
    public String getClassName() {
        return className;
    }

    /**
     * 班级名称
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 学期
     */
    public String getClassTerm() {
        return classTerm;
    }

    /**
     * 学期
     */
    public void setClassTerm(String classTerm) {
        this.classTerm = classTerm;
    }

    /**
     * 日志-创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 日志-创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 日志-创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 日志-创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 日志-修改人
     */
    public String getModifiedUser() {
        return modifiedUser;
    }

    /**
     * 日志-修改人
     */
    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    /**
     * 日志-修改时间
     */
    public Date getModifiedTime() {
        return modifiedTime;
    }

    /**
     * 日志-修改时间
     */
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
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
        TClass other = (TClass) that;
        return (this.getCid() == null ? other.getCid() == null : this.getCid().equals(other.getCid()))
            && (this.getGid() == null ? other.getGid() == null : this.getGid().equals(other.getGid()))
            && (this.getHeadTeacherId() == null ? other.getHeadTeacherId() == null : this.getHeadTeacherId().equals(other.getHeadTeacherId()))
            && (this.getClassName() == null ? other.getClassName() == null : this.getClassName().equals(other.getClassName()))
            && (this.getClassTerm() == null ? other.getClassTerm() == null : this.getClassTerm().equals(other.getClassTerm()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getModifiedUser() == null ? other.getModifiedUser() == null : this.getModifiedUser().equals(other.getModifiedUser()))
            && (this.getModifiedTime() == null ? other.getModifiedTime() == null : this.getModifiedTime().equals(other.getModifiedTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCid() == null) ? 0 : getCid().hashCode());
        result = prime * result + ((getGid() == null) ? 0 : getGid().hashCode());
        result = prime * result + ((getHeadTeacherId() == null) ? 0 : getHeadTeacherId().hashCode());
        result = prime * result + ((getClassName() == null) ? 0 : getClassName().hashCode());
        result = prime * result + ((getClassTerm() == null) ? 0 : getClassTerm().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getModifiedUser() == null) ? 0 : getModifiedUser().hashCode());
        result = prime * result + ((getModifiedTime() == null) ? 0 : getModifiedTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cid=").append(cid);
        sb.append(", gid=").append(gid);
        sb.append(", headTeacherId=").append(headTeacherId);
        sb.append(", className=").append(className);
        sb.append(", classTerm=").append(classTerm);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifiedUser=").append(modifiedUser);
        sb.append(", modifiedTime=").append(modifiedTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
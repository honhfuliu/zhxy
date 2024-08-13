package com.ziheng.zhxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName grade
 */
@TableName(value ="grade")
public class Grade extends BaseEntity implements Serializable{
    /**
     * 年级id
     */
    @TableId(type = IdType.AUTO)
    private Integer gid;

    /**
     * 年级名称
     */
    private String gName;

    /**
     * 年级开始
     */

    private Date startYear;

    /**
     * 年级结束
     */

    private Date endYear;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 年级id
     */
    public Integer getGid() {
        return gid;
    }

    /**
     * 年级id
     */
    public void setGid(Integer gid) {
        this.gid = gid;
    }

    /**
     * 年级名称
     */
    public String getgName() {
        return gName;
    }

    /**
     * 年级名称
     */
    public void setgName(String gName) {
        this.gName = gName;
    }

    /**
     * 年级开始
     */
    public Date getStartYear() {
        return startYear;
    }

    /**
     * 年级开始
     */
    public void setStartYear(Date startYear) {
        this.startYear = startYear;
    }

    /**
     * 年级结束
     */
    public Date getEndYear() {
        return endYear;
    }

    /**
     * 年级结束
     */
    public void setEndYear(Date endYear) {
        this.endYear = endYear;
    }

    /**
     * 是否删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 是否删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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
        Grade other = (Grade) that;
        return (this.getGid() == null ? other.getGid() == null : this.getGid().equals(other.getGid()))
            && (this.getgName() == null ? other.getgName() == null : this.getgName().equals(other.getgName()))
            && (this.getStartYear() == null ? other.getStartYear() == null : this.getStartYear().equals(other.getStartYear()))
            && (this.getEndYear() == null ? other.getEndYear() == null : this.getEndYear().equals(other.getEndYear()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGid() == null) ? 0 : getGid().hashCode());
        result = prime * result + ((getgName() == null) ? 0 : getgName().hashCode());
        result = prime * result + ((getStartYear() == null) ? 0 : getStartYear().hashCode());
        result = prime * result + ((getEndYear() == null) ? 0 : getEndYear().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", gid=").append(gid);
        sb.append(", gName=").append(gName);
        sb.append(", startYear=").append(startYear);
        sb.append(", endYear=").append(endYear);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
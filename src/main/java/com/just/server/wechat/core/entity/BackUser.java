package com.just.server.wechat.core.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: wangkx
 * Date: 2018/08/13
 * Time: 14:19
 * Description: 后台用户表
 */
@Entity
@Table(name="back_user")
public class BackUser implements Serializable {

    @Id
    @Column(name="id")
    /**
     * 用户ID
     */
    private Long id;


    @Column(name="userId")
    /**
     * UUID
     */
    private String userId;


    @Column(name="loginName")
    /**
     * 登录名
     */
    private String loginName;


    @Column(name="password")
    /**
     * 密码
     */
    private String password;


    @Column(name="salt")
    /**
     * 扰码
     */
    private String salt;


    @Column(name="name")
    /**
     * 姓名
     */
    private String name;


    @Column(name="createDate")
    /**
     * 添加日期
     */
    private Date createDate;


    @Column(name="lastLoginDate")
    /**
     * 上次登录日期
     */
    private Date lastLoginDate;


    @Column(name="status")
    /**
     * 1：正常，0：未激活，-1：禁用
     */
    private Integer status;


    @Column(name="remark")
    /**
     * 备注
     */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

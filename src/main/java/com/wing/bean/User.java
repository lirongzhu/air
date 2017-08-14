package com.wing.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

import com.wing.common.enums.EnableStatus;
import com.wing.common.enums.UserType;

/**
 * 用户分为加盟商和俱乐部，都在这张表里。
 * 俱乐部身兼加盟商身份，注意区分。
 *
 * @author Hijiao created on 2016-2-19.
 */
@Entity
@Table(name = "crm_user", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    public User(){

    }

    @Id
    @TableGenerator(name = "tg_user", table = "tg_id_table", pkColumnName = "table_name", pkColumnValue = "crm_user", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_user")
    private Integer id;
    
    /**
     * 登录名
     */

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "password", length = 128)
    private String password;
    
    /**
     * 联系人员姓名
     */
    @Column(name = "contact_name", length = 20)
    private String contactName;

    /**
     * 联系方式
     */
    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    /**
     * 创建日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  
    @Column(name = "create_date", nullable = false, length = 19)
    private Date createDate;
    
    /**
     */
    @Column(name = "user_type", length = 1)
    private UserType userType;

    /**
     */
    @Column(name = "user_status", length = 1)
    private EnableStatus userStatus;

    /**
     * 备注
     */
    @Lob
    private String remark;
    
    @Transient
    private String permissions;
    
    @Column(name = "f_salt", length = 32)
    private String salt;

    @Transient
    private String rawPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

	/**
	 * @return the userType
	 */
	public UserType getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	/**
	 * @return the permissions
	 */
	public String getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		if(getUserType() == UserType.系统管理员){
			return "admin";
		}else if(getUserType() == UserType.普通用户){
			return "normal";
		}else if(getUserType() == UserType.路线审核员){
			return "auditor";
		}else if(getUserType() == UserType.资讯管理员){
			return "information";
		}else{
			return "";
		}
	}

	/**
	 * @return the userStatus
	 */
	public EnableStatus getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(EnableStatus userStatus) {
		this.userStatus = userStatus;
	}
}

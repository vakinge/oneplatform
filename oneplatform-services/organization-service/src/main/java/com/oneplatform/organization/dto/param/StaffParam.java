package com.oneplatform.organization.dto.param;

import java.util.Date;

/**
 * 
 * <br>
 * Class Name : StaffParam
 */
public class StaffParam {

	private String id;
	private String code;
	private String name;
	private String gender;
	private Date birthday;
	private String idNo;
	private String email;
	private String mobile;
	private String employType;
	private Date entryDate;
	private String departmentId;
	private String postionId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmployType() {
		return employType;
	}

	public void setEmployType(String employType) {
		this.employType = employType;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getPostionId() {
		return postionId;
	}

	public void setPostionId(String postionId) {
		this.postionId = postionId;
	}
	
	

}
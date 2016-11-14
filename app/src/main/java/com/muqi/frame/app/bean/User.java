package com.muqi.frame.app.bean;

import com.google.gson.Gson;

import org.json.JSONObject;

public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String im_user_name;
	private String first_name;
	private String last_name;
	private String nickname;
	private String email;
	private String password;
	private String registerDate;
	private String lastvisitDate;
	private String token;
	private String mobile;
	private Integer language_id;
	private String language_name;
	private String sex;
	private String birthday;
	private String introduce;
	private String avatar;
	private String avatar_thumb500;
	private String avatar_thumb200;

	private float account;//账户余额
	private float account_pay;//支出统计
	private float account_income;//收入统计
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getIm_user_name() {
		return im_user_name;
	}
	public void setIm_user_name(String im_user_name) {
		this.im_user_name = im_user_name;
	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	
	public String getLastvisitDate() {
		return lastvisitDate;
	}
	public void setLastvisitDate(String lastvisitDate) {
		this.lastvisitDate = lastvisitDate;
	}

	public Integer getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(Integer language_id) {
		this.language_id = language_id;
	}

	public String getLanguage_name() {
		return language_name;
	}
	public void setLanguage_name(String language_name) {
		this.language_name = language_name;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public String getAvatar_thumb500() {
		return avatar_thumb500;
	}
	public void setAvatar_thumb500(String avatar_thumb500) {
		this.avatar_thumb500 = avatar_thumb500;
	}
	
	public String getAvatar_thumb200() {
		return avatar_thumb200;
	}
	public void setAvatar_thumb200(String avatar_thumb200) {
		this.avatar_thumb200 = avatar_thumb200;
	}

	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public float getAccount() {
		return account;
	}
	public void setAccount(float account) {
		this.account = account;
	}
	public float getAccount_pay() {
		return account_pay;
	}
	public void setAccount_pay(float account_pay) {
		this.account_pay = account_pay;
	}
	public float getAccount_income() {
		return account_income;
	}
	public void setAccount_income(float account_income) {
		this.account_income = account_income;
	}

	public static User fromJson(String json) {
		try {
			JSONObject resultJson = new JSONObject(json.toString());
			if(resultJson != null){
				return new Gson().fromJson(resultJson.getString("data"),User.class);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}

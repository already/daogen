package com.kifanle.test.pojo;

import java.util.List;
import java.util.Date;

/**
 *
 * @author microboss
 * @since 2013-05-04
 */
public class UserinfoDO{

	/** 序列化ID */
	private static final long serialVersionUID = 1L;

	/** 用户ID **/
    private Long userid;
	/** 用户登录名 **/
    private String loginname;
	/** 邮箱 **/
    private String email;
	/** 电话 **/
    private String mobile;
	/** 登录密码 **/
    private String password;
	/** 昵称 **/
    private String nickname;
	/** 性别0未明示 1男 2女 **/
    private Byte gender;
	/** 心情签名 **/
    private String mood;
	/** 职业 **/
    private String career;
	/** 兴趣爱好 **/
    private String interest;
	/** 用户头像地址 **/
    private String avatarUrl;
	/** 地址位置，格式为39.11,116.11 **/
    private String latlng;
	/** 注册日期 **/
    private Date createTime;
	/** 修改日期 **/
    private Date modifiedTime;

	
   /**
    * 获取属性:userid
    * 用户ID
    * @return userid
    */
   public Long getUserid() {
       return userid;
   }
   /**
    * 设置属性:userid
    * 用户ID
    * @param userid
    */
   public void setUserid(Long userid) {
       this.userid = userid;
   }
	
   /**
    * 获取属性:loginname
    * 用户登录名
    * @return loginname
    */
   public String getLoginname() {
       return loginname;
   }
   /**
    * 设置属性:loginname
    * 用户登录名
    * @param loginname
    */
   public void setLoginname(String loginname) {
       this.loginname = loginname;
   }
	
   /**
    * 获取属性:email
    * 邮箱
    * @return email
    */
   public String getEmail() {
       return email;
   }
   /**
    * 设置属性:email
    * 邮箱
    * @param email
    */
   public void setEmail(String email) {
       this.email = email;
   }
	
   /**
    * 获取属性:mobile
    * 电话
    * @return mobile
    */
   public String getMobile() {
       return mobile;
   }
   /**
    * 设置属性:mobile
    * 电话
    * @param mobile
    */
   public void setMobile(String mobile) {
       this.mobile = mobile;
   }
	
   /**
    * 获取属性:password
    * 登录密码
    * @return password
    */
   public String getPassword() {
       return password;
   }
   /**
    * 设置属性:password
    * 登录密码
    * @param password
    */
   public void setPassword(String password) {
       this.password = password;
   }
	
   /**
    * 获取属性:nickname
    * 昵称
    * @return nickname
    */
   public String getNickname() {
       return nickname;
   }
   /**
    * 设置属性:nickname
    * 昵称
    * @param nickname
    */
   public void setNickname(String nickname) {
       this.nickname = nickname;
   }
	
   /**
    * 获取属性:gender
    * 性别0未明示 1男 2女
    * @return gender
    */
   public Byte getGender() {
       return gender;
   }
   /**
    * 设置属性:gender
    * 性别0未明示 1男 2女
    * @param gender
    */
   public void setGender(Byte gender) {
       this.gender = gender;
   }
	
   /**
    * 获取属性:mood
    * 心情签名
    * @return mood
    */
   public String getMood() {
       return mood;
   }
   /**
    * 设置属性:mood
    * 心情签名
    * @param mood
    */
   public void setMood(String mood) {
       this.mood = mood;
   }
	
   /**
    * 获取属性:career
    * 职业
    * @return career
    */
   public String getCareer() {
       return career;
   }
   /**
    * 设置属性:career
    * 职业
    * @param career
    */
   public void setCareer(String career) {
       this.career = career;
   }
	
   /**
    * 获取属性:interest
    * 兴趣爱好
    * @return interest
    */
   public String getInterest() {
       return interest;
   }
   /**
    * 设置属性:interest
    * 兴趣爱好
    * @param interest
    */
   public void setInterest(String interest) {
       this.interest = interest;
   }
	
   /**
    * 获取属性:avatarUrl
    * 用户头像地址
    * @return avatarUrl
    */
   public String getAvatarUrl() {
       return avatarUrl;
   }
   /**
    * 设置属性:avatarUrl
    * 用户头像地址
    * @param avatarUrl
    */
   public void setAvatarUrl(String avatarUrl) {
       this.avatarUrl = avatarUrl;
   }
	
   /**
    * 获取属性:latlng
    * 地址位置，格式为39.11,116.11
    * @return latlng
    */
   public String getLatlng() {
       return latlng;
   }
   /**
    * 设置属性:latlng
    * 地址位置，格式为39.11,116.11
    * @param latlng
    */
   public void setLatlng(String latlng) {
       this.latlng = latlng;
   }
	
   /**
    * 获取属性:createTime
    * 注册日期
    * @return createTime
    */
   public Date getCreateTime() {
       return createTime;
   }
   /**
    * 设置属性:createTime
    * 注册日期
    * @param createTime
    */
   public void setCreateTime(Date createTime) {
       this.createTime = createTime;
   }
	
   /**
    * 获取属性:modifiedTime
    * 修改日期
    * @return modifiedTime
    */
   public Date getModifiedTime() {
       return modifiedTime;
   }
   /**
    * 设置属性:modifiedTime
    * 修改日期
    * @param modifiedTime
    */
   public void setModifiedTime(Date modifiedTime) {
       this.modifiedTime = modifiedTime;
   }

	/**
     * 需要更新时的代码，keys 代表主键list
     */
	private List<Long> keys;
		
	public List<Long> getKeys() {
		return keys;
	}
	
	public void setIds(List<Long> keys) {
		this.keys = keys;
	}
}
package com.kifanle.test.query;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author microboss
 * @since 2013-05-04
 */
public class UserinfoQuery extends BaseQuery {

	/** 序列化ID */
	private static final long serialVersionUID = 1L;
	
	/** ====================查询唯一单条记录使用==========================**/
	
	/**==============================批量查询、更新、删除时的Where条件设置==================================**/
	/** 用户ID **/
    private Long userid;
	/**
    * 获取属性:userid
    * 用户ID
    * @return userid
    */
	public Long getUserid () {
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
	/** 用户登录名 **/
    private String loginname;
	/**
    * 获取属性:loginname
    * 用户登录名
    * @return loginname
    */
	public String getLoginname () {
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
	/** 邮箱 **/
    private String email;
	/**
    * 获取属性:email
    * 邮箱
    * @return email
    */
	public String getEmail () {
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
	/** 电话 **/
    private String mobile;
	/**
    * 获取属性:mobile
    * 电话
    * @return mobile
    */
	public String getMobile () {
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
	/** 登录密码 **/
    private String password;
	/**
    * 获取属性:password
    * 登录密码
    * @return password
    */
	public String getPassword () {
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
	/** 昵称 **/
    private String nickname;
	/**
    * 获取属性:nickname
    * 昵称
    * @return nickname
    */
	public String getNickname () {
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
	/** 心情签名 **/
    private String mood;
	/**
    * 获取属性:mood
    * 心情签名
    * @return mood
    */
	public String getMood () {
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
	/** 职业 **/
    private String career;
	/**
    * 获取属性:career
    * 职业
    * @return career
    */
	public String getCareer () {
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
	/** 兴趣爱好 **/
    private String interest;
	/**
    * 获取属性:interest
    * 兴趣爱好
    * @return interest
    */
	public String getInterest () {
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
	/** 用户头像地址 **/
    private String avatarUrl;
	/**
    * 获取属性:avatarUrl
    * 用户头像地址
    * @return avatarUrl
    */
	public String getAvatarUrl () {
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
	/** 地址位置，格式为39.11,116.11 **/
    private String latlng;
	/**
    * 获取属性:latlng
    * 地址位置，格式为39.11,116.11
    * @return latlng
    */
	public String getLatlng () {
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
	/**==============================批量查询时的Order条件顺序设置==================================**/
	public class OrderField{
		public OrderField(String fieldName, String order) {
			super();
			this.fieldName = fieldName;
			this.order = order;
		}
		private String fieldName;
		private String order;
		public String getFieldName() {
			return fieldName;
		}
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		public String getOrder() {
			return order;
		}
		public void setOrder(String order) {
			this.order = order;
		}
	}

	/**==============================批量查询时的Order条件顺序设置==================================**/
	/**排序列表字段**/
	private List<OrderField> orderFields = new ArrayList<OrderField>();
	/**
	 * 设置排序按属性：用户ID
	 * @param isAsc 是否升序，否则为降序
	 */	
	public void orderbyUserid(boolean isAsc){
		orderFields.add(new OrderField("userid",isAsc?"ASC":"DESC"));
	}
	/**
	 * 设置排序按属性：用户登录名
	 * @param isAsc 是否升序，否则为降序
	 */	
	public void orderbyLoginname(boolean isAsc){
		orderFields.add(new OrderField("loginname",isAsc?"ASC":"DESC"));
	}
	/**
	 * 设置排序按属性：邮箱
	 * @param isAsc 是否升序，否则为降序
	 */	
	public void orderbyEmail(boolean isAsc){
		orderFields.add(new OrderField("email",isAsc?"ASC":"DESC"));
	}
	/**
	 * 设置排序按属性：电话
	 * @param isAsc 是否升序，否则为降序
	 */	
	public void orderbyMobile(boolean isAsc){
		orderFields.add(new OrderField("mobile",isAsc?"ASC":"DESC"));
	}
	/**
	 * 设置排序按属性：登录密码
	 * @param isAsc 是否升序，否则为降序
	 */	
	public void orderbyPassword(boolean isAsc){
		orderFields.add(new OrderField("password",isAsc?"ASC":"DESC"));
	}
	/**
	 * 设置排序按属性：昵称
	 * @param isAsc 是否升序，否则为降序
	 */	
	public void orderbyNickname(boolean isAsc){
		orderFields.add(new OrderField("nickname",isAsc?"ASC":"DESC"));
	}
	/**
	 * 设置排序按属性：性别0未明示 1男 2女
	 * @param isAsc 是否升序，否则为降序
	 */	
	public void orderbyGender(boolean isAsc){
		orderFields.add(new OrderField("gender",isAsc?"ASC":"DESC"));
	}
	/**
	 * 设置排序按属性：心情签名
	 * @param isAsc 是否升序，否则为降序
	 */	
	public void orderbyMood(boolean isAsc){
		orderFields.add(new OrderField("mood",isAsc?"ASC":"DESC"));
	}
	/**
	 * 设置排序按属性：职业
	 * @param isAsc 是否升序，否则为降序
	 */	
	public void orderbyCareer(boolean isAsc){
		orderFields.add(new OrderField("career",isAsc?"ASC":"DESC"));
	}
	/**
	 * 设置排序按属性：兴趣爱好
	 * @param isAsc 是否升序，否则为降序
	 */	
	public void orderbyInterest(boolean isAsc){
		orderFields.add(new OrderField("interest",isAsc?"ASC":"DESC"));
	}
	/**
	 * 设置排序按属性：用户头像地址
	 * @param isAsc 是否升序，否则为降序
	 */	
	public void orderbyAvatarUrl(boolean isAsc){
		orderFields.add(new OrderField("avatar_url",isAsc?"ASC":"DESC"));
	}
	/**
	 * 设置排序按属性：地址位置，格式为39.11,116.11
	 * @param isAsc 是否升序，否则为降序
	 */	
	public void orderbyLatlng(boolean isAsc){
		orderFields.add(new OrderField("latlng",isAsc?"ASC":"DESC"));
	}
	/**
	 * 设置排序按属性：注册日期
	 * @param isAsc 是否升序，否则为降序
	 */	
	public void orderbyCreateTime(boolean isAsc){
		orderFields.add(new OrderField("create_time",isAsc?"ASC":"DESC"));
	}
	/**
	 * 设置排序按属性：修改日期
	 * @param isAsc 是否升序，否则为降序
	 */	
	public void orderbyModifiedTime(boolean isAsc){
		orderFields.add(new OrderField("modified_time",isAsc?"ASC":"DESC"));
	}
}

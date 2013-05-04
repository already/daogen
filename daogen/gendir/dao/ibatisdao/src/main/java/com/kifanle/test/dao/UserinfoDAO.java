package com.kifanle.test.dao;

import java.util.List;

import javax.annotation.Resource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

import org.springframework.stereotype.Repository;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.kifanle.test.pojo.UserinfoDO;
import java.util.Collections;
import com.kifanle.test.common.Result;
import com.kifanle.test.query.UserinfoQuery;
/**
 * @author microboss
 * @since 2013-05-04
 */
@Repository
public class UserinfoDAO {
	
	@Resource
	SqlMapClient sqlMapClientTemplate;
	
	public Long addUserinfoDO(UserinfoDO userinfo) throws SQLException{
		return (Long)this.sqlMapClientTemplate.insert("Userinfo.insertUserinfoDO", userinfo);
	}
	
	public UserinfoDO getUserinfoDObyKey(long userid) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		UserinfoDO result = (UserinfoDO) this.sqlMapClientTemplate.queryForObject(
				"Userinfo.getUserinfoDO", params);
		return result;
	}
	
    public List<UserinfoDO> getUserinfoDOsByKeys(List<Long> userids) throws SQLException {
		if (userids.isEmpty()) {
			return Collections.emptyList();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keys", userids);
		return  (List<UserinfoDO>)this.sqlMapClientTemplate.queryForList("Userinfo.getUserinfoDOsByKeys", params);
	}	
	
	public Integer deleteByKey(Long userid) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("key", userid);
		Integer row = (Integer) this.sqlMapClientTemplate.delete("Userinfo.deleteByKey", params);
		return row;
	}
	
    public Integer deleteByKeys(List<Long> userids) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keys", userids);
		Integer row = (Integer) this.sqlMapClientTemplate.delete("Userinfo.deleteByKeys", params);
		return row;
	}
	
    public Integer updateUserinfoDO(UserinfoDO userinfo) throws SQLException{
		return (Integer) this.sqlMapClientTemplate.update("Userinfo.updateUserinfoDO", userinfo);
	}
	
	/**
	 * 成批更新对象，需要设置对象中的keys。
	 */
    public Integer updateUserinfoDOsByKeys(UserinfoDO userinfo) throws SQLException{
		return (Integer) sqlMapClientTemplate.update("Userinfo.updateUserinfoDOsByKeys", userinfo);
	}

    			
	 public UserinfoDO getUserInfoByEmail(String mobile,Byte gender) throws SQLException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", mobile);
		params.put("gender", gender);
		UserinfoDO result = (UserinfoDO) this.sqlMapClientTemplate.queryForObject("Userinfo.getUserInfoByEmail", params);
		return result;
	}	
					
		@SuppressWarnings("unchecked")
    public Result<UserinfoDO> getUserinfoDOListWithPage(UserinfoQuery userinfoQuery){
	    Result<UserinfoDO> rs = new Result<UserinfoDO>(); 
		try{		
			rs.setCount((Integer) this.sqlMapClientTemplate.queryForObject("Userinfo.getUserinfoListCount",userinfoQuery));
			rs.setList((List<UserinfoDO>)this.sqlMapClientTemplate.queryForList("Userinfo.getUserinfoListWithPage", userinfoQuery));
		}catch(SQLException e){
			rs.setSuccess(false);
			rs.setCount(0);
			rs.setList(Collections.EMPTY_LIST);
			rs.setErrorMsg(e.toString());
		}
		return rs;
	}
    
}
